package dev.azuuure.playerlist.fabric.mixin;

import dev.azuuure.playerlist.fabric.BetterPlayerList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow @Final
    private MinecraftClient client;

    @Redirect(
            method = "renderPlayerList",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z"
            )
    )
    private boolean showPlayerList(KeyBinding instance) {
        if (client.currentScreen != null) {
            return false;
        }

        return BetterPlayerList.getInstance()
                .getSettings()
                .shouldDisplayList();
    }
}
