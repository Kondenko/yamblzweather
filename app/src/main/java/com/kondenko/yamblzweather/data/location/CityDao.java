package com.kondenko.yamblzweather.data.location;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Mishkun on 05.08.2017.
 */
@Dao
public interface CityDao {
    @Insert(onConflict = REPLACE)
    void insertCity(CityEntity cityEntity);

    @Delete
    void deleteCity(CityEntity cityEntity);

    @Query("UPDATE city SET selected = 0")
    void deselectAll();

    @Query("UPDATE city SET selected = 1 WHERE id = :place_id")
    void setSelectedCity(String place_id);

    @Query("SELECT * FROM city WHERE selected = 1 LIMIT 1")
    Flowable<CityEntity> getSelectedCity();

    @Query("SELECT * FROM city")
    Single<List<CityEntity>> getCities();
}
