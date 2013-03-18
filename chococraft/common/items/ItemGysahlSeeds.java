package chococraft.common.items;

import chococraft.common.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSeeds;

public class ItemGysahlSeeds extends ItemSeeds
{
	public ItemGysahlSeeds(int itemId, int blockType, int soilBlockID)
	{
		super(itemId, blockType, soilBlockID);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setUnlocalizedName("item.gysahlSeeds");
		//this.setCreativeTab(chocoboCreativeItems);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void func_94581_a(IconRegister iconRegister)
	{
		this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_GY_SEEDS);
	}
}
