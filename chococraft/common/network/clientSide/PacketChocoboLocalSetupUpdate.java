// <copyright file="PacketChocoboLocalSetupUpdate.java">
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
// <summary>Network Packet wrapper for sending a client the signal to revert setting to the local config file</summary>

package chococraft.common.network.clientSide;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import chococraft.common.ChocoboConfig;
import chococraft.common.ModChocoCraft;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.network.Player;

public class PacketChocoboLocalSetupUpdate extends PacketChocoboClient
{
	public PacketChocoboLocalSetupUpdate(EntityPlayer player)
	{
		super();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(PID_SETUP);
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
			ModChocoCraft.isRemoteClient = false;
			ChocoboConfig.readConfigFilePreInit();
		}		
	}
}
