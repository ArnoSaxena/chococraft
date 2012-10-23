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

public class PacketChocoboHealth extends PacketChocobo
{	
	public PacketChocoboHealth(EntityAnimalChocobo chocobo)
	{
		super();	
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(PID_HEALTH);
			outputStream.writeInt(chocobo.entityId);
			outputStream.writeInt(chocobo.getHealth());
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
		if (Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
		{
			try
			{
				int entityId = inputStream.readInt();
				int health = inputStream.readInt();
				int dimension = inputStream.readInt();
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
