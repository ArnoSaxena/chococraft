package chococraft.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemFood;
import chococraft.common.config.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ChocoboItemFood extends ItemFood
{
	public ChocoboItemFood(int healAmt, boolean wolfsFavorite)
	{
		super(healAmt, wolfsFavorite);
		this.maxStackSize = 64;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		String name = this.getUnlocalizedName().substring(5);
		this.itemIcon = iconRegister.registerIcon(Constants.TCC_MODID + ":" + name);
	}
}
