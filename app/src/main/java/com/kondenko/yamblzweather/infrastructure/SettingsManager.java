package com.kondenko.yamblzweather.infrastructure;


import android.content.Context;
import android.content.SharedPreferences;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.domain.entity.TempUnit;
import com.kondenko.yamblzweather.domain.guards.TemperatureUnitProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SettingsManager implements TemperatureUnitProvider {

    private static final String PREF_REFRESH_RATE_DEFAULT_HOURS = "2";

    private final Context context;
    private final SharedPreferences preferences;

    @Inject
    public SettingsManager(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(context.getPackageName() + "_preferences", Context.MODE_PRIVATE);
    }

    @Override
    public TempUnit getUnitKey() {
        String unitKey = context.getString(R.string.pref_key_temp_unit);
        String defaultUnit = context.getString(R.string.pref_key_unit_celsius);
        String unit = preferences.getString(unitKey, defaultUnit);
        if (unit.equals(context.getString(R.string.pref_key_unit_fahrenheit)))
            return TempUnit.IMPERIAL;
        if (unit.equals(context.getString(R.string.pref_key_unit_celsius)))
            return TempUnit.METRIC;
        if (unit.equals(context.getString(R.string.pref_key_unit_kelvin)))
            return TempUnit.SCIENTIFIC;
        throw new IllegalArgumentException("Wrong temperature unit");
    }

    public int getRefreshRateHr() {
        String rateKey = context.getString(R.string.pref_key_refresh_rate);
        return Integer.parseInt(preferences.getString(rateKey, PREF_REFRESH_RATE_DEFAULT_HOURS));
    }

    public long getRefreshRateSec() {
        return TimeUnit.HOURS.toSeconds(getRefreshRateHr());
    }

}
