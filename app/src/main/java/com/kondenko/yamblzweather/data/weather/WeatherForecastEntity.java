package com.kondenko.yamblzweather.data.weather;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Mishkun on 05.08.2017.
 */
@Entity(tableName = "weather_forecast", primaryKeys = {"forecast", "timestamp"}, foreignKeys = @ForeignKey(entity = ForecastEntity.class, parentColumns = "city", childColumns = "forecast", onDelete = CASCADE))
public class WeatherForecastEntity {
    @ColumnInfo(name = "timestamp")
    private long timestamp;

    @ColumnInfo(name = "forecast")
    private String forecast;

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

    long getTimestamp() {
        return timestamp;
    }

    void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    String getForecast() {
        return forecast;
    }

    void setForecast(String forecast) {
        this.forecast = forecast;
    }

    int getWeatherConditionCode() {
        return weatherConditionCode;
    }

    void setWeatherConditionCode(int weatherConditionCode) {
        this.weatherConditionCode = weatherConditionCode;
    }

    double getTemperature() {
        return temperature;
    }

    void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    double getHumidity() {
        return humidity;
    }

    void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    double getPressure() {
        return pressure;
    }

    void setPressure(double pressure) {
        this.pressure = pressure;
    }

    double getWindSpeed() {
        return windSpeed;
    }

    void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }


    double getNightTemperature() {
        return nightTemperature;
    }

    void setNightTemperature(double nightTemperature) {
        this.nightTemperature = nightTemperature;
    }

    double getDayTemperature() {
        return dayTemperature;
    }

    void setDayTemperature(double dayTemperature) {
        this.dayTemperature = dayTemperature;
    }
}
