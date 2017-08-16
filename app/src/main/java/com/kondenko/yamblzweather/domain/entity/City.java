package com.kondenko.yamblzweather.domain.entity;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

/**
 * Created by Mishkun on 03.08.2017.
 */
@AutoValue
abstract public class City implements Parcelable{
    @NonNull
    public static City create(@NonNull Location location,@NonNull String name,@NonNull String id) {
        return new AutoValue_City(location, name, id);
    }

    public abstract Location location();

    public abstract String name();

    public abstract String id();

    @Override
    public String toString(){
        return name();
    }
}
