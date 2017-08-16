package com.kondenko.yamblzweather.data.weather;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.kondenko.yamblzweather.data.location.CityEntity;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Mishkun on 05.08.2017.
 */
@Entity(tableName = "weather", foreignKeys = @ForeignKey(entity = CityEntity.class, parentColumns = "id", childColumns = "city", onDelete = CASCADE))
public class WeatherEntity {
    @ColumnInfo(name = "timestamp")
    private long timestamp;
    @ColumnInfo(name = "weather_condition_code")
    private int weatherConditionCode;
    @ColumnInfo(name = "temperature")
    private double temperature;
    @ColumnInfo(name = "day_temperature")
    private double dayTemperature;
    @ColumnInfo(name = "night_temperature")
    private double nightTemperature;
    @ColumnInfo(name = "humidity")
    private double humidity;
    @ColumnInfo(name = "pressure")
    private double pressure;
    @ColumnInfo(name = "wind_speed")
    private double windSpeed;
    @PrimaryKey
    @ColumnInfo(name = "city")
    private String place_id;

    int getWeatherConditionCode() {
        return weatherConditionCode;
    }

    void setWeatherConditionCode(int weatherConditionCode) {
        this.weatherConditionCode = weatherConditionCode;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    double getWindSpeed() {
        return windSpeed;
    }

    void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    String getPlace_id() {
        return place_id;
    }

    void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    long getTimestamp() {
        return timestamp;
    }

    void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    double getDayTemperature() {
        return dayTemperature;
    }

    void setDayTemperature(double dayTemperature) {
        this.dayTemperature = dayTemperature;
    }

    double getNightTemperature() {
        return nightTemperature;
    }

    void setNightTemperature(double nightTemperature) {
        this.nightTemperature = nightTemperature;
    }
}
