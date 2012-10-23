package chococraft.common.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ChocoboPacketHandler implements IPacketHandler
{    
	public ChocoboPacketHandler()
	{
	}

	@Override
	public void onPacketData(NetworkManager manager, Packet250CustomPayload packet, Player player)
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
				PacketChocoboDropSaddleAndBags.handleUpdate(data, player);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}