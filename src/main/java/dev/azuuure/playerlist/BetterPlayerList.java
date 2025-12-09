package dev.azuuure.playerlist;

import dev.azuuure.playerlist.listener.ClientTickEventsListener;
import dev.azuuure.playerlist.settings.BetterPlayerListSettings;
import dev.azuuure.playerlist.utils.Constants;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BetterPlayerList implements ClientModInitializer {

    private static BetterPlayerList instance;
    private Logger logger;
    private BetterPlayerListSettings settings;

    @Override
    public void onInitializeClient() {
        instance = this;
        logger = LoggerFactory.getLogger(BetterPlayerList.class);
        settings = new BetterPlayerListSettings();
        settings.load();

        new ClientTickEventsListener().register();

        logger.info("Running {} version {}", Constants.MOD_ID, Constants.MOD_VERSION);
        logger.info("GitHub: https://github.com/azurejelly/better-player-list");
        logger.info("Modrinth: https://modrinth.com/mod/better-player-list");
    }

    public static BetterPlayerList getInstance() {
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }

    public BetterPlayerListSettings getSettings() {
        return settings;
    }
}
