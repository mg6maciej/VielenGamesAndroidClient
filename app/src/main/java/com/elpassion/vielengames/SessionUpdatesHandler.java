package com.elpassion.vielengames;

import android.os.Handler;

import com.elpassion.vielengames.api.VielenGamesClient;
import com.elpassion.vielengames.data.Updates;
import com.elpassion.vielengames.event.AppForegroundEvent;
import com.elpassion.vielengames.event.SessionStartedResponseEvent;
import com.elpassion.vielengames.event.SessionUpdatesFailedEvent;
import com.elpassion.vielengames.event.SessionUpdatesResponseEvent;
import com.elpassion.vielengames.event.bus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class SessionUpdatesHandler {

    private final VielenGamesClient client;
    private final ForegroundNotifier notifier;

    private final Handler handler = new Handler();
    private final Runnable requestUpdatesRunnable = new Runnable() {
        @Override
        public void run() {
            client.requestUpdates(lastUpdateTimestamp);
        }
    };
    private boolean requestingUpdates;

    private String lastUpdateTimestamp;

    @Inject
    public SessionUpdatesHandler(VielenGamesClient client, ForegroundNotifier notifier, EventBus eventBus) {
        this.client = client;
        this.notifier = notifier;
        eventBus.register(this);
    }

    @SuppressWarnings("unused")
    public void onEvent(SessionUpdatesResponseEvent event) {
        requestingUpdates = false;
        updateTimestamp(event.getUpdates());
        requestUpdates(false);
    }

    @SuppressWarnings("unused")
    public void onEvent(SessionUpdatesFailedEvent event) {
        requestingUpdates = false;
        requestUpdates(false);
    }

    @SuppressWarnings("unused")
    public void onEvent(AppForegroundEvent event) {
        requestUpdates(true);
    }

    private void updateTimestamp(Updates updates) {
        lastUpdateTimestamp = updates.getUntil();
    }

    private void requestUpdates(boolean immediate) {
        if (!requestingUpdates && notifier.isInForeground()) {
            requestingUpdates = true;
            handler.postDelayed(requestUpdatesRunnable, immediate ? 0 : 1000);
        }
    }
}
