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
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(chocobo.entityId);
			outputStream.writeBoolean(chocobo.isTamed());
			outputStream.writeUTF(chocobo.getOwnerName());
			outputStream.writeInt(chocobo.worldObj.getWorldInfo().getDimension());
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

			try
			{				
				int entityId = inputStream.readInt();
				boolean tamed = inputStream.readBoolean();
				String ownerName = inputStream.readUTF();
				int dimension = inputStream.readInt();
				
				EntityAnimalChocobo chocobo = ChocoboHelper.getChocoboByID(entityId, dimension);
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
