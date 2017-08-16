package com.kondenko.yamblzweather.data.weather;

import com.kondenko.yamblzweather.domain.entity.WeatherConditions;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Mishkun on 11.08.2017.
 */
public class WeatherConditionMapperTest {

    int code;

    private void checkRange(int start, int end, WeatherConditions expected){
        for (int i = start; i <= end; i++){
            assertEquals(expected, WeatherConditionMapper.codeToCondition(i));
        }
    }

    @Test
    public void codeToThunderstorm() throws Exception {
        checkRange(200, 299, WeatherConditions.THUNDERSTORM);
    }

    @Test
    public void codeToDrizzle() throws Exception {
        checkRange(300, 399, WeatherConditions.DRIZZLE);
    }

    @Test
    public void codeToRain() throws Exception {
        checkRange(500, 599, WeatherConditions.RAIN);
    }

    @Test
    public void codeToSnow() throws Exception {
        checkRange(600, 699, WeatherConditions.SNOW);
    }

    @Test
    public void codeToFog() throws Exception {
        checkRange(700, 799, WeatherConditions.FOG);
    }

    @Test
    public void codeToClear() throws Exception {
        checkRange(800, 800, WeatherConditions.CLEAR);
    }

    @Test
    public void codeToPartlyCloudy() throws Exception {
        checkRange(801, 802, WeatherConditions.PARTLY_CLOUDY);
    }

    @Test
    public void codeToCloudy() throws Exception {
        checkRange(803, 899, WeatherConditions.CLOUDY);
    }
}