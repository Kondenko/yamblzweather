package com.kondenko.yamblzweather.domain.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

/**
 * Created by Mishkun on 03.08.2017.
 */
@AutoValue
public abstract class Temperature implements Parcelable{
    public static Temperature valueOfKelvin(double kelvin) {
        return new AutoValue_Temperature(kelvin);
    }

    public abstract double kelvinDegrees();

    public double celsiusDegrees() {
        return kelvinDegrees() - 273;
    }

    public double fahrenheitDegrees() {
        return kelvinDegrees() * 9 / 5 - 459.67;
    }
}
