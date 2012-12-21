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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

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
    public ItemStack transferStackInSlot(EntityPlayer player, int slot)
    {
        ItemStack iStackTarget = null;
        Slot slotObj = (Slot)this.inventorySlots.get(slot);

        if (slotObj != null && slotObj.getHasStack())
        {
            ItemStack iStackSource = slotObj.getStack();
            iStackTarget = iStackSource.copy();

            int nSlots = this.chocoBagInv.getSizeInventory();
            
            if (slot < nSlots)
            {
                if (!this.mergeItemStack(iStackSource, nSlots, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(iStackSource, 0, nSlots, false))
            {
                return null;
            }

            if (iStackSource.stackSize == 0)
            {
                slotObj.putStack((ItemStack)null);
            }
            else
            {
                slotObj.onSlotChanged();
            }
        }
        return iStackTarget;
    }
}
