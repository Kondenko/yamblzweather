package com.kondenko.yamblzweather.data.weather;

import android.util.Log;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Forecast;
import com.kondenko.yamblzweather.domain.entity.Location;
import com.kondenko.yamblzweather.domain.entity.Temperature;
import com.kondenko.yamblzweather.domain.entity.Weather;
import com.kondenko.yamblzweather.domain.entity.WeatherConditions;
import com.kondenko.yamblzweather.domain.guards.WeatherProvider;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by Mishkun on 04.08.2017.
 */

public class OpenWeatherWeatherProvider implements WeatherProvider {
    private static final String TAG = OpenWeatherWeatherProvider.class.getSimpleName();
    private final WeatherService weatherService;
    private final BehaviorSubject<Weather> weatherBehaviorSubject;
    private final BehaviorSubject<Forecast> forecastBehaviorSubject;

    @Inject
    OpenWeatherWeatherProvider(WeatherService weatherService) {
        this.weatherService = weatherService;
        weatherBehaviorSubject = BehaviorSubject.create();
        forecastBehaviorSubject = BehaviorSubject.create();
        weatherBehaviorSubject.onNext(getDefaultWeather());
    }

    private Weather getDefaultWeather(){
        return Weather.builder()
                      .timestamp(new Date().getTime())
                      .city(City.create(Location.builder().latitude(0).longitude(0).build(), "ABC", "ID"))
                      .weatherConditions(WeatherConditions.CLEAR)
                      .humidity(0)
                      .pressure(0)
                      .temperature(Temperature.valueOfKelvin(0))
                      .windSpeed(0)
                      .build();
    }

    @Override
    public Observable<Weather> getWeatherSubscription() {
        return weatherBehaviorSubject.doOnNext(weather -> Log.d(TAG, "gotWeatherSubscription"));
    }

    @Override
    public Observable<Forecast> getForecastSubscription() {
        return forecastBehaviorSubject;
    }

    @Override
    public Completable update(City city) {
        return Completable.mergeArray(updateWeather(city), updateForecast(city))
                ;
    }

    private Completable updateForecast(City city) {
        return weatherService.getForecast(city.location().latitude(), city.location().longitude())
                             .map(ForecastResponse::getList)
                             .flatMapObservable(Observable::fromIterable)
                             .map(WeatherMapper::responseToDomain)
                             .map(builder -> builder.city(city)
                                                    .timestamp(new Date().getTime())
                                                    .build())
                             .toList()
                             .map(Forecast::create)
                             .doOnSuccess(forecastBehaviorSubject::onNext)
                             .toCompletable();
    }

    private Completable updateWeather(City city) {
        return weatherService.getWeather(city.location().latitude(), city.location().longitude())
                             .map(WeatherMapper::responseToDomain)
                             .map(builder -> builder.city(city)
                                                    .timestamp(new Date().getTime())
                                                    .build())
                             .doOnSuccess(weather -> weatherBehaviorSubject.onNext(weather))
                             .doOnSuccess(weather -> Log.d(TAG, "updateWeather" + weather))
                             .toCompletable();
    }
}
