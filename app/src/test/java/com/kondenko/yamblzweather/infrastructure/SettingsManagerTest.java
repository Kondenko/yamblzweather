package com.kondenko.yamblzweather.infrastructure;

import android.content.Context;
import android.content.SharedPreferences;

import com.kondenko.yamblzweather.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 22.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class SettingsManagerTest {
    private final String PACKAGE_NAME = "TEST_PACKAGE";
    @Mock
    Context context;

    @Mock
    SharedPreferences sharedPreferences;

    @Mock
    SharedPreferences.Editor editor;

    @Before
    public void setUp() throws Exception {
        when(context.getPackageName()).thenReturn(PACKAGE_NAME);
        when(context.getSharedPreferences(PACKAGE_NAME + "_preferences", Context.MODE_PRIVATE)).thenReturn(sharedPreferences);
    }


    private void setUpRefreshRate(String key, String testValue) {
        when(context.getString(R.string.pref_key_refresh_rate)).thenReturn(key);
        when(sharedPreferences.getString(key, "2")).thenReturn(testValue);
    }

    @Test
    public void getRefreshRateHr() throws Exception {
        String key = "key";
        String testValue = "11";
        setUpRefreshRate(key, testValue);

        SettingsManager settingsManager = new SettingsManager(context);
        assertEquals(settingsManager.getRefreshRateHr(), Integer.parseInt(testValue));
    }


    @Test
    public void getRefreshRateSec() throws Exception {
        String key = "key";
        String testValue = "1";
        setUpRefreshRate(key, testValue);

        SettingsManager settingsManager = new SettingsManager(context);
        assertEquals(settingsManager.getRefreshRateSec(), Integer.parseInt(testValue) * 60 * 60);
    }


}