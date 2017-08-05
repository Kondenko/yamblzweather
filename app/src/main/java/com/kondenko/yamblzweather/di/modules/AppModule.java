package com.kondenko.yamblzweather.di.modules;

import android.content.Context;

import com.kondenko.yamblzweather.App;
import com.kondenko.yamblzweather.domain.guards.JobsScheduler;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentWeatherInteractor;
import com.kondenko.yamblzweather.infrastructure.AppJobCreator;
import com.kondenko.yamblzweather.infrastructure.SharedPrefsLoactionProvider;
import com.kondenko.yamblzweather.infrastructure.WeatherJobsScheduler;
import com.kondenko.yamblzweather.ui.citysuggest.dagger.SuggestsSubcomponent;
import com.kondenko.yamblzweather.ui.weather.dagger.WeatherSubcomponent;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.kondenko.yamblzweather.Const.JOB;
import static com.kondenko.yamblzweather.Const.UI;

@Module(subcomponents = {WeatherSubcomponent.class, SuggestsSubcomponent.class})
public class AppModule {

    private App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApp() { return application; }

    @Provides
    @Singleton
    Context provideContext() {
        return application.getBaseContext();
    }

    @Provides
    @Singleton
    SettingsManager provideSettingsManager() {
        return new SettingsManager(application);
    }


    @Provides
    @Singleton
    LocationProvider provideLocationStore(Context context) {
        return new SharedPrefsLoactionProvider(context);
    }

    @Provides
    @Singleton
    @Named(JOB)
    Scheduler provideBackgroundScheduler(){
        return Schedulers.io();
    }

    @Provides
    @Singleton
    @Named(UI)
    Scheduler provideUiScheduler(){
        return AndroidSchedulers.mainThread();
    }


    @Provides
    @Singleton
    static JobsScheduler provideJobsRepository(AppJobCreator appJobCreator) {
        return new WeatherJobsScheduler(appJobCreator);
    }
}
