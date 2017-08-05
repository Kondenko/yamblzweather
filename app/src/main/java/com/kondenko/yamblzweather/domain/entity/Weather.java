package com.kondenko.yamblzweather.domain.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

/**
 * Created by Mishkun on 03.08.2017.
 */
@AutoValue
public abstract class Weather implements Parcelable {
    public static Builder builder() {
        return new AutoValue_Weather.Builder();
    }

    public abstract long timestamp();

    public abstract City city();

    public abstract WeatherConditions weatherConditions();

    public abstract Temperature temperature();

    public abstract double humidity();

    public abstract double windSpeed();

    public abstract double pressure();

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder city(City city);

        public abstract Builder timestamp(long timestamp);

        public abstract Builder temperature(Temperature temperature);

        public abstract Builder humidity(double humidity);

        public abstract Builder windSpeed(double windSpeed);

        public abstract Builder weatherConditions(WeatherConditions weatherConditions);

        public abstract Builder pressure(double pressure);

        public abstract Weather build();
    }
}
