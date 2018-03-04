package com.kondenko.yamblzweather.ui.citysuggest;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Location;
import com.kondenko.yamblzweather.domain.entity.Prediction;
import com.kondenko.yamblzweather.domain.usecase.DeleteCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.FetchCityCoordinatesInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetCitySuggestsInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetFavoredCitiesInteractor;
import com.kondenko.yamblzweather.domain.usecase.SetCurrentCityInteractor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 12.08.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SuggestsPresenterTest {

    private static final String NAME = "NAME";
    private static final String PLACE_ID = "ID";
    private static final java.lang.String EMPTY = "";
    private SuggestsPresenter suggestsPresenter;
    @Mock
    private GetCitySuggestsInteractor getCitySuggestsInteractor;
    @Mock
    private FetchCityCoordinatesInteractor fetchCityCoordinatesInteractor;
    @Mock
    private GetFavoredCitiesInteractor getFavoredCitiesInteractor;
    @Mock
    private SetCurrentCityInteractor setCurrentCityInteractor;
    @Mock
    private DeleteCityInteractor deleteCityInteractor;
    @Mock
    private GetCurrentCityInteractor getCurrentCityInteractor;
    @Mock
    private SuggestsView view;
    private City city;
    private Prediction prediction;
    private List<Prediction> predictions;
    private List<City> cities;

    @Before
    public void setUp() throws Exception {
        suggestsPresenter = new SuggestsPresenter(getCitySuggestsInteractor, fetchCityCoordinatesInteractor, getFavoredCitiesInteractor,
                                                  setCurrentCityInteractor,
                                                  deleteCityInteractor, getCurrentCityInteractor);
        city = City.create(Location.builder().longitude(0).latitude(0).build(), NAME, PLACE_ID);
        prediction = Prediction.create(NAME, PLACE_ID);
        predictions = new ArrayList<Prediction>() {{
            add(prediction);
            add(prediction);
        }};
        cities = new ArrayList<City>() {{
            add(city);
            add(city);
        }};
    }

    @Test
    public void shouldSubscribeToView() throws Exception {
        when(view.getCitiesDeletionsClicks()).thenReturn(Observable.never());
        when(view.getCitiesClicks()).thenReturn(Observable.never());
        when(view.getSuggestsClicks()).thenReturn(Observable.never());
        when(view.getCityNamesStream()).thenReturn(Observable.just(NAME));
        when(getCitySuggestsInteractor.run(anyString())).thenReturn(Single.just(predictions));
        InOrder loadingInOrder = inOrder(view, getCitySuggestsInteractor);

        suggestsPresenter.attachView(view);

        loadingInOrder.verify(view).showLoading(true);
        loadingInOrder.verify(getCitySuggestsInteractor).run(NAME);
        loadingInOrder.verify(view).showLoading(false);

        verify(view).setData(SuggestsViewModel.createWithPredictions(predictions));
    }

    @Test
    public void shouldReSubOnError() throws Exception {
        when(view.getCitiesDeletionsClicks()).thenReturn(Observable.never());
        when(view.getCitiesClicks()).thenReturn(Observable.never());
        when(view.getSuggestsClicks()).thenReturn(Observable.never());
        String ERROR = "ERROR";
        PublishSubject<String> subject = PublishSubject.create();
        when(view.getCityNamesStream()).thenReturn(subject);
        when(getCitySuggestsInteractor.run(eq(NAME))).thenReturn(Single.just(predictions));
        when(getCitySuggestsInteractor.run(eq(ERROR))).thenReturn(Single.error(new Throwable()));

        suggestsPresenter.attachView(view);
        subject.onNext(ERROR);
        subject.onNext(NAME);
        subject.onNext(NAME);

        verify(view).setData(SuggestsViewModel.createWithPredictions(predictions));
        verify(view).showError(any());
    }

    @Test
    public void shouldSubscribeOnEmptyStrings() throws Exception {
        when(view.getCitiesDeletionsClicks()).thenReturn(Observable.never());
        when(view.getCitiesClicks()).thenReturn(Observable.never());
        when(view.getSuggestsClicks()).thenReturn(Observable.never());
        when(view.getCityNamesStream()).thenReturn(Observable.just(EMPTY));
        when(getFavoredCitiesInteractor.run()).thenReturn(Single.just(cities));
        when(getCurrentCityInteractor.run()).thenReturn(Observable.just(city));

        suggestsPresenter.attachView(view);

        verify(view).setData(SuggestsViewModel.createWithCities(cities, city));
    }

    @Test
    public void shouldSubscribeCitiesClicks() throws Exception {
        when(view.getCitiesDeletionsClicks()).thenReturn(Observable.never());
        when(view.getCityNamesStream()).thenReturn(Observable.never());
        when(view.getSuggestsClicks()).thenReturn(Observable.never());
        when(view.getCitiesClicks()).thenReturn(Observable.just(city));
        when(setCurrentCityInteractor.run(city)).thenReturn(Completable.complete());

        suggestsPresenter.attachView(view);

        verify(view).finishScreen();
    }

    @Test
    public void shouldSubscribeDeleteCitiesClicks() throws Exception {
        when(view.getCityNamesStream()).thenReturn(Observable.never());
        when(view.getSuggestsClicks()).thenReturn(Observable.never());
        when(view.getCitiesClicks()).thenReturn(Observable.never());
        when(view.getCitiesDeletionsClicks()).thenReturn(Observable.just(city));
        when(deleteCityInteractor.run(city)).thenReturn(Completable.complete());
        when(getFavoredCitiesInteractor.run()).thenReturn(Single.just(cities));
        when(getCurrentCityInteractor.run()).thenReturn(Observable.just(city));

        suggestsPresenter.attachView(view);

        verify(view).setData(SuggestsViewModel.createWithCities(cities, city));
    }

    @Test
    public void shouldSubscribeSuggestsClicks() throws Exception {
        when(view.getCityNamesStream()).thenReturn(Observable.never());
        when(view.getCitiesClicks()).thenReturn(Observable.never());
        when(view.getCitiesDeletionsClicks()).thenReturn(Observable.never());
        when(view.getSuggestsClicks()).thenReturn(Observable.just(prediction));
        when(fetchCityCoordinatesInteractor.run(prediction)).thenReturn(Completable.complete());

        suggestsPresenter.attachView(view);

        verify(view).finishScreen();
    }

    @Test
    public void shouldResubOnSuggestsClicks() throws Exception {
        when(view.getCityNamesStream()).thenReturn(Observable.never());
        when(view.getCitiesClicks()).thenReturn(Observable.never());
        when(view.getCitiesDeletionsClicks()).thenReturn(Observable.never());
        Prediction ERROR = Prediction.create("ERROR", "ERROR");
        PublishSubject<Prediction> subject = PublishSubject.create();
        when(view.getSuggestsClicks()).thenReturn(subject);
        when(fetchCityCoordinatesInteractor.run(ERROR)).thenReturn(Completable.error(new Throwable()));
        when(fetchCityCoordinatesInteractor.run(prediction)).thenReturn(Completable.complete());

        suggestsPresenter.attachView(view);
        subject.onNext(ERROR);
        subject.onNext(prediction);

        verify(view).showError(any());
        verify(view).finishScreen();
    }
}