package com.kondenko.yamblzweather.ui.weather.dagger;

import com.kondenko.yamblzweather.data.weather.OpenWeatherWeatherProvider;
import com.kondenko.yamblzweather.data.weather.WeatherService;
import com.kondenko.yamblzweather.domain.guards.JobsScheduler;
import com.kondenko.yamblzweather.domain.guards.WeatherProvider;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentWeatherInteractor;
import com.kondenko.yamblzweather.infrastructure.AppJobCreator;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;
import com.kondenko.yamblzweather.infrastructure.WeatherJobsScheduler;
import com.kondenko.yamblzweather.ui.weather.WeatherActivity;
import com.kondenko.yamblzweather.ui.weather.WeatherView;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

import static com.kondenko.yamblzweather.di.modules.NetModule.OPEN_WEATHER_MAP_API;

@Module
public abstract class WeatherModule {

    @Binds
    abstract WeatherView provideView(WeatherActivity activity);

}
