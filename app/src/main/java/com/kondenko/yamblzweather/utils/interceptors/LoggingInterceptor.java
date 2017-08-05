package com.kondenko.yamblzweather.utils.interceptors;

import com.kondenko.yamblzweather.utils.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {

    private static final String TAG = "HTTP-Log";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        //Logger.i(TAG, request.headers().toString());
        Logger.i(TAG, request.toString());
        //Logger.i(TAG, response.headers().toString());
        Logger.i(TAG, response.toString());
        return response;
    }

}