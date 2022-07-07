package io.github.arctanmc.arctan.config;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.github.arctanmc.arctan.ArctanClient;
import it.unimi.dsi.fastutil.objects.Reference2BooleanOpenHashMap;

public class ArctanConfig {
	private static final Gson GSON = new GsonBuilder()
		.setPrettyPrinting()
		.excludeFieldsWithModifiers(Modifier.PRIVATE)
		.create();
	
	private Path path;

	public final Map<String, Boolean> enabledModules = new Reference2BooleanOpenHashMap<>();

	public ArctanConfig() {}

	public ArctanConfig(Path path) {
		this.path = path;
	}

	public static ArctanConfig load(Path path) {
		ArctanConfig config;

		if (Files.exists(path)) {
			try (FileReader reader = new FileReader(path.toFile())) {
				config = GSON.fromJson(reader, ArctanConfig.class);
				config.path = path;
			} catch (IOException e) {
				ArctanClient.LOGGER.error("Error while loading config. Reverting to defaults.", e);
				config = new ArctanConfig(path);
			}
		} else {
			config = new ArctanConfig(path);
		}

		try {
			config.save();
		} catch (IOException e) {
			ArctanClient.LOGGER.error("Couldn't save config.", e);
		}

		return config;
	}

	public void save() throws IOException {
		Files.writeString(path, GSON.toJson(this));
	}
}
