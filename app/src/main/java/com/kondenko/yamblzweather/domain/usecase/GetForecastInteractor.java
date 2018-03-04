package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.di.Job;
import com.kondenko.yamblzweather.di.Ui;
import com.kondenko.yamblzweather.domain.entity.Forecast;
import com.kondenko.yamblzweather.domain.guards.WeatherProvider;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;

/**
 * Created by Mishkun on 04.08.2017.
 */

public class GetForecastInteractor {
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;
    private final WeatherProvider weatherProvider;

    @Inject
    GetForecastInteractor(@Job Scheduler jobScheduler, @Ui Scheduler uiScheduler,
                          WeatherProvider weatherProvider) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
        this.weatherProvider = weatherProvider;
    }

    public Maybe<Forecast> run() {
        return weatherProvider.getForecast()
                              .subscribeOn(jobScheduler)
                              .observeOn(uiScheduler);
    }
}
