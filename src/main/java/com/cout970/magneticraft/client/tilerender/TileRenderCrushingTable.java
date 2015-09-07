package com.cout970.magneticraft.client.tilerender;

import com.cout970.magneticraft.client.model.ModelCrushingTable;
import com.cout970.magneticraft.tileentity.TileCrushingTable;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.ENTITY;

public class TileRenderCrushingTable extends TileEntitySpecialRenderer {

    private final RenderItem RenderItemMG;
    private final EntityItem itemEntity = new EntityItem(null);
    private ModelCrushingTable model;

    public TileRenderCrushingTable() {
        model = new ModelCrushingTable();
        RenderItemMG = new RenderItem() {
            @Override
            public boolean shouldBob() {
                return false;
            }

            @Override
            public boolean shouldSpreadItems() {
                return false;
            }
        };
        RenderItemMG.setRenderManager(RenderManager.instance);
    }

    @Override
    public void renderTileEntityAt(TileEntity t, double x, double y, double z, float frames) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glRotatef(180, 0, 0, 1);
        TileCrushingTable tile = (TileCrushingTable) t;

        RenderUtil.bindTexture(ModelTextures.CRUSHING_TABLE);
        model.renderStatic(0.0625f);
        if (tile.getInput() != null) {
            ItemStack itemstack = tile.getInput();
            if (itemstack.getItemSpriteNumber() == 0 && itemstack.getItem() instanceof ItemBlock
                    && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemstack.getItem()).getRenderType())) {
                GL11.glRotatef(180, 0, 0, 1);
                GL11.glTranslatef(0, -0.5F, 0);
            } else if (MinecraftForgeClient.getItemRenderer(itemstack, ENTITY) == null) {
                GL11.glTranslatef(0, 0.605F, -0.1F);
                GL11.glRotatef(90, 1, 0, 0);
            } else {
                GL11.glRotatef(180, 0, 0, 1);
                GL11.glTranslatef(0, -0.5F, 0);
            }
            itemEntity.setEntityItemStack(tile.getInput());
            itemEntity.age = 0;
            itemEntity.hoverStart = 0;

            boolean config = RenderManager.instance.options.fancyGraphics;
            RenderManager.instance.options.fancyGraphics = true;
            RenderItemMG.doRender(itemEntity, 0, 0, 0, 0, 0);
            RenderManager.instance.options.fancyGraphics = config;
        }
        GL11.glPopMatrix();
    }
}
