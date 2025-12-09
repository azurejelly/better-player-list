package dev.azuuure.playerlist.settings;

public final class BetterPlayerListSettings {

    private boolean header;
    private boolean footer;
    private boolean replaceLatencySymbols;
    private boolean shouldHold;
    private boolean shouldDisplayList;

    public BetterPlayerListSettings() {
        this.header = false;
        this.footer = true;
        this.replaceLatencySymbols = true;
        this.shouldHold = true;
        this.shouldDisplayList = false;
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
}
