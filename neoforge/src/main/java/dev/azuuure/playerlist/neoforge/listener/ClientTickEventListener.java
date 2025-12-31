package dev.azuuure.playerlist.neoforge.listener;

import dev.azuuure.playerlist.neoforge.BetterPlayerList;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public final class ClientTickEventListener {

    private static boolean previouslyPressed = false;

    @SubscribeEvent
    static void onTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        BetterPlayerList mod = BetterPlayerList.getInstance();
        boolean pressed = minecraft.options.keyPlayerList.isDown();

        if (mod.getSettings().shouldHold()) {
            mod.getSettings().setShouldDisplayList(pressed);
            return;
        }

        if (pressed && !previouslyPressed) {
            boolean current = mod.getSettings().shouldDisplayList();
            mod.getSettings().setShouldDisplayList(!current);
        }

        previouslyPressed = pressed;
    }
}
