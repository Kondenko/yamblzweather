package com.kondenko.yamblzweather.data.weather;

import com.kondenko.yamblzweather.data.weather.ForecastResponse.ForecastWeather;
import com.kondenko.yamblzweather.domain.entity.Temperature;
import com.kondenko.yamblzweather.domain.entity.Weather;

/**
 * Created by Mishkun on 09.08.2017.
 */

class ForecastMapper {
    static WeatherForecastEntity responseToWeatherForecastdb(ForecastWeather weatherModel) {
        WeatherForecastEntity weatherEntity = new WeatherForecastEntity();
        weatherEntity.setHumidity(weatherModel.getHumidity());
        weatherEntity.setPressure(weatherModel.getPressure());
        weatherEntity.setTemperature(weatherModel.getTemp().getDay());
        weatherEntity.setDayTemperature(weatherModel.getTemp().getDay());
        weatherEntity.setNightTemperature(weatherModel.getTemp().getNight());
        weatherEntity.setWindSpeed(weatherModel.getWindspeed());
        weatherEntity.setTimestamp(weatherModel.getDt());
        weatherEntity.setWeatherConditionCode(weatherModel.getWeatherCondition().get(0).getId());
        return weatherEntity;
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
