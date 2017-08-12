package com.kondenko.yamblzweather.data.weather;

import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Forecast;
import com.kondenko.yamblzweather.domain.entity.Location;
import com.kondenko.yamblzweather.domain.entity.Temperature;
import com.kondenko.yamblzweather.domain.entity.Weather;
import com.kondenko.yamblzweather.domain.entity.WeatherConditions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 11.08.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class OpenWeatherWeatherProviderTest {
    private static final double PRESSURE = 765.3;
    private static final double TEMP = 273;
    private static final double HUMIDITY = 1280;
    private static final long TIMESTAMP = 9999999999L;
    private static final double WINDSPEED = 0.2;
    private static final int CONDITION_ID = 800;
    private static final String FORECAST_ID = "FORECAST_ID";

    private static final String NAME = "IZHEVSK";
    private static final double LONGITUDE = 0.32;
    private static final double LATITUDE = 0.78;
    private static final String PLACE_ID = "PLACE_ID";


    private Weather weather;
    private WeatherModel weatherModel;
    private WeatherEntity weatherEntity;
    private Forecast forecast;
    private WeatherForecastEntity weatherForecastEntity;
    private ForecastResponse forecastResponse;
    private ForecastEntity forecastEntity;
    private City city;


    @Mock
    private WeatherService weatherService;
    @Mock
    private WeatherDao weatherDao;
    @Mock
    private ForecastDao forecastDao;
    private OpenWeatherWeatherProvider openWeatherWeatherProvider;

    @Before
    public void setUp() throws Exception {
        openWeatherWeatherProvider = new OpenWeatherWeatherProvider(weatherService,weatherDao, forecastDao);

        setupDomainWeather();
        setupResponseWeather();
        setupDbWeather();
        setupDomainForecast();
        setupDbForecast();
        setupResponseForecast();
        setupCity();
    }

    private void setupCity() {
        city = City.create(Location.builder().latitude(LATITUDE).longitude(LONGITUDE).build(), NAME, PLACE_ID);
    }

    private void setupResponseForecast() {
        ForecastResponse.ForecastWeather.Temp temp = new ForecastResponse.ForecastWeather.Temp(TEMP, TEMP);
        WeatherModel.WeatherCondition weatherCondition = new WeatherModel.WeatherCondition(CONDITION_ID);
        ForecastResponse.ForecastWeather forecastWeatherResponse = new ForecastResponse.ForecastWeather(TIMESTAMP,
                                                                       temp,
                                                                       PRESSURE,
                                                                       HUMIDITY,
                                                                       WINDSPEED,
                                                                       new ArrayList<WeatherModel.WeatherCondition>() {{
                                                                           add(weatherCondition);
                                                                       }});
        forecastResponse = new ForecastResponse(new ArrayList<ForecastResponse.ForecastWeather>(){{add(forecastWeatherResponse);add(forecastWeatherResponse);}});
    }

    private void setupDbForecast() {
        weatherForecastEntity = new WeatherForecastEntity();
        weatherForecastEntity.setNightTemperature(TEMP);
        weatherForecastEntity.setDayTemperature(TEMP);
        weatherForecastEntity.setTemperature(TEMP);
        weatherForecastEntity.setTimestamp(TIMESTAMP);
        weatherForecastEntity.setPressure(PRESSURE);
        weatherForecastEntity.setForecast(FORECAST_ID);
        weatherForecastEntity.setHumidity(HUMIDITY);
        weatherForecastEntity.setWindSpeed(WINDSPEED);
        weatherForecastEntity.setWeatherConditionCode(CONDITION_ID);
        forecastEntity = new ForecastEntity();
        forecastEntity.setPlace_id(PLACE_ID);
        forecastEntity.setTimestamp(TIMESTAMP);
    }

    private void setupDomainForecast() {
        Temperature temperature = Temperature.valueOfKelvin(TEMP);
        weather = Weather.builder()
                         .weatherConditions(WeatherConditions.CLEAR)
                         .dayTemperature(temperature)
                         .nightTemperature(temperature)
                         .temperature(temperature)
                         .pressure(PRESSURE)
                         .humidity(HUMIDITY)
                         .timestamp(TIMESTAMP)
                         .windSpeed(WINDSPEED)
                         .build();
        forecast = Forecast.create(new ArrayList<Weather>(){{add(weather);add(weather);}});
    }

    private void setupDbWeather() {
        weatherEntity = new WeatherEntity();
        weatherEntity.setNightTemperature(TEMP);
        weatherEntity.setDayTemperature(TEMP);
        weatherEntity.setTemperature(TEMP);
        weatherEntity.setTimestamp(TIMESTAMP);
        weatherEntity.setPressure(PRESSURE);
        weatherEntity.setHumidity(HUMIDITY);
        weatherEntity.setWindSpeed(WINDSPEED);
        weatherEntity.setWeatherConditionCode(CONDITION_ID);
    }

    private void setupResponseWeather() {
        WeatherModel.Main main = new WeatherModel.Main(TEMP, PRESSURE, HUMIDITY);
        WeatherModel.WeatherCondition weatherCondition = new WeatherModel.WeatherCondition(CONDITION_ID);
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
                         .pressure(PRESSURE)
                         .humidity(HUMIDITY)
                         .timestamp(TIMESTAMP)
                         .windSpeed(WINDSPEED)
                         .build();
    }


    @Test
    public void shouldGetWeatherSubscription() throws Exception {
        when(weatherDao.getWeather()).thenReturn(Flowable.just(weatherEntity, weatherEntity));

        TestObserver<Weather> weatherTestObserver = openWeatherWeatherProvider.getWeather().test();

        weatherTestObserver.assertResult(weather, weather);
    }



    @Test
    public void shouldGetForecast() throws Exception {
        when(forecastDao.getForecastEntity()).thenReturn(Maybe.just(forecastEntity));
        when(forecastDao.getWeatherForecasts(PLACE_ID)).thenReturn(Maybe.just(new ArrayList<WeatherForecastEntity>(){{add(weatherForecastEntity);add(weatherForecastEntity);}}));

        TestObserver<Forecast> forecastTestObserver = openWeatherWeatherProvider.getForecast().test();

        forecastTestObserver.assertResult(forecast);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void update() throws Exception {
        ArgumentCaptor<ForecastEntity> forecastCaptor = ArgumentCaptor.forClass(ForecastEntity.class);
        ArgumentCaptor<List<WeatherForecastEntity>> weatherForecastCaptor = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<WeatherEntity> weatherCaptor = ArgumentCaptor.forClass(WeatherEntity.class);
        when(weatherService.getWeather(LATITUDE, LONGITUDE)).thenReturn(Single.just(weatherModel));
        when(weatherService.getForecast(eq(LATITUDE),eq(LONGITUDE), anyInt())).thenReturn(Single.just(forecastResponse));

        TestObserver<Void> voidTestObserver = openWeatherWeatherProvider.update(city).test();

        voidTestObserver.assertComplete();

        verify(forecastDao).insertAllForecasts(forecastCaptor.capture());
        verify(forecastDao).insertAllWeatherForecasts(weatherForecastCaptor.capture());
        verify(weatherDao).insertAllWeather(weatherCaptor.capture());

        assertWeatherEqual(weatherEntity, weatherCaptor.getValue());
        assertWeatherForecastListEqual(weatherForecastEntity, weatherForecastCaptor.getValue());
        assertForecastEqual(forecastEntity,forecastCaptor.getValue());
    }

    private void assertWeatherEqual(WeatherEntity expected, WeatherEntity value) {
        assertEquals(expected.getTemperature(),value.getTemperature());
        assertEquals(expected.getPressure(),value.getPressure());
        assertEquals(expected.getWindSpeed(),value.getWindSpeed());
        assertEquals(expected.getWeatherConditionCode(),value.getWeatherConditionCode());
        assertEquals(expected.getHumidity(),value.getHumidity());
    }

    private void assertWeatherForecastListEqual(WeatherForecastEntity expected, List<WeatherForecastEntity> value) {
        for(WeatherForecastEntity entity : value){
            assertEquals(expected.getTemperature(),entity.getTemperature());
            assertEquals(expected.getPressure(),entity.getPressure());
            assertEquals(expected.getWindSpeed(),entity.getWindSpeed());
            assertEquals(expected.getWeatherConditionCode(),entity.getWeatherConditionCode());
            assertEquals(expected.getHumidity(),entity.getHumidity());
        }
    }

    private void assertForecastEqual(ForecastEntity expected, ForecastEntity value) {
        assertEquals(expected.getPlace_id(), value.getPlace_id());
    }

}