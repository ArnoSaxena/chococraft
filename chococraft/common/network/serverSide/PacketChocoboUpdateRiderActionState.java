// <copyright file="PacketChocoboUpdateRiderActionState.java">
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

import net.minecraft.entity.Entity;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.network.Player;

import chococraft.common.ModChocoCraft;
import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChocoboRideable;
import chococraft.common.entities.RiderActionState;

public class PacketChocoboUpdateRiderActionState extends PacketChocoboServer
{
	public PacketChocoboUpdateRiderActionState(EntityAnimalChocobo chocobo, Entity rider)
	{
		super();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		float moveStrafe  = 0.0F;
		float moveForward = 0.0F;
		boolean jump      = false;
		boolean sneak     = false;

		RiderActionState ras = ModChocoCraft.proxy.getRiderActionState(rider);
		if(null != ras)
		{
			moveStrafe = ras.moveStrafe;
			moveForward = ras.moveForward;
			jump = ras.jump;
			sneak = ras.sneak;
		}
		
		try
		{
			outputStream.writeInt(PID_RIDER_ACT_ST);
			outputStream.writeInt(chocobo.entityId);
			outputStream.writeInt(chocobo.worldObj.provider.dimensionId);
			outputStream.writeFloat(moveStrafe);
			outputStream.writeFloat(moveForward);
			outputStream.writeBoolean(jump);
			outputStream.writeBoolean(sneak);
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
				RiderActionState ras = new RiderActionState();

				int chocoboId      = inputStream.readInt();
				int dimension      = inputStream.readInt();				
				ras.moveStrafe     = inputStream.readFloat();
				ras.moveForward    = inputStream.readFloat();
				ras.jump           = inputStream.readBoolean();
				ras.sneak          = inputStream.readBoolean();
				
				EntityAnimalChocobo chocobo = getChocoboByID(chocoboId, dimension);
								
				if(null != chocobo && chocobo instanceof EntityChocoboRideable)
				{
					((EntityChocoboRideable)chocobo).setRiderActionState(ras);
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
