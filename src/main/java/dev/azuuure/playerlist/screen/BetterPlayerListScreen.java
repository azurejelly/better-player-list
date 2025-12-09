package dev.azuuure.playerlist.screen;

import dev.azuuure.playerlist.BetterPlayerList;
import dev.azuuure.playerlist.settings.BetterPlayerListSettings;
import dev.azuuure.playerlist.settings.latency.LatencyDisplayMode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;

import java.util.List;

public final class BetterPlayerListScreen extends GameOptionsScreen {

    private final BetterPlayerListSettings settings;

    public BetterPlayerListScreen(Screen parent) {
        super(
                parent,
                MinecraftClient.getInstance().options,
                Text.translatable("better-player-list.settings.title")
        );

        this.settings = BetterPlayerList.getInstance().getSettings();
    }

    @Override
    protected void addOptions() {
        if (body == null) {
            return;
        }

        // just in case because i do not trust minecraft
        var client = this.client != null ? this.client : MinecraftClient.getInstance();
        var header = CyclingButtonWidget.onOffBuilder()
                .initially(settings.isHeaderEnabled())
                .tooltip((v) ->
                        Tooltip.of(
                                Text.translatable("better-player-list.settings.header.tooltip")
                        )
                ).build(Text.translatable("better-player-list.settings.header"),
                        (w, v) -> settings.setHeader(v));

        var footer = CyclingButtonWidget.onOffBuilder()
                .initially(settings.isFooterEnabled())
                .tooltip((v) ->
                        Tooltip.of(
                                Text.translatable("better-player-list.settings.footer.tooltip")
                        )
                ).build(Text.translatable("better-player-list.settings.footer"),
                        (w, v) -> settings.setFooter(v));

        var hold = CyclingButtonWidget
                .onOffBuilder(
                        Text.translatable("better-player-list.settings.key.hold"),
                        Text.translatable("better-player-list.settings.key.toggle")
                ).initially(settings.shouldHold())
                .tooltip((v) ->
                        Tooltip.of(
                                Text.translatable("better-player-list.settings.key.tooltip",
                                        Text.translatable(client.options.playerListKey.getBoundKeyTranslationKey())
                                                .styled(s -> s.withBold(true))
                                )
                        )
                ).build(Text.translatable("better-player-list.settings.key"),
                        (w, v) -> settings.setShouldHold(v));

        var symbols = CyclingButtonWidget
                .builder(d -> ((LatencyDisplayMode) d).getName())
                .values(LatencyDisplayMode.values())
                .initially(settings.getLatencyDisplayMode())
                .tooltip((v) -> {
                    var mode = (LatencyDisplayMode) v;
                    var path = mode.getPath();
                    return Tooltip.of(Text.translatable(path + ".tooltip"));
                }).build(
                        Text.translatable("better-player-list.settings.latency-symbols"),
                        (w, v) -> {
                            var mode = (LatencyDisplayMode) v;
                            settings.setLatencyDisplayMode(mode);
                        }
                );

        var forceHeads = CyclingButtonWidget.onOffBuilder()
                .initially(settings.forcesHeads())
                .tooltip((v) ->
                        Tooltip.of(
                                Text.translatable("better-player-list.settings.force-heads.tooltip")
                        )
                ).build(Text.translatable("better-player-list.settings.force-heads"),
                        (w, v) -> settings.setForceHeads(v));

        if (!settings.shouldRenderHeads()) {
            forceHeads.active = false;
        }

        var renderHeads = CyclingButtonWidget.onOffBuilder()
                .initially(settings.shouldRenderHeads())
                .tooltip((v) ->
                        Tooltip.of(
                                Text.translatable("better-player-list.settings.render-heads.tooltip")
                        )
                ).build(Text.translatable("better-player-list.settings.render-heads"),
                        (w, v) -> {
                            settings.setShouldRenderHeads(v);
                            forceHeads.active = v;
                        });

        body.addAll(
                List.of(header, footer, hold, symbols, renderHeads, forceHeads)
        );
    }

    @Override
    public void close() {
        super.close();
        settings.save();
    }
}
