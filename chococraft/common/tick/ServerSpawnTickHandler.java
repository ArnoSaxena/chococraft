package chococraft.common.tick;

import java.util.EnumSet;
import java.util.Iterator;

import chococraft.common.entities.spawner.ChocoboSpawner;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayerMP;

import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

public class ServerSpawnTickHandler implements IScheduledTickHandler
{
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		//DebugFileWriter.instance().writeEmptyLine();
		//DebugFileWriter.instance().writeLine("ChSTiH", "tick ...");
		
        Iterator<?> playerIter = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();
        while (playerIter.hasNext())
        {
            EntityPlayerMP player = (EntityPlayerMP)playerIter.next();
            //DebugFileWriter.instance().writeLine("ChSTiH", "spawn for player " + player.username);
            ChocoboSpawner.doChocoboSpawning(player.worldObj, player.posX, player.posY, player.posZ);
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
		return 200;
	}

}
