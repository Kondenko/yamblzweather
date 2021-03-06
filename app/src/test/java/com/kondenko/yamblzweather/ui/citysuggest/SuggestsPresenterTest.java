package com.kondenko.yamblzweather.ui.citysuggest;

import com.kondenko.yamblzweather.model.entity.CitySuggest;
import com.kondenko.yamblzweather.model.entity.Prediction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.subjects.PublishSubject;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 29.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SuggestsPresenterTest {
    private static final String CITY_NAME = "CITY";
    @Mock
    private CitySuggestInteractor citySuggestInteractor;

    @Mock
    private FetchCityCoords fetchCityCoords;

    @Mock
    private SuggestsView view;

    @Mock
    private CitySuggest citySuggest;

    @Mock
    private Prediction prediction;

    private PublishSubject<Prediction> clicksSubject;
    private PublishSubject<String> cityNamesStream;

    @Mock
    private Throwable throwable;

    @Before
    public void setUp() throws Exception {
        clicksSubject = PublishSubject.create();
        cityNamesStream = PublishSubject.create();


        when(view.getClicks()).thenReturn(clicksSubject);
        when(view.getCityNamesStream()).thenReturn(cityNamesStream);
    }

    @Test
    public void shouldShowLoadingAndLoadCitySuggestsData() throws Exception {
        InOrder inViewOrder = inOrder(view, citySuggestInteractor);
        when(citySuggestInteractor.getCitySuggests(CITY_NAME)).thenReturn(Single.just(citySuggest));

        SuggestsPresenter suggestsPresenter = new SuggestsPresenter(citySuggestInteractor, fetchCityCoords);
        suggestsPresenter.attachView(view);
        verify(view, atLeastOnce()).getCityNamesStream();

        cityNamesStream.onNext(CITY_NAME);

        inViewOrder.verify(view).showLoading(true);
        inViewOrder.verify(citySuggestInteractor).getCitySuggests(CITY_NAME);
        verifyNoMoreInteractions(citySuggestInteractor);
        inViewOrder.verify(view).showLoading(false);

        verify(view).setData(citySuggest);

        verify(view, never()).showError(any());
        verifyZeroInteractions(citySuggest);
        verifyZeroInteractions(fetchCityCoords);
    }

    @Test
    public void shouldShowErrorWhenTryingToLoadCitySuggests() throws Exception {
        when(citySuggestInteractor.getCitySuggests(CITY_NAME)).thenReturn(Single.error(throwable));

        SuggestsPresenter suggestsPresenter = new SuggestsPresenter(citySuggestInteractor, fetchCityCoords);
        suggestsPresenter.attachView(view);

        cityNamesStream.onNext(CITY_NAME);

        verify(view).showError(throwable);
        verify(view, never()).setData(any());

        verifyZeroInteractions(citySuggest);
        verifyZeroInteractions(fetchCityCoords);
    }

    @Test
    public void shouldCheckCityAndFinishFragment() throws Exception {
        when(fetchCityCoords.getCityCoordinatesAndWrite(prediction)).thenReturn(Completable.complete());

        SuggestsPresenter suggestsPresenter = new SuggestsPresenter(citySuggestInteractor, fetchCityCoords);
        suggestsPresenter.attachView(view);

        verify(view, atLeastOnce()).getClicks();

        clicksSubject.onNext(prediction);

        verify(fetchCityCoords).getCityCoordinatesAndWrite(prediction);
        verify(view).finishScreen();
        verify(view, never()).showError(any());

        verifyZeroInteractions(prediction);
        verifyZeroInteractions(citySuggestInteractor);
    }

    @Test
    public void shouldShowErrorWhenTryingToLoadCity() throws Exception {
        when(fetchCityCoords.getCityCoordinatesAndWrite(prediction)).thenReturn(Completable.error(throwable));

        SuggestsPresenter suggestsPresenter = new SuggestsPresenter(citySuggestInteractor, fetchCityCoords);
        suggestsPresenter.attachView(view);

        clicksSubject.onNext(prediction);

        verify(fetchCityCoords).getCityCoordinatesAndWrite(prediction);
        verify(view, never()).finishScreen();
        verify(view).showError(throwable);

        verifyZeroInteractions(prediction);
        verifyZeroInteractions(citySuggestInteractor);
    }
}