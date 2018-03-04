package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.di.Job;
import com.kondenko.yamblzweather.di.Ui;
import com.kondenko.yamblzweather.domain.entity.Prediction;
import com.kondenko.yamblzweather.domain.guards.CitySuggestProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class GetCitySuggestsInteractor {
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;
    private final CitySuggestProvider citySuggestProvider;

    @Inject
    GetCitySuggestsInteractor(@Job Scheduler jobScheduler, @Ui Scheduler uiScheduler, CitySuggestProvider citySuggestProvider) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
        this.citySuggestProvider = citySuggestProvider;
    }

    public Single<List<Prediction>> run(String query) {
        return citySuggestProvider.getCitySuggests(query)
                                  .subscribeOn(jobScheduler)
                                  .observeOn(uiScheduler);
    }

}
