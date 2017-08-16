package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Location;
import com.kondenko.yamblzweather.domain.entity.Prediction;
import com.kondenko.yamblzweather.domain.guards.CitySuggestProvider;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 11.08.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class FetchCityCoordinatesInteractorTest {
    private static final String NAME = "NAME";
    private static final String ID = "ID";

    private FetchCityCoordinatesInteractor fetchCityCoordinatesInteractor;
    private TestScheduler testScheduler;

    @Mock
    private CitySuggestProvider citySuggestProvider;
    @Mock
    private LocationProvider locationProvider;

    private City city;
    private Prediction prediction;

    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
        fetchCityCoordinatesInteractor = new FetchCityCoordinatesInteractor(testScheduler, testScheduler, citySuggestProvider, locationProvider);
        city = City.create(Location.builder().longitude(0).latitude(0).build(), NAME, ID);
        prediction = Prediction.create(NAME, ID);
    }

    @Test
    public void shouldFetchCoordsAndSelectCity() throws Exception {
        when(citySuggestProvider.getCityFromPrediction(prediction)).thenReturn(Single.just(city));
        when(locationProvider.setCurrentCity(any())).thenReturn(Completable.complete());
        when(locationProvider.addFavoredCity(any())).thenReturn(Completable.complete());

        TestObserver<Void> voidTestObserver = fetchCityCoordinatesInteractor.run(prediction).test();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        voidTestObserver.assertComplete();
        verify(locationProvider).addFavoredCity(city);
        verify(locationProvider).setCurrentCity(city);
    }

}