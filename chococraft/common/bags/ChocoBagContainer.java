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
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class ChocoBagContainer extends Container
{
    private IInventory playerInventory;
    private int playerNumRows;

    public ChocoBagContainer(IInventory chocoboIInventory, IInventory playerIInventory)
    {
        playerInventory = playerIInventory;
        playerNumRows = playerIInventory.getSizeInventory() / 9;
        playerIInventory.openChest();
        int i = (playerNumRows - 4) * 18;

        for (int j = 0; j < playerNumRows; j++)
        {
            for (int i1 = 0; i1 < 9; i1++)
            {
                this.addSlotToContainer(new Slot(playerIInventory, i1 + j * 9, 8 + i1 * 18, 18 + j * 18));
            }
        }

        for (int k = 0; k < 3; k++)
        {
            for (int j1 = 0; j1 < 9; j1++)
            {
            	this.addSlotToContainer(new Slot(chocoboIInventory, j1 + k * 9 + 9, 8 + j1 * 18, 103 + k * 18 + i));
            }
        }

        for (int l = 0; l < 9; l++)
        {
        	this.addSlotToContainer(new Slot(chocoboIInventory, l, 8 + l * 18, 161 + i));
        }
    }

    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return playerInventory.isUseableByPlayer(entityPlayer);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack transferStackInSlot(int invIdx)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)inventorySlots.get(invIdx);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (invIdx < playerNumRows * 9)
            {
                if (!mergeItemStack(itemstack1, playerNumRows * 9, inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(itemstack1, 0, playerNumRows * 9, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void onCraftGuiClosed(EntityPlayer entityPlayer)
    {
        super.onCraftGuiClosed(entityPlayer);
        playerInventory.closeChest();
    }
}
