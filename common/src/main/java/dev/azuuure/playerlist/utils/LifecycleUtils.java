package dev.azuuure.playerlist.utils;

import dev.azuuure.playerlist.PlayerListMod;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;

@UtilityClass
public class LifecycleUtils {

    public static void onInit(PlayerListMod mod) {
        Logger logger = mod.getLogger();
        logger.info("Running {} version {} for {}", Constants.MOD_ID, mod.getVersion(), mod.getPlatform());
        logger.info("GitHub: https://github.com/azurejelly/better-player-list");
        logger.info("Modrinth: https://modrinth.com/mod/better-player-list");
    }
}
