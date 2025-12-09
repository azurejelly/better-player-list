package dev.azuuure.playerlist.compatibility;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.azuuure.playerlist.screen.BetterPlayerListScreen;

public final class ModMenuApiImpl implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return BetterPlayerListScreen::new;
    }
}
