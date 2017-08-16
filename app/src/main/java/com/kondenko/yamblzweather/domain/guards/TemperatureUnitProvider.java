package com.kondenko.yamblzweather.domain.guards;

import com.kondenko.yamblzweather.domain.entity.TempUnit;

/**
 * Created by Mishkun on 13.08.2017.
 */

public interface TemperatureUnitProvider {
    TempUnit getUnitKey();
}
