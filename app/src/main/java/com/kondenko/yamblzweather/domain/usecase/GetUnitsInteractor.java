package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.di.Job;
import com.kondenko.yamblzweather.di.Ui;
import com.kondenko.yamblzweather.domain.entity.TempUnit;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;

/**
 * Created by Mishkun on 10.08.2017.
 */

public class GetUnitsInteractor {
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;
    private final SettingsManager settingsManager;

    @Inject
    GetUnitsInteractor(@Job Scheduler jobScheduler, @Ui Scheduler uiScheduler, SettingsManager settingsManager) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
        this.settingsManager = settingsManager;
    }

    public Single<TempUnit> run() {
        return Single.fromCallable(settingsManager::getUnitKey)
                     .subscribeOn(jobScheduler)
                     .observeOn(uiScheduler);
    }
}
