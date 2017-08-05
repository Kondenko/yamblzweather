package com.kondenko.yamblzweather.domain.entity;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

/**
 * Created by Mishkun on 04.08.2017.
 */
@AutoValue
public abstract class Prediction implements Parcelable{
    public static Prediction create(String name, String id){
        return new AutoValue_Prediction(name, id);
    }

    public abstract String name();

    public abstract String id();
}
