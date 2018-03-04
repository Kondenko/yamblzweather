package com.kondenko.yamblzweather.di;

import com.kondenko.yamblzweather.domain.guards.JobsScheduler;
import com.kondenko.yamblzweather.domain.guards.TemperatureUnitProvider;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;
import com.kondenko.yamblzweather.infrastructure.WeatherJobsScheduler;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Mishkun on 13.08.2017.
 */
@Module
abstract class InfrastructureModule {
    @Binds
    @Singleton
    abstract TemperatureUnitProvider provideTemperatureUnitsProvider(SettingsManager settingsManager);

    @Binds
    @Singleton
    abstract JobsScheduler provideJobsRepository(WeatherJobsScheduler weatherJobsScheduler);

}
