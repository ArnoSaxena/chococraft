package chococraft.common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.debugger.DebugFileWriter;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.FMLCommonHandler;
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
		DebugFileWriter.instance().writeLine("ChPaHa", "receiving packet " + packet.channel);
		
		if (packet.channel.equals(Constants.PCHAN_HEALTHUPDATE))
		{
			this.handleChocoboHealthUpdateClient(packet, player);
		}
		else if (packet.channel.equals(Constants.PCHAN_INTERACTION))
		{
			this.handleChocoboInteraction(packet, player);
		}
		else if (packet.channel.equals(Constants.PCHAN_MOUNTUPDATE))
		{
			this.handleChocoboMountUpdate(packet, player);
		}
		else if (packet.channel.equals(Constants.PCHAN_TAMEDUPDATE))
		{
			this.handleTamedUpdate(packet, player);
		}
		else if (packet.channel.equals(Constants.PCHAN_ATTRIBUTEUPDATE))
		{
			this.handleAttributeUpdate(packet, player);
		}
		else if (packet.channel.equals(Constants.PCHAN_STEERING_UPDATE))
		{
			this.handleSteeringUpdate(packet, player);
		}
	}

	private void handleSteeringUpdate(Packet250CustomPayload packet, Player player)
	{
		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			try
			{
				int chocoboId = inputStream.readInt();
				String userName = inputStream.readUTF();
				
				boolean jump = inputStream.readBoolean();
				boolean sneak = inputStream.readBoolean();
				float moveForward = inputStream.readFloat();
				float moveStrafe = inputStream.readFloat();
				float rotationYaw = inputStream.readFloat();

				EntityPlayerMP riderEntity = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(userName);
				EntityAnimalChocobo chocobo = this.getChocoboByID(chocoboId, player);

				if(null != chocobo)
				{
					float speedFactor = (float) chocobo.landSpeedFactor;
					if(chocobo.isAirBorne)
					{
						speedFactor = (float) chocobo.airbornSpeedFactor;
					}
					
					if(chocobo.canFly)
					{
						if(jump)
						{
							chocobo.motionY = 0.1D * chocobo.flyingMovementFactor;
						}
						else if (sneak)
						{
							chocobo.motionY = -0.1D * chocobo.flyingMovementFactor;
						}
					}
					
					chocobo.rotationYaw = rotationYaw;
					riderEntity.rotationYaw = rotationYaw;
					
					chocobo.moveEntityWithHeading(moveStrafe * speedFactor, moveForward * speedFactor);
					chocobo.updateRiderPosition();
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return;
			}
		}
	}

	private void handleAttributeUpdate(Packet250CustomPayload packet, Player player)
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
				EntityAnimalChocobo chocobo = this.getChocoboByID(chocoboId, player);
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

	private void handleTamedUpdate(Packet250CustomPayload packet, Player player)
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
				EntityAnimalChocobo chocobo = this.getChocoboByID(entityId, player);
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

	private void handleChocoboMountUpdate(Packet250CustomPayload packet, Player player)
	{
		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			int mountId;
			String riderName;
			int riderId;
			try
			{
				mountId = inputStream.readInt();
				riderName = inputStream.readUTF();
				riderId = inputStream.readInt();
				
				EntityAnimalChocobo chocobo = this.getChocoboByID(mountId, player);
				
				if(riderName.isEmpty())
				{
					chocobo.riddenByEntity = null;
				}
				else
				{
					EntityPlayer riderEntity = this.getPlayer(riderId, riderName, player);
					riderEntity.mountEntity(chocobo);
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return;
			}
		}
	}

	private void handleChocoboHealthUpdateClient(Packet250CustomPayload packet, Player player)
	{
		if (Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
		{
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			int entityId;
			int health;

			try
			{
				entityId = inputStream.readInt();
				health = inputStream.readInt();
				EntityAnimalChocobo chocobo = this.getChocoboByID(entityId, player);
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

	private void handleChocoboInteraction(Packet250CustomPayload packet, Player player)
	{
		//DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
	}
	
	private EntityPlayer getPlayer(int searchedPlayerId, String searchedPlayerName, Player searchingPlayer)
	{
		Entity targetEntity = null;

		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.SERVER)
		{
			if(searchingPlayer instanceof EntityPlayer)
			{
				EntityPlayerMP searchingPlayerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(((EntityPlayer)searchingPlayer).username);
				for (Object object : searchingPlayerMP.worldObj.loadedEntityList )
				{
					if(object instanceof Entity)
					{
						if(((Entity)object).entityId == searchedPlayerId)
						{
							targetEntity = ((Entity)object);
						}
					}
				}
			}
		}
    	else if (side == Side.CLIENT)
		{
    		targetEntity =  FMLClientHandler.instance().getClient().theWorld.getPlayerEntityByName(searchedPlayerName);
		}
    	
    	
    	if(null != targetEntity && targetEntity instanceof EntityPlayer)
    	{
    		return (EntityPlayer)targetEntity;
    	}
    	else
    	{
    		return null;
    	}
	}

	private EntityAnimalChocobo getChocoboByID(int entityId, Player player)
	{
		Entity targetEntity = null;

		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.SERVER)
		{
			if(player instanceof EntityPlayer)
			{
				EntityPlayerMP playerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(((EntityPlayer)player).username);
				for (Object object : playerMP.worldObj.loadedEntityList )
				{
					if(object instanceof Entity)
					{
						if(((Entity)object).entityId == entityId)
						{
							targetEntity = ((Entity)object);
						}
					}
				}
			}
		}
    	else if (side == Side.CLIENT)
		{
			targetEntity = (Entity)FMLClientHandler.instance().getClient().theWorld.getEntityByID(entityId);
		}
    	
    	
    	if(null != targetEntity && targetEntity instanceof EntityAnimalChocobo)
    	{
    		return (EntityAnimalChocobo)targetEntity;
    	}
    	else
    	{
    		return null;
    	}
    }    
}