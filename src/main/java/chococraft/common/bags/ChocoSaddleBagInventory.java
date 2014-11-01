// <copyright file="ChocoSaddleBagInventory.java">
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
// <summary>Inventory class for chocobo saddle bags</summary>

package chococraft.common.bags;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import chococraft.common.entities.EntityChocoboRideable;


public class ChocoSaddleBagInventory extends ChocoBagInventory
{
	
	public static int INV_SIZE_SMALL = 36;
	
	public ChocoSaddleBagInventory(EntityChocoboRideable entityChocobo)
	{
		this.entitychocobo = entityChocobo;
		this.mainInventory = new ItemStack[INV_SIZE_SMALL];
	}	

	@Override
	public String getInvName()
	{
        return this.entitychocobo.getName() + " saddle bags";
	}
	
	@Override
	public void readFromNBT(NBTTagList nbttaglist) {
        mainInventory = new ItemStack[INV_SIZE_SMALL];

        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot") & 0xff;
            ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);

            if (itemstack == null)
            {
                continue;
            }

            if (j >= 0 && j < mainInventory.length)
            {
                mainInventory[j] = itemstack;
            }
        }
	}

	@Override
	public NBTBase writeToNBT(NBTTagList nbtTagList) {
        for (int i = 0; i < mainInventory.length; i++)
        {
            if (this.mainInventory[i] != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.mainInventory[i].writeToNBT(nbttagcompound);
                nbtTagList.appendTag(nbttagcompound);
            }
        }
        return nbtTagList;
	}

	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public void markDirty() {

	}

	@Override
	public void openInventory() {

	}

	@Override
	public void closeInventory() {

	}
}
