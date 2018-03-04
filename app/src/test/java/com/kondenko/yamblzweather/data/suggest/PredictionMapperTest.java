package com.kondenko.yamblzweather.data.suggest;

import com.kondenko.yamblzweather.data.suggest.CitySuggest.PredictionResponse;
import com.kondenko.yamblzweather.domain.entity.Prediction;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Mishkun on 11.08.2017.
 */
public class PredictionMapperTest {

    private static final String PLACE_ID = "ID";
    private static final String NAME = "IZHEVSK";
    private Prediction prediction;
    private PredictionResponse predictionResponse;

    @Before
    public void setUp() throws Exception {
        predictionResponse = new PredictionResponse(NAME, PLACE_ID);
        prediction = Prediction.create(NAME, PLACE_ID);
    }

    @Test
    public void shouldConvertResponseToDomain() throws Exception {
        assertEquals(PredictionMapper.responseToDomain(predictionResponse), prediction);
    }

}