package com.cout970.magneticraft.block;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileFluidHopper;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BlockFluidHopper extends BlockMg {

    public BlockFluidHopper() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.SteamAgeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileFluidHopper();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public String getName() {
        return "fluid_hopper";
    }

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int par6, float par7, float par8, float par9) {

        if (p.isSneaking()) return false;
        boolean h;
        ItemStack current = p.inventory.getCurrentItem();
        if (current != null) {
            FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(current);
            IFluidHandler1_8 tank = (IFluidHandler1_8) w.getTileEntity(x, y, z);
            if (liquid != null) {
                int qty = tank.fillMg(MgDirection.UP, liquid, true);
                if (qty != 0 && !p.capabilities.isCreativeMode) {
                    p.inventory.setInventorySlotContents(p.inventory.currentItem, consumeItem(current));
                }
                if (qty == 0) {
                    p.openGui(Magneticraft.INSTANCE, 0, w, x, y, z);
                }
                return true;
            } else {
                h = true;
            }
        } else {
            h = true;
        }
        if (h) {
            p.openGui(Magneticraft.INSTANCE, 0, w, x, y, z);
            return true;
        }
        return false;
    }

    public static ItemStack consumeItem(ItemStack stack) {
        if (stack.stackSize == 1) {
            if (stack.getItem().hasContainerItem(stack)) {
                return stack.getItem().getContainerItem(stack);
            } else {
                return null;
            }
        } else {
            stack.splitStack(1);
            return stack;
        }
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }

    public int onBlockPlaced(World w, int x, int y, int z, int side, float p_149660_6_, float p_149660_7_, float p_149660_8_, int meta) {
        return ForgeDirection.getOrientation(side).getOpposite().ordinal();
    }
}
