package dev.azuuure.playerlist.neoforge.bootstrap;

import dev.azuuure.playerlist.neoforge.BetterPlayerList;
import dev.azuuure.playerlist.utils.Constants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public final class BetterPlayerListBootstrap {

    @SubscribeEvent
    static void init(FMLClientSetupEvent event) {
        BetterPlayerList.getInstance().init();
    }
}
