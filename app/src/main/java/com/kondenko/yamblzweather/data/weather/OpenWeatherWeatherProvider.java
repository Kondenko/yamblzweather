package com.kondenko.yamblzweather.data.weather;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Forecast;
import com.kondenko.yamblzweather.domain.entity.Weather;
import com.kondenko.yamblzweather.domain.guards.WeatherProvider;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Mishkun on 04.08.2017.
 */

public class OpenWeatherWeatherProvider implements WeatherProvider {
    @SuppressWarnings("unused")
    private static final String TAG = OpenWeatherWeatherProvider.class.getSimpleName();
    private static final int COUNT = 8;
    private final WeatherService weatherService;

    private final WeatherDao weatherDao;
    private final ForecastDao forecastDao;

    @Inject
    OpenWeatherWeatherProvider(WeatherService weatherService, WeatherDao weatherDao, ForecastDao forecastDao) {
        this.weatherService = weatherService;
        this.weatherDao = weatherDao;
        this.forecastDao = forecastDao;
    }


    @Override
    public Observable<Weather> getWeather() {
        return weatherDao.getWeather()
                         .map(WeatherMapper::dbToDomain).toObservable();
    }

    @Override
    public Maybe<Forecast> getForecast() {
        return forecastDao.getForecastEntity()
                          .flatMapSingleElement(forecastEntity -> forecastDao.getWeatherForecasts(forecastEntity.getCity())
                                                                             .flatMapSingle(weatherForecastEntities ->
                                                                                                    Observable.fromIterable(weatherForecastEntities)
                                                                                                              .map(ForecastMapper::dbToDomain)
                                                                                                              .toList()))
                          .map(Forecast::create);
    }

    @Override
    public Completable update(City city) {
        return Completable.mergeArray(updateWeather(city), updateForecast(city));
    }

    private Completable updateForecast(City city) {
        return weatherService.getForecast(city.location().latitude(), city.location().longitude(), COUNT)
                             .map(ForecastResponse::getList)
                             .doOnSuccess(ignore -> {
                                 ForecastEntity forecastEntity = new ForecastEntity();
                                 forecastEntity.setPlace_id(city.id());
                                 forecastEntity.setTimestamp(new Date().getTime());
                                 forecastDao.insertAllForecasts(forecastEntity);
                             })
                             .flatMap(weatherModels -> Observable.fromIterable(weatherModels)
                                                                 .skip(1)
                                                                 .map(ForecastMapper::responseToWeatherForecastDb)
                                                                 .map(weatherForecastEntity -> {
                                                                     weatherForecastEntity.setForecast(city.id());
                                                                     return weatherForecastEntity;
                                                                 })
                                                                 .toList())
                             .doOnSuccess(forecastDao::insertAllWeatherForecasts)
                             .toCompletable();
    }


    private Completable updateWeather(City city) {
        return Single.zip(weatherService.getWeather(city.location().latitude(), city.location().longitude()).map(WeatherMapper::responseToWeatherDb),
                          weatherService.getForecast(city.location().latitude(), city.location().longitude(), 1),
                          (weatherEntity, forecast) -> {
                              weatherEntity.setDayTemperature(forecast.getList().get(0).getTemp().getDay());
                              weatherEntity.setNightTemperature(forecast.getList().get(0).getTemp().getNight());
                              return weatherEntity;
                          })
                     .map(weatherEntity -> {
                         weatherEntity.setPlace_id(city.id());
                         weatherEntity.setTimestamp(new Date().getTime());
                         return weatherEntity;
                     })
                     .doOnSuccess(weatherDao::insertAllWeather)
                     .toCompletable();
    }
}
