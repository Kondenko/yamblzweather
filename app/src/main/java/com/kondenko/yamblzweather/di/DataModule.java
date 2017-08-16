package com.kondenko.yamblzweather.di;

import com.kondenko.yamblzweather.data.location.RoomLocationProvider;
import com.kondenko.yamblzweather.data.suggest.GooglePlacesCitySuggestProvider;
import com.kondenko.yamblzweather.data.weather.OpenWeatherWeatherProvider;
import com.kondenko.yamblzweather.domain.guards.CitySuggestProvider;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;
import com.kondenko.yamblzweather.domain.guards.WeatherProvider;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Mishkun on 05.08.2017.
 */
@Module
public abstract class DataModule {

    @Binds
    @Singleton
    abstract CitySuggestProvider provideCitySuggestProvider(GooglePlacesCitySuggestProvider googlePlacesCitySuggestProvider);

    @Binds
    @Singleton
    abstract WeatherProvider provideWeatherProvider(OpenWeatherWeatherProvider openWeatherWeatherProvider);

    @Binds
    @Singleton
    abstract LocationProvider provideLocationStore(RoomLocationProvider roomLocationProvider);
}
