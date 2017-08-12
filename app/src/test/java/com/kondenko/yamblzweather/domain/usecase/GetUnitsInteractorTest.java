package com.kondenko.yamblzweather.domain.usecase;

import com.kondenko.yamblzweather.domain.entity.TempUnit;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;

import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 11.08.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetUnitsInteractorTest {
    private TestScheduler testScheduler;
    private GetUnitsInteractor getUnitsInteractor;
    @Mock
    private SettingsManager settingsManager;

    @Before
    public void setUp() throws Exception {
        testScheduler = new TestScheduler();
        getUnitsInteractor = new GetUnitsInteractor(testScheduler, testScheduler, settingsManager);
    }

    @Test
    public void run() throws Exception {
        when(settingsManager.getUnitKey()).thenReturn(TempUnit.IMPERIAL);

        TestObserver<TempUnit> tempUnitTestObserver = getUnitsInteractor.run().test();
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        tempUnitTestObserver.assertResult(TempUnit.IMPERIAL);
    }

}