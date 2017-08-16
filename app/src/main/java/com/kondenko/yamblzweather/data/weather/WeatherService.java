package com.kondenko.yamblzweather.data.weather;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("weather")
    Single<WeatherModel> getWeather(@Query("lat") double lat, @Query("lon") double lon);

    @GET("forecast/daily")
    Single<ForecastResponse> getForecast(@Query("lat") double lat, @Query("lon") double lon, @Query("cnt") int count);

}
