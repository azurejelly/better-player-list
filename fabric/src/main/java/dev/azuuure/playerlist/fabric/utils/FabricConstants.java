package dev.azuuure.playerlist.fabric.utils;

import dev.azuuure.playerlist.utils.Constants;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public final class FabricConstants {

    private FabricConstants() {
        throw new UnsupportedOperationException();
    }

    /**
     * The name of the mod configuration file.
     */
    public static final String CONFIGURATION_FILE_NAME = Constants.MOD_ID + ".properties";

    /**
     * The folder where the configuration file is stored at.
     */
    public static final String CONFIGURATION_FOLDER = "config" + File.separator + Constants.MOD_ID;

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
