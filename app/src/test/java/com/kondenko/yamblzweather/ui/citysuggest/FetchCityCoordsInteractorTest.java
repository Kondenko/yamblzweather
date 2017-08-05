package com.kondenko.yamblzweather.ui.citysuggest;

import com.kondenko.yamblzweather.data.suggest.CityResponse;
import com.kondenko.yamblzweather.data.suggest.CitySearchResult;
import com.kondenko.yamblzweather.data.suggest.Coord;
import com.kondenko.yamblzweather.data.suggest.PredictionResponse;
import com.kondenko.yamblzweather.data.suggest.CitiesSuggestService;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 29.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class FetchCityCoordsInteractorTest {

    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String PLACE_ID = "PLACE_ID";
    private static final double LATITUDE = 0.0;
    private static final double LONGITUDE = 1.0;
    @Mock
    private LocationProvider locationProvider;
    @Mock
    private CitiesSuggestService citiesSuggestService;


    private TestScheduler testScheduler = new TestScheduler();
    private PredictionResponse predictionResponse;
    private CitySearchResult citySearchResult;
    private Coord coordinates;

    @Before
    public void setUp() throws Exception {
        predictionResponse = new PredictionResponse(DESCRIPTION, PLACE_ID);
        citySearchResult = mock(CitySearchResult.class, RETURNS_DEEP_STUBS);
        coordinates = new Coord();
        coordinates.setLat(LATITUDE);
        coordinates.setLon(LONGITUDE);
        when(citySearchResult.getResult().getGeometry().getCoordinates()).thenReturn(coordinates);
    }

    @Test
    public void shouldGetCityCoordinatesAndWrite() throws Exception {
        CityResponse testCity = new CityResponse(coordinates, DESCRIPTION);
        when(citiesSuggestService.getCityCoordinatesById(PLACE_ID)).thenReturn(Single.just(citySearchResult));
        when(locationProvider.setCurrentCity(any())).thenReturn(Completable.complete());
        ArgumentCaptor<CityResponse> cityArgumentCaptor = ArgumentCaptor.forClass(CityResponse.class);

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        verify(citiesSuggestService).getCityCoordinatesById(PLACE_ID);
        verifyNoMoreInteractions(citiesSuggestService);
        verifyNoMoreInteractions(locationProvider);

        CityResponse cityResult = cityArgumentCaptor.getValue();
        assertTrue(cityResult.equals(testCity));
    }

}