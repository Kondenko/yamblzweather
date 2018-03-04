package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.entity.TempUnit;
import com.kondenko.yamblzweather.domain.guards.TemperatureUnitProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 11.08.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetUnitsInteractorTest {
    private TestScheduler testScheduler;
    private GetUnitsInteractor getUnitsInteractor;
    @Mock
    private TemperatureUnitProvider temperatureUnitProvider;

    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
        getUnitsInteractor = new GetUnitsInteractor(testScheduler, testScheduler, temperatureUnitProvider);
    }

    @Test
    public void run() throws Exception {
        when(temperatureUnitProvider.getUnitKey()).thenReturn(TempUnit.IMPERIAL);

        TestObserver<TempUnit> tempUnitTestObserver = getUnitsInteractor.run().test();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        tempUnitTestObserver.assertResult(TempUnit.IMPERIAL);
    }

}