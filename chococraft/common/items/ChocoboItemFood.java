package chococraft.common.items;

import chococraft.common.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;

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
		if(name.equals("item.chocoboLegRaw"))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_LEG_RAW);
		}
		else if(name.equals("item.chocoboLegCooked"))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_LEG_RAW);
		}
	}
}
