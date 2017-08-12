package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.di.Job;
import com.kondenko.yamblzweather.di.Ui;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Scheduler;

/**
 * Created by Mishkun on 05.08.2017.
 */

public class DeleteCityInteractor {
    private final LocationProvider locationProvider;
    private final Scheduler jobScheduler;
    private final Scheduler uiScheduler;

    @Inject
    DeleteCityInteractor(LocationProvider locationProvider, @Job Scheduler jobScheduler, @Ui Scheduler uiScheduler) {
        this.locationProvider = locationProvider;
        this.jobScheduler = jobScheduler;
        this.uiScheduler = uiScheduler;

    }

    public Completable run(City city) {
        return locationProvider.getCurrentCity().firstElement()
                               .flatMapCompletable(currentCity -> !currentCity.equals(city)
                                       ? locationProvider.deleteFavoriteCity(city)
                                       : locationProvider.deleteFavoriteCity(city)
                                                         .andThen(locationProvider.getFavoriteCities()
                                                                                  .flatMapCompletable(
                                                                                          cities -> locationProvider.setCurrentCity(cities.get(
                                                                                                  0)))))
                               .subscribeOn(jobScheduler)
                               .observeOn(uiScheduler);
    }
}
