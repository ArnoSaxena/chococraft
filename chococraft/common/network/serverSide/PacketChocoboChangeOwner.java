// <copyright file="PacketChocoboChangeOwner.java">
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
// <summary>Network Packet wrapper for distributing a Chocobo change owner event to the clients</summary>

package chococraft.common.network.serverSide;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.Player;

import chococraft.common.entities.EntityAnimalChocobo;

public class PacketChocoboChangeOwner extends PacketChocoboServer
{
	public PacketChocoboChangeOwner(EntityAnimalChocobo chocobo)
	{
		super();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(PID_CHOWN);			
			outputStream.writeInt(chocobo.entityId);
			outputStream.writeBoolean(chocobo.isTamed());
			outputStream.writeUTF(chocobo.getOwnerName());
			outputStream.writeInt(chocobo.worldObj.provider.dimensionId);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}		

		this.packet.data = bos.toByteArray();
		this.packet.length = bos.size();
	}

	public static void handleUpdate(DataInputStream inputStream, Player player)
	{
		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			try
			{
				int entityId = inputStream.readInt();
				boolean tamed = inputStream.readBoolean();
				String ownerName = inputStream.readUTF();
				int dimension = inputStream.readInt();

				EntityAnimalChocobo chocobo = getChocoboByID(entityId, dimension);
				if(null != chocobo)
				{
					if(tamed)
					{
						chocobo.setOwner(ownerName);
					}
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
