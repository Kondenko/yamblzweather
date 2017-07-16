package com.kondenko.yamblzweather.ui.weather.dagger;

import com.kondenko.yamblzweather.job.UpdateWeatherJob;
import com.kondenko.yamblzweather.ui.weather.WeatherActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = {WeatherModule.class})
public interface WeatherSubcomponent extends AndroidInjector<WeatherActivity> {

    @Subcomponent.Builder
    public abstract class Builder extends AndroidInjector.Builder<WeatherActivity> {
    }

}
