package com.kondenko.yamblzweather.data.location;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.processors.BehaviorProcessor;

/**
 * Created by Mishkun on 05.08.2017.
 */

public class RoomLocationProvider implements LocationProvider {
    @SuppressWarnings("unused")
    private static final String TAG = RoomLocationProvider.class.getSimpleName();
    private final CityDao cityDao;
    private final BehaviorProcessor<CityEntity> cityBehaviour;

    @Inject
    RoomLocationProvider(CityDao cityDao) {
        this.cityDao = cityDao;
        cityBehaviour = BehaviorProcessor.create();
        cityDao.getSelectedCity().subscribe(cityBehaviour);
    }

    @Override
    public Observable<City> getCurrentCity() {
        return cityBehaviour
                      .map(CityMapper::dbToDomain)
                      .toObservable();
    }

    @Override
    public Completable setCurrentCity(City city) {
        return Completable.fromAction(() -> {
            cityDao.deselectAll();
            cityDao.setSelectedCity(city.id());
        });
    }

    @Override
    public Completable addFavoredCity(City city) {
        return Completable.fromAction(() -> cityDao.insertCity(CityMapper.domainToDb(city)));
    }

    @Override
    public Single<List<City>> getFavoriteCities() {
        return cityDao.getCities().flatMap(cityEntities -> Observable.fromIterable(cityEntities)
                                                                     .map(CityMapper::dbToDomain)
                                                                     .toList());
    }

    @Override
    public Completable deleteFavoriteCity(City city) {
        return Completable.fromAction(() -> cityDao.deleteCity(CityMapper.domainToDb(city)));
    }
}
