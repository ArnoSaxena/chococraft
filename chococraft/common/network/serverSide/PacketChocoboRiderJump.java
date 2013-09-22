// <copyright file="PacketChocoboRiderJump.java">
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
// <summary>Network Packet wrapper for sending the jump/sneak status of 
// a player riding a chocobo to the server</summary>

package chococraft.common.network.serverSide;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.network.Player;

import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChocobo;

public class PacketChocoboRiderJump extends PacketChocoboServer
{
	public PacketChocoboRiderJump(EntityPlayer riderEntity, EntityChocobo chocobo)
	{
		super();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(PID_RIDERJUMP);
			outputStream.writeInt(chocobo.entityId);
			outputStream.writeUTF(riderEntity.username);
			outputStream.writeBoolean(chocobo.riderActionState.jump);
			outputStream.writeBoolean(riderEntity.isSneaking());
			outputStream.writeInt(chocobo.worldObj.provider.dimensionId);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		this.packet.data = bos.toByteArray();
		this.packet.length = bos.size();
	}
	
	static public void handleUpdate(DataInputStream inputStream, Player player)
	{
		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			try
			{
				int chocoboEntityId    = inputStream.readInt();
				String riderName       = inputStream.readUTF();
				boolean riderJumping   = inputStream.readBoolean();
				boolean riderSneaking  = inputStream.readBoolean();
				int dimension          = inputStream.readInt();
				
				EntityAnimalChocobo chocobo = getChocoboByID(chocoboEntityId, dimension);
				EntityPlayer riderEntity = null;
				if(!riderName.isEmpty())
				{
					riderEntity = getPlayer(riderName, dimension);
				}
								
				if(riderEntity != null && chocobo != null && chocobo.riddenByEntity != null
						&& chocobo.riddenByEntity.equals(riderEntity))
				{
					riderEntity.setJumping(riderJumping);
					riderEntity.setSneaking(riderSneaking);
				}
				
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return;
			}
		}
	}
}
