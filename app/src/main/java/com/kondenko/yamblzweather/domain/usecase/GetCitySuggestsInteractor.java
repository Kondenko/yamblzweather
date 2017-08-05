package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.BaseInteractor;
import com.kondenko.yamblzweather.domain.entity.Prediction;
import com.kondenko.yamblzweather.domain.guards.CitySuggestProvider;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;

import static com.kondenko.yamblzweather.Const.JOB;
import static com.kondenko.yamblzweather.Const.UI;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class GetCitySuggestsInteractor extends BaseInteractor {
    public static final String TYPES = "(cities)";
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;
    private final CitySuggestProvider citySuggestProvider;

    @Inject
    GetCitySuggestsInteractor(@Named(JOB) Scheduler jobScheduler, @Named(UI) Scheduler uiScheduler, CitySuggestProvider citySuggestProvider) {
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
