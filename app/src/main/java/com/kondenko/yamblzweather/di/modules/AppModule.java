package com.kondenko.yamblzweather.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.kondenko.yamblzweather.App;
import com.kondenko.yamblzweather.data.AppDatabase;
import com.kondenko.yamblzweather.data.location.CityDao;
import com.kondenko.yamblzweather.data.location.RoomLocationProvider;
import com.kondenko.yamblzweather.data.weather.ForecastDao;
import com.kondenko.yamblzweather.data.weather.WeatherDao;
import com.kondenko.yamblzweather.di.Job;
import com.kondenko.yamblzweather.di.Lang;
import com.kondenko.yamblzweather.di.Ui;
import com.kondenko.yamblzweather.domain.guards.JobsScheduler;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;
import com.kondenko.yamblzweather.infrastructure.AppJobCreator;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;
import com.kondenko.yamblzweather.infrastructure.WeatherJobsScheduler;
import com.kondenko.yamblzweather.ui.citysuggest.dagger.SuggestsSubcomponent;
import com.kondenko.yamblzweather.ui.weather.dagger.WeatherSubcomponent;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module(subcomponents = {WeatherSubcomponent.class, SuggestsSubcomponent.class})
public class AppModule {

    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    static JobsScheduler provideJobsRepository(AppJobCreator appJobCreator) {
        return new WeatherJobsScheduler(appJobCreator);
    }

    @Provides
    @Singleton
    App provideApp() {
        return application;
    }

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
    LocationProvider provideLocationStore(CityDao cityDao) {
        return new RoomLocationProvider(cityDao);
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context,
                                    AppDatabase.class, "database-name").build();
    }

    @Provides
    @Lang
    String provideLangString() {
        return Locale.getDefault().getLanguage();
    }

    @Provides
    CityDao provideCityDao(AppDatabase appDatabase) {
        return appDatabase.cityDao();
    }

    @Provides
    WeatherDao provideWeatherDao(AppDatabase appDatabase) {
        return appDatabase.weatherDao();
    }

    @Provides
    ForecastDao provideForecastDao(AppDatabase appDatabase) {
        return appDatabase.forecastDao();
    }


    @Provides
    @Singleton
    @Job
    Scheduler provideBackgroundScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Singleton
    @Ui
    Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
