package com.kondenko.yamblzweather.infrastructure;


import android.content.Context;
import android.content.SharedPreferences;

import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.R;

import java.util.concurrent.TimeUnit;

public class SettingsManager {

    private static final String KEY_SELECTED_CITY = "selected_city";

    private static final String KEY_LATEST_UPDATE = "latestUpdate";

    private Context context;
    private SharedPreferences preferences;

    public SettingsManager(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(context.getPackageName() + "_preferences", Context.MODE_PRIVATE);
    }

    public String getUnitKey() {
        String unitKey = context.getString(R.string.pref_key_temp_unit);
        String defaultUnit = context.getString(R.string.pref_key_unit_kelvin);
        return preferences.getString(unitKey, defaultUnit);
    }

    public String getUnitValue() {
        String selectedUnitKey = getUnitKey();
        if (selectedUnitKey.equals(context.getString(R.string.pref_key_unit_fahrenheit)))
            return Const.VALUE_UNIT_TEMP_IMPERIAL;
        if (selectedUnitKey.equals(context.getString(R.string.pref_key_unit_celsius)))
            return Const.VALUE_UNIT_TEMP_METRIC;
        if (selectedUnitKey.equals(context.getString(R.string.pref_key_unit_kelvin)))
            return Const.VALUE_UNIT_TEMP_DEFAULT;
        throw new IllegalArgumentException("Wrong temperature unit");
    }

    public int getRefreshRateHr() {
        String rateKey = context.getString(R.string.pref_key_refresh_rate);
        String defaultRate = Const.PREF_REFRESH_RATE_DEFAULT_HOURS;
        return Integer.parseInt(preferences.getString(rateKey, defaultRate));
    }

    public long getRefreshRateSec() {
        return TimeUnit.HOURS.toSeconds(getRefreshRateHr());
    }

    public String getCity() {
        return preferences.getString(KEY_SELECTED_CITY, Const.ID_MOSCOW);
    }

    public void setCity(String cityId) {
        preferences.edit().putString(KEY_SELECTED_CITY, cityId).apply();
    }

    public void setLatestUpdate(long timeMs) {
        preferences.edit().putLong(KEY_LATEST_UPDATE, timeMs).apply();
    }

    public long getLatestUpdateTime() {
        return preferences.getLong(KEY_LATEST_UPDATE, 0);
    }

}
