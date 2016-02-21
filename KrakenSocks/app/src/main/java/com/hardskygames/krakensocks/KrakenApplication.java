package com.hardskygames.krakensocks;

import android.app.Application;
import android.os.Build;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import timber.log.Timber;

/**
 * Created by hardsky on 20.02.16.
 */
public class KrakenApplication extends Application {

    private ObjectGraph applicationGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        else{
            Timber.plant(new CrashReportingTree());
        }

        applicationGraph = ObjectGraph.create(getModules().toArray());
        applicationGraph.inject(this);

        if ("google_sdk".equals( Build.PRODUCT )) { //if emulator
            //disable IPV6
            java.lang.System.setProperty("java.net.preferIPv6Addresses", "false");
            java.lang.System.setProperty("java.net.preferIPv4Stack", "true");
        }
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList(new KrakenApplicationModule(this));
    }

    public ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }
}
