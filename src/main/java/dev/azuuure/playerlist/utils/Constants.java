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

    /**
     * The name of the mod configuration file.
     */
    public static final String CONFIGURATION_FILE = MOD_ID + ".properties";

    public static final int COLOR_EXCELLENT = 0xA1FF30;
    public static final int COLOR_GOOD = 0x1C9115;
    public static final int COLOR_DECENT = 0xFCE956;
    public static final int COLOR_BAD = 0xFD4B4B;
    public static final int COLOR_VERY_BAD = 0xFF0000;
    public static final int COLOR_HORRIBLE = 0x8F0000;
}
