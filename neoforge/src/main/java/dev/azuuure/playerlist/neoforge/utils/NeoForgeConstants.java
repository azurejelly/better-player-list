package dev.azuuure.playerlist.neoforge.utils;

import dev.azuuure.playerlist.utils.Constants;
import lombok.experimental.UtilityClass;
import net.neoforged.fml.ModList;

@UtilityClass
public class NeoForgeConstants {

    public static final String MOD_VERSION = ModList.get()
            .getModFileById(Constants.MOD_ID)
            .versionString();
}
