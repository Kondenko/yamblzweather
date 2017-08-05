package com.kondenko.yamblzweather.ui.citysuggest;

import com.kondenko.yamblzweather.data.suggest.CitySuggest;
import com.kondenko.yamblzweather.data.suggest.CitiesSuggestService;
import com.kondenko.yamblzweather.domain.usecase.GetCitySuggestsInteractor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 29.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetCitySuggestsInteractorTest {

    private static final String QUERY = "QUERY";
    @Mock
    private CitiesSuggestService citiesSuggestService;

    private TestScheduler testScheduler = new TestScheduler();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void shouldGetCitySuggests() throws Exception {
        CitySuggest citySuggest = mock(CitySuggest.class);
        when(citiesSuggestService.getSuggests(eq(QUERY), anyString())).thenReturn(Single.just(citySuggest));
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        verify(citiesSuggestService).getSuggests(eq(QUERY), anyString());
        verifyZeroInteractions(citySuggest);
    }

}