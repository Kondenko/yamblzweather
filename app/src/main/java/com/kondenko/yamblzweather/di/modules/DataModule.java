package com.kondenko.yamblzweather.di.modules;

import com.kondenko.yamblzweather.data.suggest.CitiesSuggestService;
import com.kondenko.yamblzweather.data.suggest.GooglePlacesCitySuggestProvider;
import com.kondenko.yamblzweather.data.weather.OpenWeatherWeatherProvider;
import com.kondenko.yamblzweather.data.weather.WeatherService;
import com.kondenko.yamblzweather.domain.guards.CitySuggestProvider;
import com.kondenko.yamblzweather.domain.guards.WeatherProvider;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

import static com.kondenko.yamblzweather.di.modules.NetModule.GOOGLE_SUGGESTS_API;
import static com.kondenko.yamblzweather.di.modules.NetModule.OPEN_WEATHER_MAP_API;

/**
 * Created by Mishkun on 05.08.2017.
 */
@Singleton
@Module
public abstract class DataModule {
    @Provides
    @Singleton
    static CitiesSuggestService provideCitiesSuggestService(@Named(GOOGLE_SUGGESTS_API) Retrofit retrofit) {
        return retrofit.create(CitiesSuggestService.class);
    }

    @Provides
    @Singleton
    static WeatherService provideWeatherService(@Named(OPEN_WEATHER_MAP_API) Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }

    @Binds
    @Singleton
    abstract CitySuggestProvider provideCitySuggestProvider(GooglePlacesCitySuggestProvider googlePlacesCitySuggestProvider);

    @Binds
    @Singleton
    abstract WeatherProvider provideWeatherProvider(OpenWeatherWeatherProvider openWeatherWeatherProvider);
}
