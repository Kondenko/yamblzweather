package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 11.08.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetFavoredCitiesInteractorTest {
    private TestScheduler testScheduler;
    private GetFavoredCitiesInteractor getFavoredCitiesInteractor;
    @Mock
    private LocationProvider locationProvider;

    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
        getFavoredCitiesInteractor = new GetFavoredCitiesInteractor(locationProvider, testScheduler, testScheduler);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void run() throws Exception {
        when(locationProvider.getFavoriteCities()).thenReturn(Single.just(new ArrayList<>()));

        TestObserver<List<City>> listTestObserver = getFavoredCitiesInteractor.run().test();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        listTestObserver.assertResult(new ArrayList<City>());
    }

}