package dev.azuuure.playerlist;

import dev.azuuure.playerlist.settings.PlayerListSettings;
import org.slf4j.Logger;

public interface PlayerListMod {

    void init();

    Logger getLogger();

    PlayerListSettings getSettings();

    String getVersion();

    String getPlatform();
}
