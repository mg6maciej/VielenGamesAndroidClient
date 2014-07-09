package com.elpassion.vielengames.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.elpassion.vielengames.ForegroundNotifier;
import com.elpassion.vielengames.R;
import com.elpassion.vielengames.VielenGamesPrefs;
import com.elpassion.vielengames.api.GooglePlusAuth;
import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.data.Game;
import com.elpassion.vielengames.data.VielenGamesModel;
import com.elpassion.vielengames.data.kuridor.KuridorGame;
import com.elpassion.vielengames.event.GameClickEvent;
import com.elpassion.vielengames.event.GamesUpdatedEvent;
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
    VielenGamesPrefs prefs;
    @Inject
    VielenGamesModel model;
    @Inject
    GooglePlusAuth googlePlusAuth;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setButtonListeners();
        initViewPager();
        ViewUtils.setSelected(this, viewPager.getCurrentItem() == 0
                ? R.id.main_proposals_button
                : R.id.main_my_games_button);
        updateGamesCount();
        eventBus.register(this);
    }

    private void initViewPager() {
        final MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager = ViewUtils.findView(this, R.id.main_view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    ViewUtils.setSelected(MainActivity.this, R.id.main_proposals_button);
                    ViewUtils.setNotSelected(MainActivity.this, R.id.main_my_games_button);
                } else {
                    ViewUtils.setSelected(MainActivity.this, R.id.main_my_games_button);
                    ViewUtils.setNotSelected(MainActivity.this, R.id.main_proposals_button);
                }
            }
        });
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
        viewPager.setCurrentItem(0, true);
    }

    private void onAddProposalClick() {
        client.createGameProposal("kuridor");
    }

    private void onMyGamesClick() {
        viewPager.setCurrentItem(1, true);
    }

    @SuppressWarnings("unused")
    public void onEvent(GameClickEvent event) {
        GameActivity.intent(this)
                .game((KuridorGame) event.getGame())
                .start();
    }

    @SuppressWarnings("unused")
    public void onEvent(GamesUpdatedEvent event) {
        updateGamesCount();
    }

    private void updateGamesCount() {
        int count = 0;
        for (Game game : model.getMyGames()) {
            if (imActiveUser(game)) {
                count++;
            }
        }
        ViewUtils.setVisible(count > 0, this, R.id.main_my_games_count);
        ViewUtils.setText(String.valueOf(count), this, R.id.main_my_games_count);
    }

    private boolean imActiveUser(Game game) {
        return prefs.getMe().equals(((KuridorGame) game).getActivePlayer());
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
