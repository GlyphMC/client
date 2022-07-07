/*
 * Copyright (c) 2022 ArctanMC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.arctanmc.arctan.module.rpc;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import io.github.arctanmc.arctan.ArctanClient;
import io.github.arctanmc.arctan.module.SimpleModule;
import net.minecraft.client.Minecraft;

import java.time.OffsetDateTime;

public class DiscordRPCModule extends SimpleModule {
	private IPCClient client;

	public DiscordRPCModule() {
		super("rpc");
	}

	@Override
	public void onEnable() {
		client = new IPCClient(947514926589693983L);

		client.setListener(new IPCListener() {
			@Override
			public void onReady(IPCClient client) {
				RichPresence.Builder builder = new RichPresence.Builder();
				String version = Minecraft.getInstance().getGame().getVersion().getName();
				builder.setState("Arctan Client")
						.setDetails("Playing Minecraft " + version)
						.setStartTimestamp(OffsetDateTime.now())
						.setLargeImage("arctan", "Arctan Client");
				client.sendRichPresence(builder.build());
			}
		});

		try {
			client.connect(DiscordBuild.ANY);
			ArctanClient.LOGGER.info("Connected to Discord");
		} catch (NoDiscordClientException | RuntimeException e) {
			ArctanClient.LOGGER.info("No Discord Client found");
		}

		super.onEnable();
	}

	@Override
	public void onDisable() {
		client.close();
		client = null;

		super.onDisable();
	}
}
