package chococraft.common.gui;

import chococraft.common.ModChocoCraft;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;

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
