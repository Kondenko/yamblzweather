package com.kondenko.yamblzweather.infrastructure;


import android.content.Context;
import android.content.SharedPreferences;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.domain.entity.TempUnit;

import java.util.concurrent.TimeUnit;

public class SettingsManager {

    private static final String KEY_SELECTED_CITY = "selected_city";
    private static final String PREF_REFRESH_RATE_DEFAULT_HOURS = "2";
    private static final String KEY_LATEST_UPDATE = "latestUpdate";

    private final Context context;
    private final SharedPreferences preferences;

    public SettingsManager(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(context.getPackageName() + "_preferences", Context.MODE_PRIVATE);
    }

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
        String defaultRate = PREF_REFRESH_RATE_DEFAULT_HOURS;
        return Integer.parseInt(preferences.getString(rateKey, defaultRate));
    }

    public long getRefreshRateSec() {
        return TimeUnit.HOURS.toSeconds(getRefreshRateHr());
    }

    public void setLatestUpdate(long timeMs) {
        preferences.edit().putLong(KEY_LATEST_UPDATE, timeMs).apply();
    }

    public long getLatestUpdateTime() {
        return preferences.getLong(KEY_LATEST_UPDATE, 0);
    }

}
