package dev.azuuure.playerlist.fabric;

import com.mojang.logging.LogUtils;
import dev.azuuure.playerlist.fabric.listener.ClientTickEventsListener;
import dev.azuuure.playerlist.settings.BetterPlayerListSettings;
import dev.azuuure.playerlist.fabric.utils.FabricUtils;
import dev.azuuure.playerlist.utils.LifecycleUtils;
import lombok.Getter;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;

import java.io.IOException;

public final class BetterPlayerList implements ClientModInitializer {

    @Getter private static BetterPlayerList instance;
    @Getter private Logger logger;
    @Getter private BetterPlayerListSettings settings;

    @Override
    public void onInitializeClient() {
        instance = this;
        logger = LogUtils.getLogger();
        settings = new BetterPlayerListSettings(Minecraft.getInstance().gameDirectory);

        try {
            settings.load();
        } catch (IOException e) {
            logger.error("Could not load mod configuration", e);
        } catch (IllegalArgumentException e) {
            logger.warn("One or more values in the configuration are invalid!", e);
        }

        new ClientTickEventsListener().register();
        LifecycleUtils.onInit(logger, FabricUtils.getModVersion(), "Fabric");
    }
}
