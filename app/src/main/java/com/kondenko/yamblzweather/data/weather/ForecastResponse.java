package com.kondenko.yamblzweather.data.weather;

import com.google.gson.annotations.SerializedName;
import com.kondenko.yamblzweather.data.weather.WeatherModel.WeatherCondition;

import java.util.List;

/**
 * Created by Mishkun on 04.08.2017.
 */

class ForecastResponse {
    @SerializedName("list")
    private List<ForecastWeather> list;

    ForecastResponse(List<ForecastWeather> list) {
        this.list = list;
    }

    ForecastResponse() {
    }

    List<ForecastWeather> getList() {
        return list;
    }


    static class ForecastWeather {
        @SerializedName("dt")
        private long dt;

        @SerializedName("temp")
        private Temp temp;

        @SerializedName("pressure")
        private double pressure;

        @SerializedName("humidity")
        private double humidity;

        @SerializedName("speed")
        private double windspeed;

        @SerializedName("weather")
        private List<WeatherCondition> weatherCondition;

        ForecastWeather(long dt, Temp temp, double pressure, double humidity,
                        double windspeed, List<WeatherCondition> weatherCondition) {
            this.dt = dt;
            this.temp = temp;
            this.pressure = pressure;
            this.humidity = humidity;
            this.windspeed = windspeed;
            this.weatherCondition = weatherCondition;
        }

        ForecastWeather() {
        }

        long getDt() {
            return dt;
        }

        Temp getTemp() {
            return temp;
        }

        double getPressure() {
            return pressure;
        }

        double getHumidity() {
            return humidity;
        }

        List<WeatherCondition> getWeatherCondition() {
            return weatherCondition;
        }

        double getWindspeed() {
            return windspeed;
        }


        static class Temp {

            @SerializedName("day")
            private double day;

            @SerializedName("night")
            private double night;

            Temp(double day, double night) {
                this.day = day;
                this.night = night;
            }

            Temp() {
            }

            double getDay() {
                return day;
            }


            double getNight() {
                return night;
            }
        }
    }

}

