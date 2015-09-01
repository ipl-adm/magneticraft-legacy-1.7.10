package com.cout970.magneticraft.items;


import com.cout970.magneticraft.api.electricity.IBatteryItem;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemElectricSword extends ItemCharged {

    public static int CHARGE_PER_HIT = 10000;

    public ItemElectricSword(String unlocalizedname) {
        super(unlocalizedname, 500000);
        setMaxStackSize(1);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Multimap getAttributeModifiers(ItemStack a) {
        Multimap multimap = super.getAttributeModifiers(a);
        if (((IBatteryItem) a.getItem()).getCharge(a) >= CHARGE_PER_HIT)
            multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 10d, 0));
        return multimap;
    }

    public boolean hitEntity(ItemStack i, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
        if (((IBatteryItem) i.getItem()).getCharge(i) >= CHARGE_PER_HIT) {
            ((IBatteryItem) i.getItem()).discharge(i, CHARGE_PER_HIT);
            par2EntityLivingBase.addPotionEffect(new PotionEffect(2, 50, 3));
        } else {
            return false;
        }
        return true;
    }

    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block) {
        if (((IBatteryItem) par1ItemStack.getItem()).getCharge(par1ItemStack) >= CHARGE_PER_HIT) {
            if (Block.isEqualTo(par2Block, Blocks.web)) {
                return 15.0F;
            } else {
                Material material = par2Block.getMaterial();
                return material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves ? 1.0F : 1.5F;
            }
        }
        return 1f;
    }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, Block par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase) {
        if ((double) par3.getBlockHardness(par2World, par4, par5, par6) != 0.0D) {
            if (((IBatteryItem) par1ItemStack.getItem()).getCharge(par1ItemStack) >= CHARGE_PER_HIT) {
                ((IBatteryItem) par1ItemStack.getItem()).discharge(par1ItemStack, CHARGE_PER_HIT);
            } else {
                return false;
            }
        }

        return true;
    }

    public int getMaxItemUseDuration(ItemStack p_77626_1_) {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
}
