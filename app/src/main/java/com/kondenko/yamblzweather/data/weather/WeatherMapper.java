package com.kondenko.yamblzweather.data.weather;

import com.kondenko.yamblzweather.domain.entity.Temperature;
import com.kondenko.yamblzweather.domain.entity.Weather;
import com.kondenko.yamblzweather.domain.entity.WeatherConditions;

/**
 * Created by Mishkun on 04.08.2017.
 */

class WeatherMapper {
    static Weather.Builder responseToDomain(WeatherModel weatherModel) {
        return Weather.builder()
                      .humidity(weatherModel.getMain().getHumidity())
                      .pressure(hPaToMmHg(weatherModel.getMain().getPressure()))
                      .temperature(Temperature.valueOfKelvin(weatherModel.getMain().getTemp()))
                      .windSpeed(weatherModel.getWind().getSpeed())
                      .weatherConditions(codeToCondition(weatherModel.getWeatherCondition().get(0).getId()));
    }

    // function to convert hectopascal to millimeter mercury. Formula from here: http://www.convertunits.com/from/mm+Hg/to/hPa
    private static double hPaToMmHg(double pressureHPa) {
        return pressureHPa * 0.750061561303;
    }

    private static WeatherConditions codeToCondition(int code) {
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
        } else if (code >= 800 && code < 803) {
            return WeatherConditions.PARTLY_CLOUDY;
        } else if (code >= 803 && code < 900) {
            return WeatherConditions.CLOUDY;
        } else {
            return WeatherConditions.CLEAR;
        }
    }
}
