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
	public void func_94581_a(IconRegister iconRegister)
	{
		String name = this.getUnlocalizedName();
		if(name.equals("item." + Constants.KEY_LEG_RAW))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_LEG_RAW);
		}
		else if(name.equals("item." + Constants.KEY_LEG_COOKED))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_LEG_COOKED);
		}
	}
}
