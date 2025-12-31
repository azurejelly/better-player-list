package dev.azuuure.playerlist.fabric;

import com.mojang.logging.LogUtils;
import dev.azuuure.playerlist.fabric.listener.ClientTickEventsListener;
import dev.azuuure.playerlist.settings.BetterPlayerListSettings;
import dev.azuuure.playerlist.fabric.utils.FabricConstants;
import dev.azuuure.playerlist.utils.LifecycleUtils;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;

import java.io.IOException;

public final class BetterPlayerList implements ClientModInitializer {

    private static BetterPlayerList instance;
    private Logger logger;
    private BetterPlayerListSettings settings;

    @Override
    public void onInitializeClient() {
        instance = this;
        logger = LogUtils.getLogger();
        settings = new BetterPlayerListSettings(MinecraftClient.getInstance().runDirectory);

        try {
            settings.load();
        } catch (IOException e) {
            logger.error("Could not load mod configuration", e);
        } catch (IllegalArgumentException e) {
            logger.warn("One or more values in the configuration are invalid!", e);
        }

        new ClientTickEventsListener().register();
        LifecycleUtils.onInit(logger, FabricConstants.MOD_VERSION, "Fabric");
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
