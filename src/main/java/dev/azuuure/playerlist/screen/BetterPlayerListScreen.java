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
        body.addAll(
                List.of(
                        CyclingButtonWidget.onOffBuilder()
                                .initially(settings.isHeaderEnabled())
                                .tooltip((v) ->
                                        Tooltip.of(
                                                Text.translatable("better-player-list.settings.header.tooltip")
                                        )
                                ).build(Text.translatable("better-player-list.settings.header"),
                                        (w, v) -> settings.setHeader(v)),

                        CyclingButtonWidget.onOffBuilder()
                                .initially(settings.isFooterEnabled())
                                .tooltip((v) ->
                                        Tooltip.of(
                                                Text.translatable("better-player-list.settings.footer.tooltip")
                                        )
                                ).build(Text.translatable("better-player-list.settings.footer"),
                                        (w, v) -> settings.setFooter(v)),

                        CyclingButtonWidget.onOffBuilder()
                                .initially(settings.shouldHold())
                                .tooltip((v) ->
                                        Tooltip.of(
                                                Text.translatable("better-player-list.settings.hold.tooltip")
                                        )
                                ).build(Text.translatable("better-player-list.settings.hold"),
                                        (w, v) -> settings.setShouldHold(v)),

                        CyclingButtonWidget
                                .onOffBuilder(
                                        Text.translatable("better-player-list.settings.latency-symbols.numbers"),
                                        Text.translatable("better-player-list.settings.latency-symbols.vanilla")
                                ).initially(settings.shouldReplaceLatencySymbols())
                                .tooltip((v) ->
                                        Tooltip.of(
                                                Text.translatable("better-player-list.settings.latency-symbols.tooltip")
                                        )
                                ).build(Text.translatable("better-player-list.settings.latency-symbols"),
                                        (w, v) -> settings.setReplaceLatencySymbols(v))
                )
        );
    }
}
