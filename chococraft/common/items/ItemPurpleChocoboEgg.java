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

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import chococraft.common.entities.EntityPurpleChocoboEgg;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemPurpleChocoboEgg extends ChocoboItem
{
    public ItemPurpleChocoboEgg(int itemIndex)
    {
        super(itemIndex);
        this.maxStackSize = 64;
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
		if(Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			world.spawnEntityInWorld(new EntityPurpleChocoboEgg(world, player));
        }
        return itemstack;
    }
}
