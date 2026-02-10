package dev.azuuure.playerlist.fabric.listener;

import dev.azuuure.playerlist.PlayerListMod;
import dev.azuuure.playerlist.provider.PlayerListModProvider;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

public final class ClientTickEventsListener {

    private final PlayerListMod mod;
    private boolean previouslyPressed;

    public ClientTickEventsListener() {
        this.mod = PlayerListModProvider.getInstance();
        this.previouslyPressed = false;
    }

    public void register() {
        ClientTickEvents.END_CLIENT_TICK.register(this::handle);
    }

    private void handle(Minecraft minecraft) {
        var pressed = minecraft.options.keyPlayerList.isDown();
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
