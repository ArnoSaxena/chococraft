// <copyright file="ChocoSaddleBagContainer.java">
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
// <summary>container class for chococraft containers</summary>

package chococraft.common.bags;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class ChocoBagContainer extends Container
{
	private int chocoBagRows;
	private ChocoBagInventory chocoBagInv;

	public ChocoBagContainer (InventoryPlayer inventoryPlayer, ChocoBagInventory chocoBagInv)
	{
		this.chocoBagInv = chocoBagInv;
		this.chocoBagRows = chocoBagInv.getSizeInventory() / 9;

		int offset = 0;
		if(this.chocoBagInv instanceof ChocoPackBagInventory)
		{
			offset -= 24;
		}
		
		for (int i = 0; i < this.chocoBagRows; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(chocoBagInv, j + i * 9, 8 + j * 18, 18 + i * 18 + offset));
			}
		}
		this.bindPlayerInventory(inventoryPlayer);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return chocoBagInv.isUseableByPlayer(player);
	}


	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		int offset = (this.chocoBagRows - 4) * 18;
		if(this.chocoBagInv instanceof ChocoPackBagInventory)
		{
			offset -= 24;
		}
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 103 + i * 18 + offset));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 161 + offset));
		}
	}

	@Override
//	public ItemStack transferStackInSlot(int slot)	
	public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int slot)
	{
		ItemStack stack = null;
		Slot slotObject = (Slot) this.inventorySlots.get(slot);

		if (slotObject != null && slotObject.getHasStack())
		{
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			if (slot == 0)
			{
				if (!this.mergeItemStack(stackInSlot, 1, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(stackInSlot, 0, 1, false))
			{
				return null;
			}

			if (stackInSlot.stackSize == 0)
			{
				slotObject.putStack(null);
			}
			else
			{
				slotObject.onSlotChanged();
			}
		}
		return stack;
	}
}
