package dev.azuuure.playerlist.settings;

import dev.azuuure.playerlist.BetterPlayerList;
import dev.azuuure.playerlist.settings.latency.LatencyDisplayMode;
import dev.azuuure.playerlist.utils.Constants;
import net.minecraft.client.MinecraftClient;

import java.io.*;
import java.util.Properties;

public final class BetterPlayerListSettings {

    private final File file;
    private final Properties properties;

    private boolean header;
    private boolean footer;
    private LatencyDisplayMode latencyDisplayMode;
    private boolean shouldHold;
    private boolean shouldDisplayList;
    private boolean shouldRenderHeads;
    private boolean forceHeads;

    public BetterPlayerListSettings() {
        this.file = new File(MinecraftClient.getInstance().runDirectory, Constants.CONFIGURATION_FILE);
        this.properties = new Properties();
        this.header = true;
        this.footer = true;
        this.latencyDisplayMode = LatencyDisplayMode.FULL_SIZE;
        this.shouldHold = true;
        this.shouldDisplayList = false;
        this.shouldRenderHeads = true;
        this.forceHeads = false;
    }

    public void load() {
        if (!file.exists()) {
            return;
        }

        var mod = BetterPlayerList.getInstance();
        if (!file.canRead()) {
            mod.getLogger().warn("File {} exists, but it cannot be read. Please check your file permissions!", file);
            return;
        }

        try (InputStream stream = new FileInputStream(file)) {
            properties.load(stream);
        } catch (IOException e) {
            mod.getLogger().error("Could not read mod configuration file. Will fallback to defaults!", e);
            return;
        }

        this.header = Boolean.parseBoolean(properties.getProperty("header", String.valueOf(header)));
        this.footer = Boolean.parseBoolean(properties.getProperty("footer", String.valueOf(footer)));
        this.shouldHold = Boolean.parseBoolean(properties.getProperty("hold", String.valueOf(shouldHold)));
        this.shouldRenderHeads = Boolean.parseBoolean(properties.getProperty("heads", String.valueOf(shouldRenderHeads)));
        this.forceHeads = Boolean.parseBoolean(properties.getProperty("force-heads", String.valueOf(forceHeads)));

        try {
            var raw = properties.getProperty("latency", latencyDisplayMode.name());
            this.latencyDisplayMode = LatencyDisplayMode.valueOf(raw);
        } catch (IllegalArgumentException ex) {
            mod.getLogger().warn("Failed to parse latency display mode from configuration file", ex);
            return;
        }

        mod.getLogger().info("Successfully loaded configuration from disk.");
    }

    public void save() {
        var mod = BetterPlayerList.getInstance();
        if (file.exists() && !file.canWrite()) {
            mod.getLogger().error("Cannot write configuration to disk. Please check your file permissions!");
            return;
        }

        properties.setProperty("header", String.valueOf(header));
        properties.setProperty("footer", String.valueOf(footer));
        properties.setProperty("hold", String.valueOf(shouldHold));
        properties.setProperty("heads", String.valueOf(shouldRenderHeads));
        properties.setProperty("force-heads", String.valueOf(forceHeads));
        properties.setProperty("latency", latencyDisplayMode.name());

        try {
            properties.store(new FileWriter(file), null);
        } catch (IOException e) {
            mod.getLogger().error("An error occurred while writing configuration to disk", e);
            return;
        }

        mod.getLogger().info("Wrote configuration to disk");
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
