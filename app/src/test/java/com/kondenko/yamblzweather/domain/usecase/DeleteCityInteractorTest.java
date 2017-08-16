package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Location;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 11.08.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DeleteCityInteractorTest {
    private DeleteCityInteractor deleteCityInteractor;

    @Mock
    private LocationProvider locationProvider;

    private TestScheduler testScheduler;
    private City city;
    private City cityOther;

    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
        deleteCityInteractor = new DeleteCityInteractor(locationProvider, testScheduler, testScheduler);
        city = City.create(Location.builder().longitude(0).latitude(0).build(), "name", "id");
        cityOther = City.create(Location.builder().longitude(10).latitude(0.5).build(), "name2", "id2");
    }

    @Test
    public void shouldJustDeleteCity() throws Exception {
        when(locationProvider.getCurrentCity()).thenReturn(Observable.just(cityOther));
        when(locationProvider.deleteFavoriteCity(any())).thenReturn(Completable.complete());

        TestObserver<Void> voidTestObserver = deleteCityInteractor.run(city).test();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        voidTestObserver.assertComplete();
        verify(locationProvider).deleteFavoriteCity(city);
    }

    @Test
    public void shouldDeleteCityAndChooseNewOne() throws Exception{
        when(locationProvider.getCurrentCity()).thenReturn(Observable.just(city));
        when(locationProvider.getFavoriteCities()).thenReturn(Single.just(new ArrayList<City>(){{add(cityOther);}}));
        when(locationProvider.deleteFavoriteCity(any())).thenReturn(Completable.complete());
        when(locationProvider.setCurrentCity(any())).thenReturn(Completable.complete());

        TestObserver<Void> voidTestObserver = deleteCityInteractor.run(city).test();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        voidTestObserver.assertComplete();
        verify(locationProvider).deleteFavoriteCity(city);
        verify(locationProvider).setCurrentCity(cityOther);
    }

}