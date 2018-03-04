package com.kondenko.yamblzweather.infrastructure;

import com.evernote.android.job.Job;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.UpdateWeatherInteractor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

/**
 * Created by Mishkun on 25.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AppJobCreatorTest {

    @Mock
    SettingsManager settingsManager;

    @Mock
    UpdateWeatherInteractor updateWeatherInteractor;

    @Mock
    GetCurrentCityInteractor getCurrentCityInteractor;

    @Test
    public void shouldCreateWeatherJob() throws Exception {
        AppJobCreator appJobCreator = new AppJobCreator(updateWeatherInteractor, getCurrentCityInteractor);
        Job updateJob = appJobCreator.create(UpdateWeatherJob.TAG);
        assertTrue(updateJob instanceof UpdateWeatherJob);
    }

}