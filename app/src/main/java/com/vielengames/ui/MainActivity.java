package com.vielengames.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.Session;
import com.vielengames.ForegroundNotifier;
import com.vielengames.R;
import com.vielengames.VielenGamesPrefs;
import com.vielengames.api.VielenGamesClient;
import com.vielengames.data.Game;
import com.vielengames.data.VielenGamesModel;
import com.vielengames.data.kuridor.KuridorGame;
import com.vielengames.event.CreateGameProposalClickEvent;
import com.vielengames.event.GameClickEvent;
import com.vielengames.event.GamesUpdatedEvent;
import com.vielengames.event.RefreshGameProposalsEvent;
import com.vielengames.event.bus.EventBus;
import com.vielengames.utils.ViewUtils;

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
                    refreshGameProposals();
                    ViewUtils.setSelected(MainActivity.this, R.id.main_proposals_button);
                    ViewUtils.setNotSelected(MainActivity.this, R.id.main_my_games_button);
                } else {
                    ViewUtils.setSelected(MainActivity.this, R.id.main_my_games_button);
                    ViewUtils.setNotSelected(MainActivity.this, R.id.main_proposals_button);
                }
            }
        });
    }

    private void refreshGameProposals() {
        client.requestGameProposals();
        eventBus.post(new RefreshGameProposalsEvent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.main_sign_out:
                signOut();
                return true;
            case R.id.main_about:
                showAbout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        prefs.clearSignedIn();
        Session.openActiveSessionFromCache(getApplicationContext())
                .closeAndClearTokenInformation();
        finish();
    }

    private void showAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
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

    private void onProposalsClick() {
        viewPager.setCurrentItem(0, true);
    }

    private void onAddProposalClick() {
        client.createGameProposal("kuridor");
        eventBus.post(new CreateGameProposalClickEvent());
        viewPager.setCurrentItem(0, true);
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
