package com.kondenko.yamblzweather.data.suggest;

import com.kondenko.yamblzweather.data.suggest.CitySearchResult.Result;
import com.kondenko.yamblzweather.data.suggest.CitySearchResult.Result.Geometry;
import com.kondenko.yamblzweather.data.suggest.CitySearchResult.Result.Geometry.Coord;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Location;
import com.kondenko.yamblzweather.domain.entity.Prediction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 11.08.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class GooglePlacesCitySuggestProviderTest {

    private static final String QUERY = "QUERY";
    private static final String PLACE_ID = "ID";
    private static final String NAME = "IZHEVSK";
    private static final double latitude = 0.56;
    private static final double longitude = 0.23;

    private GooglePlacesCitySuggestProvider googlePlacesCitySuggestProvider;

    private CitySuggest citySuggest;
    private CitySearchResult citySearchResult;
    private Prediction prediction;

    @Mock
    private CitiesSuggestService citiesSuggestService;
    private City city;

    @Before
    public void setUp() throws Exception {
        googlePlacesCitySuggestProvider = new GooglePlacesCitySuggestProvider(citiesSuggestService);

        setupPredictions();
        setupCitySearchResult();
    }

    private void setupCitySearchResult() {
        Coord coords = new Coord(latitude, longitude);
        Geometry geometry = new Geometry(coords);
        Result result = new Result(geometry, NAME, PLACE_ID);
        citySearchResult = new CitySearchResult(result);
        city = City.create(Location.builder().latitude(latitude).longitude(longitude).build(), NAME, PLACE_ID);
    }

    private void setupPredictions() {
        CitySuggest.PredictionResponse predictionResponse = new CitySuggest.PredictionResponse(NAME, PLACE_ID);
        List<CitySuggest.PredictionResponse> predictionResponses = new ArrayList<CitySuggest.PredictionResponse>(){{add(predictionResponse); add(predictionResponse);}};
        citySuggest = new CitySuggest(predictionResponses);
        prediction = Prediction.create(NAME, PLACE_ID);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldGetCitySuggests() throws Exception {
        List<Prediction> predictions = new ArrayList<Prediction>(){{add(prediction); add(prediction);}};
        when(citiesSuggestService.getSuggests(eq(QUERY), anyString())).thenReturn(Single.just(citySuggest));

        TestObserver<List<Prediction>> listTestObserver = googlePlacesCitySuggestProvider.getCitySuggests(QUERY).test();
        listTestObserver.assertResult(predictions);
    }



    @Test
    public void shouldGetCityFromPrediction() throws Exception {
        when(citiesSuggestService.getCityCoordinatesById(PLACE_ID)).thenReturn(Single.just(citySearchResult));

        TestObserver<City> cityTestObserver = googlePlacesCitySuggestProvider.getCityFromPrediction(prediction).test();
        cityTestObserver.assertResult(city);
    }

}