package dev.azuuure.playerlist;

import dev.azuuure.playerlist.listener.ClientTickEventsListener;
import dev.azuuure.playerlist.settings.BetterPlayerListSettings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterPlayerList implements ClientModInitializer {

    public static final String MOD_ID = "better-player-list";
    public static final String MOD_VERSION = FabricLoader.getInstance()
            .getModContainer(MOD_ID)
            .map(mod -> mod.getMetadata().getVersion().getFriendlyString())
            .orElse("<unknown>");

    private static BetterPlayerList instance;
    private Logger logger;
    private BetterPlayerListSettings settings;

    @Override
    public void onInitializeClient() {
        instance = this;
        logger = LoggerFactory.getLogger(BetterPlayerList.class);
        settings = new BetterPlayerListSettings();

        new ClientTickEventsListener().register();

        logger.info("Running {} version {}", MOD_ID, MOD_VERSION);
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
