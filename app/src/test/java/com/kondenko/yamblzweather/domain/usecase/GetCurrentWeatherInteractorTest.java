package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.entity.Temperature;
import com.kondenko.yamblzweather.domain.entity.Weather;
import com.kondenko.yamblzweather.domain.entity.WeatherConditions;
import com.kondenko.yamblzweather.domain.guards.WeatherProvider;

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

import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 11.08.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetCurrentWeatherInteractorTest {
    private GetCurrentWeatherInteractor getCurrentWeatherInteractor;
    private TestScheduler testScheduler;
    @Mock
    private WeatherProvider weatherProvider;
    private Weather weather;

    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
        getCurrentWeatherInteractor = new GetCurrentWeatherInteractor(testScheduler, testScheduler, weatherProvider);
        setupDomainWeather();
    }
    private void setupDomainWeather() {
        Temperature temperature = Temperature.valueOfKelvin(0);
        weather = Weather.builder()
                         .weatherConditions(WeatherConditions.CLEAR)
                         .dayTemperature(temperature)
                         .nightTemperature(temperature)
                         .temperature(temperature)
                         .pressure(0)
                         .humidity(0)
                         .timestamp(0)
                         .windSpeed(0)
                         .build();
    }
    @Test
    public void run() throws Exception {
        when(weatherProvider.getWeather()).thenReturn(Observable.just(weather, weather));

        TestObserver<Weather> weatherTestObserver = getCurrentWeatherInteractor.run().test();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        weatherTestObserver.assertResult(weather, weather);
    }

}