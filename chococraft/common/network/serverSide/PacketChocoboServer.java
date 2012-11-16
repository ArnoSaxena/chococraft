package chococraft.common.network.serverSide;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.WorldServer;
import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.network.PacketChocobo;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;

public abstract class PacketChocoboServer extends PacketChocobo
{
	protected static EntityAnimalChocobo getChocoboByID(int entityId, int dimension)
	{
		Entity targetEntity = null;
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.SERVER)
		{
			WorldServer worldserver = MinecraftServer.getServer().worldServerForDimension(dimension);
			targetEntity = worldserver.getEntityByID(entityId);
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
	
	protected static EntityPlayer getPlayer(String name, int dimension)
	{
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.SERVER)
		{
			WorldServer worldserver = MinecraftServer.getServer().worldServerForDimension(dimension);
			return worldserver.getPlayerEntityByName(name);
		}
		return null;
	}
}
