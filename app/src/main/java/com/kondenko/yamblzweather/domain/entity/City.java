package com.kondenko.yamblzweather.domain.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

/**
 * Created by Mishkun on 03.08.2017.
 */
@AutoValue
abstract public class City implements Parcelable{
    public static City create(Location location, String name, String id) {
        return new AutoValue_City(location, name, id);
    }

    public abstract Location location();

    public abstract String name();

    public abstract String id();
}
