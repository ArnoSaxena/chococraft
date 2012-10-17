package chococraft.common.items;

import chococraft.common.Constants;
import chococraft.common.ModChocoCraft;
import chococraft.debugger.DebugEntitySpawner;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ChocoboItem extends Item {

	public ChocoboItem(int itemId) {
		super(itemId);
		this.setTextureFile(Constants.CHOCOBO_ITEM_TEXTURES);
	}
//	
//	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
//	{
//		super.onItemRightClick(itemStack, world, player);
//		return itemStack;
//	}
}
