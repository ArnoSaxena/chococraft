// <copyright file="PacketChocoboAttribute.java">
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
// <summary>Network Packet wrapper for sending Chocobo Entity attribute information</summary>

package chococraft.common.network.serverSide;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.Player;

import chococraft.common.entities.EntityAnimalChocobo;

public class PacketChocoboAttribute extends PacketChocoboServer
{
	public PacketChocoboAttribute(EntityAnimalChocobo chocobo)
	{
		super();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(PID_ATTRIBUTE);
			outputStream.writeInt(chocobo.entityId);
			outputStream.writeUTF(chocobo.getName());
			outputStream.writeBoolean(chocobo.isHidename());
			outputStream.writeBoolean(chocobo.isFollowing());
			outputStream.writeBoolean(chocobo.isWander());
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
				int chocoboId = inputStream.readInt();
				String chocoboName = inputStream.readUTF();
				boolean hidename = inputStream.readBoolean();
				boolean following = inputStream.readBoolean();
				boolean wander = inputStream.readBoolean();
				int dimension = inputStream.readInt();
				
				EntityAnimalChocobo chocobo = getChocoboByID(chocoboId, dimension);
				if(null != chocobo)
				{
					chocobo.setHidename(hidename);
					chocobo.setFollowing(following);
					chocobo.setWander(wander);
					chocobo.setName(chocoboName);
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
