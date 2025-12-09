package dev.azuuure.playerlist.mixin;

import dev.azuuure.playerlist.BetterPlayerList;
import dev.azuuure.playerlist.settings.latency.LatencyDisplayMode;
import dev.azuuure.playerlist.utils.ColorUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import org.joml.Matrix3x2fStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin {

    @Shadow @Final
    private MinecraftClient client;

    @Shadow
    private Text header;

    @Shadow
    private Text footer;

    @Redirect(
            method = "render",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/hud/PlayerListHud;header:Lnet/minecraft/text/Text;",
                    opcode = Opcodes.GETFIELD
            )
    )
    public Text redirectHeader(PlayerListHud instance) {
        if (!BetterPlayerList.getInstance().getSettings().isHeaderEnabled()) {
            return null;
        }

        return header;
    }

    @Redirect(
            method = "render",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/hud/PlayerListHud;footer:Lnet/minecraft/text/Text;",
                    opcode = Opcodes.GETFIELD
            )
    )
    public Text redirectFooter(PlayerListHud instance) {
        if (!BetterPlayerList.getInstance().getSettings().isFooterEnabled()) {
            return null;
        }

        return footer;
    }

    @Inject(method = "renderLatencyIcon", at = @At("HEAD"), cancellable = true)
    public void renderLatencyAsText(DrawContext ctx, int width, int x, int y, PlayerListEntry entry, CallbackInfo ci) {
        var mod = BetterPlayerList.getInstance();
        var settings = mod.getSettings();

        LatencyDisplayMode mode = settings.getLatencyDisplayMode();
        if (mode == LatencyDisplayMode.VANILLA) {
            return;
        }

        int ms = Math.min(9999, entry.getLatency());
        int color = ColorUtils.latencyToColor(ms);
        var renderer = client.textRenderer;
        boolean showUnit = mode == LatencyDisplayMode.COMPACT_WITH_UNIT
                || mode == LatencyDisplayMode.FULL_SIZE;

        Text text;
        if (showUnit) {
            text = Text.literal(ms + "ms")
                    .withColor(color);
        } else {
            text = Text.literal(String.valueOf(ms))
                    .withColor(color);
        }

        switch (mode) {
            case COMPACT_WITH_UNIT:
            case COMPACT: {
                float scale = 0.5f;
                Matrix3x2fStack matrices = ctx.getMatrices();
                matrices.pushMatrix();
                matrices.scale(scale);

                int maxX = (int)((x + width - 2) / scale);
                int drawX = maxX - renderer.getWidth(text);
                int drawY = (int)(y / scale + 5);
                ctx.drawTextWithShadow(renderer, text, drawX, drawY, -1);

                matrices.popMatrix();
                break;
            }
            case FULL_SIZE: {
                int drawX = (x + width) - renderer.getWidth(text);
                ctx.drawTextWithShadow(renderer, text, drawX, y, -1);
                break;
            }
            case DISABLED: {
                break;
            }
        }

        ci.cancel();
    }

    // targets this line:
    //   int m = Math.min(p * ((bl ? 9 : 0) + j + q + 13), scaledWindowWidth - 50) / p;
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

    @ModifyVariable(
            method = "render",
            at = @At(value = "STORE"),
            name = "bl"
    )
    public boolean renderPlayerHeads(boolean value) {
        var settings = BetterPlayerList.getInstance().getSettings();

        if (!settings.shouldRenderHeads()) {
            return false;
        }

        if (settings.forcesHeads()) {
            return true;
        }

        return value;
    }
}
