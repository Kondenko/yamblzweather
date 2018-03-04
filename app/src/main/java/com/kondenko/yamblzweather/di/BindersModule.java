package com.kondenko.yamblzweather.di;

import android.app.Activity;
import android.content.ContentProvider;

import com.kondenko.yamblzweather.infrastructure.WeatherProvider;
import com.kondenko.yamblzweather.ui.citysuggest.SuggestsActivity;
import com.kondenko.yamblzweather.ui.citysuggest.dagger.SuggestsSubcomponent;
import com.kondenko.yamblzweather.ui.weather.WeatherActivity;
import com.kondenko.yamblzweather.ui.weather.dagger.WeatherSubcomponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.ContentProviderKey;
import dagger.multibindings.IntoMap;

@Module
abstract class BindersModule {

    @Binds
    @IntoMap
    @ActivityKey(WeatherActivity.class)
    public abstract AndroidInjector.Factory<? extends Activity>
    bindMainActivityInjectorFactory(WeatherSubcomponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(SuggestsActivity.class)
    public abstract AndroidInjector.Factory<? extends Activity>
    bindSuggestActivityInjectorFactory(SuggestsSubcomponent.Builder builder);


}
