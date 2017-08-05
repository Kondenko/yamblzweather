package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;

import static com.kondenko.yamblzweather.Const.JOB;
import static com.kondenko.yamblzweather.Const.UI;

/**
 * Created by Mishkun on 05.08.2017.
 */

public class GetCurrentCityInteractor {
    private final LocationProvider locationProvider;
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;

    @Inject
    GetCurrentCityInteractor(LocationProvider locationProvider, @Named(JOB) Scheduler jobScheduler, @Named(UI) Scheduler uiScheduler) {
        this.locationProvider = locationProvider;
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
    }

    public Single<City> run() {
        return locationProvider.getCurrentCity()
                               .subscribeOn(jobScheduler)
                               .observeOn(uiScheduler);
    }
}
