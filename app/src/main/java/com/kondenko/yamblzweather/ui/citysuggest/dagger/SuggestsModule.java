package com.kondenko.yamblzweather.ui.citysuggest.dagger;

import com.kondenko.yamblzweather.ui.citysuggest.SuggestsActivity;
import com.kondenko.yamblzweather.ui.citysuggest.SuggestsView;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Mishkun on 28.07.2017.
 */
@Module
abstract class SuggestsModule {

    @Binds
    abstract SuggestsView provideView(SuggestsActivity activity);
}
