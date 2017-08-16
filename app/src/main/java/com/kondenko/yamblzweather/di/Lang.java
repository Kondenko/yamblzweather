package com.kondenko.yamblzweather.di;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Mishkun on 11.08.2017.
 */
@Qualifier
@Retention(RUNTIME)
public @interface Lang {
}
