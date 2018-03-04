package com.kondenko.yamblzweather.data.weather;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Maybe;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Mishkun on 05.08.2017.
 */
@Dao
public interface ForecastDao {
    @Insert(onConflict = REPLACE)
    void insertAllForecasts(ForecastEntity... entities);

    @Insert(onConflict = REPLACE)
    void insertAllWeatherForecasts(List<WeatherForecastEntity> entities);

    @Query("SELECT * FROM forecast WHERE city = (SELECT id FROM city WHERE selected = 1 LIMIT 1) LIMIT 1")
    Maybe<ForecastEntity> getForecastEntity();

    @Query("SELECT * FROM weather_forecast WHERE forecast = :forecastId")
    Maybe<List<WeatherForecastEntity>> getWeatherForecasts(String forecastId);
}
