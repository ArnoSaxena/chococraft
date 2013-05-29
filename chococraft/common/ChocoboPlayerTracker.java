// <copyright file="ChocoboPlayerTracker.java">
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
// <date>2012-12-16</date>
// <summary>Player tracker for login and logoff events</summary>

package chococraft.common;

import net.minecraft.entity.player.EntityPlayer;
import chococraft.common.network.clientSide.PacketChocoboLocalSetupUpdate;
import chococraft.common.network.clientSide.PacketChocoboSetupUpdate;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;


public class ChocoboPlayerTracker implements IPlayerTracker
{

	@Override
	public void onPlayerLogin(EntityPlayer player)
	{
		this.sendSetupUpdate(player);
	}

	@Override
	public void onPlayerLogout(EntityPlayer player)
	{
		this.sendLocalSetupUpdate(player);
	}


	@Override
	public void onPlayerChangedDimension(EntityPlayer player){}

	@Override
	public void onPlayerRespawn(EntityPlayer player){}
	
	protected void sendSetupUpdate(EntityPlayer player)
	{
		if (!player.worldObj.isRemote)
		{
			PacketChocoboSetupUpdate packet = new PacketChocoboSetupUpdate(player);
			PacketDispatcher.sendPacketToPlayer(packet.getPacket(), (Player)player);
		}		
	}
	
	protected void sendLocalSetupUpdate(EntityPlayer player)
	{
		if (!player.worldObj.isRemote)
		{
			PacketChocoboLocalSetupUpdate packet = new PacketChocoboLocalSetupUpdate(player);
			PacketDispatcher.sendPacketToPlayer(packet.getPacket(), (Player)player);
		}		
	}
}