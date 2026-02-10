package dev.azuuure.playerlist.screen;

import dev.azuuure.playerlist.PlayerListMod;
import dev.azuuure.playerlist.provider.PlayerListModProvider;
import dev.azuuure.playerlist.settings.PlayerListSettings;
import dev.azuuure.playerlist.settings.latency.LatencyDisplayMode;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.util.List;

public final class PlayerListOptionsScreen extends OptionsSubScreen {

    private final PlayerListMod mod;

    public PlayerListOptionsScreen(Screen parent) {
        super(
                parent,
                Minecraft.getInstance().options,
                Component.translatable("betterplayerlist.settings.title")
        );

        this.mod = PlayerListModProvider.getInstance();
    }

    @Override
    protected void addOptions() {
        if (list == null) {
            throw new IllegalStateException("Called addOptions with a null list");
        }

        PlayerListSettings settings = mod.getSettings();

        list.addHeader(Component.translatable("betterplayerlist.settings.server-features"));
        list.addSmall(
                List.of(
                        CycleButton.onOffBuilder(settings.isHeaderEnabled())
                                .withTooltip((_) ->
                                        Tooltip.create(
                                                Component.translatable("betterplayerlist.settings.header.tooltip")
                                        )
                                ).create(Component.translatable("betterplayerlist.settings.header"),
                                        (w, v) -> settings.setHeaderEnabled(v)),

                        CycleButton.onOffBuilder(settings.isFooterEnabled())
                                .withTooltip((_) ->
                                        Tooltip.create(
                                                Component.translatable("betterplayerlist.settings.footer.tooltip")
                                        )
                                ).create(Component.translatable("betterplayerlist.settings.footer"),
                                        (_, v) -> settings.setFooterEnabled(v)))
        );


        list.addHeader(Component.translatable("betterplayerlist.settings.controls"));
        list.addSmall(
                List.of(
                        Button.builder(Component.translatable("controls.keybinds"), (_) -> {
                            KeyBindsScreen screen = new KeyBindsScreen(this, minecraft.options);
                            minecraft.setScreen(screen);
                        }).build(),

                        CycleButton
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
                                        (_, v) -> settings.setKeybindHold(v))
                )
        );

        var withUnit = CycleButton.onOffBuilder(settings.isLatencyUnitEnabled())
                .withTooltip((_) ->
                        Tooltip.create(
                                Component.translatable("betterplayerlist.settings.show-units.tooltip")
                        )
                ).create(Component.translatable("betterplayerlist.settings.show-units"),
                        (_, v) -> settings.setLatencyUnitEnabled(v));

        var symbols = CycleButton
                .builder((d) -> Component.translatable(d.getPath()), settings.getLatencyDisplayMode())
                .withValues(LatencyDisplayMode.values())
                .withTooltip((v) -> Tooltip.create(Component.translatable(v.getPath() + ".tooltip")))
                .create(
                        Component.translatable("betterplayerlist.settings.latency-symbols"),
                        (_, v) -> {
                            withUnit.active = v.canDisplayUnit();
                            settings.setLatencyDisplayMode(v);
                        }
                );

        if (!settings.getLatencyDisplayMode().canDisplayUnit()) {
            withUnit.active = false;
        }

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

        list.addHeader(Component.translatable("betterplayerlist.settings.player-entries"));
        list.addSmall(
                List.of(renderHeads, forceHeads, symbols, withUnit)
        );
    }

    @Override
    public void onClose() {
        super.onClose();

        try {
            mod.getSettings().save();
        } catch (IOException e) {
            mod.getLogger().error("Failed to write configuration to disk", e);
        }
    }
}
