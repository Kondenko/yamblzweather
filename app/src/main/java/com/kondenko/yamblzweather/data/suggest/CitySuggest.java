package com.kondenko.yamblzweather.data.suggest;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mishkun on 27.07.2017.
 */

class CitySuggest {
    @SerializedName("predictions")
    private List<PredictionResponse> predictionResponses;

    List<PredictionResponse> getPredictionResponses() {
        return predictionResponses;
    }

    CitySuggest(){

    }

    CitySuggest(List<PredictionResponse> predictionResponses){
        this.predictionResponses = predictionResponses;
    }

    static class PredictionResponse {
        @SerializedName("description")
        private
        String place;
        @SerializedName("place_id")
        private
        String id;

        PredictionResponse() {

        }

        PredictionResponse(String description, String placeId) {
            place = description;
            id = placeId;
        }

        String getPlace() {
            return place;
        }

        String getId() {
            return id;
        }

    }
}
