package dev.azuuure.playerlist.neoforge;

import com.mojang.logging.LogUtils;
import dev.azuuure.playerlist.PlayerListMod;
import dev.azuuure.playerlist.neoforge.listener.PostClientTickEventListener;
import dev.azuuure.playerlist.neoforge.utils.NeoForgeUtils;
import dev.azuuure.playerlist.provider.PlayerListModProvider;
import dev.azuuure.playerlist.screen.PlayerListOptionsScreen;
import dev.azuuure.playerlist.settings.PlayerListSettings;
import dev.azuuure.playerlist.utils.Constants;
import dev.azuuure.playerlist.utils.LifecycleUtils;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import java.io.IOException;

@Getter
@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public final class NeoForgePlayerListMod implements PlayerListMod {

    private final ModContainer modContainer;
    private final Logger logger;
    private PlayerListSettings settings;

    public NeoForgePlayerListMod(ModContainer container) {
        PlayerListModProvider.setInstance(this);

        modContainer = container;
        logger = LogUtils.getLogger();
    }

    @Override
    public void init() {
        try {
            settings = new PlayerListSettings(Minecraft.getInstance().gameDirectory);
            settings.load();

            logger.info("Loaded configuration from disk.");
        } catch (IOException e) {
            logger.error("Could not load mod configuration", e);
        } catch (IllegalArgumentException e) {
            logger.warn("One or more values in the configuration are invalid", e);
        }

        modContainer.registerExtensionPoint(
                IConfigScreenFactory.class,
                (_, parent) -> new PlayerListOptionsScreen(parent)
        );

        NeoForge.EVENT_BUS.register(PostClientTickEventListener.class);
        LifecycleUtils.onInit(this);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        PlayerListModProvider.getInstance().init();
    }

    @Override
    public String getVersion() {
        return NeoForgeUtils.getModVersion();
    }

    @Override
    public String getPlatform() {
        return NeoForgeUtils.PLATFORM;
    }
}
