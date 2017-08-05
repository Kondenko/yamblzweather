package com.kondenko.yamblzweather.ui.citysuggest;

import com.kondenko.yamblzweather.domain.usecase.FetchCityCoordsInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetCitySuggestsInteractor;
import com.kondenko.yamblzweather.domain.usecase.SetCurrentCityInteractor;
import com.kondenko.yamblzweather.ui.BasePresenter;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;

/**
 * Created by Mishkun on 28.07.2017.
 */

public class SuggestsPresenter extends BasePresenter<SuggestsView> {
    private static final String TAG = SuggestsPresenter.class.getSimpleName();
    private final GetCitySuggestsInteractor getCitySuggestsInteractor;
    private final SetCurrentCityInteractor setCurrentCityInteractor;
    private final FetchCityCoordsInteractor fetchCityCoordsInteractor;

    @Inject
    SuggestsPresenter(GetCitySuggestsInteractor interactor, FetchCityCoordsInteractor fetchCityCoordsInteractor, SetCurrentCityInteractor setCurrentCityInteractor) {
        this.fetchCityCoordsInteractor = fetchCityCoordsInteractor;
        this.getCitySuggestsInteractor = interactor;
        this.setCurrentCityInteractor = setCurrentCityInteractor;
    }

    @Override
    public void attachView(SuggestsView view) {
        super.attachView(view);
        view.getCityNamesStream()
            .compose(bindToLifecycle())
            .doOnNext((ignore) -> {
                if (isViewAttached()) getView().showLoading(true);
            })
            .flatMapSingle(getCitySuggestsInteractor::run)
            .map(SuggestsViewModel::create)
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
                       }
            );
        view.getClicks()
            .compose(bindToLifecycle())
            .flatMapSingle(fetchCityCoordsInteractor::run)
            .flatMapCompletable(city -> setCurrentCityInteractor.run(city).doOnComplete(() -> {
                if (isViewAttached()) {
                    getView().finishScreen();
                }
            }))
            .doOnError(error -> {
                if (isViewAttached()) getView().showError(
                        error);
            })
            .retryWhen((error) -> view.getClicks().toFlowable(BackpressureStrategy.DROP))
            .subscribe();
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
