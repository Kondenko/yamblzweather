package com.kondenko.yamblzweather.data.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mishkun on 04.08.2017.
 */

class ForecastResponse {
    @SerializedName("list")
    private List<WeatherModel> list;

    public List<WeatherModel> getList() {
        return list;
    }

}
