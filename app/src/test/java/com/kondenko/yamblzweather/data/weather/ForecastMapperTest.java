package com.kondenko.yamblzweather.data.weather;

import com.kondenko.yamblzweather.data.weather.ForecastResponse.ForecastWeather;
import com.kondenko.yamblzweather.data.weather.ForecastResponse.ForecastWeather.Temp;
import com.kondenko.yamblzweather.data.weather.WeatherModel.WeatherCondition;
import com.kondenko.yamblzweather.domain.entity.Temperature;
import com.kondenko.yamblzweather.domain.entity.Weather;
import com.kondenko.yamblzweather.domain.entity.WeatherConditions;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Mishkun on 11.08.2017.
 */
public class ForecastMapperTest {

    private static final double PRESSURE_HPA = 765.3;
    private static final double PRESSURE_HHMG = 574.0221128651858;
    private static final double TEMP = 273;
    private static final double HUMIDITY = 1280;
    private static final long TIMESTAMP = 9999999999L;
    private static final double WINDSPEED = 0.2;
    private static final int CONDITION_CODE = 800;
    private WeatherForecastEntity weatherForecastEntity;
    private Weather weather;
    private ForecastWeather forecastWeatherResponse;

    @Before
    public void setUp() throws Exception {
        setupDomainForecast();
        setupDbForecast();
        setupResponseForecast();
    }

    private void setupResponseForecast() {
        Temp temp = new Temp(TEMP, TEMP);
        WeatherCondition weatherCondition = new WeatherCondition(CONDITION_CODE);
        forecastWeatherResponse = new ForecastWeather(TIMESTAMP,
                                                      temp,
                                                      PRESSURE_HPA,
                                                      HUMIDITY,
                                                      WINDSPEED,
                                                      new ArrayList<WeatherCondition>() {{
                                                          add(weatherCondition);
                                                      }});
    }

    private void setupDbForecast() {
        weatherForecastEntity = new WeatherForecastEntity();
        weatherForecastEntity.setNightTemperature(TEMP);
        weatherForecastEntity.setDayTemperature(TEMP);
        weatherForecastEntity.setTemperature(TEMP);
        weatherForecastEntity.setTimestamp(TIMESTAMP);
        weatherForecastEntity.setPressure(PRESSURE_HHMG);
        weatherForecastEntity.setForecast("FORECAST_ID");
        weatherForecastEntity.setHumidity(HUMIDITY);
        weatherForecastEntity.setWindSpeed(WINDSPEED);
        weatherForecastEntity.setWeatherConditionCode(CONDITION_CODE);
    }

    private void setupDomainForecast() {
        Temperature temperature = Temperature.valueOfKelvin(TEMP);
        weather = Weather.builder()
                         .weatherConditions(WeatherConditions.CLEAR)
                         .dayTemperature(temperature)
                         .nightTemperature(temperature)
                         .temperature(temperature)
                         .pressure(PRESSURE_HHMG)
                         .humidity(HUMIDITY)
                         .timestamp(TIMESTAMP)
                         .windSpeed(WINDSPEED)
                         .build();
    }

    @Test
    public void shouldConvertResponseToWeatherForecastdb() throws Exception {
        WeatherForecastEntity testWeatherForecastEntity = ForecastMapper.responseToWeatherForecastDb(forecastWeatherResponse);
        assertEquals(testWeatherForecastEntity.getDayTemperature(), weatherForecastEntity.getDayTemperature());
        assertEquals(testWeatherForecastEntity.getNightTemperature(),weatherForecastEntity.getNightTemperature());
        assertEquals(testWeatherForecastEntity.getTemperature(),weatherForecastEntity.getTemperature());
        assertEquals(testWeatherForecastEntity.getPressure(),weatherForecastEntity.getPressure());
        assertEquals(testWeatherForecastEntity.getTimestamp(),weatherForecastEntity.getTimestamp());
        assertEquals(testWeatherForecastEntity.getWindSpeed(),weatherForecastEntity.getWindSpeed());
        assertEquals(testWeatherForecastEntity.getWeatherConditionCode(),weatherForecastEntity.getWeatherConditionCode());
        assertEquals(testWeatherForecastEntity.getHumidity(),weatherForecastEntity.getHumidity());
    }

    @Test
    public void shouldConvertDbToDomain() throws Exception {
        Weather testWeather = ForecastMapper.dbToDomain(weatherForecastEntity);
        assertEquals(testWeather, weather);
    }

}