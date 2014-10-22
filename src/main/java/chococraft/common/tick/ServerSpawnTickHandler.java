package chococraft.common.tick;

import java.util.Iterator;

import chococraft.common.ModChocoCraft;
import chococraft.common.entities.spawner.ChocoboSpawner;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class ServerSpawnTickHandler
{
	public static int spawnTimer = 0;
	@SubscribeEvent
	public void worldTick(TickEvent.WorldTickEvent event)
	{
		if(spawnTimer++ < ModChocoCraft.spawnTimeDelay)
			return;
		spawnTimer = 0;

        Iterator<?> playerIter = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();
        while (playerIter.hasNext())
        {
            EntityPlayerMP player = (EntityPlayerMP)playerIter.next();
            ChocoboSpawner.doChocoboSpawning(player.worldObj, player.posX, player.posY, player.posZ);
            ModChocoCraft.spawnDbTimeDelay = player.worldObj.getTotalWorldTime();
        }
	}

}
