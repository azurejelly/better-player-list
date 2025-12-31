package dev.azuuure.playerlist;

import com.mojang.logging.LogUtils;
import dev.azuuure.playerlist.utils.Constants;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(Constants.MOD_ID)
public class BetterPlayerList {

    private final Logger logger;

    public BetterPlayerList(IEventBus modEventBus, ModContainer modContainer) {
        logger = LogUtils.getLogger();
        NeoForge.EVENT_BUS.register(this);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onClientSetupEvent(FMLClientSetupEvent event)
    {
        logger.info("Hello world!");
    }
}
