package com.kondenko.yamblzweather.ui.citysuggest;

import com.kondenko.yamblzweather.data.suggest.CitySuggest;
import com.kondenko.yamblzweather.data.suggest.PredictionResponse;
import com.kondenko.yamblzweather.domain.entity.Prediction;
import com.kondenko.yamblzweather.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Mishkun on 28.07.2017.
 */

public interface SuggestsView extends BaseView<SuggestsViewModel> {
    Observable<String> getCityNamesStream();
    Observable<Prediction> getClicks();

    void finishScreen();
}
