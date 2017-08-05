package com.kondenko.yamblzweather.data.suggest;

/**
 * Created by Mishkun on 04.08.2017.
 */

class PredictionMapper {
    static com.kondenko.yamblzweather.domain.entity.Prediction responseToDomain(PredictionResponse predictionResponse) {
        return com.kondenko.yamblzweather.domain.entity.Prediction.create(predictionResponse.getPlace(), predictionResponse.getId());
    }
}
