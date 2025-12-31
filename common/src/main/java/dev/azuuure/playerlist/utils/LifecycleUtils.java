package dev.azuuure.playerlist.utils;

import org.slf4j.Logger;

public final class LifecycleUtils {

    private LifecycleUtils() {
        throw new UnsupportedOperationException();
    }

    public static void onInit(Logger logger, String version, String platform) {
        logger.info("Running {} version {} for {}", Constants.MOD_ID, version, platform);
        logger.info("GitHub: https://github.com/azurejelly/better-player-list");
        logger.info("Modrinth: https://modrinth.com/mod/better-player-list");
    }
}
