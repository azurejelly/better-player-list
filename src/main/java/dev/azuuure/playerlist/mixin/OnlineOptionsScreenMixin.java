package dev.azuuure.playerlist.mixin;

import dev.azuuure.playerlist.BetterPlayerList;
import dev.azuuure.playerlist.screen.BetterPlayerListScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OnlineOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(OnlineOptionsScreen.class)
public abstract class OnlineOptionsScreenMixin extends GameOptionsScreenMixin {

    @Inject(method = "addOptions", at = @At("RETURN"))
    private void injectButton(CallbackInfo ci) {
        var inst = (Screen) (Object) this;
        body.addAll(
                List.of(
                        ButtonWidget.builder(
                                Text.translatable("better-player-list.settings.title"),
                                btn -> MinecraftClient
                                        .getInstance()
                                        .setScreen(new BetterPlayerListScreen(inst))
                        ).build()
                )
        );
    }
}
