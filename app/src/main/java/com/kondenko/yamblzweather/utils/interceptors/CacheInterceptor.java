package com.kondenko.yamblzweather.utils.interceptors;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class CacheInterceptor implements Interceptor {

    private static final int DEFAULT_CACHING_TIME_SECONDS = 60 * 10;
    private final Context context;
    private final int maxStaleSec;

    @Inject
    public CacheInterceptor(Context context, Integer updateRateSec) {
        this.context = context;
        this.maxStaleSec = updateRateSec;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        CacheControl.Builder cacheControl = new CacheControl.Builder();
        if (isOnline(context)) {
            cacheControl.maxAge(DEFAULT_CACHING_TIME_SECONDS, TimeUnit.SECONDS);
        } else {
            cacheControl.onlyIfCached().maxStale(maxStaleSec, TimeUnit.SECONDS);
        }
        request = request.newBuilder().cacheControl(cacheControl.build()).build();
        return chain.proceed(request);
    }

    private boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}

