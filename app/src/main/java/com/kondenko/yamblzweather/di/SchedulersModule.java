package com.kondenko.yamblzweather.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Mishkun on 13.08.2017.
 */
@Module
class SchedulersModule {

    @Provides
    @Singleton
    @Job
    Scheduler provideBackgroundScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Singleton
    @Ui
    Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
