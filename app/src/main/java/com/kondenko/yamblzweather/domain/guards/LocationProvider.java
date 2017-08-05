package com.kondenko.yamblzweather.domain.guards;

import com.kondenko.yamblzweather.domain.entity.City;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Mishkun on 27.07.2017.
 */

public interface LocationProvider {
    Single<City> getCurrentCity();

    Completable setCurrentCity(City city);
}
