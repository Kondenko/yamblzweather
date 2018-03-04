package com.kondenko.yamblzweather.infrastructure;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.facebook.stetho.inspector.network.AsyncPrettyPrinter;
import com.kondenko.yamblzweather.App;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Weather;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentCityInteractor;
import com.kondenko.yamblzweather.domain.usecase.GetCurrentWeatherInteractor;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Observable;

/**
 * @author themishkun on 04/03/2018.
 */

public class WeatherProvider extends ContentProvider {

    GetCurrentWeatherInteractor getCurrentWeatherInteractor;
    GetCurrentCityInteractor getCurrentCityInteractor;

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        App applicationContext = (App) getContext().getApplicationContext();
        getCurrentWeatherInteractor = applicationContext.getCurrentWeatherInteractor;
        getCurrentCityInteractor = applicationContext.getCurrentCityInteractor;
        Pair<Weather, City> weatherCityPair =
            Observable.zip(getCurrentWeatherInteractor.run(), getCurrentCityInteractor.run(), Pair::new).blockingFirst();
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"city", "temperature", "weather"}, 1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            matrixCursor.newRow().add("city", weatherCityPair.second.name()).add("temperature", (float) weatherCityPair.first.temperature().kelvinDegrees()).add("weather", weatherCityPair.first.weatherConditions().toString());
        }
        return matrixCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
