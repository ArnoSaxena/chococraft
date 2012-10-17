// <copyright file="ChocoBagInventory.java">
// Copyright (c) 2012 All Right Reserved, http://chococraft.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <email>al-s@gmx.de</email>
// <date>2012-05-09</date>
// <summary>abstract inventory class for all chococraft containers</summary>

package chococraft.common.bags;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import chococraft.common.entities.EntityChocoboRideable;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagList;

public abstract class ChocoBagInventory implements IInventory, IEntityAdditionalSpawnData
{

    public ItemStack mainInventory[];
	public boolean inventoryChanged;
	protected EntityChocoboRideable entitychocobo;

    @Override
	public int getSizeInventory() 
	{
		return this.mainInventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		if(i >= this.mainInventory.length)
		{
			return null;
		}
        return this.mainInventory[i];
	}
	
	@Override
	public ItemStack decrStackSize(int i, int decrAmount)
	{
        if (i >= this.mainInventory.length)
        {
        	return null;
        }

        if (this.mainInventory[i] != null)
        {
            if (this.mainInventory[i].stackSize <= decrAmount)
            {
                ItemStack itemstack = this.mainInventory[i];
                this.mainInventory[i] = null;
                return itemstack;
            }

            ItemStack itemstack = this.mainInventory[i].splitStack(decrAmount);

            if (this.mainInventory[i].stackSize == 0)
            {
            	this.mainInventory[i] = null;
            }

            return itemstack;
        }
        else
        {
            return null;
        }
	}
	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
        if (i >= this.mainInventory.length)
        {
        	return null;
        }

        if (this.mainInventory[i] != null)
        {
            ItemStack itemstack = this.mainInventory[i];
            this.mainInventory[i] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
        if (i >= this.mainInventory.length)
        {
        	return;
        }
        this.mainInventory[i] = itemstack;
	}
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void onInventoryChanged()
	{
        this.inventoryChanged = true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
        return entityplayer.getDistanceSqToEntity(this.entitychocobo) <= 64D;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}
	
	public void dropAllItems()
	{
        for (int i = 0; i < this.getSizeInventory(); i++)
        {
        	ItemStack itemStack = this.getStackInSlot(i);
            if (itemStack != null)
            {
                this.entitychocobo.entityDropItem(itemStack, 0.0F);
                this.setInventorySlotContents(i, null);
            }
        }
	}

	public void insertInventory(IInventory inventory)
	{
		if(null == inventory)
		{
			return;
		}
		for(int invIdx = 0; invIdx < inventory.getSizeInventory(); invIdx++)
		{
			if(invIdx < this.getSizeInventory())
			{
				this.setInventorySlotContents(invIdx, inventory.getStackInSlot(invIdx));
			}
			else
			{
				this.entitychocobo.entityDropItem(inventory.getStackInSlot(invIdx), 0.0F);
			}
			inventory.setInventorySlotContents(invIdx, null);
		}
	}	

	abstract public void readFromNBT(NBTTagList nbttaglist);
	
	abstract public NBTBase writeToNBT(NBTTagList nbtTagList);
	
	abstract public String getInvName();
}
