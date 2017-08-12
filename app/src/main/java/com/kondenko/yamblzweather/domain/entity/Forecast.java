package com.kondenko.yamblzweather.domain.entity;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by Mishkun on 03.08.2017.
 */
@AutoValue
public abstract class Forecast implements Parcelable{
    public abstract List<Weather> weatherList();

    @NonNull
    public static Forecast create(@NonNull List<Weather> forecast){
        return new AutoValue_Forecast(forecast);
    }
}
