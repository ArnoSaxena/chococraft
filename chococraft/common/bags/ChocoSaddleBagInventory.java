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

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import chococraft.common.entities.EntityChocoboRideable;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;

public class ChocoSaddleBagInventory extends ChocoBagInventory {
	
	public static int INVENTORY_SIZE_SMALL = 36;
	
	public ChocoSaddleBagInventory(EntityChocoboRideable entityChocobo)
	{
		this.inventoryChanged = false;
		this.entitychocobo = entityChocobo;
		this.mainInventory = new ItemStack[INVENTORY_SIZE_SMALL];
	}

	@Override
	public String getInvName()
	{
        return this.entitychocobo.getName() + " saddle bags";
	}

	public void readFromNBT(NBTTagList nbttaglist) {
        mainInventory = new ItemStack[INVENTORY_SIZE_SMALL];

        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
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
	public void writeSpawnData(ByteArrayDataOutput data)
	{
//		for(int i = 0; i < INVENTORY_SIZE_SMALL; i++)
//		{
//			data.writeInt(this.mainInventory[i].stackSize);
//			if(this.mainInventory[i].stackSize > 0)
//			{
//				data.writeInt(this.mainInventory[i].itemID);
//				data.writeInt(this.mainInventory[i].getItemDamage());
//			}
//		}
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data)
	{		
//		for(int i = 0; i < INVENTORY_SIZE_SMALL; i++)
//		{
//			int stackSize = data.readInt();
//			if(stackSize > 0)
//			{
//				this.mainInventory[i] = new ItemStack(stackSize, data.readInt(), data.readInt());				
//			}
//		}
	}
}
