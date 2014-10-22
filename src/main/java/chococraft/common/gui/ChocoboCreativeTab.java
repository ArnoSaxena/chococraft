package chococraft.common.gui;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import chococraft.common.ModChocoCraft;


public class ChocoboCreativeTab extends CreativeTabs
{
	public ChocoboCreativeTab(String label)
	{
		super(label);
	}

	@Override
	public Item getTabIconItem()
	{
	    return ModChocoCraft.chocoboFeatherItem;
	}

}
