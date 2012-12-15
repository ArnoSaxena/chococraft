// <copyright file="PacketChocoboParticles.java">
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
// <summary>Network Packet wrapper for distributing a Particle spawn event to the clients</summary>

package chococraft.common.network.clientSide;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.Player;

import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.helper.ChocoboParticleHelper;

public class PacketChocoboParticles extends PacketChocoboClient
{
	public PacketChocoboParticles(EntityAnimalChocobo chocobo, String particleName, int amount)
	{
		super();	
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(PID_PARTICLES);
			outputStream.writeInt(chocobo.entityId);
			outputStream.writeUTF(particleName);
			outputStream.writeInt(amount);
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
		if (Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
		{
			try
			{
				int entityId = inputStream.readInt();
				String particleName = inputStream.readUTF();
				int amount = inputStream.readInt();
				int dimension = inputStream.readInt();
				EntityAnimalChocobo chocobo = getChocoboByID(entityId, dimension);
				
				if(null != chocobo)
				{
					ChocoboParticleHelper.showParticleAroundEntityFx(particleName, chocobo, amount);
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
