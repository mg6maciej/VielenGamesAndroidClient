package com.vielengames;

import com.vielengames.event.AppBackgroundEvent;
import com.vielengames.event.AppForegroundEvent;
import com.vielengames.event.bus.EventBus;

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
