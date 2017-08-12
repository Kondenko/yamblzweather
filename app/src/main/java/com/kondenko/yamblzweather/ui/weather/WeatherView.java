package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.ui.BaseView;

import java.util.List;

import io.reactivex.Observable;

public interface WeatherView extends BaseView<WeatherViewModel> {

    Observable<City> getCitySelections();

    void setCity(City city, List<City> cities);
}