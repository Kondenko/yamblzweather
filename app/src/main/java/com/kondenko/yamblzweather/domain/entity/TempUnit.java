package com.kondenko.yamblzweather.domain.entity;

/**
 * Created by Mishkun on 10.08.2017.
 */

public enum TempUnit {
    IMPERIAL("F"),
    METRIC("C"),
    SCIENTIFIC("K");

    private final String repr;

    TempUnit(String repr) {
        this.repr = repr;
    }
    public String getUnitLetter(){
        return repr;
    }
}
