package dev.azuuure.playerlist.mixin;

import dev.azuuure.playerlist.BetterPlayerList;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Redirect(
            method = "renderPlayerList",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z"
            )
    )
    private boolean redirectKeypressDetection(KeyBinding instance) {
        return BetterPlayerList.getInstance()
                .getSettings()
                .shouldDisplayList();
    }
}
