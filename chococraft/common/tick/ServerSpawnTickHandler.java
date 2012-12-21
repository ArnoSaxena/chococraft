package chococraft.common.tick;

import java.util.EnumSet;
import java.util.Iterator;

import chococraft.common.ModChocoCraft;
import chococraft.common.entities.spawner.ChocoboSpawner;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

public class ServerSpawnTickHandler implements IScheduledTickHandler
{
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
        Iterator<?> playerIter = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();
        while (playerIter.hasNext())
        {
            EntityPlayerMP player = (EntityPlayerMP)playerIter.next();
            ChocoboSpawner.doChocoboSpawning(player.worldObj, player.posX, player.posY, player.posZ);
            ModChocoCraft.spawnDbTimeDelay = player.worldObj.getTotalWorldTime();
        }
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.SERVER);
	}

	@Override
	public String getLabel()
	{
		return "chocobo.tick.spawn";
	}

	@Override
	public int nextTickSpacing()
	{
		return ModChocoCraft.spawnTimeDelay;
	}

}
