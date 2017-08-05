package com.kondenko.yamblzweather.infrastructure;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.UpdateWeatherInteractor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppJobCreator implements JobCreator {

    private final GetCurrentCityInteractor getCurrentCityInteractor;
    private UpdateWeatherInteractor updateWeatherInteractor;
    private SettingsManager settingsManager;

    @Inject
    public AppJobCreator(UpdateWeatherInteractor updateWeatherInteractor, GetCurrentCityInteractor getCurrentCityInteractor, SettingsManager settingsManager) {
        this.updateWeatherInteractor = updateWeatherInteractor;
        this.getCurrentCityInteractor = getCurrentCityInteractor;
        this.settingsManager = settingsManager;
    }

    @Override
    public Job create(String tag) {
        if (tag.equals(UpdateWeatherJob.TAG)) return new UpdateWeatherJob(getCurrentCityInteractor, updateWeatherInteractor, settingsManager);
        return null;
    }

}