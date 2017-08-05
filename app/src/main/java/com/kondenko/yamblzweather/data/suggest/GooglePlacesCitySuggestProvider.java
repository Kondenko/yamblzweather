package com.kondenko.yamblzweather.data.suggest;

import android.util.Log;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Prediction;
import com.kondenko.yamblzweather.domain.guards.CitySuggestProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Mishkun on 04.08.2017.
 */

public class GooglePlacesCitySuggestProvider implements CitySuggestProvider {
    private static final String TAG = GooglePlacesCitySuggestProvider.class.getSimpleName();
    private static final String GEOCODE_KEY = "(cities)";
    private final CitiesSuggestService citiesSuggestService;

    @Inject
    GooglePlacesCitySuggestProvider(CitiesSuggestService citiesSuggestService) {
        this.citiesSuggestService = citiesSuggestService;
    }

    @Override
    public Single<List<Prediction>> getCitySuggests(String query) {
        return citiesSuggestService.getSuggests(query, GEOCODE_KEY)
                                   .map(CitySuggest::getPredictionResponses)
                                   .flatMapObservable(Observable::fromIterable)
                                   .map(PredictionMapper::responseToDomain)
                                   .toList()
                .doOnSuccess(predictions -> Log.d(TAG, "getCitySuggests"))
                .doOnError(error -> Log.d(TAG, error.getMessage()));
    }

    @Override
    public Single<City> getCityFromPrediction(Prediction prediction) {
        return citiesSuggestService.getCityCoordinatesById(prediction.id())
                                   .map(CityMapper::responseToDomain);
    }
}
