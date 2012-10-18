package chococraft.common.network;

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
		//if (packet.channel.equals(Constants.PCHAN_HEALTHUPDATE))
		if (packet instanceof PacketChocoboHealth)
		{
			PacketChocoboHealth.handleUpdate(packet, player);
		}
		//else if (packet.channel.equals(Constants.PCHAN_MOUNTUPDATE))
		else if (packet instanceof PacketChocoboMount)
		{
			PacketChocoboMount.handleUpdate(packet, player);
		}
		//else if (packet.channel.equals(Constants.PCHAN_TAMEDUPDATE))
		else if (packet instanceof PacketChocoboTamed)
		{
			PacketChocoboTamed.handleUpdate(packet, player);
		}
		//else if (packet.channel.equals(Constants.PCHAN_ATTRIBUTEUPDATE))
		else if (packet instanceof PacketChocoboAttribute)
		{
			PacketChocoboAttribute.handleUpdate(packet, player);
		}
		//else if (packet.channel.equals(Constants.PCHAN_RIDERJUMPUPDATE))
		else if (packet instanceof PacketChocoboRiderJump)
		{
			PacketChocoboRiderJump.handleUpdate(packet, player);
		}
	}
}