package com.elpassion.vielengames.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.elpassion.vielengames.R;
import com.elpassion.vielengames.api.GooglePlusAuth;
import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.event.CreateGameProposalEvent;
import com.elpassion.vielengames.event.OnAccessTokenRevoked;
import com.elpassion.vielengames.event.bus.EventBus;
import com.elpassion.vielengames.utils.ViewUtils;

import javax.inject.Inject;

public final class MainActivity extends BaseActivity {

    @Inject
    VielenGamesClient client;
    @Inject
    EventBus eventBus;

    @Inject
    GooglePlusAuth googlePlusAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setButtonListeners();
        if (savedInstanceState == null) {
            replaceWithFragment(new GameProposalsFragment());
        }
        eventBus.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    private void setButtonListeners() {
        ViewUtils.setOnClickListener(this, R.id.main_proposals_button, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProposalsClick();
            }
        });
        ViewUtils.setOnClickListener(this, R.id.main_add_proposal_button, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddProposalClick();
            }
        });
        ViewUtils.setOnClickListener(this, R.id.main_my_games_button, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyGamesClick();
            }
        });

        ViewUtils.setOnClickListener(this, R.id.button_sign_out, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignOutClick();
            }
        });

    }

    private void onSignOutClick() {
        googlePlusAuth.requestSignUserOut(this);
//        finish();

    }

    public void onEvent(OnAccessTokenRevoked event){
        finish();
    }


    private void onProposalsClick() {
        replaceWithFragment(new GameProposalsFragment());
    }

    private void onAddProposalClick() {
        client.createGameProposal("kuridor");
    }

    private void onMyGamesClick() {
        replaceWithFragment(new MyGamesFragment());
    }

    private void replaceWithFragment(BaseFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .commit();
    }

    @SuppressWarnings("unused")
    public void onEvent(CreateGameProposalEvent event) {
        Toast.makeText(this, "Created new game proposal.", Toast.LENGTH_SHORT).show();
    }
}
