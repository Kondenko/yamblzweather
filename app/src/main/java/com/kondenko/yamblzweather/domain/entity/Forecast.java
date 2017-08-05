package com.kondenko.yamblzweather.domain.entity;

import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by Mishkun on 03.08.2017.
 */
@AutoValue
public abstract class Forecast {
    public abstract List<Weather> weatherList();

    public static Forecast create(List<Weather> forecast){
        return new AutoValue_Forecast(forecast);
    }
}
