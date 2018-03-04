package com.kondenko.yamblzweather.ui.weather;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Forecast;
import com.kondenko.yamblzweather.domain.entity.TempUnit;
import com.kondenko.yamblzweather.domain.entity.Weather;

import java.util.List;

/**
 * Created by Mishkun on 06.08.2017.
 */
@AutoValue
abstract class WeatherViewModel implements Parcelable {
    public static WeatherViewModel create(Weather weather, Forecast forecast, City city, List<City> cityList, TempUnit tempUnit) {
        return new AutoValue_WeatherViewModel(weather, forecast, city, cityList, tempUnit);
    }

    abstract public Weather weather();

    abstract Forecast forecast();

    public abstract City city();

    public abstract List<City> cities();

    public abstract TempUnit tempUnit();

}
