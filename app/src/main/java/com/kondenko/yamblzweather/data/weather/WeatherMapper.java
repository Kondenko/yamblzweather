package com.kondenko.yamblzweather.data.weather;

import com.kondenko.yamblzweather.domain.entity.Temperature;
import com.kondenko.yamblzweather.domain.entity.Weather;

/**
 * Created by Mishkun on 04.08.2017.
 */

class WeatherMapper {
    static WeatherEntity responseToWeatherdb(WeatherModel weatherModel) {
        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setHumidity(weatherModel.getMain().getHumidity());
        weatherEntity.setPressure(weatherModel.getMain().getPressure());
        weatherEntity.setTemperature(weatherModel.getMain().getTemp());
        weatherEntity.setWindSpeed(weatherModel.getWind().getSpeed());
        weatherEntity.setWeatherConditionCode(weatherModel.getWeatherCondition().get(0).getId());
        return weatherEntity;
    }

    // function to convert hectopascal to millimeter mercury. Formula from here: http://www.convertunits.com/from/mm+Hg/to/hPa
    private static double hPaToMmHg(double pressureHPa) {
        return pressureHPa * 0.750061561303;
    }


    static Weather dbToDomain(WeatherEntity weatherEntity) {
        return Weather.builder()
                      .timestamp(weatherEntity.getTimestamp())
                      .temperature(Temperature.valueOfKelvin(weatherEntity.getTemperature()))
                      .weatherConditions(WeatherConditionMapper.codeToCondition(weatherEntity.getWeatherConditionCode()))
                      .pressure(weatherEntity.getPressure())
                      .humidity(weatherEntity.getHumidity())
                      .windSpeed(weatherEntity.getWindSpeed())
                      .dayTemperature(Temperature.valueOfKelvin(weatherEntity.getDayTemperature()))
                      .nightTemperature(Temperature.valueOfKelvin(weatherEntity.getNightTemperature()))
                      .build();
    }
}
