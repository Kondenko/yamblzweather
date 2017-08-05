package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.entity.Forecast;
import com.kondenko.yamblzweather.domain.guards.WeatherProvider;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

import static com.kondenko.yamblzweather.Const.JOB;
import static com.kondenko.yamblzweather.Const.UI;

/**
 * Created by Mishkun on 04.08.2017.
 */

public class GetForecastInteractor {
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;
    private final WeatherProvider weatherProvider;

    @Inject
    GetForecastInteractor(@Named(JOB) Scheduler jobScheduler, @Named(UI) Scheduler uiScheduler,
                          WeatherProvider weatherProvider) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
        this.weatherProvider = weatherProvider;
    }

    public Observable<Forecast> run() {
        return weatherProvider.getForecastSubscription()
                              .subscribeOn(jobScheduler)
                              .observeOn(uiScheduler);
    }
}
