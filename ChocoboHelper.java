// <copyright file="ChocoboHelper.java">
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
// <summary>global support methods for chococraft</summary>

package net.minecraft.src;

import java.util.ArrayList;

public class ChocoboHelper {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static BiomeGenBase[] getBiomeGenBaseArray()
	{
		ArrayList bgbList= new ArrayList();
		for(BiomeGenBase bgb : BiomeGenBase.biomeList)
		{
			if(null != bgb)
			{
				if(!(bgb instanceof BiomeGenHell) && !(bgb instanceof BiomeGenEnd))
				{
					bgbList.add(bgb);
				}
			}
		}		
		BiomeGenBase[] returnBgbList = new BiomeGenBase[bgbList.size()];
		int i = 0;
		for (Object tmpBgb : bgbList) {
			if(tmpBgb instanceof BiomeGenBase)
			{
				returnBgbList[i] = (BiomeGenBase)tmpBgb;
			}
			i++;
		}
		return returnBgbList;
	}
	
	public static String[] getBiomeGenBaseNameArray()
	{
		return getBiomeGenNameArray(getBiomeGenBaseArray());
	}
	
	public static String[] getBiomeGenNameArray(BiomeGenBase[] bgbArray)
	{
		if(null != bgbArray)
		{
			if (0 != bgbArray.length)
			{
				String[] bgbNames = new String[bgbArray.length];
				int idx = 0;
				for (BiomeGenBase bgb : bgbArray) {
					if(null != bgb)
					{
						bgbNames[idx] = bgb.biomeName;
						idx++;
					}
				}
				return bgbNames;
			}
			return null;
		}
		return getBiomeGenBaseNameArray();
	}
	
	public static Boolean entityIsVanilla(Entity entity)
	{
		if (entity instanceof EntityCow 
				|| entity instanceof EntitySheep 
				|| entity instanceof EntityPig 
				|| entity instanceof EntityChicken 
				|| entity instanceof EntitySquid 
				|| entity instanceof EntityWolf
				|| entity instanceof EntityOcelot)
		{
			return true;
		}
		return false;
	}
	
	public static ChunkPosition getRandomSpawningPointInChunk(World worldObj, int coordX, int coordY)
	{
		Chunk chunk = worldObj.getChunkFromChunkCoords(coordX, coordY);
		int i = coordX * 16 + worldObj.rand.nextInt(16);
		int j = worldObj.rand.nextInt(chunk != null ? Math.max(128, chunk.getTopFilledSegment()) : 128);
		int k = coordY * 16 + worldObj.rand.nextInt(16);
		return new ChunkPosition(i, j, k);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static int countEntities(Class entityClass, World world)
	{
		int amountEntities = 0;
		for(int i = 0; i < world.loadedEntityList.size(); i++)
		{
			Entity entity = (Entity)world.loadedEntityList.get(i);
			if(entityClass.isAssignableFrom(entity.getClass()))
			{
				amountEntities++;
			}
		}
		return amountEntities;
	}
	
	public static int countWildChocobos(World world)
	{
		int amountEntities = 0;
		for(int i = 0; i < world.loadedEntityList.size(); i++)
		{
			Entity entity = (Entity)world.loadedEntityList.get(i);
			if(entity instanceof EntityChocobo)
			{
				if(!((EntityChocobo)entity).isTamed())
				{
					amountEntities++;
				}
			}
		}
		return amountEntities;
	}
	
	public static boolean isBiomeHell(World world, int posX, int posZ)
	{
		BiomeGenBase chocoBgb = world.getBiomeGenForCoords(posX, posZ);
		if(chocoBgb instanceof BiomeGenHell)
		{
			return true;
		}
		return false;
	}
	
	public static boolean isWorldHell(World world)
	{
		return isBiomeHell(world, 0, 0);
	}
}
