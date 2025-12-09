package dev.azuuure.playerlist.settings;

import dev.azuuure.playerlist.settings.latency.LatencyDisplayMode;

public final class BetterPlayerListSettings {

    private boolean header;
    private boolean footer;
    private LatencyDisplayMode latencyDisplayMode;
    private boolean shouldHold;
    private boolean shouldDisplayList;
    private boolean shouldRenderHeads;
    private boolean forceHeads;

    public BetterPlayerListSettings() {
        this.header = true;
        this.footer = true;
        this.latencyDisplayMode = LatencyDisplayMode.FULL_SIZE;
        this.shouldHold = true;
        this.shouldDisplayList = false;
        this.shouldRenderHeads = true;
        this.forceHeads = false;
    }

    public boolean isHeaderEnabled() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public boolean isFooterEnabled() {
        return footer;
    }

    public void setFooter(boolean footer) {
        this.footer = footer;
    }

    public boolean shouldHold() {
        return shouldHold;
    }

    public void setShouldHold(boolean shouldHold) {
        this.shouldHold = shouldHold;
    }

    public boolean shouldDisplayList() {
        return shouldDisplayList;
    }

    public void setShouldDisplayList(boolean shouldDisplayList) {
        this.shouldDisplayList = shouldDisplayList;
    }

    public boolean shouldRenderHeads() {
        return shouldRenderHeads;
    }

    public void setShouldRenderHeads(boolean shouldRenderHeads) {
        this.shouldRenderHeads = shouldRenderHeads;
    }

    public boolean forcesHeads() {
        return forceHeads;
    }

    public void setForceHeads(boolean forceHeads) {
        this.forceHeads = forceHeads;
    }

    public LatencyDisplayMode getLatencyDisplayMode() {
        return latencyDisplayMode;
    }

    public void setLatencyDisplayMode(LatencyDisplayMode latencyDisplayMode) {
        this.latencyDisplayMode = latencyDisplayMode;
    }
}
