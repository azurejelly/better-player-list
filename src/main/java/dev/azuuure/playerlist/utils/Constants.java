package dev.azuuure.playerlist.utils;

import net.fabricmc.loader.api.FabricLoader;

public class Constants {

    /**
     * The ID of the mod.
     */
    public static final String MOD_ID = "better-player-list";

    /**
     * The version of the mod, provided by the Fabric loader.
     *
     * <p>Fallbacks to <code>{unknown}</code> if Fabric isn't able to provide
     * the mod version.
     */
    public static final String MOD_VERSION = FabricLoader.getInstance()
            .getModContainer(MOD_ID)
            .map(mod -> mod.getMetadata().getVersion().getFriendlyString())
            .orElse("{unknown}");

    /**
     * The scale applied to the latency numbers on the player list.
     */
    public static float LATENCY_TEXT_SCALE = 0.5f;
}
