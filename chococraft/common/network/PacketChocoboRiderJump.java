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
import chococraft.common.entities.EntityChocobo;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Packet250CustomPayload;

public class PacketChocoboRiderJump extends Packet250CustomPayload
{
	public PacketChocoboRiderJump(EntityPlayer riderEntity, EntityChocobo chocobo)
	{
		//this.channel = Constants.PCHAN_RIDERJUMPUPDATE;
		this.channel = Constants.PCHAN_CHOCOBO;
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(chocobo.entityId);
			outputStream.writeUTF(riderEntity.username);
			outputStream.writeInt(riderEntity.entityId);
			outputStream.writeBoolean(riderEntity.isJumping);
			outputStream.writeBoolean(riderEntity.isSneaking());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		this.data = bos.toByteArray();
		this.length = bos.size();
	}
	
	static public void handleUpdate(Packet250CustomPayload packet, Player player)
	{
		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			try
			{
				int chocoboEntityId = inputStream.readInt();
				String riderName = inputStream.readUTF();
				int riderEntityId = inputStream.readInt();
				boolean riderJumping = inputStream.readBoolean();
				boolean riderSneaking = inputStream.readBoolean();
				
				EntityAnimalChocobo chocobo = ChocoboHelper.getChocoboByID(chocoboEntityId, player);
				EntityPlayer riderEntity = null;
				if(!riderName.isEmpty())
				{
					riderEntity = ChocoboHelper.getPlayer(riderEntityId, riderName, player);
				}
								
				if(riderEntity != null && chocobo != null && chocobo.riddenByEntity != null
						&& chocobo.riddenByEntity.equals(riderEntity))
				{
					riderEntity.isJumping = riderJumping;
					riderEntity.setSneaking(riderSneaking);
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
