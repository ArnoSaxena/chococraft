// <copyright file="ChocoboGuiHandler.java">
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
// <date>2012-10-25</date>
// <summary>providing the server and client gui elements</summary>

package chococraft.common.gui;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import chococraft.common.bags.ChocoBagContainer;
import chococraft.common.entities.EntityChocobo;
import cpw.mods.fml.common.network.IGuiHandler;

public class ChocoboGuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int chocoboId, int saddleOrPack, int dummy)
	{
		EntityChocobo chocobo = this.getChocoboById(world, chocoboId, world.provider.dimensionId);
		if(null != chocobo)
		{
			return new ChocoBagContainer(player.inventory, chocobo.getChocoBagInventory());
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int chocoboId, int saddleOrPack, int dummy)
	{
		EntityChocobo chocobo = this.getChocoboById(world, chocoboId, world.provider.dimensionId);
		if(null != chocobo)
		{
			return new GuiChocoboBag(player.inventory, chocobo.getChocoBagInventory());
		}
		return null;
	}


	private EntityChocobo getChocoboById(World world, int chocoboId, int dimension)
	{
		Entity entity = world.getEntityByID(chocoboId);
		if(null != entity && entity instanceof EntityChocobo)
		{
			return (EntityChocobo)entity;
		}
		else
		{
			return null;
		}
	}	
}
