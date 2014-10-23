// <copyright file="ItemPurpleChocoboEgg.java">
// Copyright (c) 2012 All Right Reserved, http://chococraft.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <email>al-s@gmx.de</email>
// <date>2012-12-04</date>
// <summary>Item class for purple Chocobo eggs</summary>

package chococraft.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chococraft.common.config.Constants;
import chococraft.common.entities.EntityPurpleChocoboEgg;


public class ItemPurpleChocoboEgg extends ChocoboItem
{
    public ItemPurpleChocoboEgg()
    {
        super();
        this.maxStackSize = 64;
        this.setUnlocalizedName(Constants.KEY_EGG_PURPLE);
		this.setCreativeTab(CreativeTabs.tabMisc);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {    	
    	super.onItemRightClick(itemstack, world, player);
    	
        if (!player.capabilities.isCreativeMode)
        {
            itemstack.stackSize--;
        }
        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if(!world.isRemote)
		{
			world.spawnEntityInWorld(new EntityPurpleChocoboEgg(world, player));
        }
        return itemstack;
    }
}
