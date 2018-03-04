package com.kondenko.yamblzweather.data.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

class WeatherModel {
    @SerializedName("weather")
    @Expose
    private List<WeatherCondition> weatherCondition;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("wind")
    @Expose
    private Wind wind;

    @SuppressWarnings("unused")
    WeatherModel() {
    }

    WeatherModel(List<WeatherCondition> weatherCondition, Main main, Wind wind) {
        this.weatherCondition = weatherCondition;
        this.main = main;
        this.wind = wind;
    }

    List<WeatherCondition> getWeatherCondition() {
        return weatherCondition;
    }

    Main getMain() {
        return main;
    }


    Wind getWind() {
        return wind;
    }

    static class WeatherCondition {

        @SerializedName("id")
        @Expose
        private int id;

        WeatherCondition(int id) {
            this.id = id;
        }

        @SuppressWarnings("unused")
        WeatherCondition() {
        }

        int getId() {
            return id;
        }
    }

    static class Main {
        @SerializedName("temp")
        @Expose
        private double temp;
        @SerializedName("pressure")
        @Expose
        private double pressure;
        @SerializedName("humidity")
        @Expose
        private double humidity;

        @SuppressWarnings("unused")
        Main() {
        }

        Main(double temp, double pressure, double humidity) {
            this.temp = temp;
            this.pressure = pressure;
            this.humidity = humidity;
        }

        double getTemp() {
            return temp;
        }


        double getPressure() {
            return pressure;
        }


        double getHumidity() {
            return humidity;
        }
    }

    static class Wind {
        @SerializedName("speed")
        @Expose
        private double speed;

        @SuppressWarnings("unused")
        Wind() {
        }

        Wind(double speed) {
            this.speed = speed;
        }

        double getSpeed() {
            return speed;
        }
    }
}
