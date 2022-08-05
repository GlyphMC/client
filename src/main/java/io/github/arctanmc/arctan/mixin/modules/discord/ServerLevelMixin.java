package io.github.arctanmc.arctan.mixin.modules.discord;

import io.github.arctanmc.arctan.rpc.RPCEvents;
import io.github.arctanmc.arctan.rpc.RPCUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerLevel.class)
@Environment(EnvType.CLIENT)
public abstract class ServerLevelMixin {

	@Shadow
	@Final
	List<ServerPlayer> players;

	@Inject(at = @At("HEAD"), method = "addPlayer")
	public void addPlayer(ServerPlayer player, CallbackInfo ci) {
		System.out.println("server level add player");
		RPCEvents.getInstance().gameRPC(RPCUtils.GameType.MULTIPLAYER, players.size());
	}

}
