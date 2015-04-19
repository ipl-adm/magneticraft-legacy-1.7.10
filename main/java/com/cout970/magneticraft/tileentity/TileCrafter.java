package com.cout970.magneticraft.tileentity;


import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.tileentity.TileCrafter.RedstoneState;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.InventoryCrafterAux;
import com.cout970.magneticraft.util.InventoryUtils;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.tile.RedstoneControl;

public class TileCrafter extends TileBase implements IInventoryManaged{

	private InventoryComponent inv = new InventoryComponent(this, 16, "Crafter");
	private InventoryComponent invResult = new InventoryComponent(this, 1, "Result");
	private InventoryCrafting recipe = new InventoryCrafterAux(this, 3, 3);
	private List<InvSlot> checked = new ArrayList<InvSlot>();
	private int itemMatches = -512;
	private IRecipe craftRecipe;
	private boolean nextCraft = false;
	public RedstoneState state = RedstoneState.NORMAL;

	public InventoryComponent getInv(){
		return inv;
	}

	public void onNeigChange(){
		super.onNeigChange();
		if(isPowered())nextCraft = true;
	}
	
	public void updateEntity(){
		super.updateEntity();
		if(itemMatches == -512){
			refreshItemMatches();
		}
		if(isControled()){
			craft();
			nextCraft = false;
		}
	}
	
	public boolean isControled(){
		if(state == RedstoneState.NORMAL)return !Powered;
		if(state == RedstoneState.INVERTED)return Powered;
		return nextCraft;
	}

	public void refreshItemMatches(){
		itemMatches = 0;
		checked.clear();
		invResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(recipe, worldObj));
		craftRecipe = null;
		for(Object rec : CraftingManager.getInstance().getRecipeList()){
			if(rec instanceof IRecipe){
				if(((IRecipe) rec).matches(recipe, worldObj)){
					craftRecipe = (IRecipe) rec;
					break;
				}
			}
		}
		if(craftRecipe == null)return;
		ItemStack result = craftRecipe.getCraftingResult(recipe);
		if(result == null)return;
		
