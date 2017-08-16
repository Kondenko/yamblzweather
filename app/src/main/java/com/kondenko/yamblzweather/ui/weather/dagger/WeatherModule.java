package com.kondenko.yamblzweather.ui.weather.dagger;

import com.kondenko.yamblzweather.ui.weather.WeatherActivity;
import com.kondenko.yamblzweather.ui.weather.WeatherView;

import dagger.Binds;
import dagger.Module;

@Module
abstract class WeatherModule {

    @Binds
    abstract WeatherView provideView(WeatherActivity activity);

}
