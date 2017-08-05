package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.BaseInteractor;
import com.kondenko.yamblzweather.domain.entity.Weather;
import com.kondenko.yamblzweather.domain.guards.WeatherProvider;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

import static com.kondenko.yamblzweather.Const.JOB;
import static com.kondenko.yamblzweather.Const.UI;

public class GetCurrentWeatherInteractor extends BaseInteractor {

    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;
    private final WeatherProvider weatherProvider;

    @Inject
    GetCurrentWeatherInteractor(@Named(JOB) Scheduler jobScheduler, @Named(UI) Scheduler uiScheduler,
                                       WeatherProvider weatherProvider) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
        this.weatherProvider = weatherProvider;
    }

    public Observable<Weather> run() {
        return weatherProvider.getWeatherSubscription()
                              .subscribeOn(jobScheduler)
                              .observeOn(uiScheduler);
    }

}
