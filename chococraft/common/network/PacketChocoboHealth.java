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

public class PacketChocoboHealth extends Packet250CustomPayload
{
	public PacketChocoboHealth(EntityAnimalChocobo chocobo)
	{
		//this.channel = Constants.PCHAN_HEALTHUPDATE;
		this.channel = Constants.PCHAN_CHOCOBO;

		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(chocobo.entityId);
			outputStream.writeInt(chocobo.getHealth());
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
			int entityId;
			int health;
			int dimension;

			try
			{
				entityId = inputStream.readInt();
				health = inputStream.readInt();
				dimension = inputStream.readInt();
				EntityAnimalChocobo chocobo = ChocoboHelper.getChocoboByID(entityId, dimension);
				if(null != chocobo)
				{
					chocobo.setEntityHealth(health);
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
