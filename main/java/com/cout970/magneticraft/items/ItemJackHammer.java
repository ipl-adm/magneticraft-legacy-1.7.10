package com.cout970.magneticraft.items;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.cout970.magneticraft.api.electricity.item.IBatteryItem;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemJackHammer extends ItemCharged{
	
	public static int CHARGE_PER_WORK = 5000;
	public static int HARVEST_LEVEL = 3;
	private static final Set vanilla_minable_blocks = Sets.newHashSet(new Block[] {Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail});

	public ItemJackHammer(String unlocalizedname) {
		super(unlocalizedname, 500000);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Multimap getAttributeModifiers(ItemStack a){
		Multimap multimap = super.getAttributeModifiers(a);
		if(((IBatteryItem)a.getItem()).getCharge(a) >= CHARGE_PER_WORK)
			multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 5d, 0));
		return multimap;
	}

	public boolean hitEntity(ItemStack i, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase){
		
		if(((IBatteryItem)i.getItem()).getCharge(i) >= CHARGE_PER_WORK){
			((IBatteryItem)i.getItem()).discharge(i, CHARGE_PER_WORK);
		}else{
			return false;
		}
		return true;
	}
	
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, Block par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase){
		
		if ((double)par3.getBlockHardness(par2World, par4, par5, par6) != 0.0D){
			if(((IBatteryItem)par1ItemStack.getItem()).getCharge(par1ItemStack) >= CHARGE_PER_WORK){
				((IBatteryItem)par1ItemStack.getItem()).discharge(par1ItemStack, CHARGE_PER_WORK);
			}else{
				return false;
			}
		}
		return true;
	}
	
	public int getMaxItemUseDuration(ItemStack p_77626_1_)
	{
		return 0;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
	
	public float func_150893_a(ItemStack item, Block block)
    {
		if(((IBatteryItem)item.getItem()).getCharge(item) < CHARGE_PER_WORK)return 0;
        return block.getMaterial() != Material.iron && block.getMaterial() != Material.anvil && block.getMaterial() != Material.rock ?  !vanilla_minable_blocks.contains(block) ? 1F : 44.9F: 44.9F;
    }
	
	@Override
    public int getHarvestLevel(ItemStack stack, String toolClass)
    {
        int level = super.getHarvestLevel(stack, toolClass);
        if (level == -1)
        {
            return HARVEST_LEVEL;
        }
        else
        {
            return level;
        }
    }
	
	public boolean func_150897_b(Block p_150897_1_)
    {
        return p_150897_1_ == Blocks.obsidian ? HARVEST_LEVEL == 3 : (p_150897_1_ != Blocks.diamond_block && p_150897_1_ != Blocks.diamond_ore ? (p_150897_1_ != Blocks.emerald_ore && p_150897_1_ != Blocks.emerald_block ?
        		(p_150897_1_ != Blocks.gold_block && p_150897_1_ != Blocks.gold_ore ? (p_150897_1_ != Blocks.iron_block && p_150897_1_ != Blocks.iron_ore ? (p_150897_1_ != Blocks.lapis_block && p_150897_1_ != Blocks.lapis_ore ?
        				(p_150897_1_ != Blocks.redstone_ore && p_150897_1_ != Blocks.lit_redstone_ore ? (p_150897_1_.getMaterial() == Material.rock ? true : (p_150897_1_.getMaterial() == Material.iron ? 
        						true : p_150897_1_.getMaterial() == Material.anvil)) : HARVEST_LEVEL >= 2) : HARVEST_LEVEL >= 1) : HARVEST_LEVEL >= 1) : HARVEST_LEVEL >= 2) :
        							HARVEST_LEVEL >= 2) : HARVEST_LEVEL >= 2);
    }
}
