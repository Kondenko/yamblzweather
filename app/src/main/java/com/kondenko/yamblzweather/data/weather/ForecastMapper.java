package com.kondenko.yamblzweather.data.weather;

import com.kondenko.yamblzweather.data.weather.ForecastResponse.ForecastWeather;
import com.kondenko.yamblzweather.domain.entity.Temperature;
import com.kondenko.yamblzweather.domain.entity.Weather;

/**
 * Created by Mishkun on 09.08.2017.
 */

class ForecastMapper {
    static WeatherForecastEntity responseToWeatherForecastDb(ForecastWeather weatherModel) {
        WeatherForecastEntity weatherEntity = new WeatherForecastEntity();
        weatherEntity.setHumidity(weatherModel.getHumidity());
        weatherEntity.setPressure(hPaToMmHg(weatherModel.getPressure()));
        weatherEntity.setTemperature(weatherModel.getTemp().getMorning());
        weatherEntity.setDayTemperature(weatherModel.getTemp().getDay());
        weatherEntity.setNightTemperature(weatherModel.getTemp().getNight());
        weatherEntity.setWindSpeed(weatherModel.getWindspeed());
        weatherEntity.setTimestamp(weatherModel.getDt());
        weatherEntity.setWeatherConditionCode(weatherModel.getWeatherCondition().get(0).getId());
        return weatherEntity;
    }

    // function to convert hectopascal to millimeter mercury. Formula from here: http://www.convertunits.com/from/mm+Hg/to/hPa
    private static double hPaToMmHg(double pressureHPa) {
        return pressureHPa * 0.750061561303;
    }

    static Weather dbToDomain(WeatherForecastEntity weatherForecastEntity) {
        return Weather.builder()
                      .timestamp(weatherForecastEntity.getTimestamp())
                      .temperature(Temperature.valueOfKelvin(weatherForecastEntity.getTemperature()))
                      .weatherConditions(WeatherConditionMapper.codeToCondition(weatherForecastEntity.getWeatherConditionCode()))
                      .pressure(weatherForecastEntity.getPressure())
                      .dayTemperature(Temperature.valueOfKelvin(weatherForecastEntity.getDayTemperature()))
                      .nightTemperature(Temperature.valueOfKelvin(weatherForecastEntity.getNightTemperature()))
                      .humidity(weatherForecastEntity.getHumidity())
                      .windSpeed(weatherForecastEntity.getWindSpeed())
                      .build();
    }
}
