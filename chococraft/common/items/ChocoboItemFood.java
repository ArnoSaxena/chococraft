package chococraft.common.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;
import chococraft.common.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ChocoboItemFood extends ItemFood
{
	public ChocoboItemFood(int itemId, int healAmt, boolean wolfsFavorite)
	{
		super(itemId, healAmt, wolfsFavorite);
		this.maxStackSize = 64;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateIcons(IconRegister iconRegister)
	{
		String name = this.getUnlocalizedName().substring(5);
		this.iconIndex = iconRegister.registerIcon(Constants.TCC_MODID + ":" + name);
	}
}
