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
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import chococraft.common.network.clientSide.PacketChicoboCanGrowUp;
import chococraft.common.network.clientSide.PacketChocoboHealth;
import chococraft.common.network.clientSide.PacketChocoboHunger;
import chococraft.common.network.clientSide.PacketChocoboParticles;
import chococraft.common.network.clientSide.PacketChocoboSetupUpdate;
import chococraft.common.network.clientSide.PacketChocoboTamed;
import chococraft.common.network.serverSide.PacketChocoboAttribute;
import chococraft.common.network.serverSide.PacketChocoboChangeOwner;
import chococraft.common.network.serverSide.PacketChocoboDropGear;
import chococraft.common.network.serverSide.PacketChocoboMount;
import chococraft.common.network.serverSide.PacketChocoboSetInLove;
import chococraft.common.network.serverSide.PacketChocoboSpawnItem;
import chococraft.common.network.serverSide.PacketChocoboUpdateRiderActionState;
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
			else if(packetId == PacketChocobo.PID_DROPSADDLE)
			{
				PacketChocoboDropGear.handleUpdate(data, player);
			}
			else if(packetId == PacketChocobo.PID_CHIGROWUP)
			{
				PacketChicoboCanGrowUp.handleUpdate(data, player);
			}
			else if(packetId == PacketChocobo.PID_PARTICLES)
			{
				PacketChocoboParticles.handleUpdate(data, player);
			}
			else if(packetId == PacketChocobo.PID_SETINLOVE)
			{
				PacketChocoboSetInLove.handleUpdate(data, player);
			}
			else if(packetId == PacketChocobo.PID_CHOWN)
			{
				PacketChocoboChangeOwner.handleUpdate(data, player);
			}
			else if(packetId == PacketChocobo.PID_HUNGER)
			{
				PacketChocoboHunger.handleUpdate(data, player);
			}
			else if(packetId == PacketChocobo.PID_SETUP)
			{
				PacketChocoboSetupUpdate.handleUpdate(data, player);
			}
			else if(packetId == PacketChocobo.PID_SPAWN_ITEM)
			{
				PacketChocoboSpawnItem.handleUpdate(data, player);
			}
            else if(packetId == PacketChocobo.PID_RIDER_ACT_ST)
            {
                PacketChocoboUpdateRiderActionState.handleUpdate(data, player);
            }
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}