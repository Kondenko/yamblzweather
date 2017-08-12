package com.kondenko.yamblzweather.data.suggest;

import com.kondenko.yamblzweather.data.suggest.CitySuggest.PredictionResponse;
import com.kondenko.yamblzweather.domain.entity.Prediction;

/**
 * Created by Mishkun on 04.08.2017.
 */

class PredictionMapper {
    static Prediction responseToDomain(PredictionResponse predictionResponse) {
        return Prediction.create(predictionResponse.getPlace(), predictionResponse.getId());
    }
}
