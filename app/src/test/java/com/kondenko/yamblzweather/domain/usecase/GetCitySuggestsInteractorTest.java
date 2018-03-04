package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.entity.Prediction;
import com.kondenko.yamblzweather.domain.guards.CitySuggestProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 11.08.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetCitySuggestsInteractorTest {

    private static final String QUERY = "QUERY";
    private GetCitySuggestsInteractor getCitySuggestsInteractor;
    @Mock
    private CitySuggestProvider citySuggestProvider;
    private TestScheduler testScheduler;

    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
        getCitySuggestsInteractor = new GetCitySuggestsInteractor(testScheduler, testScheduler, citySuggestProvider);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldGetCitySuggests() throws Exception {
        when(citySuggestProvider.getCitySuggests(QUERY)).thenReturn(Single.just(new ArrayList<>()));

        TestObserver<List<Prediction>> listTestObserver = getCitySuggestsInteractor.run(QUERY).test();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        listTestObserver.assertResult(new ArrayList<>());
    }

}