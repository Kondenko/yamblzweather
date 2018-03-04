package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.di.Job;
import com.kondenko.yamblzweather.di.Ui;
import com.kondenko.yamblzweather.domain.entity.TempUnit;
import com.kondenko.yamblzweather.domain.guards.TemperatureUnitProvider;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;

/**
 * Created by Mishkun on 10.08.2017.
 */

public class GetUnitsInteractor {
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;
    private final TemperatureUnitProvider temperatureUnitProvider;

    @Inject
    GetUnitsInteractor(@Job Scheduler jobScheduler, @Ui Scheduler uiScheduler, TemperatureUnitProvider temperatureUnitProvider) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
        this.temperatureUnitProvider = temperatureUnitProvider;
    }

    public Single<TempUnit> run() {
        return Single.fromCallable(temperatureUnitProvider::getUnitKey)
                     .subscribeOn(jobScheduler)
                     .observeOn(uiScheduler);
    }
}
