package com.kondenko.yamblzweather.utils.interceptors;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {

    @SuppressWarnings("unused")
    private static final String TAG = "HTTP-Log";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        Log.i(TAG, request.toString());
        Log.i(TAG, response.toString());
        return response;
    }

}