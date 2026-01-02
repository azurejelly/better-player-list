package dev.azuuure.playerlist.utils;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;

@UtilityClass
public class LifecycleUtils {

    public static void onInit(Logger logger, String version, String platform) {
        logger.info("Running {} version {} for {}", Constants.MOD_ID, version, platform);
        logger.info("GitHub: https://github.com/azurejelly/better-player-list");
        logger.info("Modrinth: https://modrinth.com/mod/better-player-list");
    }
}
