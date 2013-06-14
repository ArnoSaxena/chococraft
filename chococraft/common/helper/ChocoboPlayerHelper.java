// <copyright file="ChocoboPlayerHelper.java">
// Copyright (c) 2013 All Right Reserved, http://chococraft.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <email>al-s@gmx.de</email>
// <date>2013-06-08</date>
// <summary>Static helper class for methods connected to player activities</summary>

package chococraft.common.helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ChocoboPlayerHelper
{
	public static void useCurrentItem(EntityPlayer player)
	{
		ItemStack stack = player.inventory.getCurrentItem();
		if (null != stack)
		{
			stack.stackSize--;
			if (stack.stackSize <= 0)
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
			}
		}
	}
}
