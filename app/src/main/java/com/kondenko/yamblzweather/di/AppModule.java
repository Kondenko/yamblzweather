package com.kondenko.yamblzweather.di;

import android.content.Context;

import com.kondenko.yamblzweather.App;
import com.kondenko.yamblzweather.ui.citysuggest.dagger.SuggestsSubcomponent;
import com.kondenko.yamblzweather.ui.weather.dagger.WeatherSubcomponent;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(subcomponents = {WeatherSubcomponent.class, SuggestsSubcomponent.class})
public class AppModule {

    private final App application;

    public AppModule(App application) {
        this.application = application;
    }


    @Provides
    @Singleton
    Context provideContext() {
        return application.getBaseContext();
    }

    @Provides
    @Lang
    String provideLangString() {
        return Locale.getDefault().getLanguage();
    }

}
