package io.github.arctanmc.arctan.mixin.modules.discord;

import io.github.arctanmc.arctan.rpc.DiscordRPC;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.UUID;

@Mixin(ServerLevel.class)
@Environment(EnvType.CLIENT)
public class ServerLevelMixin {

	@Shadow
	@Final
	List<ServerPlayer> players;

	@Inject(method = "addPlayer", at = @At("HEAD"))
	public void addPlayer(ServerPlayer player, CallbackInfo ci) {
		String partyId = UUID.randomUUID().toString();
		String secret = UUID.randomUUID().toString();
		int size = players.size();
		DiscordRPC.updateActivity("Multiplayer (" + size + " players)", partyId, secret);
	}

	// TODO Disconnect when player leaves

}
