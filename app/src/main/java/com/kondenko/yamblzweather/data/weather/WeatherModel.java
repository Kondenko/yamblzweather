package com.kondenko.yamblzweather.data.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherModel {

    @SerializedName("weather")
    @Expose
    private List<WeatherCondition> weatherCondition;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;


    public List<WeatherCondition> getWeatherCondition() {
        return weatherCondition;
    }


    public String getBase() {
        return base;
    }


    public Main getMain() {
        return main;
    }


    public Wind getWind() {
        return wind;
    }


    public Clouds getClouds() {
        return clouds;
    }




    public static class WeatherCondition {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("main")
        @Expose
        private String main;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("icon")
        @Expose
        private String icon;

        public int getId() {
            return id;
        }


        public String getMain() {
            return main;
        }


        public String getDescription() {
            return description;
        }


        public String getIcon() {
            return icon;
        }


    }

    public static class Sys {

        @SerializedName("type")
        @Expose
        private int type;
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("message")
        @Expose
        private double message;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("sunrise")
        @Expose
        private int sunrise;
        @SerializedName("sunset")
        @Expose
        private int sunset;

        public int getType() {
            return type;
        }


        public int getId() {
            return id;
        }


        public double getMessage() {
            return message;
        }


        public String getCountry() {
            return country;
        }


        public int getSunrise() {
            return sunrise;
        }


        public int getSunset() {
            return sunset;
        }


    }

    public static class Main {

        @SerializedName("temp")
        @Expose
        private double temp;
        @SerializedName("pressure")
        @Expose
        private double pressure;
        @SerializedName("humidity")
        @Expose
        private double humidity;

        public double getTemp() {
            return temp;
        }


        double getPressure() {
            return pressure;
        }


        double getHumidity() {
            return humidity;
        }


    }

    static class Clouds {

        @SerializedName("all")
        @Expose
        private int all;

        public int getAll() {
            return all;
        }


    }

    static class Wind {

        @SerializedName("speed")
        @Expose
        private double speed;
        @SerializedName("deg")
        @Expose
        private double deg;

        public double getSpeed() {
            return speed;
        }

        public double getDeg() {
            return deg;
        }
    }
}
