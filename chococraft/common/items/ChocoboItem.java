package chococraft.common.items;

import chococraft.common.Constants;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ChocoboItem extends Item {

	public ChocoboItem(int itemId) {
		super(itemId);
		this.setTextureFile(Constants.CHOCOBO_ITEM_TEXTURES);
	}

	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		super.onItemRightClick(itemStack, world, player);
		
		/// testing
		
//		Random rand = new Random();
//		int colourIdx = rand.nextInt((chocoboColor.values()).length);
//		EntityChicobo ec = FactoryEntityChocobo.createNewChicobo(world, chocoboColor.values()[colourIdx]);		
//		ec.setLocationAndAngles(player.posX, player.posY, player.posZ, 0.0F, 0.0F);
//		world.spawnEntityInWorld(ec);
		
		/// end testing
		
		
		return itemStack;
	}
}
