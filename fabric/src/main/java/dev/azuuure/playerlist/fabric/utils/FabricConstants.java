package dev.azuuure.playerlist.fabric.utils;

import dev.azuuure.playerlist.utils.Constants;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public final class FabricConstants {

    private FabricConstants() {
        throw new UnsupportedOperationException();
    }

    /**
     * The version of the mod, provided by the Fabric loader.
     *
     * <p>Fallbacks to <code>{unknown}</code> if Fabric isn't able to provide
     * the mod version.
     */
    public static final String MOD_VERSION = FabricLoader.getInstance()
            .getModContainer(Constants.MOD_ID)
            .map(mod -> mod.getMetadata().getVersion().getFriendlyString())
            .orElse("{unknown}");
}
