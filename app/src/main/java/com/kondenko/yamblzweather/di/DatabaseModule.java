package com.kondenko.yamblzweather.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.kondenko.yamblzweather.data.AppDatabase;
import com.kondenko.yamblzweather.data.location.CityDao;
import com.kondenko.yamblzweather.data.weather.ForecastDao;
import com.kondenko.yamblzweather.data.weather.WeatherDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mishkun on 13.08.2017.
 */
@Module
class DatabaseModule {

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context,
                                    AppDatabase.class, "database-name").build();
    }

    @Provides
    CityDao provideCityDao(AppDatabase appDatabase) {
        return appDatabase.cityDao();
    }

    @Provides
    WeatherDao provideWeatherDao(AppDatabase appDatabase) {
        return appDatabase.weatherDao();
    }

    @Provides
    ForecastDao provideForecastDao(AppDatabase appDatabase) {
        return appDatabase.forecastDao();
    }
}
