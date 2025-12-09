package dev.azuuure.playerlist.screen;

import dev.azuuure.playerlist.BetterPlayerList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;

import java.util.List;

public class BetterPlayerListScreen extends GameOptionsScreen {

    public BetterPlayerListScreen(Screen parent) {
        super(
                parent,
                MinecraftClient.getInstance().options,
                Text.translatable("better-player-list.settings.title")
        );
    }

    @Override
    protected void addOptions() {
        if (body == null) {
            return;
        }

        var settings = BetterPlayerList.getInstance().getSettings();
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

        var hold = CyclingButtonWidget.onOffBuilder()
                .initially(settings.shouldHold())
                .tooltip((v) ->
                        Tooltip.of(
                                Text.translatable("better-player-list.settings.hold.tooltip")
                        )
                ).build(Text.translatable("better-player-list.settings.hold"),
                        (w, v) -> settings.setShouldHold(v));

        var symbols = CyclingButtonWidget
                .onOffBuilder(
                        Text.translatable("better-player-list.settings.latency-symbols.numbers"),
                        Text.translatable("better-player-list.settings.latency-symbols.vanilla")
                ).initially(settings.shouldReplaceLatencySymbols())
                .tooltip((v) ->
                        Tooltip.of(
                                Text.translatable("better-player-list.settings.latency-symbols.tooltip")
                        )
                ).build(Text.translatable("better-player-list.settings.latency-symbols"),
                        (w, v) -> settings.setReplaceLatencySymbols(v));

        var forceHeads = CyclingButtonWidget.onOffBuilder()
                .initially(settings.forcesHeads())
                .tooltip((v) ->
                        Tooltip.of(
                                Text.translatable("better-player-list.settings.force-heads.tooltip")
                        )
                ).build(Text.translatable("better-player-list.settings.force-heads"),
                        (w, v) -> settings.setForceHeads(v));

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
        // TODO: save configuration
    }
}
