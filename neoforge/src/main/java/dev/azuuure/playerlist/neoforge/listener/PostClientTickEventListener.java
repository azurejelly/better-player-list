package dev.azuuure.playerlist.neoforge.listener;

import dev.azuuure.playerlist.PlayerListMod;
import dev.azuuure.playerlist.neoforge.NeoForgePlayerListMod;
import dev.azuuure.playerlist.provider.PlayerListModProvider;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public final class PostClientTickEventListener {

    private static boolean previouslyPressed = false;

    @SubscribeEvent
    static void onTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        PlayerListMod mod = PlayerListModProvider.getInstance();
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
