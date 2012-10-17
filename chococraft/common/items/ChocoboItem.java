package chococraft.common.items;

import chococraft.common.Constants;
import net.minecraft.src.Item;

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
