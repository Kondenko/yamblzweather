package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Location;
import com.kondenko.yamblzweather.domain.guards.WeatherProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 11.08.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UpdateWeatherInteractorTest {
    private TestScheduler testScheduler;
    private UpdateWeatherInteractor updateWeatherInteractor;
    @Mock
    private WeatherProvider weatherProvider;
    private City city;


    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
        updateWeatherInteractor = new UpdateWeatherInteractor(testScheduler, testScheduler, weatherProvider);
        city = City.create(Location.builder().longitude(0).latitude(0).build(), "name", "id");
    }

    @Test
    public void run() throws Exception {
        when(weatherProvider.update(any())).thenReturn(Completable.complete());

        TestObserver<Void> voidTestObserver = updateWeatherInteractor.run(city).test();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        voidTestObserver.assertComplete();
        verify(weatherProvider).update(city);
    }

}