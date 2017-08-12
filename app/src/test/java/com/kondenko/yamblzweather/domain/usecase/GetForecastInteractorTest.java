package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.entity.Forecast;
import com.kondenko.yamblzweather.domain.guards.WeatherProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 11.08.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetForecastInteractorTest {
    private TestScheduler testScheduler;
    private GetForecastInteractor getForecastInteractor;
    @Mock
    private WeatherProvider weatherProvider;
    private Forecast forecast;

    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
        getForecastInteractor = new GetForecastInteractor(testScheduler, testScheduler, weatherProvider);
        forecast = Forecast.create(new ArrayList<>());
    }

    @Test
    public void run() throws Exception {
        when(weatherProvider.getForecast()).thenReturn(Maybe.just(forecast));

        TestObserver<Forecast> forecastTestObserver = getForecastInteractor.run().test();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        forecastTestObserver.assertResult(forecast);
    }

}