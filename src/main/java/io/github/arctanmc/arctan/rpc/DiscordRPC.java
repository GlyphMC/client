package io.github.arctanmc.arctan.rpc;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import io.github.arctanmc.arctan.ArctanClient;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.time.OffsetDateTime;

public class DiscordRPC {
	public static Core core;

	public static void init() {
		try {
			Core.initDownload();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try(CreateParams params = new CreateParams()) {
			params.setClientID(947514926589693983L);
			params.setFlags(CreateParams.getDefaultFlags());
			// Create the Core
				core = new Core(params);
				// Create the Activity
				try (Activity activity = new Activity()) {
					activity.setDetails("Playing Minecraft " + SharedConstants.getCurrentVersion().getName());
					activity.setState("Arctan Client");

					// Setting a start time causes an "elapsed" field to appear
					activity.timestamps().setStart(Instant.now());

					// Make a "cool" image show up
					activity.assets().setLargeImage("arctan");

					// Setting a join secret and a party ID causes an "Ask to Join" button to appear
					activity.party().setID("Party!");
					activity.secrets().setJoinSecret("Join!");

					// Finally, update the current activity to our activity
					core.activityManager().updateActivity(activity);
			}
		}
	}
}
