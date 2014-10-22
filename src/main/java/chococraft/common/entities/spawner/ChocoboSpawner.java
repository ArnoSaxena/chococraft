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

package chococraft.common.entities.spawner;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import chococraft.common.ModChocoCraft;
import chococraft.common.entities.EntityAnimalChocobo.chocoboColor;
import chococraft.common.entities.EntityChocobo;
import chococraft.common.entities.FactoryEntityChocobo;
import chococraft.common.helper.ChocoboBiomeHelper;


public final class ChocoboSpawner
{
	public static void doChocoboSpawning(World world, double posX, double posY, double posZ)
	{
		if(!world.isRemote)
		{
			int maxTries = 20;

			// only spawn if random check is positive
			int d100 = world.rand.nextInt(100);
			if(d100 > ModChocoCraft.spawnProbability)
			{
				ModChocoCraft.spawnDbStatus = "< prob";
				return;
			}				

			// random spawning point in an area at least 4 chunks from player but not further than 8..
			int outerSpawnRadius = 64;
			int innerSpawnRadius = 32;
			
			if(ChocoboBiomeHelper.isWorldHell(world))
			{
				outerSpawnRadius = 48;
				innerSpawnRadius = 32;
			}

			// check up to maxTries random positions for correct spawn biome
			int randDeltaX = 0;
			int randDeltaZ = 0;
			Boolean canSpawnHere = false;

			for(int i = 0; i < maxTries; i++)
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

				// check if Chocobos can spawn in this Biome
				canSpawnHere = canChocoboSpawnInBiome(world, posX + randDeltaX, posZ + randDeltaZ);

				if(ChocoboBiomeHelper.isWorldHell(world))
				{
					canSpawnHere = true;
				}				

				// check if another player is in the spawn area
				boolean otherPlayerNear = isOtherPlayerNear(world, posX + randDeltaX, posZ + randDeltaZ, innerSpawnRadius/2);
				if(canSpawnHere && otherPlayerNear)
				{
					canSpawnHere = false;
				}

				// stop looking, if spawn position is found
				if(canSpawnHere)
				{
					break;
				}
			}
			
			if(!canSpawnHere)
			{
				ModChocoCraft.spawnDbStatus = "not found";
				return;
			}
			
			int chunkPosX_0 = MathHelper.floor_double(posX + randDeltaX);
			int chunkPosZ_0 = MathHelper.floor_double(posZ + randDeltaZ);
			
			int wildInChunks = countWildInChunkRadius(world, chunkPosX_0, chunkPosZ_0, ModChocoCraft.spawnLimitChunkRadius);
			
			if (wildInChunks > ModChocoCraft.spawnTotalMax)
			{
				ModChocoCraft.spawnDbStatus = "to many (" + wildInChunks + ")";
				return;
			}
			
			int distanceNextWild = distanceToNextWild(world, chunkPosX_0, chunkPosZ_0);
			if (distanceNextWild < ModChocoCraft.spawnDistanceNextWild)
			{
				ModChocoCraft.spawnDbStatus = "to close (" + distanceNextWild + ")";
				return;
			}

			int spawnedChocobos = 0;

			int randomGroupSize = ModChocoCraft.spawnGroupMin;
			int groupSizeDelta = ModChocoCraft.spawnGroupMax - ModChocoCraft.spawnGroupMin;
			
			if(groupSizeDelta > 0)
			{
				randomGroupSize += world.rand.nextInt(groupSizeDelta);
			}

			for (int i = 0; i < maxTries; i++)
			{
				// actual spawning point of an individual Chocobo is in a 
				// random area of 6x6 clicks so groups won't start at exactly
				// the same spot
				int chocoPosX = (int)posX + randDeltaX + world.rand.nextInt(6);
				int chocoPosZ = (int)posZ + randDeltaZ + world.rand.nextInt(6);

				int chocoPosY = 0;
				if(ChocoboBiomeHelper.isWorldHell(world))
				{
					//chocoPosY = world.getFirstUncoveredBlock(chocoPosX, chocoPosZ);//shouldn't work anyway, this returned block id
					chocoPosY = world.getTopSolidOrLiquidBlock(chocoPosX, chocoPosZ);
				}
				else
				{
					chocoPosY = world.getTopSolidOrLiquidBlock(chocoPosX, chocoPosZ);
				}

				int chocoRotYawn = world.rand.nextInt(360);

				// check if Chocobos can spawn at spawn-point...
				Boolean canSpawnLoc = canChocoboSpawnAtLocation(world, chocoPosX, chocoPosY, chocoPosZ);

				// create the entity to be spawn if it can spawn
				if(canSpawnLoc && canSpawnHere)
				{
					chocoboColor color = ChocoboBiomeHelper.isWorldHell(world) ? chocoboColor.PURPLE : chocoboColor.YELLOW;
					EntityChocobo newChocobo = FactoryEntityChocobo.createNewChocobo(world, color);
					newChocobo.setLocationAndAngles(chocoPosX, chocoPosY, chocoPosZ, chocoRotYawn, 0.0F);

					// and finally spawn the entity ...
					world.spawnEntityInWorld(newChocobo);
					spawnedChocobos++;
					if(spawnedChocobos >= randomGroupSize)
					{
						break;
					}
				}
				else
				{
					ModChocoCraft.spawnDbStatus = "not spawn at loc";
				}
			}
			if(spawnedChocobos > 0)
			{
				ModChocoCraft.spawnDbStatus = spawnedChocobos + " spawned";
			}
		}
	}

	private static int countWildInChunkRadius(World world, int posX_0, int posZ_0, int chunkRadius)
	{
		int amount = 0;
		
		ArrayList<Chunk> targetChunks = new ArrayList<Chunk>();
		
		for(int posX = posX_0 - chunkRadius; posX <= posX_0 + chunkRadius; posX++)
		{
			for(int posZ = posZ_0 - chunkRadius; posZ <= posZ_0 + chunkRadius; posZ++)
			{
				Chunk tmpChunk = world.getChunkFromBlockCoords(posX, posZ);
				targetChunks.add(tmpChunk);
			}
		}
		
		for(int i = 0; i < world.loadedEntityList.size(); i++)
		{
			Entity entity = (Entity)world.loadedEntityList.get(i);
			if(entity instanceof EntityChocobo)
			{
				if(!((EntityChocobo)entity).isTamed())
				{
					Chunk entityChunk = world.getChunkFromBlockCoords(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posZ));
					if(targetChunks.contains(entityChunk))
					{
						amount++;
					}
				}
			}
		}
		return amount;
	}

	private static boolean isOtherPlayerNear(World world, double posX, double posZ, int distance)
	{
		int tmpPosX = MathHelper.floor_double(posX);
		int tmpPosZ = MathHelper.floor_double(posZ);

		int tmpPosY = 0;
		if(ChocoboBiomeHelper.isWorldHell(world))
		{
			//tmpPosY = world.getFirstUncoveredBlock(tmpPosX, tmpPosZ);
			tmpPosY = world.getTopSolidOrLiquidBlock(tmpPosX, tmpPosZ);
		}
		else
		{
			tmpPosY = world.getTopSolidOrLiquidBlock(tmpPosX, tmpPosZ);
		}

		if(null != world.getClosestPlayer(tmpPosX, tmpPosY, tmpPosZ, distance))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private static boolean canChocoboSpawnAtLocation(World world, int posX, int posY, int posZ)
	{
		//Boolean normalCubeBelow = isNormalCubeAround(world, posX, posY - 1, posZ);
		if(!isNormalCubesAround(world, posX, posY - 1, posZ))
		{
			return false;
		}
		
		//Boolean notNormalCube = !isNormalCubeAround(world, posX, posY, posZ);
		if(isNormalCubesAround(world, posX, posY, posZ))
		{
			return false;
		}
		
		//Boolean notLiquidCube = !world.getBlockMaterial(posX, posY, posZ).isLiquid();
		if(world.getBlock(posX, posY, posZ).getMaterial().isLiquid())
		{
			return false;
		}
		
		//Boolean notNormalAbove = !isNormalCubeAround(world, posX, posY + 1, posZ);
		if(isNormalCubesAround(world, posX, posY + 1, posZ))
		{
			return false;
		}
		
		//Boolean notNormalTwoAbove = !isNormalCubeAround(world, posX, posY + 2, posZ);
		if(isNormalCubesAround(world, posX, posY + 2, posZ))
		{
			return false;
		}

		return true;
		//return normalCubeBelow && notNormalCube && notLiquidCube && notNormalAbove && notNormalTwoAbove;
	}
	
	private static boolean isNormalCubesAround(World world, int posX, int posY, int posZ)
	{
		for(int x = posX -1; x <= posX +1; x++)
		{
			for(int z = posZ -1; z <= posZ +1; z++)
			{
				if(!world.getBlock(x, posY, z).isNormalCube())
				{
					return false;
				}
			}
		}
		return true;
	}

	private static boolean canChocoboSpawnInBiome(World world, double posX, double posZ)
	{
		int intPosX = MathHelper.floor_double(posX);
		int intPosZ = MathHelper.floor_double(posZ);		
		BiomeGenBase chocoBgb = world.getBiomeGenForCoords(intPosX, intPosZ);
		if(null != ModChocoCraft.spawnBiomes)
		{
			for (int i = 0; i < ModChocoCraft.spawnBiomes.length; i++) {
				if (ModChocoCraft.spawnBiomes[i].equals(chocoBgb)) {
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
	
	private static int distanceToNextWild(World world, double posX, double posZ)
	{
		double distance = 10000.0;

		for(int i = 0; i < world.loadedEntityList.size(); i++)
		{
			Entity entity = (Entity)world.loadedEntityList.get(i);
			if(entity instanceof EntityChocobo)
			{
				EntityChocobo chocobo = (EntityChocobo)entity;
				if(!chocobo.isTamed())
				{
					double sqDistX = (posX - chocobo.posX) * (posX - chocobo.posX);
					double sqDistZ = (posZ - chocobo.posZ) * (posZ - chocobo.posZ);					
					double tmpDistance = Math.sqrt(sqDistX + sqDistZ);
					if(tmpDistance < distance)
					{
						distance = tmpDistance;
					}
				}
			}
		}
		return MathHelper.floor_double(distance);
	}
}
