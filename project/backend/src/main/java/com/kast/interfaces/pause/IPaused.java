package com.kast.interfaces.pause;

import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;

public interface IPaused {
    public boolean isPaused();

    // https://stackoverflow.com/a/77787292/18750563
    @EventListener
    public void onStartup(StartupEvent event);
}
