package io.github.retinamc.retina.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.AbstractWidget;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractWidget.class)
@Environment(EnvType.CLIENT)
public class AbstractWidgetMixin {

}
