package com.elpassion.vielengames.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.elpassion.vielengames.ForegroundNotifier;
import com.elpassion.vielengames.R;
import com.elpassion.vielengames.api.GooglePlusAuth;
import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.data.kuridor.KuridorGame;
import com.elpassion.vielengames.event.GameClickEvent;
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
    ForegroundNotifier notifier;

    @Inject
    GooglePlusAuth googlePlusAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setButtonListeners();
        if (savedInstanceState == null) {
            replaceWithFragment(new GameProposalsFragment());
            ViewUtils.setSelected(this, R.id.main_proposals_button);
        } else {
            if (getSupportFragmentManager().findFragmentById(R.id.main_fragment_container) instanceof GameProposalsFragment) {
                ViewUtils.setSelected(this, R.id.main_proposals_button);
            } else {
                ViewUtils.setSelected(this, R.id.main_my_games_button);
            }
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
    }

    private void onSignOutClick() {
        googlePlusAuth.requestSignUserOut(this);

    }

    public void onEvent(OnAccessTokenRevoked event) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sign_out:
                onSignOutClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void onProposalsClick() {
        replaceProposalsFragment();
    }

    private void replaceProposalsFragment() {
        replaceWithFragment(new GameProposalsFragment());
        ViewUtils.setSelected(this, R.id.main_proposals_button);
        ViewUtils.setNotSelected(this, R.id.main_my_games_button);
    }

    private void onAddProposalClick() {
        client.createGameProposal("kuridor");
    }

    private void onMyGamesClick() {
        replaceWithFragment(new MyGamesFragment());
        ViewUtils.setNotSelected(this, R.id.main_proposals_button);
        ViewUtils.setSelected(this, R.id.main_my_games_button);
    }

    private void replaceWithFragment(BaseFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .commit();
    }

    @SuppressWarnings("unused")
    public void onEvent(GameClickEvent event) {
        GameActivity.intent(this)
                .game((KuridorGame) event.getGame())
                .start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        notifier.onActivityStarted();
    }

    @Override
    protected void onStop() {
        super.onStop();
        notifier.onActivityStopped();
    }
}
