package dev.azuuure.playerlist.utils;

import lombok.experimental.UtilityClass;

import java.io.File;

@UtilityClass
public class Constants {

    /**
     * The ID of the mod.
     */
    public static final String MOD_ID = "betterplayerlist";

    /**
     * The name of the mod configuration file.
     */
    public static final String CONFIGURATION_FILE_NAME = MOD_ID + ".properties";

    /**
     * The folder where the configuration file is stored at.
     */
    public static final String CONFIGURATION_FOLDER = "config" + File.separator + MOD_ID;

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
