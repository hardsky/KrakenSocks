package com.hardskygames.krakensocks;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hardsky on 20.02.16.
 */
@Module(
        injects = {
                ChatActivity.class,
        },
        addsTo = com.hardskygames.krakensocks.KrakenApplicationModule.class,
        library = true
)
public class ChatActivityModule {

    private final BaseActivity mActivity;

    public ChatActivityModule(BaseActivity activity){
        mActivity = activity;
    }

    @Provides
    KrakenClient provideWebSocketClient(BaseActivity activity, Bus bus){
        return new KrakenClient(activity, bus);
    }

    @Provides
    @Singleton
    BaseActivity provideActivity(){
        return mActivity;
    }
}
