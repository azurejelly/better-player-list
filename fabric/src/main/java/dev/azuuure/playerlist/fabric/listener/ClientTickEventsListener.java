package dev.azuuure.playerlist.fabric.listener;

import dev.azuuure.playerlist.fabric.BetterPlayerList;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public final class ClientTickEventsListener {

    private final BetterPlayerList mod;
    private boolean previouslyPressed;

    public ClientTickEventsListener() {
        this.mod = BetterPlayerList.getInstance();
        this.previouslyPressed = false;
    }

    public void register() {
        ClientTickEvents.END_CLIENT_TICK.register(this::handle);
    }

    private void handle(MinecraftClient client) {
        var pressed = client.options.playerListKey.isPressed();
        if (mod.getSettings().isKeybindHold()) {
            mod.getSettings().setListRenderingEnabled(pressed);
            return;
        }

        if (pressed && !previouslyPressed) {
            var current = mod.getSettings().isListRenderingEnabled();
            mod.getSettings().setListRenderingEnabled(!current);
        }

        previouslyPressed = pressed;
    }
}
