package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.BaseInteractor;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

import static com.kondenko.yamblzweather.Const.JOB;
import static com.kondenko.yamblzweather.Const.UI;

/**
 * Created by Mishkun on 04.08.2017.
 */

public class SetCurrentCityInteractor extends BaseInteractor {
    private final LocationProvider locationProvider;
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;

    @Inject
    SetCurrentCityInteractor(LocationProvider locationProvider, @Named(JOB) Scheduler jobScheduler, @Named(UI) Scheduler uiScheduler) {
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
