package com.kondenko.yamblzweather.data.weather;

import com.kondenko.yamblzweather.domain.entity.WeatherConditions;

/**
 * Created by Mishkun on 09.08.2017.
 */

class WeatherConditionMapper {
    static WeatherConditions codeToCondition(int code) {
        if (code >= 200 && code < 300) {
            return WeatherConditions.THUNDERSTORM;
        } else if (code >= 300 && code < 400) {
            return WeatherConditions.DRIZZLE;
        } else if (code >= 500 && code < 600) {
            return WeatherConditions.RAIN;
        } else if (code >= 600 && code < 700) {
            return WeatherConditions.SNOW;
        } else if (code >= 700 && code < 800) {
            return WeatherConditions.FOG;
        } else if (code >= 801 && code < 803) {
            return WeatherConditions.PARTLY_CLOUDY;
        } else if (code >= 803 && code < 900) {
            return WeatherConditions.CLOUDY;
        } else {
            return WeatherConditions.CLEAR;
        }
    }
}
