package dev.azuuure.playerlist.mixin;

import dev.azuuure.playerlist.provider.PlayerListModProvider;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Hud.class)
public abstract class HudMixin {

    @Shadow @Final
    private Minecraft minecraft;

    @Redirect(
            method = "extractTabList",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/KeyMapping;isDown()Z"
            )
    )
    private boolean showPlayerList(KeyMapping instance) {
        if (minecraft.gui.screen() != null) {
            return false;
        }

        return PlayerListModProvider.getInstance()
                .getSettings()
                .isListRenderingEnabled();
    }
}
