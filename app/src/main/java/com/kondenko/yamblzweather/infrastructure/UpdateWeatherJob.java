package com.kondenko.yamblzweather.infrastructure;


import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.UpdateWeatherInteractor;

import javax.inject.Inject;

class UpdateWeatherJob extends Job {

    // Do not delete, needed for job creation
    static final String TAG = "UpdateWeatherJob";

    private final GetCurrentCityInteractor getCurrentCityInteractor;
    private UpdateWeatherInteractor updateWeatherInteractor;
    private SettingsManager settingsManager;
    private String units;

    @Inject
    UpdateWeatherJob(GetCurrentCityInteractor getCurrentCityInteractor, UpdateWeatherInteractor updateWeatherInteractor, SettingsManager settingsManager) {
        this.getCurrentCityInteractor = getCurrentCityInteractor;
        this.updateWeatherInteractor = updateWeatherInteractor;
        this.settingsManager = settingsManager;
        units = settingsManager.getUnitKey();
    }

    static void schedulePeriodicJob(long refreshRateMs) {
        new JobRequest.Builder(UpdateWeatherJob.TAG)
                .setPeriodic(refreshRateMs)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
                .setUpdateCurrent(true)
                .setPersisted(true)
                .build()
                .schedule();
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        getCurrentCityInteractor.run().flatMapCompletable(updateWeatherInteractor::run);
        return Result.SUCCESS;

    }

}
