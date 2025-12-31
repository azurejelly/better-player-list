package dev.azuuure.playerlist.settings;

import dev.azuuure.playerlist.settings.latency.LatencyDisplayMode;
import dev.azuuure.playerlist.utils.Constants;

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

    public BetterPlayerListSettings(File gameDirectory) {
        this.header = true;
        this.footer = true;
        this.latencyDisplayMode = LatencyDisplayMode.FULL_SIZE;
        this.shouldHold = true;
        this.shouldDisplayList = false;
        this.shouldRenderHeads = true;
        this.forceHeads = false;
        this.properties = new Properties();
        this.file = new File(
                gameDirectory,
                Constants.CONFIGURATION_FOLDER
                        + File.separator
                        + Constants.CONFIGURATION_FILE_NAME
        );
    }

    public void load() throws IOException {
        if (!file.exists()) {
            return;
        }

        if (!file.canRead()) {
            throw new IOException("Mod configuration exists, but it cannot be read! Please check your file permissions.");
        }

        try (InputStream stream = new FileInputStream(file)) {
            properties.load(stream);
        } catch (IOException e) {
            throw new IOException("Could not read mod configuration file", e);
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
            throw new IllegalArgumentException("The stored latency display mode is invalid", ex);
        }
    }

    public void save() throws IOException {
        if (file.exists() && !file.canWrite()) {
            throw new IOException("Cannot write to the mod configuration file. Please check your file permissions!");
        }

        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new IOException("Cannot make configuration directory. Please check your file permissions!");
        }

        properties.setProperty("header", String.valueOf(header));
        properties.setProperty("footer", String.valueOf(footer));
        properties.setProperty("hold", String.valueOf(shouldHold));
        properties.setProperty("heads", String.valueOf(shouldRenderHeads));
        properties.setProperty("force-heads", String.valueOf(forceHeads));
        properties.setProperty("latency", latencyDisplayMode.name());
        properties.store(new FileWriter(file), null);
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
