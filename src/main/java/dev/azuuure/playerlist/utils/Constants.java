package dev.azuuure.playerlist.utils;

import net.fabricmc.loader.api.FabricLoader;

public final class Constants {

    private Constants() {
        throw new UnsupportedOperationException();
    }
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
}
