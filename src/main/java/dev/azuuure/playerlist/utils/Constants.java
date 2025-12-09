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

    /**
     * A lime green color in hexadecimal, which is used for very good
     * network connections.
     */
    public static final int COLOR_EXCELLENT = 0xA1FF30;

    /**
     * A dark green color in hexadecimal, which is used for good
     * network connections.
     */
    public static final int COLOR_GOOD = 0x1C9115;

    /**
     * A yellow color in hexadecimal, which is used for network
     * connections that are good enough.
     */
    public static final int COLOR_DECENT = 0xFCE956;

    /**
     * A red color in hexadecimal, which is used for bad network
     * connections.
     */
    public static final int COLOR_BAD = 0xFD4B4B;

    /**
     * A very vibrant red color in hexadecimal, used for very bad
     * network connections.
     */
    public static final int COLOR_VERY_BAD = 0xFF0000;

    /**
     * A dark red color in hexadecimal. How are you even connected
     * still?
     */
    public static final int COLOR_HORRIBLE = 0x8F0000;
}
