package com.cout970.magneticraft.block;

import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileReactorController;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockReactorController extends BlockMg {

    public BlockReactorController() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.InformationAgeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileReactorController();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"reactor_controller", "reactor_controller_head"};
    }

    @Override
    public String getName() {
        return "reactor_controller";
    }

    public IIcon getIcon(int side, int meta) {
        return side == meta % 6 ? icons[1] : icons[0];
    }

    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i) {
        int l = MathHelper.floor_double((double) (p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (l == 0)
            w.setBlockMetadataWithNotify(x, y, z, 2, 2);
        if (l == 1)
            w.setBlockMetadataWithNotify(x, y, z, 5, 2);
        if (l == 2)
            w.setBlockMetadataWithNotify(x, y, z, 3, 2);
        if (l == 3)
            w.setBlockMetadataWithNotify(x, y, z, 4, 2);
    }
}
