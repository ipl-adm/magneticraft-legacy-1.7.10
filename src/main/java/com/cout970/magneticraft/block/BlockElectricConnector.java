package com.cout970.magneticraft.block;

import buildcraft.api.tools.IToolWrench;
import cofh.api.item.IToolHammer;
import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.electricity.ITileElectricPole;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConnector;
import com.cout970.magneticraft.api.tool.IWrench;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.pole.TileElectricConnector;
import com.cout970.magneticraft.tileentity.pole.TileElectricConnectorDown;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockElectricConnector extends BlockMg {

    public BlockElectricConnector() {
        super(Material.wood);
        setCreativeTab(CreativeTabsMg.IndustrialAgeTab);
        float tam = 5f * 0.0625f;
        setBlockBounds(tam, 0, tam, 1 - tam, 1, 1 - tam);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int meta) {
        if (meta >= 6) return new TileElectricConnector();
        return new TileElectricConnectorDown();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public String getName() {
        return "ElectricPoleCableConnectionSmall";
    }

    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side) {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack item) {

        int meta = w.getBlockMetadata(x, y, z);
        if (meta == 0) {
            int m = p == null ? 0 : MathHelper.floor_double((double) ((p.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
            m >>= 1;
            w.setBlock(x, y + 1, z, this, m + 6, 2);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World w, int x, int y, int z) {
        for (int d = 0; d <= 1; d++) {
            Block block = w.getBlock(x, y + 1, z);
            if (block != null && !block.isReplaceable(w, x, y + d, z)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World w, int x, int y, int z, Block b, int meta) {
        super.breakBlock(w, x, y, z, b, meta);
        if (meta == 0) {
            w.setBlockToAir(x, y + 1, z);
        } else {
            w.setBlockToAir(x, y - 1, z);
        }
    }

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p.isSneaking() && p.getCurrentEquippedItem() != null) {
            Item item = p.getCurrentEquippedItem().getItem();
            if (MgUtils.isWrench(item)) {
                TileEntity te = w.getTileEntity(x, y, z);
                TileElectricConnector tilePole = null;
                if (te instanceof ITileElectricPole) {
                    tilePole = (TileElectricConnector) ((ITileElectricPole) te).getMainTile();
                }
                if (tilePole != null) {
                    int currentMode = tilePole.pole.getConnectionMode();
                    ElectricConnector pole = tilePole.pole;
                    String messageBase = "Connection mode set to ";
                    switch (currentMode) {
                        case 0: {
                            pole.blockAutoConnections();
                            if (!w.isRemote) {
                                p.addChatComponentMessage(new ChatComponentText(messageBase + "MANUAL."));
                            }
                            break;
                        }

                        case 1: {
                            pole.blockAllConnections();
                            if (!w.isRemote) {
                                p.addChatComponentMessage(new ChatComponentText(messageBase + "BLOCKED."));
                            }
                            break;
                        }

                        case 2: {
                            pole.allowConnections();
                            if (!w.isRemote) {
                                p.addChatComponentMessage(new ChatComponentText(messageBase + "ALLOWED."));
                            }
                            break;
                        }
                    }
                    tilePole.clientUpdate = true;
                    tilePole.updateEntity();
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
