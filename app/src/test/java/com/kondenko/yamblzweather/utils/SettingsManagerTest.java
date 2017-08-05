package com.kondenko.yamblzweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.kondenko.yamblzweather.Const;
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

    @Test
    public void getShouldGetUnitKey() throws Exception {
        String keypref = "test_key";
        String defaultpref = "default";
        String answer = "???";
        when(context.getString(R.string.pref_key_temp_unit)).thenReturn(keypref);
        when(context.getString(R.string.pref_key_unit_kelvin)).thenReturn(defaultpref);
        when(sharedPreferences.getString(keypref, defaultpref)).thenReturn(answer);


        SettingsManager settingsManager = new SettingsManager(context);
        assertEquals(settingsManager.getUnitKey(), answer);
    }

    @Test
    public void shouldGetRightUnits() throws Exception {
        String celsius = "c";
        String fahrenheit = "f";
        String kelvin = "k";
        String tempUnit = "temp";
        when(context.getString(R.string.pref_key_unit_kelvin)).thenReturn(kelvin);
        when(context.getString(R.string.pref_key_unit_fahrenheit)).thenReturn(fahrenheit);
        when(context.getString(R.string.pref_key_unit_celsius)).thenReturn(celsius);
        when(context.getString(R.string.pref_key_temp_unit)).thenReturn(tempUnit);

        SettingsManager settingsManager = new SettingsManager(context);

        when(sharedPreferences.getString(tempUnit, kelvin)).thenReturn(kelvin);
        assertEquals(settingsManager.getUnitValue(), Const.VALUE_UNIT_TEMP_DEFAULT);

        when(sharedPreferences.getString(tempUnit, kelvin)).thenReturn(fahrenheit);
        assertEquals(settingsManager.getUnitValue(), Const.VALUE_UNIT_TEMP_IMPERIAL);

        when(sharedPreferences.getString(tempUnit, kelvin)).thenReturn(celsius);
        assertEquals(settingsManager.getUnitValue(), Const.VALUE_UNIT_TEMP_METRIC);
    }


    private void setUpRefreshRate(String key, String testValue) {
        when(context.getString(R.string.pref_key_refresh_rate)).thenReturn(key);
        when(sharedPreferences.getString(key, Const.PREF_REFRESH_RATE_DEFAULT_HOURS)).thenReturn(testValue);
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
    public void getCity() throws Exception {
        String testCity = "test";
        when(sharedPreferences.getString("selected_city", Const.ID_MOSCOW)).thenReturn(testCity);

        SettingsManager settingsManager = new SettingsManager(context);
        assertEquals(settingsManager.getCity(), testCity);
    }

    @Test
    public void setCity() throws Exception {
        String testCity = "test";
        when(sharedPreferences.edit()).thenReturn(editor);
        when(editor.putString("selected_city", testCity)).thenReturn(editor);

        SettingsManager settingsManager = new SettingsManager(context);
        settingsManager.setCity(testCity);
        verify(sharedPreferences).edit();
        verify(editor).putString("selected_city", testCity);
        verify(editor).apply();
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