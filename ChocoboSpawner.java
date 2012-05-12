// <copyright file="ChocoboSpawner.java">
// Copyright (c) 2012 All Right Reserved, http://chococraft.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <email>al-s@gmx.de</email>
// <date>2012-05-09</date>
// <summary>handles spawning of wild Chocobos in the given mc world.</summary>

package net.minecraft.src;

import java.util.*;

@SuppressWarnings("all")
public final class ChocoboSpawner
{
	private static HashMap<ChunkCoordIntPair, Boolean> eligibleChunksForSpawning = new HashMap<ChunkCoordIntPair, Boolean>();
	
	private EntityChocobo lastDebugger;
	private String name = "";

	public ChocoboSpawner()
	{
	}
	
	public void doChocoboSpawningTest(World world)
	{
		EntityPlayer player = (EntityPlayer)world.playerEntities.get(0);
		
		// player position
		int pPosX = MathHelper.floor_double(player.posX);
		int pPosY = MathHelper.floor_double(player.posY);
		int pPosZ = MathHelper.floor_double(player.posZ);
		int pRotYaw = MathHelper.floor_double(player.rotationYaw);
		
		// black chocos for delivering debugging messages				
		// build name for debugging
		String name = mod_chocobo.getRandomName();
		
		// create the entity to be spawn
		EntityNetherChocobo newChocobo = new EntityNetherChocobo(world);
		//newChocobo.setColor(chocoColor);
		int chocoColor;
		int chocoColorRand = world.rand.nextInt(100);
		//name = name + " " + chocoColorRand;
		if(chocoColorRand < 2)
		{
			chocoColor = EntityChocobo.COLOR_RED; // 2
		}
		else if(chocoColorRand < 4)
		{
			chocoColor = EntityChocobo.COLOR_PINK; // 2
		}
		else if(chocoColorRand < 12)
		{
			chocoColor = EntityChocobo.COLOR_PURPLE; // 8
		}
		else if(chocoColorRand < 15)
		{
			chocoColor = EntityChocobo.COLOR_GOLD; // 3
		}
		else if(chocoColorRand < 23)
		{
			chocoColor = EntityChocobo.COLOR_BLACK; // 8
		}
		else if(chocoColorRand < 31)
		{
			chocoColor = EntityChocobo.COLOR_WHITE; // 8
		}
		else if(chocoColorRand < 43)
		{
			chocoColor = EntityChocobo.COLOR_BLUE; // 12
		}
		else if(chocoColorRand < 55)
		{
			chocoColor = EntityChocobo.COLOR_GREEN; // 12
		}
		else
		{
			chocoColor = EntityChocobo.COLOR_YELLOW; // 45
		}

		int wildChoco = world.rand.nextInt(4);
		if(wildChoco == 1)
		{
			newChocobo.setTamed(false);	
		}
		else
		{
			newChocobo.setTamed(true);
			newChocobo.name = name;
		}		
		
		newChocobo.setTamed(true);
		newChocobo.name = Boolean.toString(ChocoboHelper.isWorldHell(world)) + " " + this.name;
		
		newChocobo.setLocationAndAngles(pPosX + 1, pPosY, pPosZ + 1, pRotYaw, 0.0F);
		
		// and finally spawn the entity ...
		world.spawnEntityInWorld(newChocobo);
		
		// remove the last debugger so we won't be swarmed by chocos
		if(this.lastDebugger != null)
		{
			this.lastDebugger.setDead();
		}
		this.lastDebugger = newChocobo;

		return;
	}
	
