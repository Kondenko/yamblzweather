package com.kondenko.yamblzweather;

import android.app.Activity;
import android.app.Application;
import android.content.ContentProvider;

import com.evernote.android.job.BuildConfig;
import com.evernote.android.job.JobManager;
import com.facebook.stetho.Stetho;
import com.kondenko.yamblzweather.di.AppModule;
import com.kondenko.yamblzweather.di.DaggerAppComponent;
import com.kondenko.yamblzweather.domain.guards.JobsScheduler;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentWeatherInteractor;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasContentProviderInjector;


public class App extends Application implements HasActivityInjector, HasContentProviderInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Inject
    DispatchingAndroidInjector<ContentProvider> dispatchingAndroidInjector;

    @Inject
    JobsScheduler weatherJobsScheduler;

    @Inject
    SettingsManager settingsManager;

    @Inject
    public GetCurrentWeatherInteractor getCurrentWeatherInteractor;

    @Inject
    public GetCurrentCityInteractor getCurrentCityInteractor;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) return;
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
            Stetho.initializeWithDefaults(this);
        }
        JobManager.create(this);
        DaggerAppComponent.builder()
                          .application(this)
                          .appModule(new AppModule(this))
                          .build()
                          .inject(this);
        weatherJobsScheduler.scheduleUpdateJob(settingsManager.getRefreshRateHr());
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

    @Override
    public AndroidInjector<ContentProvider> contentProviderInjector() {
        return dispatchingAndroidInjector;
    }
}
