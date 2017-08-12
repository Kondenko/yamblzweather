package com.kondenko.yamblzweather.infrastructure;

import android.content.Context;
import android.content.SharedPreferences;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;

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


    @Test
    public void setLatestUpdate() throws Exception {
        long testMs = 123;
        when(sharedPreferences.edit()).thenReturn(editor);
        when(editor.putLong("latestUpdate", testMs)).thenReturn(editor);

        SettingsManager settingsManager = new SettingsManager(context);
        settingsManager.setLatestUpdate(testMs);
        verify(sharedPreferences).edit();
        verify(editor).putLong("latestUpdate", testMs);
        verify(editor).apply();
    }

    @Test
    public void getLatestUpdateTime() throws Exception {
        long testMs = 123;
        when(sharedPreferences.getLong("latestUpdate", 0)).thenReturn(testMs);

        SettingsManager settingsManager = new SettingsManager(context);
        assertEquals(settingsManager.getLatestUpdateTime(), testMs);
    }

}