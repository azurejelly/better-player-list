package dev.azuuure.playerlist.mixin;

import dev.azuuure.playerlist.PlayerListMod;
import dev.azuuure.playerlist.provider.PlayerListModProvider;
import dev.azuuure.playerlist.settings.PlayerListSettings;
import dev.azuuure.playerlist.settings.latency.LatencyDisplayMode;
import dev.azuuure.playerlist.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import org.joml.Matrix3x2fStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerTabOverlay.class)
public abstract class PlayerTabOverlayMixin {

    @Shadow @Final
    private Minecraft minecraft;

    @Shadow
    private Component header;

    @Shadow
    private Component footer;

    @Redirect(
            method = "render",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/components/PlayerTabOverlay;header:Lnet/minecraft/network/chat/Component;",
                    opcode = Opcodes.GETFIELD
            )
    )
    public Component betterplayerlist$redirectHeader(PlayerTabOverlay instance) {
        PlayerListMod mod = PlayerListModProvider.getInstance();
        if (!mod.getSettings().isHeaderEnabled()) {
            return null;
        }

        return header;
    }

    @Redirect(
            method = "render",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/components/PlayerTabOverlay;footer:Lnet/minecraft/network/chat/Component;",
                    opcode = Opcodes.GETFIELD
            )
    )
    public Component betterplayerlist$redirectFooter(PlayerTabOverlay instance) {
        PlayerListMod mod = PlayerListModProvider.getInstance();
        if (!mod.getSettings().isFooterEnabled()) {
            return null;
        }

        return footer;
    }


    @Inject(
            method = "renderPingIcon",
            at = @At("HEAD"),
            cancellable = true
    )
    public void betterplayerlist$renderLatencyAsText(GuiGraphics guiGraphics, int width, int x, int y, PlayerInfo playerInfo, CallbackInfo ci) {
        PlayerListMod mod = PlayerListModProvider.getInstance();
        PlayerListSettings settings = mod.getSettings();
        LatencyDisplayMode mode = settings.getLatencyDisplayMode();

        if (mode == LatencyDisplayMode.VANILLA) {
            return;
        }

        Font font = minecraft.font;
        int ms = Math.min(9999, playerInfo.getLatency());
        int color = ColorUtils.latencyToColor(ms);
        boolean showUnit = mode.canDisplayUnit()
                && settings.isLatencyUnitEnabled();

        Component component;
        if (showUnit) {
            component = Component.literal(ms + "ms")
                    .withColor(color);
        } else {
            component = Component.literal(String.valueOf(ms))
                    .withColor(color);
        }

        switch (mode) {
            case COMPACT: {
                float scale = 0.5f;
                Matrix3x2fStack matrices = guiGraphics.pose();
                matrices.pushMatrix();
                matrices.scale(scale);

                int maxX = (int)((x + width - 2) / scale);
                int drawX = maxX - font.width(component);
                int drawY = (int)(y / scale + 5);
                guiGraphics.drawString(font, component, drawX, drawY, -1, true);

                matrices.popMatrix();
                break;
            }
            case FULL_SIZE: {
                int drawX = (x + width) - font.width(component);
                guiGraphics.drawString(font, component, drawX, y, -1, true);
                break;
            }
            case DISABLED: {
                break;
            }
        }

        ci.cancel();
    }

    // targets:
    //   int slotWidth = Math.min(cols * ((showHead ? 9 : 0) + maxNameWidth + widthForScore + 13), screenWidth - 50) / cols;
    @ModifyConstant(
            method = "render",
            constant = @Constant(intValue = 13)
    )
    public int betterplayerlist$expandEntries(int constant) {
        PlayerListMod mod = PlayerListModProvider.getInstance();
        PlayerListSettings settings = mod.getSettings();
        LatencyDisplayMode mode = settings.getLatencyDisplayMode();
        boolean showUnit = settings.isLatencyUnitEnabled();

        switch (mode) {
            case COMPACT: {
                constant += (showUnit ? 10 : 5);
                break;
            }
            case DISABLED: {
                constant -= 5;
                break;
            }
            case FULL_SIZE: {
                constant += (showUnit ? 26 : 17);
                break;
            }
            default: {
                // noop
            }
        }

        return constant;
    }

    // targets:
    //   boolean flag1 = this.minecraft.isLocalServer() || this.minecraft.getConnection().getConnection().isEncrypted();
    @ModifyVariable(
            method = "render",
            at = @At("STORE"),
            ordinal = 0
    )
    public boolean betterplayerlist$renderPlayerHeads(boolean value) {
        PlayerListMod mod = PlayerListModProvider.getInstance();
        PlayerListSettings settings = mod.getSettings();

        if (!settings.isHeadRenderingEnabled()) {
            return false;
        }

        if (settings.isForcingHeads()) {
            return true;
        }

        return value;
    }
}
