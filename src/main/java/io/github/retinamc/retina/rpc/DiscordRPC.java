package io.github.retinamc.retina.rpc;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import io.github.retinamc.retina.RetinaClient;
import net.minecraft.client.Minecraft;

public class DiscordRPC {

    public static void init() {
		IPCClient client = new IPCClient(947459441022885888L);
		client.setListener(new IPCListener() {
			@Override
			public void onReady(IPCClient client) {
				RichPresence.Builder builder = new RichPresence.Builder();
				String version = Minecraft.getInstance().getGame().getVersion().getName();
				builder.setState("Retina Client")
					.setDetails("Playing Minecraft " + version)
					.setLargeImage("retina-client", "Retina Client");
				client.sendRichPresence(builder.build());
			}
		});
		try {
			client.connect(DiscordBuild.STABLE, DiscordBuild.PTB, DiscordBuild.CANARY);
			RetinaClient.LOGGER.info("Connected to Discord");
		} catch (NoDiscordClientException e) {
			RetinaClient.LOGGER.info("No Discord Client found");
		}
	}

}
