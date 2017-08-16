package com.kondenko.yamblzweather.di;

import com.kondenko.yamblzweather.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        BindersModule.class,
        AppModule.class,
        NetModule.class,
        DataModule.class,
        SchedulersModule.class,
        DatabaseModule.class,
        InfrastructureModule.class,
        SchedulersModule.class
        })
public interface AppComponent {

    void inject(App app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App application);

        Builder appModule(AppModule appModule);

        AppComponent build();
    }

}


