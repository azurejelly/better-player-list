package dev.azuuure.playerlist.settings.latency;

import net.minecraft.text.Text;

public enum LatencyDisplayMode {

    VANILLA,
    COMPACT,
    COMPACT_WITH_UNIT,
    FULL_SIZE,
    DISABLED;

    public String getPath() {
        String name = name()
                .toLowerCase()
                .replace("_", "-");

        return "better-player-list.settings.latency-symbols." + name;
    }

    public Text getName() {
        String path = getPath();
        return Text.translatable(path);
    }
}
