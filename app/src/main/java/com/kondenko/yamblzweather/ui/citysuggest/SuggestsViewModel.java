package com.kondenko.yamblzweather.ui.citysuggest;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.kondenko.yamblzweather.domain.entity.Prediction;

import java.util.List;

/**
 * Created by Mishkun on 05.08.2017.
 */
@AutoValue
abstract class SuggestsViewModel implements Parcelable {
    abstract List<Prediction> predictions();

    static SuggestsViewModel create(List<Prediction> predictions) {
        return new AutoValue_SuggestsViewModel(predictions);
    }
}
