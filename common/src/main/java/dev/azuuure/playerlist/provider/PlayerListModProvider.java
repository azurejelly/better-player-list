package dev.azuuure.playerlist.provider;

import dev.azuuure.playerlist.PlayerListMod;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class PlayerListModProvider {

    private static PlayerListMod instance;

    public PlayerListMod getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Attempted to obtain an instance before the mod was initialized");
        }

        return instance;
    }

    @ApiStatus.Internal
    public void setInstance(@NotNull PlayerListMod inst) {
        if (instance != null) {
            throw new IllegalStateException("Attempted to set an instance when it was already set");
        }

        instance = inst;
    }
}
