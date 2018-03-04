package com.kondenko.yamblzweather.utils.interceptors;


import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Adds the API key to every request sent.
 */
public class ParameterInterceptor implements Interceptor {

    private final String parameterName;
    private final String parameterValue;

    public ParameterInterceptor(String parameterName, String parameterValue) {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder().addQueryParameter(parameterName, parameterValue).build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}