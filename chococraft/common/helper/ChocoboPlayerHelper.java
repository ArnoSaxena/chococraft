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
