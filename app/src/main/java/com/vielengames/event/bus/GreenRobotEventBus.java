package com.vielengames.event.bus;

public final class GreenRobotEventBus implements EventBus {

    private final de.greenrobot.event.EventBus bus = de.greenrobot.event.EventBus.getDefault();

    @Override
    public void post(Object event) {
        bus.post(event);
    }

    @Override
    public void register(Object subscriber) {
        bus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        bus.unregister(subscriber);
    }
}
