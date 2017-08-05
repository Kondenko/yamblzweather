package com.kondenko.yamblzweather.ui.citysuggest;

import com.kondenko.yamblzweather.data.suggest.CitySuggest;
import com.kondenko.yamblzweather.data.suggest.PredictionResponse;
import com.kondenko.yamblzweather.domain.usecase.FetchCityCoordsInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetCitySuggestsInteractor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
    private GetCitySuggestsInteractor getCitySuggestsInteractor;

    @Mock
    private FetchCityCoordsInteractor fetchCityCoordsInteractor;

    @Mock
    private SuggestsView view;

    @Mock
    private CitySuggest citySuggest;

    @Mock
    private PredictionResponse predictionResponse;

    private PublishSubject<PredictionResponse> clicksSubject;
    private PublishSubject<String> cityNamesStream;

    @Mock
    private Throwable throwable;

    @Before
    public void setUp() throws Exception {
        clicksSubject = PublishSubject.create();
        cityNamesStream = PublishSubject.create();


        when(view.getCityNamesStream()).thenReturn(cityNamesStream);
    }

    @Test
    public void shouldShowLoadingAndLoadCitySuggestsData() throws Exception {
        InOrder inViewOrder = inOrder(view, getCitySuggestsInteractor);
        verify(view, atLeastOnce()).getCityNamesStream();

        cityNamesStream.onNext(CITY_NAME);

        inViewOrder.verify(view).showLoading(true);
        inViewOrder.verify(getCitySuggestsInteractor).run(CITY_NAME);
        verifyNoMoreInteractions(getCitySuggestsInteractor);
        inViewOrder.verify(view).showLoading(false);


        verify(view, never()).showError(any());
        verifyZeroInteractions(citySuggest);
        verifyZeroInteractions(fetchCityCoordsInteractor);
    }

    @Test
    public void shouldShowErrorWhenTryingToLoadCitySuggests() throws Exception {
        when(getCitySuggestsInteractor.run(CITY_NAME)).thenReturn(Single.error(throwable));


        cityNamesStream.onNext(CITY_NAME);

        verify(view).showError(throwable);
        verify(view, never()).setData(any());

        verifyZeroInteractions(citySuggest);
        verifyZeroInteractions(fetchCityCoordsInteractor);
    }

    @Test
    public void shouldCheckCityAndFinishFragment() throws Exception {


        verify(view, atLeastOnce()).getClicks();

        clicksSubject.onNext(predictionResponse);

        verify(view).finishScreen();
        verify(view, never()).showError(any());

        verifyZeroInteractions(predictionResponse);
        verifyZeroInteractions(getCitySuggestsInteractor);
    }

    @Test
    public void shouldShowErrorWhenTryingToLoadCity() throws Exception {



        clicksSubject.onNext(predictionResponse);

        verify(view, never()).finishScreen();
        verify(view).showError(throwable);

        verifyZeroInteractions(predictionResponse);
        verifyZeroInteractions(getCitySuggestsInteractor);
    }
}