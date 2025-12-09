package dev.azuuure.playerlist.mixin;

import dev.azuuure.playerlist.BetterPlayerList;
import dev.azuuure.playerlist.utils.Constants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {

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
        if (!BetterPlayerList.getInstance().getSettings().shouldReplaceLatencySymbols()) {
            return;
        }

        int latency = entry.getLatency();
        int color;

        if (latency < 100) {
            color = 0x8cf985;
        } else if (latency < 150) {
            color = 0x1c9115;
        } else if (latency < 300) {
            color = 0xfffa47;
        } else if (latency < 600) {
            color = 0xfd4b4b;
        } else if (latency < 1000) {
            color = 0xff0000;
        } else {
            color = 0x8f0000;
        }

        var renderer = client.textRenderer;
        var matrices = ctx.getMatrices();

        matrices.pushMatrix();
        matrices.scale(Constants.LATENCY_TEXT_SCALE);

        Text text;
        if (latency < 9999) {
            text = Text.literal(String.valueOf(latency)).withColor(color);
        } else {
            // do not display anything higher to prevent text from
            // rendering over (or below) the player username
            text = Text.literal("9999").withColor(color);
        }

        var maxX = (int)((x + width - 2) / Constants.LATENCY_TEXT_SCALE);
        var drawX = maxX - renderer.getWidth(text);
        var drawY = (int)(y / Constants.LATENCY_TEXT_SCALE + 5);

        // i have no idea what -1 ("color"?) is, but if i change it the entire text
        // for some reason stops rendering. i will simply assume it does not exist
        // and move on with my life.
        ctx.drawTextWithShadow(renderer, text, drawX, drawY, -1);

        matrices.popMatrix();
        ci.cancel();
    }

    @ModifyVariable(
            method = "render",
            at = @At(value = "STORE"),
            name = "j"
    )
    public int expandEntries(int value) {
        if (!BetterPlayerList.getInstance().getSettings().shouldReplaceLatencySymbols()) {
            return value;
        }

        // give a little more space for the numbers to display correctly
        return value + 1;
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
