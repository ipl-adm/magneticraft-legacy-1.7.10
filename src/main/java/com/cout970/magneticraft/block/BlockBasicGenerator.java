package com.cout970.magneticraft.block;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileBasicGenerator;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BlockBasicGenerator extends BlockMg {

    public BlockBasicGenerator() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileBasicGenerator();
    }

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int par6, float par7, float par8, float par9) {

        if (p.isSneaking()) return false;
        
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
            }
        }
        p.openGui(Magneticraft.INSTANCE, 0, w, x, y, z);
        return true;
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

    @Override
    public String[] getTextures() {
        return new String[]{"basic_generator", "basic_generator_head", "basic_generator_on"};
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta == 0) {
            return side == 3 ? icons[1] : icons[0];
        } else if (meta > 5) {
            return side == meta - 6 ? icons[2] : icons[0];
        }
        return side == meta ? icons[1] : icons[0];
    }

    @Override
    public String getName() {
        return "basic_generator";
    }

    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i) {
        int l = MathHelper.floor_double((double) (p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (l == 0) {
            w.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        if (l == 1) {
            w.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
        if (l == 2) {
            w.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        if (l == 3) {
            w.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
    }
}
