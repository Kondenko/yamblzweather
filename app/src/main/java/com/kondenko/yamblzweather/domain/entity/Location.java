package com.kondenko.yamblzweather.domain.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

/**
 * Created by Mishkun on 03.08.2017.
 */
@AutoValue
public abstract class Location implements Parcelable{
    public abstract double latitude();

    public abstract double longitude();

    public static Builder builder() {
        return new AutoValue_Location.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder latitude(double latitude);

        public abstract Builder longitude(double longitude);

        public abstract Location build();
    }
}
