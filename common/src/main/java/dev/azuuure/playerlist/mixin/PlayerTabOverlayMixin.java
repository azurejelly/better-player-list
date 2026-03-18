package dev.azuuure.playerlist.mixin;

import dev.azuuure.playerlist.PlayerListMod;
import dev.azuuure.playerlist.provider.PlayerListModProvider;
import dev.azuuure.playerlist.settings.PlayerListSettings;
import dev.azuuure.playerlist.settings.latency.LatencyDisplayMode;
import dev.azuuure.playerlist.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
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

    @Shadow private Component header;
    @Shadow private Component footer;

    @Shadow @Final
    private Minecraft minecraft;

    @Redirect(
            method = "extractRenderState",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/components/PlayerTabOverlay;header:Lnet/minecraft/network/chat/Component;",
                    opcode = Opcodes.GETFIELD
            )
    )
    public Component redirectHeader(PlayerTabOverlay instance) {
        PlayerListMod mod = PlayerListModProvider.getInstance();
        if (!mod.getSettings().isHeaderEnabled()) {
            return null;
        }

        return header;
    }

    @Redirect(
            method = "extractRenderState",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/components/PlayerTabOverlay;footer:Lnet/minecraft/network/chat/Component;",
                    opcode = Opcodes.GETFIELD
            )
    )
    public Component redirectFooter(PlayerTabOverlay instance) {
        PlayerListMod mod = PlayerListModProvider.getInstance();
        if (!mod.getSettings().isFooterEnabled()) {
            return null;
        }

        return footer;
    }


    @Inject(
            method = "extractPingIcon",
            at = @At("HEAD"),
            cancellable = true
    )
    public void modifyPingIcon(GuiGraphicsExtractor graphics, int slotWidth, int xo, int yo, PlayerInfo info, CallbackInfo ci) {
        PlayerListMod mod = PlayerListModProvider.getInstance();
        PlayerListSettings settings = mod.getSettings();
        LatencyDisplayMode mode = settings.getLatencyDisplayMode();

        if (mode == LatencyDisplayMode.VANILLA) {
            return;
        }

        Font font = minecraft.font;
        int ms = Math.min(9999, info.getLatency());
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
                Matrix3x2fStack matrices = graphics.pose();
                matrices.pushMatrix();
                matrices.scale(scale);

                int maxX = (int)((xo + slotWidth - 2) / scale);
                int drawX = maxX - font.width(component);
                int drawY = (int)(yo / scale + 5);
                graphics.text(font, component, drawX, drawY, -1, true);

                matrices.popMatrix();
                break;
            }
            case FULL_SIZE: {
                int drawX = (xo + slotWidth) - font.width(component);
                graphics.text(font, component, drawX, yo, -1, true);
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
            method = "extractRenderState",
            constant = @Constant(intValue = 13)
    )
    public int expandSlotWidth(int constant) {
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
    //   boolean showHead = this.minecraft.isLocalServer() || this.minecraft.getConnection().getConnection().isEncrypted();
    @ModifyVariable(
            method = "extractRenderState",
            at = @At("STORE"),
            name = "showHead"
    )
    public boolean manageHeadRendering(boolean value) {
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
