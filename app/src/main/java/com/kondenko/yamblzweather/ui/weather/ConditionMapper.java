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
        int drawable = R.drawable.sun;
        switch (condition) {
            case RAIN:
                drawable = R.drawable.rain;
                break;
            case CLOUDY:
                drawable = R.drawable.cloudy;
                break;
            case PARTLY_CLOUDY:
                drawable = R.drawable.partly_cloudy;
                break;
            case THUNDERSTORM:
                drawable = R.drawable.thunderstorm;
                break;
            case FOG:
                drawable = R.drawable.fog;
                break;
            case CLEAR:
                drawable = R.drawable.sun;
                break;
            case DRIZZLE:
                drawable = R.drawable.drizzle;
                break;
            case SNOW:
                drawable = R.drawable.snowflake;
                break;
        }
        return drawable;
    }
    @DrawableRes
    static int mapDark(WeatherConditions condition) {
        int drawable = R.drawable.sun_dark;
        switch (condition) {
            case RAIN:
                drawable = R.drawable.rain_dark;
                break;
            case CLOUDY:
                drawable = R.drawable.cloudy_dark;
                break;
            case PARTLY_CLOUDY:
                drawable = R.drawable.partly_cloudy_dark;
                break;
            case THUNDERSTORM:
                drawable = R.drawable.thunderstorm_dark;
                break;
            case FOG:
                drawable = R.drawable.fog_dark;
                break;
            case CLEAR:
                drawable = R.drawable.sun_dark;
                break;
            case DRIZZLE:
                drawable = R.drawable.drizzle_dark;
                break;
            case SNOW:
                drawable = R.drawable.snowflake_dark;
                break;
        }
        return drawable;
    }
}
