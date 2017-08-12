package com.kondenko.yamblzweather.data.suggest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mishkun on 27.07.2017.
 */

class CitySearchResult {

    @SerializedName("result")
    private
    Result result;

    CitySearchResult(Result result) {
        this.result = result;
    }

    CitySearchResult() {
    }

    Result getResult() {
        return result;
    }

    static class Result {
        @SerializedName("geometry")
        Geometry geometry;

        @SerializedName("name")
        String name;
        @SerializedName("place_id")
        String id;

        Result(Geometry geometry, String name, String id) {
            this.geometry = geometry;
            this.name = name;
            this.id = id;
        }

        Result() {
        }

        Geometry getGeometry() {
            return geometry;
        }

        String getName() {
            return name;
        }

        String getId() {
            return id;
        }

        static class Geometry {
            @SerializedName("location")
            Coord coordinates;

            Geometry() {

            }

            Geometry(Coord coord) {
                coordinates = coord;
            }

            Coord getCoordinates() {
                return coordinates;
            }

            static class Coord {

                @SerializedName("lng")
                @Expose
                private double lon;
                @SerializedName("lat")
                @Expose
                private double lat;

                Coord(double lat, double lon) {
                    this.lon = lon;
                    this.lat = lat;
                }

                Coord() {
                }

                double getLon() {
                    return lon;
                }

                double getLat() {
                    return lat;
                }

            }
        }
    }


}
