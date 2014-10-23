package chococraft.common.gui;

import chococraft.common.config.ChocoCraftItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;


public class ChocoboCreativeTab extends CreativeTabs
{
	public ChocoboCreativeTab(String label)
	{
		super(label);
	}

	@Override
	public Item getTabIconItem()
	{
	    return ChocoCraftItems.chocoboFeatherItem;
	}

}
