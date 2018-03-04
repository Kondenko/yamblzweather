package com.kondenko.yamblzweather.ui.weather;

import android.support.annotation.StringRes;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.domain.entity.Temperature;
import com.kondenko.yamblzweather.domain.entity.WeatherConditions;

/**
 * Created by Mishkun on 10.08.2017.
 */

class TitleMapper {
    private final static int COLD_LINE = 5;
    private final static int WARM_LINE = 30;
    private final static int HUMIDITY_STUFFY_LINE = 75;

    private final static int[] rains = {R.string.cold_rain, R.string.just_rain, R.string.hot_rain};
    private final static int[] cloudy = {R.string.cold_cloudy, R.string.just_cloudy, R.string.hot_cloudy};
    private final static int[] partly_cloudy = {R.string.cold_cloudy, R.string.just_cloudy, R.string.hot_cloudy};
    private final static int[] thunderstorm = {R.string.cold_thunder, R.string.just_thunder, R.string.hot_thunder};
    private final static int[] fog = {R.string.cold_fog, R.string.just_fog, R.string.hot_fog};
    private final static int[] clear = {R.string.cold_clear, R.string.just_clear, R.string.hot_clear};
    private final static int[] clear_stuffy = {R.string.cold_clear_stuffy, R.string.just_clear_stuffy, R.string.hot_clear_stuffy};
    private final static int[] drizzle = {R.string.cold_drizzle, R.string.just_drizzle, R.string.hot_drizzle};
    private final static int[] snow = {R.string.cold_snow, R.string.just_snow, R.string.hot_snow};

    @StringRes
    public static int map(Temperature temperature, WeatherConditions weatherConditions, double humidity) {
        int temp = temperature.celsiusDegrees() > COLD_LINE ? (temperature.celsiusDegrees() > WARM_LINE ? 2 : 1) : 0;
        boolean stuffy = humidity > HUMIDITY_STUFFY_LINE;
        switch (weatherConditions) {
            case RAIN:
                return rains[temp];
            case CLOUDY:
                return cloudy[temp];
            case PARTLY_CLOUDY:
                return partly_cloudy[temp];
            case THUNDERSTORM:
                return thunderstorm[temp];
            case FOG:
                return fog[temp];
            case CLEAR:
                return stuffy ? clear_stuffy[temp] : clear[temp];
            case DRIZZLE:
                return drizzle[temp];
            case SNOW:
                return snow[temp];
            default:
                return R.string.default_weather;
        }
    }
}
