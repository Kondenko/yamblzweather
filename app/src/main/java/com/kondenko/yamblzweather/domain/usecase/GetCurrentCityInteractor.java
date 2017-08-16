package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.di.Job;
import com.kondenko.yamblzweather.di.Ui;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by Mishkun on 05.08.2017.
 */

public class GetCurrentCityInteractor {
    private final LocationProvider locationProvider;
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;

    @Inject
    GetCurrentCityInteractor(LocationProvider locationProvider, @Job Scheduler jobScheduler, @Ui Scheduler uiScheduler) {
        this.locationProvider = locationProvider;
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
    }

    public Observable<City> run() {
        return locationProvider.getCurrentCity()
                               .subscribeOn(jobScheduler)
                               .observeOn(uiScheduler);
    }
}
