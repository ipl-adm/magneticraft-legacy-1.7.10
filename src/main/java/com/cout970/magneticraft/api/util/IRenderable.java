package com.cout970.magneticraft.api.util;

import net.minecraft.util.ResourceLocation;

/**
 * @author Cout970
 */
public interface IRenderable {

    ResourceLocation getTexture();

    /**
     * both methods render at the same time but the static is not affected by rotation or translation
     *
     * @param f5 render scale usually 0.0625f
     */
    void renderStatic(float f5);

    void renderDynamic(float f5, float additionalData);
}
