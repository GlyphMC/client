package io.github.arctanmc.arctan.mixin.modules.discord;

import io.github.arctanmc.arctan.rpc.DiscordRPC;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

	@Inject(method = "addPlayer", at = @At("HEAD"))
	public void addPlayer(int id, AbstractClientPlayer player, CallbackInfo ci) {
		String partyId = UUID.randomUUID().toString();
		String secret = UUID.randomUUID().toString();
		DiscordRPC.updateActivity("Singleplayer", partyId, secret);
	}

	@Inject(method = "disconnect", at = @At("HEAD"))
	public void removePlayer(CallbackInfo ci) {
		DiscordRPC.updateActivity("In the Menu", null, null);
	}

}
