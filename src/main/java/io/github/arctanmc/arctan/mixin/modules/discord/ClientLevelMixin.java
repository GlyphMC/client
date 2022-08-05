package io.github.arctanmc.arctan.mixin.modules.discord;

import io.github.arctanmc.arctan.rpc.RPCEvents;
import io.github.arctanmc.arctan.rpc.RPCUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

	@Inject(method = "addPlayer", at = @At("HEAD"))
	public void addPlayer(int id, AbstractClientPlayer player, CallbackInfo ci) {
		System.out.println("client level add player");
		RPCEvents.getInstance().gameRPC(RPCUtils.GameType.SINGLEPLAYER);
	}

	/*
	TODO Distinguish better between singleplayer and multiplayer:
	Singleplayer: first server level add player is triggered, then client level add player
	Multiplayer: only client level add player is triggered
	 */

}
