// <copyright file="PacketChocoboMount.java">
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
// <summary>Network Packet wrapper for sending a mounting signal of a player to a Chocobo entity</summary>

package chococraft.common.network.serverSide;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.Player;

import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChocoboRideable;
import net.minecraft.src.EntityPlayer;

public class PacketChocoboMount extends PacketChocoboServer
{
	public PacketChocoboMount(EntityAnimalChocobo chocobo)
	{
		super();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			String playerName = FMLClientHandler.instance().getClient().thePlayer.username;

			outputStream.writeInt(PID_MOUNT);
			outputStream.writeInt(chocobo.entityId);
			outputStream.writeUTF(playerName);
			outputStream.writeInt(chocobo.worldObj.getWorldInfo().getDimension());
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
				int mountId = inputStream.readInt();
				String riderName = inputStream.readUTF();
				int dimension = inputStream.readInt();
				
				EntityAnimalChocobo chocobo = getChocoboByID(mountId, dimension);
				if(chocobo instanceof EntityChocoboRideable)
				{
					EntityChocoboRideable chocoboRideable = (EntityChocoboRideable)chocobo;
					if(riderName.isEmpty())
					{
						chocoboRideable.riddenByEntity = null;
						chocoboRideable.isJumping = false;
						chocoboRideable.setStepHeight(false);
						chocoboRideable.setLandMovementFactor(false);
					}
					else
					{
						EntityPlayer riderEntity = getPlayer(riderName, dimension);
						riderEntity.mountEntity(chocoboRideable);
						chocoboRideable.setStepHeight(true);
						chocoboRideable.setLandMovementFactor(true);
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
