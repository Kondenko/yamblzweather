package com.kondenko.yamblzweather.di;

import android.content.Context;

import com.kondenko.yamblzweather.BuildConfig;
import com.kondenko.yamblzweather.data.suggest.CitiesSuggestService;
import com.kondenko.yamblzweather.data.weather.WeatherService;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;
import com.kondenko.yamblzweather.utils.interceptors.CacheInterceptor;
import com.kondenko.yamblzweather.utils.interceptors.LoggingInterceptor;
import com.kondenko.yamblzweather.utils.interceptors.ParameterInterceptor;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class NetModule {

    private final static String GOOGLE_SUGGESTS_API = "GOOGLE_SUGGESTS_API";
    private final static String OPEN_WEATHER_MAP_API = "OPEN_WEATHER_MAP_API";

    private static final String OPEN_WEATHER_MAP_API_KEY_QUERYNAME = "APPID";
    private static final String OPEN_WEATHER_MAP_BASE_URL = "http://api.openweathermap.org/data/2.5/";

    private static final String GOOGLE_API_KEY_QUERYNAME = "key";
    private static final String GOOGLE_API_BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    private static final String GOOGLE_API_LANG_QUERYNAME = "language";

    @Provides
    @Singleton
    CitiesSuggestService provideCitiesSuggestService(@Named(NetModule.GOOGLE_SUGGESTS_API) Retrofit retrofit) {
        return retrofit.create(CitiesSuggestService.class);
    }

    @Provides
    @Singleton
    WeatherService provideWeatherService(@Named(NetModule.OPEN_WEATHER_MAP_API) Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }

    @Provides
    @Singleton
    Cache provideCache(Context context) {
        File cacheDir = new File(context.getCacheDir(), "http-cache");
        int cacheSize = 5 * 1024 * 1024;
        return new Cache(cacheDir, cacheSize);
    }

    @Provides
    @Singleton
    @Inject
    CacheInterceptor provideCacheInterceptor(Context context, SettingsManager settingsManager) {
        Integer refreshRateSeconds = ((int) settingsManager.getRefreshRateSec());
        return new CacheInterceptor(context, refreshRateSeconds);
    }

    @Provides
    @Singleton
    @Named(OPEN_WEATHER_MAP_API)
    OkHttpClient provideHttpClient(Cache cache, CacheInterceptor cacheInterceptor) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new ParameterInterceptor(OPEN_WEATHER_MAP_API_KEY_QUERYNAME, BuildConfig.OPEN_WEATHER_MAP_API_KEY))
                .addInterceptor(cacheInterceptor)
                .addInterceptor(new LoggingInterceptor())
                .build();
    }

    @Provides
    @Singleton
    @Named(OPEN_WEATHER_MAP_API)
    Retrofit provideRetrofit(@Named(OPEN_WEATHER_MAP_API) OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(OPEN_WEATHER_MAP_BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named(GOOGLE_SUGGESTS_API)
    OkHttpClient provideGooglePlacesHttpClient(Cache cache, CacheInterceptor cacheInterceptor, @Lang String language) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new ParameterInterceptor(GOOGLE_API_KEY_QUERYNAME, BuildConfig.GOOGLE_PLACES_API_KEY))
                .addInterceptor(new ParameterInterceptor(GOOGLE_API_LANG_QUERYNAME, language))
                .addInterceptor(cacheInterceptor)
                .addInterceptor(new LoggingInterceptor())
                .build();
    }

    @Provides
    @Singleton
    @Named(GOOGLE_SUGGESTS_API)
    Retrofit provideGooglePlacesRetrofit(@Named(GOOGLE_SUGGESTS_API) OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(GOOGLE_API_BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
