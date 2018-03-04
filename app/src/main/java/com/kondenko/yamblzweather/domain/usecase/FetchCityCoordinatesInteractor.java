package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.di.Job;
import com.kondenko.yamblzweather.di.Ui;
import com.kondenko.yamblzweather.domain.entity.Prediction;
import com.kondenko.yamblzweather.domain.guards.CitySuggestProvider;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class FetchCityCoordinatesInteractor {
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;
    private final CitySuggestProvider citySuggestProvider;
    private final LocationProvider locationProvider;

    @Inject
    FetchCityCoordinatesInteractor(@Job Scheduler jobScheduler, @Ui Scheduler uiScheduler, CitySuggestProvider citiesSuggestProvider,
                                   LocationProvider locationProvider) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
        this.citySuggestProvider = citiesSuggestProvider;
        this.locationProvider = locationProvider;
    }

    public Completable run(Prediction prediction) {
        return citySuggestProvider.getCityFromPrediction(prediction)
                                  .flatMapCompletable(city -> Completable.concatArray(locationProvider.addFavoredCity(city),
                                                                                      locationProvider.setCurrentCity(city)))
                                  .subscribeOn(jobScheduler)
                                  .observeOn(uiScheduler);
    }

}
