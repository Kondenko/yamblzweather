package com.kondenko.yamblzweather.data.weather;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.kondenko.yamblzweather.data.location.CityEntity;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Mishkun on 05.08.2017.
 */
@Entity(tableName = "forecast",foreignKeys = @ForeignKey(entity = CityEntity.class, parentColumns = "id", childColumns = "city", onDelete = CASCADE), indices = @Index(value = "timestamp", unique = true))
public class ForecastEntity {
    @ColumnInfo(name = "timestamp")
    private long timestamp;
    @PrimaryKey
    @ColumnInfo(name = "city")
    private String place_id;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    String getPlace_id() {
        return place_id;
    }

    void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getCity() {
        return place_id;
    }
}
