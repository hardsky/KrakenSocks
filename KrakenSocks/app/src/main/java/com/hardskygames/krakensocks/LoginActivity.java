package com.hardskygames.krakensocks;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen
 */
public class LoginActivity extends BaseActivity {

    @Inject
    User mUser;

    @Bind(R.id.login_form)
    View mLoginFormView;
    @Bind(R.id.txtName)
    EditText mTxtName;
    @Bind(R.id.login_progress)
    View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @Override
    protected List<Object> getModules() {
        return Collections.<Object>singletonList(new LoginActivityModule());
    }

    @OnClick(R.id.name_sign_in_button)
    public void attemptLogin() {
        mTxtName.setError(null);

        String name = mTxtName.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            mTxtName.setError(getString(R.string.error_name_required));
            focusView = mTxtName;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mUser.setName(name);

            Intent intent = new Intent();
            intent.setClass(this, ChatActivity.class);
            startActivity(intent);

            finish();
        }
    }
}

