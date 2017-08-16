package com.kondenko.yamblzweather.ui.weather;


import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Forecast;
import com.kondenko.yamblzweather.domain.entity.Location;
import com.kondenko.yamblzweather.domain.entity.TempUnit;
import com.kondenko.yamblzweather.domain.entity.Temperature;
import com.kondenko.yamblzweather.domain.entity.Weather;
import com.kondenko.yamblzweather.domain.entity.WeatherConditions;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentWeatherInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetFavoredCitiesInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetForecastInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetUnitsInteractor;
import com.kondenko.yamblzweather.domain.usecase.SetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.UpdateWeatherInteractor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Mishkun on 11.08.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterTest {
    private WeatherPresenter weatherPresenter;

    @Mock
    private GetCurrentWeatherInteractor currentWeatherInteractor;
    @Mock
    private GetForecastInteractor getForecastInteractor;
    @Mock
    private UpdateWeatherInteractor updateWeatherInteractor;
    @Mock
    private SetCurrentCityInteractor setCurrentCityInteractor;
    @Mock
    private GetCurrentCityInteractor getCurrentCityInteractor;
    @Mock
    private GetFavoredCitiesInteractor getFavoredCitiesInteractor;
    @Mock
    private GetUnitsInteractor getUnitsInteractor;
    @Mock
    private WeatherView view;


    private Weather weather;
    private City city;
    private Forecast forecast;
    private WeatherViewModel weatherViewModel;
    private ArrayList<City> cityList;

    @Before
    public void setUp() throws Exception {
        weatherPresenter = new WeatherPresenter(currentWeatherInteractor, getForecastInteractor, updateWeatherInteractor, setCurrentCityInteractor,
                                                getCurrentCityInteractor, getFavoredCitiesInteractor, getUnitsInteractor);
        setupWeather();
        city = City.create(Location.builder().longitude(0).latitude(0).build(), "name", "id");
        forecast = Forecast.create(new ArrayList<Weather>() {{
            add(weather);
            add(weather);
        }});
        cityList = new ArrayList<City>() {{
            add(city);
            add(city);
        }};
        weatherViewModel = WeatherViewModel.create(weather, forecast, city, cityList,
                                                   TempUnit.IMPERIAL);
    }

    private void setupWeather() {
        Temperature temperature = Temperature.valueOfKelvin(0);
        weather = Weather.builder()
                         .weatherConditions(WeatherConditions.CLEAR)
                         .dayTemperature(temperature)
                         .nightTemperature(temperature)
                         .temperature(temperature)
                         .pressure(0)
                         .humidity(0)
                         .timestamp(0)
                         .windSpeed(0)
                         .build();
    }

    @Test
    public void shouldSubscribeViewOnWeather() throws Exception {
        when(getCurrentCityInteractor.run()).thenReturn(Observable.just(city, city));
        when(currentWeatherInteractor.run()).thenReturn(Observable.just(weather, weather));
        when(getFavoredCitiesInteractor.run()).thenReturn(Single.just(cityList));
        when(getForecastInteractor.run()).thenReturn(Maybe.just(forecast));
        when(getUnitsInteractor.run()).thenReturn(Single.just(TempUnit.IMPERIAL));
        when(updateWeatherInteractor.run(any())).thenReturn(Completable.complete());
        when(view.getCitySelections()).thenReturn(Observable.never());

        weatherPresenter.attachView(view);

        verify(view, times(2)).setData(weatherViewModel);
        verify(view, times(1)).getCitySelections();
        verifyNoMoreInteractions(view);
    }

    @Test
    public void shouldSubscribeToCityUpdatesAndOnlyOnce() throws Exception {
        when(getCurrentCityInteractor.run()).thenReturn(Observable.just(city, city));
        when(setCurrentCityInteractor.run(any())).thenReturn(Completable.complete());
        when(currentWeatherInteractor.run()).thenReturn(Observable.just(weather, weather));
        when(getFavoredCitiesInteractor.run()).thenReturn(Single.just(new ArrayList<>()));
        when(getForecastInteractor.run()).thenReturn(Maybe.just(forecast));
        when(getUnitsInteractor.run()).thenReturn(Single.just(TempUnit.IMPERIAL));
        when(updateWeatherInteractor.run(any())).thenReturn(Completable.complete());
        when(view.getCitySelections()).thenReturn(Observable.just(city, city));

        weatherPresenter.attachView(view);

        verify(setCurrentCityInteractor, times(2)).run(city);
    }

    @Test
    public void shouldUpdateData() throws Exception {
        when(getCurrentCityInteractor.run()).thenReturn(Observable.just(city, city));
        when(view.getCitySelections()).thenReturn(Observable.never());
        when(currentWeatherInteractor.run()).thenReturn(Observable.never());
        when(getFavoredCitiesInteractor.run()).thenReturn(Single.just(new ArrayList<>()));
        when(getForecastInteractor.run()).thenReturn(Maybe.empty());
        when(getUnitsInteractor.run()).thenReturn(Single.just(TempUnit.IMPERIAL));
        when(updateWeatherInteractor.run(any())).thenReturn(Completable.complete());

        weatherPresenter.attachView(view);
        weatherPresenter.updateData();

        verify(updateWeatherInteractor, atLeastOnce()).run(city);
    }

    @Test
    public void shouldShowError() throws Exception {
        when(getCurrentCityInteractor.run()).thenReturn(Observable.just(city, city));
        when(view.getCitySelections()).thenReturn(Observable.never());
        when(currentWeatherInteractor.run()).thenReturn(Observable.never());
        when(getFavoredCitiesInteractor.run()).thenReturn(Single.just(new ArrayList<>()));
        when(getForecastInteractor.run()).thenReturn(Maybe.empty());
        when(getUnitsInteractor.run()).thenReturn(Single.just(TempUnit.IMPERIAL));
        when(updateWeatherInteractor.run(any())).thenReturn(Completable.error(new Throwable()));

        weatherPresenter.attachView(view);
        weatherPresenter.updateData();

        verify(view).showError(any());
    }

}