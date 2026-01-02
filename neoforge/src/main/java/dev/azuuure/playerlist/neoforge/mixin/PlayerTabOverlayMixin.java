package dev.azuuure.playerlist.neoforge.mixin;

import dev.azuuure.playerlist.neoforge.BetterPlayerList;
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
    public Component redirectHeader(PlayerTabOverlay instance) {
        if (!BetterPlayerList.getInstance().getSettings().isHeaderEnabled()) {
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
    public Component redirectFooter(PlayerTabOverlay instance) {
        if (!BetterPlayerList.getInstance().getSettings().isFooterEnabled()) {
            return null;
        }

        return footer;
    }


    @Inject(method = "renderPingIcon", at = @At("HEAD"), cancellable = true)
    public void renderLatencyAsText(GuiGraphics guiGraphics, int width, int x, int y, PlayerInfo playerInfo, CallbackInfo ci) {
        var mod = BetterPlayerList.getInstance();
        var settings = mod.getSettings();

        LatencyDisplayMode mode = settings.getLatencyDisplayMode();
        if (mode == LatencyDisplayMode.VANILLA) {
            return;
        }

        int ms = Math.min(9999, playerInfo.getLatency());
        int color = ColorUtils.latencyToColor(ms);
        Font font = minecraft.font;
        boolean showUnit = mode == LatencyDisplayMode.COMPACT_WITH_UNIT
                || mode == LatencyDisplayMode.FULL_SIZE;

        Component component;
        if (showUnit) {
            component = Component.literal(ms + "ms")
                    .withColor(color);
        } else {
            component = Component.literal(String.valueOf(ms))
                    .withColor(color);
        }

        switch (mode) {
            case COMPACT_WITH_UNIT:
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
    //   int j3 = Math.min(l2 * ((flag1 ? 9 : 0) + j + i3 + 13), width - 50) / l2;
    @ModifyConstant(
            method = "render",
            constant = @Constant(intValue = 13)
    )
    public int expandEntries(int constant) {
        var mode = BetterPlayerList
                .getInstance()
                .getSettings()
                .getLatencyDisplayMode();

        switch (mode) {
            case COMPACT: {
                constant += 5;
                break;
            }
            case COMPACT_WITH_UNIT: {
                constant += 10;
                break;
            }
            case DISABLED: {
                constant -= 5;
                break;
            }
            case FULL_SIZE: {
                constant += 26;
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
    public boolean renderPlayerHeads(boolean value) {
        var settings = BetterPlayerList.getInstance().getSettings();

        if (!settings.isHeadRenderingEnabled()) {
            return false;
        }

        if (settings.isForcingHeads()) {
            return true;
        }

        return value;
    }
}
