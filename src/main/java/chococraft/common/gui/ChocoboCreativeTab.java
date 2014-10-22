package chococraft.common.gui;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import chococraft.common.ModChocoCraft;


public class ChocoboCreativeTab extends CreativeTabs
{
	public ChocoboCreativeTab(String label)
	{
		super(label);
	}

	@Override
	public ItemStack getIconItemStack()
	{
	    return new ItemStack(ModChocoCraft.chocoboFeatherItem);
	}
}
