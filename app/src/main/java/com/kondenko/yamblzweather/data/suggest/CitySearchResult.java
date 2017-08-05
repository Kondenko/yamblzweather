package com.kondenko.yamblzweather.data.suggest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mishkun on 27.07.2017.
 */

public class CitySearchResult {

    @SerializedName("result")
    Result result;

    public Result getResult() {
        return result;
    }

    public static class Result {
        @SerializedName("geometry")
        Geometry geometry;

        @SerializedName("name")
        String name;
        @SerializedName("place_id")
        String id;

        public Geometry getGeometry() {
            return geometry;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public static class Geometry {
            @SerializedName("location")
            Coord coordinates;

            public Coord getCoordinates() {
                return coordinates;
            }
        }
    }
}
