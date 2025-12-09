package dev.azuuure.playerlist.utils;

public final class ColorUtils {

    public static final int EXCELLENT = 0x8CF985;
    public static final int GOOD = 0x1C9115;
    public static final int DECENT = 0xFFFA47;
    public static final int BAD = 0xFD4B4B;
    public static final int VERY_BAD = 0xFF0000;
    public static final int HORRIBLE = 0x8F0000;

    private ColorUtils() {
        throw new UnsupportedOperationException();
    }

    public static int latencyToColor(int ms) {
        if (ms < 100) {
            return EXCELLENT;
        } else if (ms < 150) {
            return GOOD;
        } else if (ms < 300) {
            return DECENT;
        } else if (ms < 600) {
            return BAD;
        } else if (ms < 1000) {
            return VERY_BAD;
        } else {
            return HORRIBLE;
        }
    }
}
