package com.kondenko.yamblzweather.ui.citysuggest;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Prediction;
import com.kondenko.yamblzweather.ui.BaseView;

import io.reactivex.Observable;

/**
 * Created by Mishkun on 28.07.2017.
 */

public interface SuggestsView extends BaseView<SuggestsViewModel> {
    Observable<String> getCityNamesStream();
    Observable<Prediction> getSuggestsClicks();
    Observable<City> getCitiesClicks();
    Observable<City> getCitiesDeletionsClicks();


    void finishScreen();
}
