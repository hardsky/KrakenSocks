package com.hardskygames.krakensocks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by hardsky on 20.02.16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ObjectGraph activityGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        KrakenApplication application = (KrakenApplication) getApplication();
        activityGraph = application.getApplicationGraph().plus(getModules().toArray());

        activityGraph.inject(this);
    }

    @Override
    protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected as
        // soon as possible.
        activityGraph = null;

        super.onDestroy();
    }

    protected abstract List<Object> getModules();

    public void inject(Object object) {
        activityGraph.inject(object);
    }
}