	public void doChocoboSpawning(World world, int maxChocos)
	{
		if(world.isRemote)
		{
			return;
		}
		
		int activeWildChocos = ChocoboHelper.countWildChocobos(world);
		
		// spawn first five in any case
		// only spawn if less than maxChocos are active
		// else spawn on a probability of chocoboSpawnProbability in 100
		if (activeWildChocos > mod_chocobo.sureSpawnThreshold) {
			if (activeWildChocos > mod_chocobo.maxChocobos) {
				return;
			}
			int spawnRand = world.rand.nextInt(100);
			if(spawnRand > mod_chocobo.spawnProbability)
			{
				return;
			}
		}
		
		EntityPlayer player = (EntityPlayer)world.playerEntities.get(0);
		
		// player position
		int pPosX = MathHelper.floor_double(player.posX);
		int pPosY = MathHelper.floor_double(player.posY);
		int pPosZ = MathHelper.floor_double(player.posZ);
		int pRotYaw = MathHelper.floor_double(player.rotationYaw);
		
		// fresh spawned chocos are yellow
		int chocoColor = EntityChocobo.COLOR_YELLOW;
				
		// random spawning point in an area at least 4 chunks from player but not further than 8..
		int outerSpawnRadius = 64;
		int innerSpawnRadius = 32;

		if(ChocoboHelper.isWorldHell(world))
		{
			outerSpawnRadius = 48;
			innerSpawnRadius = 32;
		}
		
		// check ten random positions for correct spawn biome
		int randDeltaX = 0;
		int randDeltaZ = 0;
		Boolean canSpawnBiome = false;
		for(int i = 0; i < 10; i++)
		{
			randDeltaX = world.rand.nextInt(outerSpawnRadius) + innerSpawnRadius;
			randDeltaZ = world.rand.nextInt(outerSpawnRadius) + innerSpawnRadius;

			//select random quadrant
			if(world.rand.nextBoolean())
			{
				randDeltaX = randDeltaX * -1;
			}
			if(world.rand.nextBoolean())
			{
				randDeltaZ = randDeltaZ * -1;
			}
			canSpawnBiome = canChocoboSpawnInBiome(world, pPosX + randDeltaX, pPosZ + randDeltaZ);
			if(ChocoboHelper.isWorldHell(world))
			{
				canSpawnBiome = true;
			}
			if(canSpawnBiome)
			{
				break;
			}
		}
		
		// random number between min and max group count;
		int numberChocoGroup;
		if(mod_chocobo.chocoboSpawnMaxGroupCount <= mod_chocobo.chocoboSpawnMinGroupCount)
		{
			numberChocoGroup = mod_chocobo.chocoboSpawnMinGroupCount;
		}
		else
		{
			numberChocoGroup = world.rand.nextInt(mod_chocobo.chocoboSpawnMaxGroupCount - mod_chocobo.chocoboSpawnMinGroupCount) + mod_chocobo.chocoboSpawnMinGroupCount;
		}
		
		int spawnedChocobos = 0;
		int maxTries = 20;
		
		for (int i = 0; i < maxTries; i++) {
			// actual spawning point of an individual Chocobo is in a 
			// random area of 6x6 clicks so groups won't start at exactly
			// the same spot
			int chocoPosX = pPosX + randDeltaX + world.rand.nextInt(6);
			int chocoPosZ = pPosZ + randDeltaZ + world.rand.nextInt(6);
			int chocoPosY = 0;
			if(ChocoboHelper.isWorldHell(world))
			{
				chocoPosY = world.getFirstUncoveredBlock(chocoPosX, chocoPosZ);
			}
			else
			{
				chocoPosY = world.getTopSolidOrLiquidBlock(chocoPosX, chocoPosZ);
			}
			this.name = this.name + " " + chocoPosY;
			int chocoRotYawn = world.rand.nextInt(360);
			
			// check if Chocobos can spawn at spawn-point...
			Boolean canSpawnLoc = canChocoboSpawnAtLocation(world, chocoPosX, chocoPosY, chocoPosZ);
			
			this.name = this.name + " " + canSpawnLoc.toString();
			
			// create the entity to be spawn if it can spawn
			if(canSpawnLoc && canSpawnBiome)
			{
				EntityChocobo newChocobo;
				if(ChocoboHelper.isWorldHell(world))
				{
					newChocobo = new EntityNetherChocobo(world);
				}
				else
				{
					newChocobo = new EntityChocobo(world);
					newChocobo.setColor(chocoColor);
				}
				newChocobo.setLocationAndAngles(chocoPosX, chocoPosY, chocoPosZ, chocoRotYawn, 0.0F);
				// and finally spawn the entity ...
				world.spawnEntityInWorld(newChocobo);
				spawnedChocobos++;
				if(spawnedChocobos >= numberChocoGroup)
				{
					break;
				}
			}
		}
		return;
	}
	
	private boolean canChocoboSpawnAtLocation(World world, int posX, int posY, int posZ)
	{
		Boolean normalCubeBelow = world.isBlockNormalCube(posX, posY - 1, posZ);
		Boolean notNormalCube = !world.isBlockNormalCube(posX, posY, posZ); 
		Boolean notLiquidCube = !world.getBlockMaterial(posX, posY, posZ).isLiquid(); 
		Boolean notNormalAbove = !world.isBlockNormalCube(posX, posY + 1, posZ);
		
		return normalCubeBelow && notNormalCube && notLiquidCube && notNormalAbove;
	}
	
	private boolean canChocoboSpawnInBiome(World world, int posX, int posZ)
	{
		BiomeGenBase chocoBgb = world.getBiomeGenForCoords(posX, posZ);
		if(null != mod_chocobo.chocoboSpawnBiomes)
		{
			for (int i = 0; i < mod_chocobo.chocoboSpawnBiomes.length; i++) {
				if (mod_chocobo.chocoboSpawnBiomes[i].equals(chocoBgb)) {
					return true;
				}
			}
		}
		else
		{
			// if there are no chocoSpawnBiomes spawn in all ...
			return true;
		}
		return false;
	}

	// old cms stuff, keep it for now ...
	protected final int entityDespawnCheck(World worldObj, EntityLiving entityliving)
	{
		if (entityliving instanceof EntityWolf && ((EntityWolf)entityliving).isTamed())
		{
			return 0;
		}

		EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(entityliving, -1D);
		if(entityplayer != null) //entityliving.canDespawn() && 
		{
			double deltaX = ((Entity) (entityplayer)).posX - entityliving.posX;
			double deltaY = ((Entity) (entityplayer)).posY - entityliving.posY;
			double deltaZ = ((Entity) (entityplayer)).posZ - entityliving.posZ;
			double qubicSumDelta = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
			if(qubicSumDelta > 16384D)
			{
				entityliving.setDead();
				return 1;
			}
			if(entityliving.entityAge > 600 && worldObj.rand.nextInt(800) == 0)
			{
				if(qubicSumDelta < 1024D)
				{
					entityliving.entityAge = 0;
				} 
				else
				{
					entityliving.setDead();
					return 1;
				}
			}
		}
		return 0;
	}

	public final int despawnMobWithMinimum(World worldObj, Class entityClass, int minimum)
	{
		int killedcount = 0;
		int mobcount = ChocoboHelper.countEntities(entityClass, worldObj);
		for(int j = 0; j < worldObj.loadedEntityList.size(); j++)
		{
			if ((mobcount - killedcount) <= minimum) 
			{
				worldObj.updateEntities();
				return killedcount;
			}
			Entity entity = (Entity)worldObj.loadedEntityList.get(j);
			if (!(entity instanceof EntityLiving))
			{
				continue;
			}
			if(entityClass == entity.getClass())
			{
				killedcount+= entityDespawnCheck(worldObj, (EntityLiving)entity);
			}
		}
		worldObj.updateEntities();
		return killedcount;
	}
}
