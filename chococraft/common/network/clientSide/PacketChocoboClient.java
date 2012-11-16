package chococraft.common.network.clientSide;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.network.PacketChocobo;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;

public abstract class PacketChocoboClient extends PacketChocobo
{
	protected static EntityAnimalChocobo getChocoboByID(int entityId, int dimension)
	{
		Entity targetEntity = null;

		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.CLIENT)
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
	
	protected static EntityPlayer getPlayer(String name, int dimension)
	{
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.CLIENT)
		{
    		return  FMLClientHandler.instance().getClient().theWorld.getPlayerEntityByName(name);
		}
		return null;
	}
}
