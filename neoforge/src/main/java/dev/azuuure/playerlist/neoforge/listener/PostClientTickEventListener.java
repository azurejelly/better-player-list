package dev.azuuure.playerlist.neoforge.listener;

import dev.azuuure.playerlist.neoforge.BetterPlayerList;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public final class PostClientTickEventListener {

    private static boolean previouslyPressed = false;

    @SubscribeEvent
    static void onTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        BetterPlayerList mod = BetterPlayerList.getInstance();
        boolean pressed = minecraft.options.keyPlayerList.isDown();

        if (mod.getSettings().isKeybindHold()) {
            mod.getSettings().setListRenderingEnabled(pressed);
            return;
        }

        if (pressed && !previouslyPressed) {
            boolean current = mod.getSettings().isListRenderingEnabled();
            mod.getSettings().setListRenderingEnabled(!current);
        }

        previouslyPressed = pressed;
    }
}
