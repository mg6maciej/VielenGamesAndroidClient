package com.vielengames.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vielengames.R;

public final class StubLoginButtonProvider implements LoginButtonProvider {

    @Override
    public void init(final CreateSessionCallback callback, ViewGroup buttonsContainer) {
        LayoutInflater inflater = LayoutInflater.from(buttonsContainer.getContext());
        Button stubLoginButton = (Button) inflater.inflate(R.layout.login_button_stub, buttonsContainer, false);
        buttonsContainer.addView(stubLoginButton);
        stubLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.createSession("stub", "token");
            }
        });
    }

    @Override
    public void close() {
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
    }
}
