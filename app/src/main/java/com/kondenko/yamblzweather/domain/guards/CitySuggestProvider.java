package com.kondenko.yamblzweather.domain.guards;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Prediction;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Mishkun on 04.08.2017.
 */

public interface CitySuggestProvider {
    Single<List<Prediction>> getCitySuggests(String query);

    Single<City> getCityFromPrediction(Prediction prediction);
}
