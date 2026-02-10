package dev.azuuure.playerlist.neoforge.utils;

import dev.azuuure.playerlist.utils.Constants;
import lombok.experimental.UtilityClass;
import net.neoforged.fml.ModList;

@UtilityClass
public class NeoForgeUtils {

    public static final String PLATFORM = "NeoForge";

    public static String getModVersion(String id) {
        return ModList.get()
                .getModFileById(id)
                .versionString();
    }

    public static String getModVersion() {
        return getModVersion(Constants.MOD_ID);
    }
}
