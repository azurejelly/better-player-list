package dev.azuuure.playerlist.settings;

import dev.azuuure.playerlist.settings.latency.LatencyDisplayMode;
import dev.azuuure.playerlist.utils.Constants;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.Properties;

@Getter
@Setter
public final class BetterPlayerListSettings {

    private final File file;
    private final Properties properties;

    private boolean headerEnabled;
    private boolean footerEnabled;
    private LatencyDisplayMode latencyDisplayMode;
    private boolean keybindHold;
    private boolean listRenderingEnabled;
    private boolean headRenderingEnabled;
    private boolean forcingHeads;

    public BetterPlayerListSettings(File gameDirectory) {
        this.headerEnabled = true;
        this.footerEnabled = true;
        this.latencyDisplayMode = LatencyDisplayMode.FULL_SIZE;
        this.keybindHold = true;
        this.listRenderingEnabled = false;
        this.headRenderingEnabled = true;
        this.forcingHeads = false;
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

        this.headerEnabled = Boolean.parseBoolean(properties.getProperty("header", String.valueOf(headerEnabled)));
        this.footerEnabled = Boolean.parseBoolean(properties.getProperty("footer", String.valueOf(footerEnabled)));
        this.keybindHold = Boolean.parseBoolean(properties.getProperty("hold", String.valueOf(keybindHold)));
        this.headRenderingEnabled = Boolean.parseBoolean(properties.getProperty("heads", String.valueOf(headRenderingEnabled)));
        this.forcingHeads = Boolean.parseBoolean(properties.getProperty("force-heads", String.valueOf(forcingHeads)));

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

        properties.setProperty("header", String.valueOf(headerEnabled));
        properties.setProperty("footer", String.valueOf(footerEnabled));
        properties.setProperty("hold", String.valueOf(keybindHold));
        properties.setProperty("heads", String.valueOf(headRenderingEnabled));
        properties.setProperty("force-heads", String.valueOf(forcingHeads));
        properties.setProperty("latency", latencyDisplayMode.name());
        properties.store(new FileWriter(file), null);
    }
}
