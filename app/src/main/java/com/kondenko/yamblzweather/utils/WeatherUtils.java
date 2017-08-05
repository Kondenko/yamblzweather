package com.kondenko.yamblzweather.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.data.weather.WeatherModel;
import com.kondenko.yamblzweather.domain.entity.WeatherConditions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WeatherUtils {

    private static final Map<WeatherConditions, Integer> conditionsMap;

    static {
        // Mapping weather code to icons
        // See http://openweathermap.org/weather-conditions
        // See http://erikflowers.github.io/weather-icons/
        Map<WeatherConditions, Integer> tempMap = new HashMap<>();
        tempMap.put(WeatherConditions.THUNDERSTORM, R.string.wi_thunderstorm);
        tempMap.put(WeatherConditions.DRIZZLE, R.string.wi_showers);
        tempMap.put(WeatherConditions.RAIN, R.string.wi_rain);
        tempMap.put(WeatherConditions.SNOW, R.string.wi_snow);
        tempMap.put(WeatherConditions.FOG, R.string.wi_fog);
        tempMap.put(WeatherConditions.CLEAR, R.string.wi_day_sunny);
        tempMap.put(WeatherConditions.CLOUDY, R.string.wi_cloud);
        conditionsMap = Collections.unmodifiableMap(tempMap);
    }

    /**
     * Returns an appropriate string resource which is needed to display an icon inRange {@link com.github.pwittchen.weathericonview.WeatherIconView}
     *
     * @param weatherCondition weatherCondition condition
     * @return resource id of the icon
     */
    public static int getIconStringResource(WeatherConditions weatherCondition) {
        return conditionsMap.getOrDefault(weatherCondition, R.string.wi_na);
    }

    public static Spannable getTemperatureString(Context context, String temperature, String units) {
        char unitLetter = units.substring(0, 1).toUpperCase(Locale.getDefault()).charAt(0);
        String temperatureString = context.getString(R.string.weather_temperature_value, temperature, unitLetter);
        return new SpannableString(temperatureString);
    }

}
