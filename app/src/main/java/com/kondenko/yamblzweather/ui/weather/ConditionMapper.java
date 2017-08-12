package com.kondenko.yamblzweather.ui.weather;

import android.support.annotation.DrawableRes;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.domain.entity.WeatherConditions;

/**
 * Created by Mishkun on 10.08.2017.
 */
class ConditionMapper {
    @DrawableRes
    static int map(WeatherConditions condition) {
        int dresource = R.drawable.sun;
        switch (condition) {
            case RAIN:
                dresource = R.drawable.rain;
                break;
            case CLOUDY:
                dresource = R.drawable.cloudy;
                break;
            case PARTLY_CLOUDY:
                dresource = R.drawable.partly_cloudy;
                break;
            case THUNDERSTORM:
                dresource = R.drawable.thunderstorm;
                break;
            case FOG:
                dresource = R.drawable.fog;
                break;
            case CLEAR:
                dresource = R.drawable.sun;
                break;
            case DRIZZLE:
                dresource = R.drawable.drizzle;
                break;
            case SNOW:
                dresource = R.drawable.snowflake;
                break;
        }
        return dresource;
    }
    @DrawableRes
    static int mapDark(WeatherConditions condition) {
        int dresource = R.drawable.sun_dark;
        switch (condition) {
            case RAIN:
                dresource = R.drawable.rain_dark;
                break;
            case CLOUDY:
                dresource = R.drawable.cloudy_dark;
                break;
            case PARTLY_CLOUDY:
                dresource = R.drawable.partly_cloudy_dark;
                break;
            case THUNDERSTORM:
                dresource = R.drawable.thunderstorm_dark;
                break;
            case FOG:
                dresource = R.drawable.fog_dark;
                break;
            case CLEAR:
                dresource = R.drawable.sun_dark;
                break;
            case DRIZZLE:
                dresource = R.drawable.drizzle_dark;
                break;
            case SNOW:
                dresource = R.drawable.snowflake_dark;
                break;
        }
        return dresource;
    }
}
