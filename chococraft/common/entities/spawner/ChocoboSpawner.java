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

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import chococraft.common.ChocoboHelper;
import chococraft.common.ModChocoCraft;
import chococraft.common.entities.EntityAnimalChocobo.chocoboColor;
import chococraft.common.entities.EntityChocobo;
import chococraft.common.entities.FactoryEntityChocobo;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Chunk;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public final class ChocoboSpawner
{
	public static void doChocoboSpawning(World world, double posX, double posY, double posZ)
	{
		//DebugFileWriter.instance().writeLine("ChSpaw", "doing the spawning");

		if(Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			int maxTries = 20;

			// only spawn if random check is positive
			int d100 = world.rand.nextInt(100);
			if(d100 > ModChocoCraft.spawnProbability)
			{
				//DebugFileWriter.instance().writeLine("ChSpaw", "world.rand.nextInt(100) (" + d100 + ") > ModChocoCraft.spawnProbability(" + ModChocoCraft.spawnProbability + ")");
				return;
			}				

			// random spawning point in an area at least 4 chunks from player but not further than 8..
			int outerSpawnRadius = 64;
			int innerSpawnRadius = 32;
			
			if(ChocoboHelper.isWorldHell(world))
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

				//DebugFileWriter.instance().writeLine("ChSpaw", "canChocoboSpawnInBiome(world, "+ (posX + randDeltaX) +", "+ (posZ + randDeltaZ) +") is " + Boolean.toString(canSpawnHere));

				if(ChocoboHelper.isWorldHell(world))
				{
					canSpawnHere = true;
				}				

				// check if another player is in the spawn area
				boolean otherPlayerNear = isOtherPlayerNear(world, posX + randDeltaX, posZ + randDeltaZ, innerSpawnRadius/2);
				if(canSpawnHere && otherPlayerNear)
				{
					canSpawnHere = false;
				}

				//DebugFileWriter.instance().writeLine("ChSpaw", "isOtherPlayerNear(world, "+ (posX + randDeltaX) +", "+ (posZ + randDeltaZ) +", " + (innerSpawnRadius/2) + ") is " + Boolean.toString(otherPlayerNear));

				// if spawn position is found break
				if(canSpawnHere)
				{
					//DebugFileWriter.instance().writeLine("ChSpaw", "found spawn location");
					break;
				}
			}
			
			Chunk tmpChunk = world.getChunkFromBlockCoords(MathHelper.floor_double(posX + randDeltaX), MathHelper.floor_double(posZ + randDeltaZ));
			int activeWildChocobosInChunk = ChocoboHelper.countWildChocobosInChunk(world, tmpChunk);
			if (activeWildChocobosInChunk > ModChocoCraft.spawnTotalMax)
			{
				//DebugFileWriter.instance().writeLine("ChSpaw", "activeWildChocobosInChunk(" + activeWildChocobosInChunk + ") > ModChocoCraft.spawnTotalMax(" + ModChocoCraft.spawnTotalMax + ")");
				return;
			}

			int spawnedChocobos = 0;

			int groupSizeDelta = ModChocoCraft.spawnGroupMax - ModChocoCraft.spawnGroupMin;
			if(groupSizeDelta < 0)
			{
				groupSizeDelta = 0;
			}
			int randomGroupSize = ModChocoCraft.spawnGroupMin + world.rand.nextInt(groupSizeDelta);

			//DebugFileWriter.instance().writeLine("ChSpaw", "randomGroupSize: " + randomGroupSize);			

			for (int i = 0; i < maxTries; i++)
			{
				// actual spawning point of an individual Chocobo is in a 
				// random area of 6x6 clicks so groups won't start at exactly
				// the same spot
				int chocoPosX = (int)posX + randDeltaX + world.rand.nextInt(6);
				int chocoPosZ = (int)posZ + randDeltaZ + world.rand.nextInt(6);

				int chocoPosY = 0;
				if(ChocoboHelper.isWorldHell(world))
				{
					chocoPosY = world.getFirstUncoveredBlock(chocoPosX, chocoPosZ);
				}
				else
				{
					chocoPosY = world.getTopSolidOrLiquidBlock(chocoPosX, chocoPosZ);
				}

				int chocoRotYawn = world.rand.nextInt(360);

				// check if Chocobos can spawn at spawn-point...
				Boolean canSpawnLoc = canChocoboSpawnAtLocation(world, chocoPosX, chocoPosY, chocoPosZ);

				//DebugFileWriter.instance().writeLine("ChSpaw", "canChocoboSpawnAtLocation(world, " + chocoPosX + ", " + chocoPosY + ", " + chocoPosZ + ") is " + Boolean.toString(canSpawnLoc));

				// create the entity to be spawn if it can spawn
				if(canSpawnLoc && canSpawnHere)
				{
					chocoboColor color = ChocoboHelper.isWorldHell(world) ? chocoboColor.PURPLE : chocoboColor.YELLOW;
					EntityChocobo newChocobo = FactoryEntityChocobo.createNewChocobo(world, color);
					newChocobo.setLocationAndAngles(chocoPosX, chocoPosY, chocoPosZ, chocoRotYawn, 0.0F);

					// and finally spawn the entity ...
					//DebugFileWriter.instance().writeLine("ChSpaw", "spawn a Chocobo!!!");
					world.spawnEntityInWorld(newChocobo);
					spawnedChocobos++;
					if(spawnedChocobos >= randomGroupSize)
					{
						break;
					}
				}
			}
		}
		return;
	}

	private static boolean isOtherPlayerNear(World world, double posX, double posZ, int distance)
	{
		int tmpPosX = MathHelper.floor_double(posX);
		int tmpPosZ = MathHelper.floor_double(posZ);

		int tmpPosY = 0;
		if(ChocoboHelper.isWorldHell(world))
		{
			tmpPosY = world.getFirstUncoveredBlock(tmpPosX, tmpPosZ);
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
		Boolean normalCubeBelow = world.isBlockNormalCube(posX, posY - 1, posZ);
		Boolean notNormalCube = !world.isBlockNormalCube(posX, posY, posZ); 
		Boolean notLiquidCube = !world.getBlockMaterial(posX, posY, posZ).isLiquid(); 
		Boolean notNormalAbove = !world.isBlockNormalCube(posX, posY + 1, posZ);

		return normalCubeBelow && notNormalCube && notLiquidCube && notNormalAbove;
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
}
