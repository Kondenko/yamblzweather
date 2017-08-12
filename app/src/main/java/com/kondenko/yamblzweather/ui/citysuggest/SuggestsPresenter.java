package com.kondenko.yamblzweather.ui.citysuggest;

import com.kondenko.yamblzweather.domain.usecase.DeleteCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.FetchCityCoordsInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetCitySuggestsInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetFavoredCitiesInteractor;
import com.kondenko.yamblzweather.domain.usecase.SetCurrentCityInteractor;
import com.kondenko.yamblzweather.ui.BasePresenter;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Maybe;

/**
 * Created by Mishkun on 28.07.2017.
 */

public class SuggestsPresenter extends BasePresenter<SuggestsView> {
    private static final String TAG = SuggestsPresenter.class.getSimpleName();
    private final GetCitySuggestsInteractor getCitySuggestsInteractor;
    private final GetFavoredCitiesInteractor getFavoredCitiesInteractor;
    private final SetCurrentCityInteractor setCurrentCityInteractor;
    private final DeleteCityInteractor deleteCityInteractor;
    private final GetCurrentCityInteractor getCurrentCityInteractor;
    private final FetchCityCoordsInteractor fetchCityCoordsInteractor;

    @Inject
    SuggestsPresenter(GetCitySuggestsInteractor getCitySuggestsInteractor,
                      FetchCityCoordsInteractor fetchCityCoordsInteractor,
                      GetFavoredCitiesInteractor getFavoredCitiesInteractor,
                      SetCurrentCityInteractor setCurrentCityInteractor,
                      DeleteCityInteractor deleteCityInteractor,
                      GetCurrentCityInteractor getCurrentCityInteractor) {
        this.fetchCityCoordsInteractor = fetchCityCoordsInteractor;
        this.getCitySuggestsInteractor = getCitySuggestsInteractor;
        this.getFavoredCitiesInteractor = getFavoredCitiesInteractor;
        this.setCurrentCityInteractor = setCurrentCityInteractor;
        this.deleteCityInteractor = deleteCityInteractor;
        this.getCurrentCityInteractor = getCurrentCityInteractor;
    }

    @Override
    public void attachView(SuggestsView view) {
        super.attachView(view);
        view.getCityNamesStream()
            .filter(query -> !query.isEmpty())
            .compose(bindToLifecycle())
            .doOnNext((ignore) -> {
                if (isViewAttached()) getView().showLoading(true);
            })
            .flatMapSingle(getCitySuggestsInteractor::run)
            .map(SuggestsViewModel::createWithPredictions)
            .doOnEach((ignore) -> {
                if (isViewAttached()) getView().showLoading(false);
            })
            .doOnError(error -> {
                if (isViewAttached()) getView().showError(error);
            })
            .retryWhen((error) -> view.getCityNamesStream())
            .subscribe((result) -> {
                if (isViewAttached()) {
                    getView().setData(result);
                }
            });

        view.getCityNamesStream()
            .filter(String::isEmpty)
            .compose(bindToLifecycle())
            .flatMapSingle((ignore) -> getFavoredCitiesInteractor.run())
            .flatMapMaybe(cities -> getCurrentCityInteractor.run().firstElement().map(city -> SuggestsViewModel.createWithCities(cities, city)))
            .subscribe((result) -> {
                if (isViewAttached()) {
                    getView().setData(result);
                }
            });

        view.getCitiesClicks()
            .compose(bindToLifecycle())
            .flatMapCompletable(city -> setCurrentCityInteractor.run(city)
                                                                .doOnComplete(() -> {
                                                                    if (isViewAttached()) {
                                                                        getView().finishScreen();
                                                                    }
                                                                }))
            .subscribe();

        view.getCitiesDeletionsClicks()
            .compose(bindToLifecycle())
            .flatMapMaybe(city -> deleteCityInteractor.run(city)
                                                      .andThen(Maybe.zip(getFavoredCitiesInteractor.run().toMaybe(),
                                                                         getCurrentCityInteractor.run().firstElement(),
                                                                         SuggestsViewModel::createWithCities)))
            .subscribe((result) -> {
                if (isViewAttached()) {
                    getView().setData(result);
                }
            });

        view.getSuggestsClicks()
            .compose(bindToLifecycle())
            .flatMapCompletable(prediction -> fetchCityCoordsInteractor.run(prediction)
                                                                       .doOnComplete(() -> {
                                                                           if (isViewAttached()) {
                                                                               getView().finishScreen();
                                                                           }
                                                                       }))
            .doOnError(error -> {
                if (isViewAttached()) getView().showError(error);
            })
            .retryWhen((error) -> view.getSuggestsClicks().toFlowable(BackpressureStrategy.BUFFER))
            .subscribe();
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
