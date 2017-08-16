package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.di.Job;
import com.kondenko.yamblzweather.di.Ui;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

/**
 * Created by Mishkun on 04.08.2017.
 */

public class SetCurrentCityInteractor {
    private final LocationProvider locationProvider;
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;

    @Inject
    SetCurrentCityInteractor(LocationProvider locationProvider, @Job Scheduler jobScheduler, @Ui Scheduler uiScheduler) {
        this.locationProvider = locationProvider;
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
    }

    public Completable run(City city) {
        return locationProvider.setCurrentCity(city)
                               .subscribeOn(jobScheduler)
                               .observeOn(uiScheduler);
    }
}
