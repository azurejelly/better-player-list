package dev.azuuure.playerlist.fabric.compatibility;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.azuuure.playerlist.screen.PlayerListOptionsScreen;

public final class ModMenuApiImpl implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return PlayerListOptionsScreen::new;
    }
}
