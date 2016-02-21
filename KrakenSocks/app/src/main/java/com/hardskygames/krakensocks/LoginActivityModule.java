package com.hardskygames.krakensocks;

import dagger.Module;

/**
 * Created by hardsky on 20.02.16.
 */
@Module(
        injects = {
                LoginActivity.class,
        },
        addsTo = com.hardskygames.krakensocks.KrakenApplicationModule.class,
        library = true
)
public class LoginActivityModule {
}
