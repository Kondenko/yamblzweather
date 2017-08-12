package com.kondenko.yamblzweather.data.weather;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Created by Mishkun on 11.08.2017.
 */
public class ForecastEntityTest {
    private static final long TIMESTAMP1 = 19;
    private static final long TIMESTAMP2 = 10;
    private static final String ID1 = "ID1";
    private static final String ID2 = "ID2";

    private ForecastEntity forecastEntity1;
    private ForecastEntity forecastEntity2;
    private ForecastEntity forecastEntity3;
    private ForecastEntity forecastEntity4;

    @Before
    public void setUp() throws Exception {
        forecastEntity1 = buildForecastEntity(TIMESTAMP1, ID1);
        forecastEntity3 = buildForecastEntity(TIMESTAMP1, ID1);
        forecastEntity2 = buildForecastEntity(TIMESTAMP2, ID2);
        forecastEntity4 = buildForecastEntity(TIMESTAMP2, ID2);
    }

    private ForecastEntity buildForecastEntity(long timestamp, String id) {
        ForecastEntity forecastEntity = new ForecastEntity();
        forecastEntity.setTimestamp(timestamp);
        forecastEntity.setPlace_id(id);
        return forecastEntity;
    }


    @Test
    public void shouldBeEqualToSelf() throws Exception {
        assertEquals(forecastEntity1, forecastEntity1);
    }

    @Test
    public void shouldBeNotEqualToNull() throws Exception {
        assertFalse(forecastEntity1.equals(null));
    }

    @Test
    public void shouldBeEqual() throws Exception {
        assertEquals(forecastEntity1, forecastEntity3);
        assertEquals(forecastEntity2, forecastEntity4);
    }


    @Test
    public void shouldBeNotEqual() throws Exception {
        assertFalse(forecastEntity1.equals(forecastEntity2));
        assertFalse(forecastEntity3.equals(forecastEntity4));
    }

    @Test
    public void shouldHaveEqualHashCode() throws Exception {
        assertEquals(forecastEntity1.hashCode(), forecastEntity3.hashCode());
        assertEquals(forecastEntity2.hashCode(), forecastEntity4.hashCode());
    }

}