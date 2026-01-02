package dev.azuuure.playerlist.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ColorUtils {

    public static int latencyToColor(int ms) {
        if (ms < 100) {
            return Constants.COLOR_EXCELLENT;
        } else if (ms < 150) {
            return Constants.COLOR_GOOD;
        } else if (ms < 300) {
            return Constants.COLOR_DECENT;
        } else if (ms < 600) {
            return Constants.COLOR_BAD;
        } else if (ms < 1000) {
            return Constants.COLOR_VERY_BAD;
        } else {
            return Constants.COLOR_HORRIBLE;
        }
    }
}
