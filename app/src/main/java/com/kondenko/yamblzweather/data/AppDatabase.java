package com.kondenko.yamblzweather.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.kondenko.yamblzweather.data.location.CityDao;
import com.kondenko.yamblzweather.data.location.CityEntity;
import com.kondenko.yamblzweather.data.weather.ForecastDao;
import com.kondenko.yamblzweather.data.weather.ForecastEntity;
import com.kondenko.yamblzweather.data.weather.WeatherDao;
import com.kondenko.yamblzweather.data.weather.WeatherEntity;
import com.kondenko.yamblzweather.data.weather.WeatherForecastEntity;

/**
 * Created by Mishkun on 05.08.2017.
 */
@Database(entities = {ForecastEntity.class, WeatherEntity.class, CityEntity.class, WeatherForecastEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CityDao cityDao();

    public abstract ForecastDao forecastDao();

    public abstract WeatherDao weatherDao();
}