		for(int slot=0; slot < 9; slot++){
			if(recipe.getStackInSlot(slot) == null){
				itemMatches |= 1 << slot;
				continue;
			}
			
			if(findItemsFromInventory(slot, this, checked, result, MgDirection.UP)){
				itemMatches |= 1 << slot;
			}else{
				for(MgDirection dir : MgDirection.values()){
					TileEntity t = MgUtils.getTileEntity(this, dir);
					if(t instanceof IInventory){
						IInventory side = (IInventory) t;
						if(findItemsFromInventory(slot, side, checked, result, dir)){
							itemMatches |= 1 << slot;
							break;
						}
					}
				}
			}
		}
	}

	public boolean craft(){
		if(worldObj.isRemote)return false;
		refreshItemMatches();
		if(itemMatches == 0x1FF && craftRecipe != null){
			ItemStack item = craftRecipe.getCraftingResult(recipe);
			if(item != null){
				int slot = InventoryUtils.getSlotForStack(getInv(), item);
				if(slot != -1){
					for(InvSlot s : checked){
						InventoryUtils.remove(s.inv, s.slot, s.amount);
					}
					ItemStack result = InventoryUtils.addition(getInv().getStackInSlot(slot), item);
					getInv().setInventorySlotContents(slot, result);
					checked.clear();
					return true;
				}
			}
		}
		return false;
	}

	private boolean findItemsFromInventory(int slot, IInventory inv, List<InvSlot> visited, ItemStack result, MgDirection dir) {
		for(int i = 0; i< inv.getSizeInventory(); i++){
			ItemStack content = inv.getStackInSlot(i);
			if(content != null && canAcces(inv,i,dir)){
				if(remplaceMatrix(recipe, result, content, slot)){
					InvSlot pos = new InvSlot(content, i, inv);
					if(!visited.contains(pos)){
						visited.add(pos);
						return true;
					}else{
						InvSlot ex = visited.get(visited.indexOf(pos));
						if(content.stackSize > ex.amount){
							ex.amount++;
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean canAcces(IInventory inv, int slot, MgDirection dir) {
		if(inv instanceof ISidedInventory){
			ISidedInventory sided = (ISidedInventory) inv;
			for(int i : sided.getAccessibleSlotsFromSide(dir.ordinal())){
				if(i == slot){
					if(sided.canExtractItem(slot, sided.getStackInSlot(slot), dir.ordinal())){
						return true;
					}
					break;
				}
			}
			return false;
		}
		return true;
	}

	public boolean remplaceMatrix(InventoryCrafting craft, ItemStack result, ItemStack stack, int slot){
		ItemStack item = craft.getStackInSlot(slot);
		craft.setInventorySlotContents(slot, stack);
		boolean ret = false;
		if(craftRecipe.matches(craft, worldObj)){
			ItemStack newResult = craftRecipe.getCraftingResult(craft);
			ret = OreDictionary.itemMatches(result, newResult, true);
		}
		craft.setInventorySlotContents(slot, item);
		return ret;
	}

	public int getSizeInventory() {
		return getInv().getSizeInventory();
	}

	public ItemStack getStackInSlot(int s) {
		return getInv().getStackInSlot(s);
	}

	public ItemStack decrStackSize(int a, int b) {
		return getInv().decrStackSize(a, b);
	}

	public ItemStack getStackInSlotOnClosing(int a) {
		return getInv().getStackInSlotOnClosing(a);
	}

	public void setInventorySlotContents(int a, ItemStack b) {
		getInv().setInventorySlotContents(a, b);
	}

	public String getInventoryName() {
		return getInv().getInventoryName();
	}

	public boolean hasCustomInventoryName() {
		return getInv().hasCustomInventoryName();
	}

	public int getInventoryStackLimit() {
		return getInv().getInventoryStackLimit();
	}

	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	public void openInventory() {}

	public void closeInventory() {}

	public boolean isItemValidForSlot(int a, ItemStack b) {
		return getInv().isItemValidForSlot(a, b);
	}

	public InventoryCrafting getRecipe(){
		return recipe;
	}
	
	public InventoryComponent getResult(){
		return invResult;
	}
	
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		inv.readFromNBT(nbt,"Inv_1");
		InventoryUtils.loadInventory(recipe,nbt,"Inv_2");
		invResult.readFromNBT(nbt,"Inv_3");
		itemMatches = nbt.getInteger("Matches");
		state = RedstoneState.values()[nbt.getByte("State")];
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		inv.writeToNBT(nbt,"Inv_1");
		InventoryUtils.saveInventory(recipe,nbt,"Inv_2");
		invResult.writeToNBT(nbt,"Inv_3");
		nbt.setInteger("Matches", itemMatches);
		nbt.setByte("State", (byte)state.ordinal());
	}

	public class InvSlot{

		public ItemStack content;
		public int amount;
		public int slot;
		public IInventory inv;

		public InvSlot(ItemStack stack, int slot, IInventory inv){
			this.inv = inv;
			this.slot = slot;
			this.inv = inv;
			this.amount = 1;
		}

		public boolean equals(Object obj){
			if(obj instanceof InvSlot){
				InvSlot pos = (InvSlot) obj;
				return pos.slot == slot && pos.inv == inv && InventoryUtils.areExaticlyEqual(content, pos.content);
			}
			return false;
		}
	}
	
	public enum RedstoneState{
		NORMAL,INVERTED,PULSE;
	}

	public static RedstoneState step(RedstoneState state) {
		return state == RedstoneState.NORMAL ? RedstoneState.INVERTED : state == RedstoneState.INVERTED ? RedstoneState.PULSE : RedstoneState.NORMAL;
	}

	public void setRedstoneState(RedstoneState e) {
		state = e;
		sendUpdateToClient();
	}
}
