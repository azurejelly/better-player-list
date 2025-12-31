package dev.azuuure.playerlist.neoforge.utils;

import dev.azuuure.playerlist.utils.Constants;
import net.neoforged.fml.ModList;

public final class NeoForgeConstants {

    private NeoForgeConstants() {
        throw new UnsupportedOperationException();
    }

    public static final String MOD_VERSION = ModList.get()
            .getModFileById(Constants.MOD_ID)
            .versionString();
}
