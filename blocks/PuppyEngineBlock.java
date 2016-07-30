package com.puppydemon.puppymod.blocks;

import cofh.api.energy.IEnergyProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
// TODO Switch extends to PuppyTileEntity
public class PuppyEngineBlock extends PuppyModelBlock implements IEnergyProvider, ISidedInventory {
	
	private boolean canPlayerAccess = true;
	private boolean canInsertItems = true;
	private boolean canSetCustomName = false;
	protected int[] fuelSlot = new int[0];
	protected ItemStack[] inventory;
	
	protected int energy;
	protected int capacity;
	protected int maxExtract;

	public PuppyEngineBlock (Material material, String name, float hardness, float resistance, HarvestToolEnum harvesttool, HarvestLevelEnum harvestlevel, CreativeTabs creativetab, int capacity, int maxExtract) {
		super(material, name, hardness, resistance, harvesttool, harvestlevel,creativetab, 1);
	}

	public void EnergyStorage(int capacity) {

		EnergyStorage(capacity, capacity);
	}

	public void EnergyStorage(int capacity, int maxExtract) {

		this.capacity = capacity;
		this.maxExtract = maxExtract;
	}

	public PuppyEngineBlock readFromNBT(NBTTagCompound nbt) {

		this.energy = nbt.getInteger("Energy");

		if (energy > capacity) {
			energy = capacity;
		}
		return this;
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

		if (energy < 0) {
			energy = 0;
		}
		nbt.setInteger("Energy", energy);
		return nbt;
	}

	public void setCapacity(int capacity) {

		this.capacity = capacity;

		if (energy > capacity) {
			energy = capacity;
		}
	}



	public void setMaxExtract(int maxExtract) {

		this.maxExtract = maxExtract;
	}

	public int getMaxExtract() {

		return maxExtract;
	}

	/**
	 * This function is included to allow for server -&gt; client sync. Do not call this externally to the containing Tile Entity, as not all IEnergyHandlers
	 * are guaranteed to have it.
	 *
	 * @param energy
	 */
	public void setEnergyStored(int energy) {

		this.energy = energy;

		if (this.energy > capacity) {
			this.energy = capacity;
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}

	public void modifyEnergyStored(int energy) {

		this.energy += energy;

		if (this.energy > capacity) {
			this.energy = capacity;
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		return energy;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return capacity;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int getSizeInventory() {
		return getNumberSlots();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
	    if(slot < 0 || slot >= inventory.length) {
	        return null;
	      }
	      return inventory[slot];
	    }

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
	    ItemStack fromStack = inventory[slot];
	    if(fromStack == null) {
	      return null;
	    }
	    if(fromStack.stackSize <= amount) {
	      inventory[slot] = null;
	      return fromStack;
	    }
	    ItemStack result = new ItemStack(fromStack.getItem(), amount, fromStack.getItemDamage());
	    if(fromStack.getTagCompound() != null) {
	      result.setTagCompound( (NBTTagCompound) fromStack.getTagCompound().copy());
	    }
	    fromStack.stackSize -= amount;
	    return result;
	  }

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
	    if(stack == null) {
	        inventory[slot] = stack;
	      } else {
	        inventory[slot] = stack.copy();
	      }

	      if(stack != null && stack.stackSize > getInventoryStackLimit(slot)) {
	        stack.stackSize = getInventoryStackLimit(slot);
	      }
	    }
	
	public int getInventoryStackLimit(int slot) {
		return getInventoryStackLimit();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.canPlayerAccess;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getName() {
		return this.getLocalizedName();
	}

	@Override
	public boolean hasCustomName() {
		return canSetCustomName;
	}

	@Override
	public ITextComponent getDisplayName() {
		return this.getDisplayName();
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return fuelSlot;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return canInsertItems;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return false;
	}
	
	  public void writeCommon(NBTTagCompound nbt) {

		    // write inventory list
		    NBTTagList itemList = new NBTTagList();
		    for (int i = 0; i < inventory.length; i++) {
		      if(inventory[i] != null) {
		        NBTTagCompound itemStackNBT = new NBTTagCompound();
		        itemStackNBT.setByte("Slot", (byte) i);
		        inventory[i].writeToNBT(itemStackNBT);
		        itemList.appendTag(itemStackNBT);
		      }
		    }
		    nbt.setTag("Items", itemList);

	  }
}