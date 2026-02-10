package dev.azuuure.playerlist.fabric.utils;

import dev.azuuure.playerlist.utils.Constants;
import lombok.experimental.UtilityClass;
import net.fabricmc.loader.api.FabricLoader;

@UtilityClass
public final class FabricUtils {

    public static final String PLATFORM = "Fabric";

    public static String getModVersion(String id) {
        return FabricLoader.getInstance()
                .getModContainer(id)
                .map(mod -> mod.getMetadata().getVersion().getFriendlyString())
                .orElse("{unknown}");
    }

    public static String getModVersion() {
        return getModVersion(Constants.MOD_ID);
    }
}
