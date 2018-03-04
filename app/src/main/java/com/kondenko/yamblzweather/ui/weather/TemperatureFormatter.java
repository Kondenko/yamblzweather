package com.kondenko.yamblzweather.ui.weather;

import com.kondenko.yamblzweather.domain.entity.TempUnit;
import com.kondenko.yamblzweather.domain.entity.Temperature;

import java.util.Locale;

/**
 * Created by Mishkun on 13.08.2017.
 */
class TemperatureFormatter {
    static String format(Temperature temperature, TempUnit units, Locale locale) {
        double temp = 0;
        switch (units) {
            case IMPERIAL:
                temp = temperature.fahrenheitDegrees();
                break;
            case METRIC:
                temp = temperature.celsiusDegrees();
                break;
            case SCIENTIFIC:
                temp = temperature.kelvinDegrees();
                break;
        }
        return String.format(locale, "%.1f°%s", temp, units.getUnitLetter());
    }
}
