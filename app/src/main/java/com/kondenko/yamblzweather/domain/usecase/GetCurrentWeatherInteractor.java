package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.di.Job;
import com.kondenko.yamblzweather.di.Ui;
import com.kondenko.yamblzweather.domain.entity.Weather;
import com.kondenko.yamblzweather.domain.guards.WeatherProvider;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class GetCurrentWeatherInteractor {

    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;
    private final WeatherProvider weatherProvider;

    @Inject
    GetCurrentWeatherInteractor(@Job Scheduler jobScheduler, @Ui Scheduler uiScheduler,
                                WeatherProvider weatherProvider) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
        this.weatherProvider = weatherProvider;
    }

    public Observable<Weather> run() {
        return weatherProvider.getWeather()
                              .subscribeOn(jobScheduler)
                              .observeOn(uiScheduler);
    }

}
