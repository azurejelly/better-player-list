package dev.azuuure.playerlist.settings.latency;

public enum LatencyDisplayMode {

    VANILLA(false),
    COMPACT(true),
    FULL_SIZE(true),
    DISABLED(false);

    private final boolean canDisplayUnit;

    LatencyDisplayMode(boolean canDisplayUnit) {
        this.canDisplayUnit = canDisplayUnit;
    }

    LatencyDisplayMode() {
        this(false);
    }

    public String getPath() {
        String name = name()
                .toLowerCase()
                .replace("_", "-");

        return "betterplayerlist.settings.latency-symbols." + name;
    }

    public boolean canDisplayUnit() {
        return canDisplayUnit;
    }
}
