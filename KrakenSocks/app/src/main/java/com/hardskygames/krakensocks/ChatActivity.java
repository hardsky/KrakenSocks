package com.hardskygames.krakensocks;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.hardskygames.krakensocks.events.WebSocketMessageEvent;
import com.hardskygames.krakensocks.events.WebSocketOpenedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {

    private static final String CHAT_URL = "ws://kraken-test-socksjs.herokuapp.com/echo/websocket";

    @Inject
    User mUser;
    @Inject
    Bus mBus;
    @Inject
    KrakenClient mClient;

    @Bind(R.id.txtChat)
    TextView mTxtChat;
    @Bind(R.id.txtAdd)
    EditText mTxtAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ButterKnife.bind(this);
    }

    @Override
    protected List<Object> getModules() {
        return Collections.<Object>singletonList(new ChatActivityModule(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBus.register(this);

        mClient.open(CHAT_URL);
    }

    @Override
    protected void onPause() {
        mBus.unregister(this);
        mClient.close();

        super.onPause();
    }

    @OnClick(R.id.btnAdd)
    void sendMessage(){
        String message = mTxtAdd.getText().toString();
        if(TextUtils.isEmpty(message))
            return;

        mClient.send(String.format("%s: %s", mUser.getName(), message));

        mTxtAdd.setText(null);
    }

    @Subscribe
    public void onJoinChat(WebSocketOpenedEvent ev){
        mClient.send(String.format("[%s] joined chat", mUser.getName()));
    }

    @Subscribe
    public void onMessage(WebSocketMessageEvent ev){
        if(TextUtils.isEmpty(ev.message))
            return;

        mTxtChat.append(String.format("%s\n", ev.message));
    }
}
