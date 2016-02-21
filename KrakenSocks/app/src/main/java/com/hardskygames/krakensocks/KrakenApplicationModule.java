package com.hardskygames.krakensocks;

import android.app.Application;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hardsky on 20.02.16.
 */
@Module(
        injects = {
                KrakenApplication.class,
        },
        library = true
)
public class KrakenApplicationModule {

    private final KrakenApplication mApplication;

    public KrakenApplicationModule(KrakenApplication application){
        mApplication = application;
    }

    @Provides
    @Singleton
    Bus provideAppBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    User provideUser(){
        return new User();
    }
}
