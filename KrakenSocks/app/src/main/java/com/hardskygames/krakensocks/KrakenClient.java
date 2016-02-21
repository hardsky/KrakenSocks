package com.hardskygames.krakensocks;

import com.hardskygames.krakensocks.events.WebSocketMessageEvent;
import com.hardskygames.krakensocks.events.WebSocketOpenedEvent;
import com.hardskygames.krakensocks.events.WebsocketClosedEvent;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketConnectionHandler;
import de.tavendo.autobahn.WebSocketException;
import timber.log.Timber;

/**
 * Created by hardsky on 20.02.16.
 */
public class KrakenClient {
    private final BaseActivity mActivity;
    private final Bus mBus;
    private final WebSocketConnection mConnection;

    @Inject
    public KrakenClient(BaseActivity activity, Bus bus){
        mActivity = activity;
        mBus = bus;
        mConnection = new WebSocketConnection();
    }

    public void open(String url){

        try {
            mConnection.connect(url, new WebSocketConnectionHandler(){
                @Override
                public void onOpen() {
                    Timber.i("Websocket is opened.");

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBus.post(new WebSocketOpenedEvent());
                        }
                    });
                }

                @Override
                public void onClose(int code, String reason) {
                    Timber.i("Websocket is closed.\nCode: %d\nReason: %s\n", code, reason);

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBus.post(new WebsocketClosedEvent());
                        }
                    });
                }

                @Override
                public void onTextMessage(final String message) {
                    Timber.i("Message is received.");

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            WebSocketMessageEvent ev = new WebSocketMessageEvent();
                            ev.message = message;
                            mBus.post(ev);
                        }
                    });
                }
            });
        } catch (WebSocketException e) {
            Timber.e(e, "WebSocket error.");
        }
    }

    public void send(String message){
        if(!mConnection.isConnected()){
            mConnection.reconnect();
            return;
        }
        mConnection.sendTextMessage(message);
    }

    public void close(){
        mConnection.disconnect();
    }
}
