package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Location;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import retrofit2.http.GET;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 11.08.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetCurrentCityInteractorTest {
    private TestScheduler testScheduler;
    private GetCurrentCityInteractor getCurrentCityInteractor;
    @Mock
    private LocationProvider locationProvider;
    private City city;

    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
        getCurrentCityInteractor = new GetCurrentCityInteractor(locationProvider, testScheduler, testScheduler);
        city = City.create(Location.builder().longitude(0).latitude(0).build(), "name", "id");
    }

    @Test
    public void run() throws Exception {
        when(locationProvider.getCurrentCity()).thenReturn(Observable.just(city, city));

        TestObserver<City> cityTestObserver = getCurrentCityInteractor.run().test();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        cityTestObserver.assertResult(city, city);
    }

}