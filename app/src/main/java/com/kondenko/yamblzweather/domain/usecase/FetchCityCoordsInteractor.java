package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.BaseInteractor;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Prediction;
import com.kondenko.yamblzweather.domain.guards.CitySuggestProvider;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;

import static com.kondenko.yamblzweather.Const.JOB;
import static com.kondenko.yamblzweather.Const.UI;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class FetchCityCoordsInteractor extends BaseInteractor {
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;
    private final CitySuggestProvider citySuggestProvider;

    @Inject
    FetchCityCoordsInteractor(@Named(JOB) Scheduler jobScheduler, @Named(UI) Scheduler uiScheduler, CitySuggestProvider citiesSuggestProvider) {
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;
        this.citySuggestProvider = citiesSuggestProvider;
    }

    public Single<City> run(Prediction prediction) {
        return citySuggestProvider.getCityFromPrediction(prediction)
                                  .subscribeOn(jobScheduler)
                                  .observeOn(uiScheduler);
    }

}
