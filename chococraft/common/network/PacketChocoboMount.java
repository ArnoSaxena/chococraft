package chococraft.common.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.Player;

import chococraft.common.ChocoboHelper;
import chococraft.common.Constants;
import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChocoboRideable;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Packet250CustomPayload;

public class PacketChocoboMount extends Packet250CustomPayload
{
	public PacketChocoboMount(EntityAnimalChocobo chocobo)
	{
		this.channel = Constants.PCHAN_CHOCOBO;

		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			String playerName = "";
			playerName = FMLClientHandler.instance().getClient().thePlayer.username;

			outputStream.writeInt(chocobo.entityId);
			outputStream.writeUTF(playerName);
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
		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			try
			{
				int mountId = inputStream.readInt();
				String riderName = inputStream.readUTF();
				int dimension = inputStream.readInt();

				EntityAnimalChocobo chocobo = ChocoboHelper.getChocoboByID(mountId, dimension);
				if(chocobo instanceof EntityChocoboRideable)
				{
					EntityChocoboRideable chocoboRideable = (EntityChocoboRideable)chocobo;
					if(riderName.isEmpty())
					{
						chocoboRideable.riddenByEntity = null;
						chocoboRideable.isJumping = false;
						chocoboRideable.setStepHeight(false);
						chocoboRideable.setLandMovementFactor(false);
					}
					else
					{
						EntityPlayer riderEntity = ChocoboHelper.getPlayer(riderName, dimension);
						riderEntity.mountEntity(chocoboRideable);
						chocoboRideable.setStepHeight(true);
						chocoboRideable.setLandMovementFactor(true);
					}
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
