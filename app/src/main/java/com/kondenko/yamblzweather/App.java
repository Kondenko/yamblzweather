package com.kondenko.yamblzweather;

import android.app.Activity;
import android.app.Application;

import com.evernote.android.job.JobManager;
import com.facebook.stetho.Stetho;
import com.kondenko.yamblzweather.di.components.DaggerAppComponent;
import com.kondenko.yamblzweather.di.modules.AppModule;
import com.kondenko.yamblzweather.domain.guards.JobsScheduler;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;
    @Inject
    JobsScheduler weatherJobsScheduler;
    @Inject
    SettingsManager settingsManager;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) return;
        LeakCanary.install(this);
        JobManager.create(this);
        DaggerAppComponent.builder()
                          .application(this)
                          .appModule(new AppModule(this))
                          .build()
                          .inject(this);
        Stetho.initializeWithDefaults(this);
        weatherJobsScheduler.scheduleUpdateJob(settingsManager.getRefreshRateHr());
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

}
