package com.kondenko.yamblzweather.ui.weather;


import android.support.v4.util.Pair;

import com.kondenko.yamblzweather.domain.entity.Weather;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentWeatherInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetFavoredCitiesInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetForecastInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetUnitsInteractor;
import com.kondenko.yamblzweather.domain.usecase.SetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.UpdateWeatherInteractor;
import com.kondenko.yamblzweather.ui.BasePresenter;

import java.util.concurrent.CancellationException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

@Singleton
public class WeatherPresenter extends BasePresenter<WeatherView> {

    @SuppressWarnings("unused") private static final String TAG = WeatherPresenter.class.getSimpleName();
    private final GetCurrentWeatherInteractor currentWeatherInteractor;
    private final GetForecastInteractor getForecastInteractor;
    private final UpdateWeatherInteractor updateWeatherInteractor;
    private final SetCurrentCityInteractor setCurrentCityInteractor;
    private final GetCurrentCityInteractor getCurrentCityInteractor;
    private final GetFavoredCitiesInteractor getFavoredCitiesInteractor;
    private final GetUnitsInteractor getUnitsInteractor;

    private boolean initialized = false;

    private WeatherViewModel weatherViewModel;

    @Inject
    WeatherPresenter(GetCurrentWeatherInteractor currentWeatherInteractor, GetForecastInteractor getForecastInteractor,
                     UpdateWeatherInteractor updateWeatherInteractor,
                     SetCurrentCityInteractor setCurrentCityInteractor, GetCurrentCityInteractor getCurrentCityInteractor,
                     GetFavoredCitiesInteractor getFavoredCitiesInteractor, GetUnitsInteractor getUnitsInteractor) {
        this.currentWeatherInteractor = currentWeatherInteractor;
        this.getForecastInteractor = getForecastInteractor;
        this.updateWeatherInteractor = updateWeatherInteractor;
        this.setCurrentCityInteractor = setCurrentCityInteractor;
        this.getCurrentCityInteractor = getCurrentCityInteractor;
        this.getFavoredCitiesInteractor = getFavoredCitiesInteractor;
        this.getUnitsInteractor = getUnitsInteractor;
    }

    @Override
    public void attachView(WeatherView view) {
        super.attachView(view);
        Observable.combineLatest(getCurrentCityInteractor.run(),
                                 currentWeatherInteractor.run(),
                                 Pair::new)
                  .compose(bindToLifecycle())
                  .doOnNext(pair -> {
                      if (!initialized && isViewAttached()) {
                          updateWeatherInteractor.run(pair.first).onErrorComplete().subscribe();
                      }
                  })
                  .flatMapMaybe(pair -> Maybe.zip(getFavoredCitiesInteractor.run().toMaybe(),
                                                  getForecastInteractor.run(),
                                                  getUnitsInteractor.run().toMaybe(),
                                                  (cities, forecast, unit) -> WeatherViewModel
                                                      .create(pair.second, forecast,
                                                              pair.first,
                                                              cities, unit)).onErrorComplete())
                  .doAfterNext(ignore -> {
                      if (!initialized && isViewAttached()) {
                          subscribeCitySelections(getView());
                          initialized = true;
                      }
                  })
                  .subscribe(result -> {
                      if (isViewAttached()) {
                          weatherViewModel = result;
                          getView().setData(result);
                      }
                  });
    }

    private void subscribeCitySelections(WeatherView view) {
        view.getCitySelections()
            .compose(bindToLifecycle())
            .flatMapCompletable(setCurrentCityInteractor::run)
            .subscribe();
    }

    @Override
    public void detachView() {
        super.detachView();
        initialized = false;
    }

    void updateData() {
        if (isViewAttached()) getView().showLoading(true);
        getCurrentCityInteractor.run()
                                .firstElement()
                                .compose(bindToLifecycle())
                                .flatMapCompletable(updateWeatherInteractor::run)
                                .doFinally(() -> {
                                    if (isViewAttached()) getView().showLoading(false);
                                })
                                .doOnError(error -> {
                                    if (isViewAttached()) getView().showError(error);
                                })
                                .onErrorResumeNext(error -> {
                                    if (error instanceof CancellationException) return Completable.never();
                                    return Completable.error(error);
                                })
                                .onErrorComplete()
                                .subscribe();
    }

    void onSelected(Weather weather, boolean isSelected) {
        if (weatherViewModel != null) {
            getView().setData(WeatherViewModel.create(isSelected ? weather : weatherViewModel.weather(),
                                                      weatherViewModel.forecast(),
                                                      weatherViewModel.city(),
                                                      weatherViewModel.cities(),
                                                      weatherViewModel.tempUnit()));
        }
    }
}
