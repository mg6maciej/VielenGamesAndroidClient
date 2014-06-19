package com.elpassion.vielengames;

import com.elpassion.vielengames.event.AppBackgroundEvent;
import com.elpassion.vielengames.event.AppForegroundEvent;
import com.elpassion.vielengames.event.bus.EventBus;

public final class ForegroundNotifier {

    private final EventBus eventBus;

    private int foregroundCount;

    public ForegroundNotifier(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void onActivityStarted() {
        foregroundCount++;
        if (foregroundCount == 1) {
            eventBus.post(new AppForegroundEvent());
        }
    }

    public void onActivityStopped() {
        foregroundCount--;
        if (foregroundCount == 0) {
            eventBus.post(new AppBackgroundEvent());
        }
    }

    public boolean isInForeground() {
        return foregroundCount > 0;
    }
}
