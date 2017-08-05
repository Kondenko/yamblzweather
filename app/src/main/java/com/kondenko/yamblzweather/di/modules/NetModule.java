package com.kondenko.yamblzweather.di.modules;

import android.content.Context;

import com.kondenko.yamblzweather.Const;
import com.kondenko.yamblzweather.infrastructure.SettingsManager;
import com.kondenko.yamblzweather.utils.interceptors.ApiKeyInterceptor;
import com.kondenko.yamblzweather.utils.interceptors.CacheInterceptor;
import com.kondenko.yamblzweather.utils.interceptors.LoggingInterceptor;

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
public class NetModule {

    public final static String GOOGLE_SUGGESTS_API = "GOOGLE_SUGGESTS_API";
    public final static String OPEN_WEATHER_MAP_API = "OPEN_WEATHE_RMAP_API";

    private static final String GOOGLE_API_KEY = "AIzaSyDAR4vuMn57JCPaCeyNPeA4Vkcv7VPno3k";
    private static final String GOOGLE_API_KEY_QUERYNAME = "key";
    private static final String GOOGLE_API_ENDPOINT = "https://maps.googleapis.com/maps/api/place/";

    public NetModule() {
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
                .addInterceptor(new ApiKeyInterceptor(Const.PARAM_API_KEY, Const.API_KEY))
                .addInterceptor(cacheInterceptor)
                .addInterceptor(new LoggingInterceptor())
                .build();
    }

    @Provides
    @Singleton
    @Named(OPEN_WEATHER_MAP_API)
    Retrofit provideRetrofit(@Named(OPEN_WEATHER_MAP_API) OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named(GOOGLE_SUGGESTS_API)
    OkHttpClient provideGooglePlacesHttpClient(Cache cache, CacheInterceptor cacheInterceptor) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new ApiKeyInterceptor(GOOGLE_API_KEY_QUERYNAME, GOOGLE_API_KEY))
                .addInterceptor(cacheInterceptor)
                .addInterceptor(new LoggingInterceptor())
                .build();
    }

    @Provides
    @Singleton
    @Named(GOOGLE_SUGGESTS_API)
    public Retrofit provideGooglePlacesRetrofit(@Named(GOOGLE_SUGGESTS_API) OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(GOOGLE_API_ENDPOINT)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
