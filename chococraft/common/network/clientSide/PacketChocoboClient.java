// <copyright file="PacketChocoboClient.java">
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
// <summary>Abstract base class for Network Packet wrappers sending packets to the client side</summary>

package chococraft.common.network.clientSide;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.network.PacketChocobo;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public abstract class PacketChocoboClient extends PacketChocobo
{
	protected static EntityAnimalChocobo getChocoboByID(int entityId, int dimension)
	{
		Entity targetEntity = null;
		if (Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
		{
			targetEntity = (Entity)FMLClientHandler.instance().getClient().theWorld.getEntityByID(entityId);
		}
    	
    	if(null != targetEntity && targetEntity instanceof EntityAnimalChocobo)
    	{
    		return (EntityAnimalChocobo)targetEntity;
    	}
    	else
    	{
    		return null;
    	}
    }
	
	protected static EntityPlayer getPlayer(String name, int dimension)
	{
		if (Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
		{
    		return  FMLClientHandler.instance().getClient().theWorld.getPlayerEntityByName(name);
		}
		return null;
	}	
}
