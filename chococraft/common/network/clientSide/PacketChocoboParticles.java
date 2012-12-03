package chococraft.common.network.clientSide;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.Player;

import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.helper.ChocoboParticleHelper;

public class PacketChocoboParticles extends PacketChocoboClient
{
	public PacketChocoboParticles(EntityAnimalChocobo chocobo, String particleName)
	{
		super();	
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(PID_PARTICLES);
			outputStream.writeInt(chocobo.entityId);
			outputStream.writeUTF(particleName);
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
				String particleName = inputStream.readUTF();
				int dimension = inputStream.readInt();
				EntityAnimalChocobo chocobo = getChocoboByID(entityId, dimension);
				
				if(null != chocobo)
				{
					ChocoboParticleHelper.showParticleAroundEntityFx(particleName, chocobo);
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
