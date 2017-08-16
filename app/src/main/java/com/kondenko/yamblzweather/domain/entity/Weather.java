package com.kondenko.yamblzweather.domain.entity;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

/**
 * Created by Mishkun on 03.08.2017.
 */
@AutoValue
public abstract class Weather implements Parcelable {
    @NonNull
    public static Builder builder() {
        return new AutoValue_Weather.Builder();
    }

    public abstract long timestamp();


    public abstract WeatherConditions weatherConditions();

    public abstract Temperature temperature();

    public abstract Temperature dayTemperature();

    public abstract Temperature nightTemperature();

    public abstract double humidity();

    public abstract double windSpeed();

    public abstract double pressure();

    @AutoValue.Builder
    public static abstract class Builder {

        public abstract Builder timestamp(long timestamp);

        public abstract Builder temperature(@NonNull Temperature temperature);

        public abstract Builder dayTemperature(@NonNull Temperature temperature);

        public abstract Builder nightTemperature(@NonNull Temperature temperature);

        public abstract Builder humidity(double humidity);

        public abstract Builder windSpeed(double windSpeed);

        public abstract Builder weatherConditions(@NonNull WeatherConditions weatherConditions);

        public abstract Builder pressure(double pressure);

        public abstract Weather build();
    }
}
