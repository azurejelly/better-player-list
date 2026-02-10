package dev.azuuure.playerlist.fabric;

import com.mojang.logging.LogUtils;
import dev.azuuure.playerlist.PlayerListMod;
import dev.azuuure.playerlist.provider.PlayerListModProvider;
import dev.azuuure.playerlist.fabric.listener.ClientTickEventsListener;
import dev.azuuure.playerlist.settings.PlayerListSettings;
import dev.azuuure.playerlist.fabric.utils.FabricUtils;
import dev.azuuure.playerlist.utils.LifecycleUtils;
import lombok.Getter;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;

import java.io.IOException;

@Getter
public final class FabricPlayerListMod implements PlayerListMod, ClientModInitializer {

    private Logger logger;
    private PlayerListSettings settings;

    @Override
    public void onInitializeClient() {
        init();
    }

    @Override
    public void init() {
        PlayerListModProvider.setInstance(this);

        logger = LogUtils.getLogger();
        settings = new PlayerListSettings(Minecraft.getInstance().gameDirectory);

        try {
            settings.load();
        } catch (IOException e) {
            logger.error("Could not load mod configuration", e);
        } catch (IllegalArgumentException e) {
            logger.warn("One or more values in the configuration are invalid!", e);
        }

        new ClientTickEventsListener().register();
        LifecycleUtils.onInit(this);
    }

    @Override
    public String getVersion() {
        return FabricUtils.getModVersion();
    }

    @Override
    public String getPlatform() {
        return FabricUtils.PLATFORM;
    }
}
