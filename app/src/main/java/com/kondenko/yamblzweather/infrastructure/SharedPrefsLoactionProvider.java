package com.kondenko.yamblzweather.infrastructure;

import android.content.Context;
import android.content.SharedPreferences;

import com.kondenko.yamblzweather.R;
import com.kondenko.yamblzweather.domain.entity.City;
import com.kondenko.yamblzweather.domain.entity.Location;
import com.kondenko.yamblzweather.domain.guards.LocationProvider;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class SharedPrefsLoactionProvider implements LocationProvider {
    private static final String TAG = SharedPrefsLoactionProvider.class.getSimpleName();
    private static final String DEFAULT_CITY_ID = "ID";
    private final String CITY_ID_KEY;
    private final SharedPreferences sharedPreferences;
    private final String LONGITUDE_KEY;
    private final String LATITUDE_KEY;
    private final float DEFAULT_LONGITUDE = 55.7558f;
    private final float DEFAULT_LATITUDE = 37.6173f;
    private final String DEFAULT_CITY_NAME;
    private final String CITY_NAME_KEY;


    public SharedPrefsLoactionProvider(Context context) {
        this.sharedPreferences = context.getSharedPreferences(context.getPackageName() + "_preferences", Context.MODE_PRIVATE);
        this.LATITUDE_KEY = context.getString(R.string.pref_key_latitude);
        this.LONGITUDE_KEY = context.getString(R.string.pref_key_longitude);
        this.DEFAULT_CITY_NAME = context.getString(R.string.default_city);
        this.CITY_ID_KEY = context.getString(R.string.pref_key_city_id);
        this.CITY_NAME_KEY = context.getString(R.string.pref_key_city);
    }

    @Override
    public Single<City> getCurrentCity() {
        return Single.zip(Single.zip(Single.fromCallable(() -> sharedPreferences.getFloat(LATITUDE_KEY, DEFAULT_LATITUDE)),
                                     Single.fromCallable(() -> sharedPreferences.getFloat(LONGITUDE_KEY, DEFAULT_LONGITUDE)),
                                     (latitude, longitude) -> Location.builder().latitude(latitude).longitude(longitude).build()),
                          Single.fromCallable(() -> sharedPreferences.getString(CITY_NAME_KEY, DEFAULT_CITY_NAME)),
                          Single.fromCallable(() -> sharedPreferences.getString(CITY_ID_KEY, DEFAULT_CITY_ID)),
                          City::create);
    }

    @Override
    public Completable setCurrentCity(City city) {
        return Completable.fromAction(() -> sharedPreferences.edit()
                                                             .putFloat(LATITUDE_KEY, (float) city.location().latitude())
                                                             .putFloat(LONGITUDE_KEY, (float) city.location().longitude())
                                                             .putString(CITY_NAME_KEY, city.name())
                                                             .putString(CITY_ID_KEY, city.id())
                                                             .commit());
    }
}
