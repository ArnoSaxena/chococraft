package chococraft.common.tick;

import chococraft.common.ModChocoCraft;
import chococraft.common.entities.spawner.ChocoboSpawner;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.List;

public class ServerSpawnTickHandler
{
	public static int spawnTimer = 0;
	@SubscribeEvent
	public void worldTick(TickEvent.WorldTickEvent event)
	{
		if(spawnTimer++ < ModChocoCraft.spawnTimeDelay)
			return;
		spawnTimer = 0;

		for(EntityPlayerMP player : (List<EntityPlayerMP>)MinecraftServer.getServer().getConfigurationManager().playerEntityList)
		{
			ChocoboSpawner.doChocoboSpawning(player.worldObj, player.posX, player.posY, player.posZ);
			ModChocoCraft.spawnDbTimeDelay = player.worldObj.getTotalWorldTime();
		}
	}

}
