package chococraft.common.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.Player;

import chococraft.common.ChocoboHelper;
import chococraft.common.entities.EntityAnimalChocobo;

public class PacketChocoboAttribute extends PacketChocobo
{
	public PacketChocoboAttribute(EntityAnimalChocobo chocobo)
	{
		super();
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(PID_ATTRIBUTE);
			outputStream.writeInt(chocobo.entityId);
			outputStream.writeUTF(chocobo.getName());
			outputStream.writeBoolean(chocobo.isHidename());
			outputStream.writeBoolean(chocobo.isFollowing());
			outputStream.writeBoolean(chocobo.isWander());
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
				int chocoboId = inputStream.readInt();
				String chocoboName = inputStream.readUTF();
				boolean hidename = inputStream.readBoolean();
				boolean following = inputStream.readBoolean();
				boolean wander = inputStream.readBoolean();
				int dimension = inputStream.readInt();
				EntityAnimalChocobo chocobo = ChocoboHelper.getChocoboByID(chocoboId, dimension);
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
