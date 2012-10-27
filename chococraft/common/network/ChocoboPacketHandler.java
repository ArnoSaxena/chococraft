// <copyright file="ChocoboPacketHandler.java">
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
// <summary>receiving and distributing network communication packets intended for the ChocoCraft mod.</summary>

package chococraft.common.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ChocoboPacketHandler implements IPacketHandler
{    
	public ChocoboPacketHandler()
	{
	}

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{

		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));

		try {
			int packetId = data.readInt();
			
			if(packetId == PacketChocobo.PID_HEALTH)
			{
				PacketChocoboHealth.handleUpdate(data, player);
			}
			else if(packetId == PacketChocobo.PID_MOUNT)
			{
				PacketChocoboMount.handleUpdate(data, player);
			}
			else if(packetId == PacketChocobo.PID_TAMED)
			{
				PacketChocoboTamed.handleUpdate(data, player);
			}
			else if(packetId == PacketChocobo.PID_ATTRIBUTE)
			{
				PacketChocoboAttribute.handleUpdate(data, player);
			}
			else if(packetId == PacketChocobo.PID_RIDERJUMP)
			{
				PacketChocoboRiderJump.handleUpdate(data, player);
			}
			else if(packetId == PacketChocobo.PID_DROPSADDLE)
			{
				PacketChocoboDropGear.handleUpdate(data, player);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}