package dev.azuuure.playerlist.neoforge;

import com.mojang.logging.LogUtils;
import dev.azuuure.playerlist.neoforge.listener.ClientTickEventListener;
import dev.azuuure.playerlist.neoforge.screen.BetterPlayerListScreen;
import dev.azuuure.playerlist.neoforge.utils.NeoForgeConstants;
import dev.azuuure.playerlist.settings.BetterPlayerListSettings;
import dev.azuuure.playerlist.utils.Constants;
import dev.azuuure.playerlist.utils.LifecycleUtils;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import java.io.IOException;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public final class BetterPlayerList {

    private static BetterPlayerList instance;
    private final ModContainer modContainer;
    private final Logger logger;
    private BetterPlayerListSettings settings;

    public BetterPlayerList(ModContainer container) {
        instance = this;
        modContainer = container;
        logger = LogUtils.getLogger();
    }

    public void init() {
        try {
            settings = new BetterPlayerListSettings(Minecraft.getInstance().gameDirectory);
            settings.load();

            logger.info("Loaded configuration from disk.");
        } catch (IOException e) {
            logger.error("Could not load mod configuration", e);
        } catch (IllegalArgumentException e) {
            logger.warn("One or more values in the configuration are invalid", e);
        }

        modContainer.registerExtensionPoint(IConfigScreenFactory.class, BetterPlayerListScreen::new);
        NeoForge.EVENT_BUS.register(ClientTickEventListener.class);
        LifecycleUtils.onInit(logger, NeoForgeConstants.MOD_VERSION, "NeoForge");
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

    public ModContainer getContainer() {
        return modContainer;
    }
}
