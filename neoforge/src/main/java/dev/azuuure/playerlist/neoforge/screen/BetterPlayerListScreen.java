package dev.azuuure.playerlist.neoforge.screen;

import dev.azuuure.playerlist.neoforge.BetterPlayerList;
import dev.azuuure.playerlist.settings.BetterPlayerListSettings;
import dev.azuuure.playerlist.settings.latency.LatencyDisplayMode;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModContainer;

import java.io.IOException;

public final class BetterPlayerListScreen extends OptionsSubScreen {

    private final BetterPlayerListSettings settings;
    private final Screen parent;

    public BetterPlayerListScreen(ModContainer unused, Screen parent) {
        this(parent);
    }

    public BetterPlayerListScreen(Screen parent) {
        super(
                parent,
                Minecraft.getInstance().options,
                Component.translatable("betterplayerlist.settings.title")
        );

        this.parent = parent;
        this.settings = BetterPlayerList.getInstance().getSettings();
    }

    @Override
    protected void addOptions() {
        if (list == null) {
            throw new IllegalStateException("Called addOptions with a null list");
        }

        list.addSmall(
                CycleButton.onOffBuilder(settings.isHeaderEnabled())
                        .withTooltip((v) ->
                                Tooltip.create(
                                        Component.translatable("betterplayerlist.settings.header.tooltip")
                                )
                        ).create(Component.translatable("betterplayerlist.settings.header"),
                                (w, v) -> settings.setHeaderEnabled(v)
                        ),
                CycleButton.onOffBuilder(settings.isFooterEnabled())
                        .withTooltip((v) ->
                                Tooltip.create(
                                        Component.translatable("betterplayerlist.settings.footer.tooltip")
                                )
                        ).create(Component.translatable("betterplayerlist.settings.footer"),
                                (w, v) -> settings.setFooterEnabled(v)
                        )
        );

        list.addSmall(
                CycleButton
                        .booleanBuilder(
                                Component.translatable("betterplayerlist.settings.key.hold"),
                                Component.translatable("betterplayerlist.settings.key.toggle"),
                                settings.isKeybindHold()
                        )
                        .withTooltip((v) ->
                            Tooltip.create(
                                    Component.translatable("betterplayerlist.settings.key.tooltip",
                                            minecraft.options.keyPlayerList
                                                    .getTranslatedKeyMessage()
                                                    .copy()
                                                    .withStyle(ChatFormatting.BOLD)
                                    )
                            )
                        ).create(Component.translatable("betterplayerlist.settings.key"),
                                (w, v) -> settings.setKeybindHold(v)
                        ),

                CycleButton
                        .builder((d) -> Component.translatable(d.getPath()), settings.getLatencyDisplayMode())
                        .withValues(LatencyDisplayMode.values())
                        .withTooltip((v) -> Tooltip.create(Component.translatable(v.getPath() + ".tooltip")))
                        .create(
                                Component.translatable("betterplayerlist.settings.latency-symbols"),
                                (w, v) -> settings.setLatencyDisplayMode(v)
                        )
        );

        var forceHeads = CycleButton.onOffBuilder(settings.isForcingHeads())
                .withTooltip((v) ->
                        Tooltip.create(
                                Component.translatable("betterplayerlist.settings.force-heads.tooltip")
                        )
                ).create(
                        Component.translatable("betterplayerlist.settings.force-heads"),
                        (w, v) -> settings.setForcingHeads(v)
                );

        if (!settings.isHeadRenderingEnabled()) {
            forceHeads.active = false;
        }

        list.addSmall(
                CycleButton
                        .onOffBuilder(settings.isHeadRenderingEnabled())
                        .withTooltip((v) ->
                                Tooltip.create(
                                        Component.translatable("betterplayerlist.settings.render-heads.tooltip")
                                )
                        ).create(Component.translatable("betterplayerlist.settings.render-heads"),
                                (w, v) -> {
                                    settings.setHeadRenderingEnabled(v);
                                    forceHeads.active = v;
                                }),
                forceHeads
        );
    }

    @Override
    public void onClose() {
        super.onClose();

        try {
            settings.save();
        } catch (IOException e) {
            BetterPlayerList.getInstance()
                    .getLogger()
                    .error("Failed to write configuration to disk", e);
        }
    }
}
