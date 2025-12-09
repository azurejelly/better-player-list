package dev.azuuure.playerlist.settings;

public final class BetterPlayerListSettings {

    private boolean header;
    private boolean footer;
    private boolean replaceLatencySymbols;
    private boolean shouldHold;
    private boolean shouldDisplayList;
    private boolean shouldRenderHeads;
    private boolean forceHeads;

    public BetterPlayerListSettings() {
        this.header = true;
        this.footer = true;
        this.replaceLatencySymbols = true;
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

    public boolean shouldReplaceLatencySymbols() {
        return replaceLatencySymbols;
    }

    public void setReplaceLatencySymbols(boolean replaceLatencySymbols) {
        this.replaceLatencySymbols = replaceLatencySymbols;
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
}
