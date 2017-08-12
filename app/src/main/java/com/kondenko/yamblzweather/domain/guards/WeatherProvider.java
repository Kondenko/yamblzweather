package com.kondenko.yamblzweather.domain.guards;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Forecast;
import com.kondenko.yamblzweather.domain.entity.Weather;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by Mishkun on 04.08.2017.
 */

public interface WeatherProvider {
    Observable<Weather> getWeather();
    Maybe<Forecast> getForecast();
    Completable update(City city);
}
