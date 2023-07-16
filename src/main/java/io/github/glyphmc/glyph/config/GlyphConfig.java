/*
 * Copyright (c) 2023 GlyphMC
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

package io.github.glyphmc.glyph.config;

import org.quiltmc.config.api.WrappedConfig;
import org.quiltmc.config.api.annotations.Comment;

public class GlyphConfig extends WrappedConfig {

	@Comment("General Settings")
	public final GeneralConfig general = new GeneralConfig();

	public class GeneralConfig implements Section {

		@Comment("Enable Glyph's Keystrokes Overlay")
		public final boolean keystrokes = true;

		@Comment("Enable Glyph's CPS Counter")
		public final boolean cps = true;

		@Comment("Enable Glyph's Discord RPC")
		public final boolean discordRPC = true;
	}

}
