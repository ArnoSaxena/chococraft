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

public class PacketChocoboTamed extends Packet250CustomPayload
{
	public PacketChocoboTamed(EntityAnimalChocobo chocobo)
	{
		this.channel = Constants.PCHAN_CHOCOBO;
		//this.channel = Constants.PCHAN_TAMEDUPDATE;
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(chocobo.entityId);
			outputStream.writeBoolean(chocobo.isTamed());
			outputStream.writeUTF(chocobo.getOwnerName());
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
		if (Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
		{
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			int entityId;
			boolean tamed;
			String ownerName;

			try
			{				
				entityId = inputStream.readInt();
				tamed = inputStream.readBoolean();
				ownerName = inputStream.readUTF();
				
				EntityAnimalChocobo chocobo = ChocoboHelper.getChocoboByID(entityId, player);
				if(null != chocobo)
				{
					chocobo.setTamed(tamed);
					chocobo.setOwner(ownerName);
					chocobo.resetEntityTexture();
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
