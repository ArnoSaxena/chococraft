// <copyright file="PacketChocoboSetInLove.java">
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
// <summary>Network Packet wrapper for sending the setInLove status of 
// a chocobo after an interaction from a player to the server</summary>

package chococraft.common.network.serverSide;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import chococraft.common.entities.EntityAnimalChocobo;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.Player;

public class PacketChocoboSetInLove extends PacketChocoboServer
{
	public PacketChocoboSetInLove(EntityAnimalChocobo chocobo)
	{
		super();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(PID_SETINLOVE);
			outputStream.writeInt(chocobo.entityId);
			outputStream.writeBoolean(chocobo.isInLove());
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
				boolean chocoboInLove  = inputStream.readBoolean();
				int dimension          = inputStream.readInt();
				
				EntityAnimalChocobo chocobo = getChocoboByID(chocoboEntityId, dimension);
				if(chocobo != null)
				{
					chocobo.setInLove(chocoboInLove);
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
