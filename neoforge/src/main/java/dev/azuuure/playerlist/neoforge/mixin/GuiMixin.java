package dev.azuuure.playerlist.neoforge.mixin;

import dev.azuuure.playerlist.neoforge.BetterPlayerList;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow @Final
    private Minecraft minecraft;

    @Redirect(
            method = "renderTabList",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/KeyMapping;isDown()Z"
            )
    )
    private boolean showPlayerList(KeyMapping instance) {
        if (minecraft.screen != null) {
            return false;
        }

        return BetterPlayerList.getInstance()
                .getSettings()
                .shouldDisplayList();
    }
}
