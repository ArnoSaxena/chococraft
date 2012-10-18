package chococraft.common.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.Player;

import chococraft.common.ChocoboHelper;
import chococraft.common.Constants;
import chococraft.common.entities.EntityAnimalChocobo;
import net.minecraft.src.Packet250CustomPayload;

public class PacketChocoboAttribute extends Packet250CustomPayload
{
	public PacketChocoboAttribute(EntityAnimalChocobo chocobo)
	{
		//this.channel = Constants.PCHAN_ATTRIBUTEUPDATE;
		this.channel = Constants.PCHAN_CHOCOBO;

		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(chocobo.entityId);
			outputStream.writeUTF(chocobo.getName());
			outputStream.writeBoolean(chocobo.isHidename());
			outputStream.writeBoolean(chocobo.isFollowing());
			outputStream.writeBoolean(chocobo.isWander());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}		

		this.data = bos.toByteArray();
		this.length = bos.size();
	}

	public static void handleUpdate(Packet250CustomPayload packet, Player player)
	{
		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			try
			{
				int chocoboId = inputStream.readInt();
				String chocoboName = inputStream.readUTF();
				boolean hidename = inputStream.readBoolean();
				boolean following = inputStream.readBoolean();
				boolean wander = inputStream.readBoolean();
				EntityAnimalChocobo chocobo = ChocoboHelper.getChocoboByID(chocoboId, player);
				if(null != chocobo)
				{
					chocobo.setName(chocoboName);
					chocobo.setHidename(hidename);
					chocobo.setFollowing(following);
					chocobo.setWander(wander);
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
