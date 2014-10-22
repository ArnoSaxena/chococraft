package chococraft.common.network;

import chococraft.common.entities.EntityAnimalChocobo;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

/**
 * Created by clienthax on 22/10/2014.
 */
public class PacketHelper {

	public static EntityAnimalChocobo getChocoboByID(int entityId, int dimension)
	{
		Entity targetEntity;
		if (Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
		{
			targetEntity = FMLClientHandler.instance().getClient().theWorld.getEntityByID(entityId);
		}
		else {
			targetEntity = MinecraftServer.getServer().worldServerForDimension(dimension).getEntityByID(entityId);
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

	public static EntityPlayer getPlayer(String name, int dimension)
	{
		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			WorldServer worldserver = MinecraftServer.getServer().worldServerForDimension(dimension);
			return worldserver.getPlayerEntityByName(name);
		}
		if (Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
		{
			return FMLClientHandler.instance().getClient().theWorld.getPlayerEntityByName(name);
		}

		return null;
	}
}
