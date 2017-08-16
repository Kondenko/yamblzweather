package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.domain.entity.TempUnit;
import com.kondenko.yamblzweather.domain.entity.Temperature;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Created by Mishkun on 13.08.2017.
 */
public class TemperatureFormatterTest {
    private static Temperature TEMPERATURE = Temperature.valueOfKelvin(289.5);
    @Test
    public void shouldFormat() throws Exception {
        assertEquals("289.5°K", TemperatureFormatter.format(TEMPERATURE, TempUnit.SCIENTIFIC, Locale.UK));
        assertEquals("16.5°C", TemperatureFormatter.format(TEMPERATURE, TempUnit.METRIC, Locale.UK));
        assertEquals("61.4°F", TemperatureFormatter.format(TEMPERATURE, TempUnit.IMPERIAL, Locale.UK));
    }

}