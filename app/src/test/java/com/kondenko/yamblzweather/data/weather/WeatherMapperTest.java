package com.kondenko.yamblzweather.data.weather;

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
public class WeatherMapperTest {
    private static final double PRESSURE_HPA = 765.3;
    private static final double PRESSURE_HHMG = 574.0221128651858;
    private static final double TEMP = 273;
    private static final double HUMIDITY = 1280;
    private static final long TIMESTAMP = 9999999999L;
    private static final double WINDSPEED = 0.2;
    private static final int ID = 800;


    private Weather weather;
    private WeatherModel weatherModel;
    private WeatherEntity weatherEntity;

    @Before
    public void setUp() throws Exception {
        setupDomainWeather();
        setupResponseWeather();
        setupDbWeather();
    }

    private void setupDbWeather() {
        weatherEntity = new WeatherEntity();
        weatherEntity.setNightTemperature(TEMP);
        weatherEntity.setDayTemperature(TEMP);
        weatherEntity.setTemperature(TEMP);
        weatherEntity.setTimestamp(TIMESTAMP);
        weatherEntity.setPressure(PRESSURE_HHMG);
        weatherEntity.setHumidity(HUMIDITY);
        weatherEntity.setWindSpeed(WINDSPEED);
        weatherEntity.setWeatherConditionCode(ID);
    }

    private void setupResponseWeather() {
        WeatherModel.Main main = new WeatherModel.Main(TEMP, PRESSURE_HPA, HUMIDITY);
        WeatherModel.WeatherCondition weatherCondition = new WeatherModel.WeatherCondition(ID);
        WeatherModel.Wind wind = new WeatherModel.Wind(WINDSPEED);
        weatherModel = new WeatherModel(new ArrayList<WeatherModel.WeatherCondition>() {{
            add(weatherCondition);
        }}, main, wind);
    }

    private void setupDomainWeather() {
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
    public void shouldConvertResponseToWeatherdb() throws Exception {
        WeatherEntity testWeatherEntity = WeatherMapper.responseToWeatherDb(weatherModel);
        assertEquals(testWeatherEntity.getTemperature(),weatherEntity.getTemperature());
        assertEquals(testWeatherEntity.getPressure(),weatherEntity.getPressure());
        assertEquals(testWeatherEntity.getWindSpeed(),weatherEntity.getWindSpeed());
        assertEquals(testWeatherEntity.getWeatherConditionCode(),weatherEntity.getWeatherConditionCode());
        assertEquals(testWeatherEntity.getHumidity(),weatherEntity.getHumidity());
    }

    @Test
    public void shouldConvertDbToDomain() throws Exception {
        Weather testWeather = WeatherMapper.dbToDomain(weatherEntity);
        assertEquals(testWeather, weather);
    }

}