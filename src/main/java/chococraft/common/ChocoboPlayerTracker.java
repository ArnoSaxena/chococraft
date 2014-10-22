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

import chococraft.common.network.PacketRegistry;
import chococraft.common.network.clientSide.ChocoboLocalSetupUpdate;
import chococraft.common.network.clientSide.ChocoboSetupUpdate;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayerMP;


public class ChocoboPlayerTracker
{

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
	{
		this.sendSetupUpdate((EntityPlayerMP)event.player);
	}

	@SubscribeEvent
	public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event)
	{
		this.sendLocalSetupUpdate((EntityPlayerMP)event.player);
	}

	
	protected void sendSetupUpdate(EntityPlayerMP player)
	{
		if (!player.worldObj.isRemote)
		{
			ChocoboSetupUpdate packet = new ChocoboSetupUpdate();
			PacketRegistry.INSTANCE.sendTo(packet, player);
		}		
	}
	
	protected void sendLocalSetupUpdate(EntityPlayerMP player)
	{
		if (!player.worldObj.isRemote)
		{
			ChocoboLocalSetupUpdate packet = new ChocoboLocalSetupUpdate();
			PacketRegistry.INSTANCE.sendTo(packet, player);
		}		
	}
}