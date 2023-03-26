package io.github.arctanmc.arctan.mixin.modules.discord;

import io.github.arctanmc.arctan.rpc.GameType;
import io.github.arctanmc.arctan.rpc.RPCEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

	@Shadow
	@Final
	private Minecraft minecraft;

	@Inject(method = "addPlayer", at = @At("HEAD"))
	public void addPlayer(int id, AbstractClientPlayer player, CallbackInfo ci) {
		if (minecraft.isLocalServer()) {
			RPCEvents.getInstance().gameRPC(GameType.SINGLEPLAYER);
		} else {
			RPCEvents.getInstance().gameRPC(GameType.MULTIPLAYER);
		}
	}

}
