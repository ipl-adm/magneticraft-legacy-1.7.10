package com.cout970.magneticraft.block;

import com.cout970.magneticraft.api.electricity.ITileElectricPole;
import com.cout970.magneticraft.api.electricity.prefab.ElectricPoleTier1;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.pole.TileElectricPoleGap;
import com.cout970.magneticraft.tileentity.pole.TileElectricPoleTier1;
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

public class BlockElectricPoleTier1 extends BlockMg {

    public BlockElectricPoleTier1() {
        super(Material.wood);
        setCreativeTab(CreativeTabsMg.IndustrialAgeTab);
        float tam = 5f * 0.0625f;
        setBlockBounds(tam, 0, tam, 1 - tam, 1, 1 - tam);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int meta) {
        if (meta >= 6) return new TileElectricPoleTier1();
        return new TileElectricPoleGap();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public String getName() {
        return "ElectricPoleTier1";
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
            for (int d = 1; d <= 3; d++) {
                w.setBlock(x, y + d, z, this, d, 2);
            }
            int m = p == null ? 0 : MathHelper.floor_double((double) ((p.rotationYaw + 180.0F) * 16.0F / 360.0F) + 0.5D) & 15;
            m >>= 1;
            w.setBlock(x, y + 4, z, this, m + 6, 2);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World w, int x, int y, int z) {
        for (int d = 0; d <= 4; d++) {
            Block block = w.getBlock(x, y + d, z);
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
            for (int d = 1; d <= 4; d++) {
                w.setBlockToAir(x, y + d, z);
            }
        } else {
            if (meta < 6)
                w.setBlockToAir(x, y - meta, z);
            else
                w.setBlockToAir(x, y - 4, z);
        }
    }

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p.isSneaking() && p.getCurrentEquippedItem() != null) {
            Item item = p.getCurrentEquippedItem().getItem();
            if (MgUtils.isWrench(item)) {
                TileEntity te = w.getTileEntity(x, y, z);
                TileElectricPoleTier1 tilePole = null;
                if (te instanceof ITileElectricPole) {
                    tilePole = (TileElectricPoleTier1) ((ITileElectricPole) te).getMainTile();
                }
                if (tilePole != null) {
                    int currentMode = tilePole.pole.getConnectionMode();
                    ElectricPoleTier1 pole = tilePole.pole;
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
                    tilePole.updateCables = true;
                    tilePole.updateEntity();
                }
            }
        } else {
            return false;
        }
        return true;
    }

}
