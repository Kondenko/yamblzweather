package com.kondenko.yamblzweather.domain.guards;

import com.kondenko.yamblzweather.domain.entity.City;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Mishkun on 27.07.2017.
 */

public interface LocationProvider {
    Observable<City> getCurrentCity();

    Completable setCurrentCity(City city);

    Completable addFavoredCity(City city);

    Single<List<City>> getFavoriteCities();

    Completable deleteFavoriteCity(City city);

}
