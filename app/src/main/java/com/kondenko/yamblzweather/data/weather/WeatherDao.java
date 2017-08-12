package com.kondenko.yamblzweather.data.weather;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Mishkun on 05.08.2017.
 */
@Dao
public interface WeatherDao {
    @Insert(onConflict = REPLACE)
    void insertAllWeather(WeatherEntity... entities);

    @Query("SELECT * FROM weather WHERE city = (SELECT id FROM city WHERE selected = 1 LIMIT 1) LIMIT 1")
    Flowable<WeatherEntity> getWeather();
}
