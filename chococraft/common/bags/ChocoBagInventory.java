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

import chococraft.common.entities.EntityChocoboRideable;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagList;

public abstract class ChocoBagInventory implements IInventory
{
	protected ItemStack[] mainInventory;
	protected EntityChocoboRideable entitychocobo;
	protected boolean inventoryChanged;
	
	@Override
	public int getSizeInventory()
	{
		return this.mainInventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return this.mainInventory[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		this.mainInventory[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit())
		{
			stack.stackSize = getInventoryStackLimit();
		}              
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt)
	{
		ItemStack stack = getStackInSlot(slot);
		if (stack != null)
		{
			if (stack.stackSize <= amt)
			{
				this.setInventorySlotContents(slot, null);
			}
			else
			{
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0)
				{
					this.setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack stack = getStackInSlot(slot);
		if (stack != null)
		{
			this.setInventorySlotContents(slot, null);
		}
		return stack;
	}
	
	@Override
	public void onInventoryChanged()
	{
        this.inventoryChanged = true;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		boolean useable = false;
		if(null != this.entitychocobo)
		{
		useable = player.getDistanceSqToEntity(this.entitychocobo) <= 64D;
		}
		return useable;
	}

	public void dropAllItems()
	{
        for (int i = 0; i < this.getSizeInventory(); i++)
        {
        	ItemStack itemStack = this.getStackInSlot(i);
            if (itemStack != null && this.entitychocobo != null)
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
				ItemStack dropItemStack = inventory.getStackInSlot(invIdx);
				if(null != dropItemStack && dropItemStack.stackSize > 0)
				{
					if(null !=  this.entitychocobo)
					{
						this.entitychocobo.entityDropItem(dropItemStack, 0.0F);
					}
				}
			}
			inventory.setInventorySlotContents(invIdx, null);
		}
	}
	
	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	abstract public void readFromNBT(NBTTagList nbttaglist);
	
	abstract public NBTBase writeToNBT(NBTTagList nbtTagList);
	
	abstract public String getInvName();
}
