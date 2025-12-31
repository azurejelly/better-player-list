package dev.azuuure.playerlist.settings;

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

        return "betterplayerlist.settings.latency-symbols." + name;
    }
}
