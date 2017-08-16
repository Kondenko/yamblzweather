package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.ui.BaseView;

import io.reactivex.Observable;

public interface WeatherView extends BaseView<WeatherViewModel> {

    Observable<City> getCitySelections();

}