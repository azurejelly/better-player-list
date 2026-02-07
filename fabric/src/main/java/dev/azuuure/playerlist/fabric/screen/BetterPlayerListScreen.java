package dev.azuuure.playerlist.fabric.screen;

import dev.azuuure.playerlist.fabric.BetterPlayerList;
import dev.azuuure.playerlist.settings.BetterPlayerListSettings;
import dev.azuuure.playerlist.settings.latency.LatencyDisplayMode;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.util.List;

public final class BetterPlayerListScreen extends OptionsSubScreen {

    private final BetterPlayerListSettings settings;

    public BetterPlayerListScreen(Screen parent) {
        super(
                parent,
                Minecraft.getInstance().options,
                Component.translatable("betterplayerlist.settings.title")
        );

        this.settings = BetterPlayerList.getInstance().getSettings();
    }

    @Override
    protected void addOptions() {
        if (list == null) {
            return;
        }

        // just in case because i do not trust minecraft
        var header = CycleButton.onOffBuilder(settings.isHeaderEnabled())
                .withTooltip((v) ->
                        Tooltip.create(
                                Component.translatable("betterplayerlist.settings.header.tooltip")
                        )
                ).create(Component.translatable("betterplayerlist.settings.header"),
                        (w, v) -> settings.setHeaderEnabled(v));

        var footer = CycleButton.onOffBuilder(settings.isFooterEnabled())
                .withTooltip((v) ->
                        Tooltip.create(
                                Component.translatable("betterplayerlist.settings.footer.tooltip")
                        )
                ).create(Component.translatable("betterplayerlist.settings.footer"),
                        (_, v) -> settings.setFooterEnabled(v));

        var hold = CycleButton
                .booleanBuilder(
                        Component.translatable("betterplayerlist.settings.key.hold"),
                        Component.translatable("betterplayerlist.settings.key.toggle"),
                        settings.isKeybindHold()
                )
                .withTooltip((_) ->
                        Tooltip.create(
                                Component.translatable("betterplayerlist.settings.key.tooltip",
                                        minecraft.options.keyPlayerList
                                                .getTranslatedKeyMessage()
                                                .copy()
                                                .withStyle(ChatFormatting.BOLD)
                                )
                        )
                ).create(Component.translatable("betterplayerlist.settings.key"),
                        (_, v) -> settings.setKeybindHold(v));

        var symbols = CycleButton
                .builder((d) -> Component.translatable(d.getPath()), settings.getLatencyDisplayMode())
                .withValues(LatencyDisplayMode.values())
                .withTooltip((v) -> Tooltip.create(Component.translatable(v.getPath() + ".tooltip")))
                .create(
                        Component.translatable("betterplayerlist.settings.latency-symbols"),
                        (_, v) -> settings.setLatencyDisplayMode(v)
                );

        var forceHeads = CycleButton.onOffBuilder(settings.isForcingHeads())
                .withTooltip((_) ->
                        Tooltip.create(
                                Component.translatable("betterplayerlist.settings.force-heads.tooltip")
                        )
                ).create(Component.translatable("betterplayerlist.settings.force-heads"),
                        (_, v) -> settings.setForcingHeads(v));

        if (!settings.isHeadRenderingEnabled()) {
            forceHeads.active = false;
        }

        var renderHeads = CycleButton.onOffBuilder(settings.isHeadRenderingEnabled())
                .withTooltip((_) ->
                        Tooltip.create(
                                Component.translatable("betterplayerlist.settings.render-heads.tooltip")
                        )
                ).create(Component.translatable("betterplayerlist.settings.render-heads"),
                        (_, v) -> {
                            settings.setHeadRenderingEnabled(v);
                            forceHeads.active = v;
                        });

        list.addSmall(
                List.of(header, footer, hold, symbols, renderHeads, forceHeads)
        );
    }

    @Override
    public void onClose() {
        super.onClose();

        try {
            settings.save();
        } catch (IOException e) {
            BetterPlayerList.getInstance().getLogger().error("Failed to write configuration to disk", e);
        }
    }
}
