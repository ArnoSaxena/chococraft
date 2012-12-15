// <copyright file="PacketChocoboSetupUpdate.java">
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
// <summary>Network Packet wrapper for overriding the client setup with the server entries</summary>

package chococraft.common.network.clientSide;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import chococraft.common.ModChocoCraft;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.Player;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;

public class PacketChocoboSetupUpdate extends PacketChocoboClient
{
	public PacketChocoboSetupUpdate(EntityPlayer player)
	{
		super();	
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(PID_SETUP);
			outputStream.writeBoolean(MinecraftServer.getServer().isDedicatedServer());
			outputStream.writeBoolean(ModChocoCraft.hungerEnabled);
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
				ModChocoCraft.isRemoteClient = inputStream.readBoolean();
				ModChocoCraft.hungerEnabled = inputStream.readBoolean();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return;
			}
		}		
	}
}
