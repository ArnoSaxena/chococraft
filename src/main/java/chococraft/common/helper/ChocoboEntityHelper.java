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

package chococraft.common.helper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import chococraft.common.entities.EntityChocobo;

public class ChocoboEntityHelper
{	
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
	
	public static int countWildChocobosInChunk(World world, Chunk chunk)
	{
		int amountEntities = 0;
		for(int i = 0; i < world.loadedEntityList.size(); i++)
		{
			Entity entity = (Entity)world.loadedEntityList.get(i);
			if(entity instanceof EntityChocobo)
			{
				if(!((EntityChocobo)entity).isTamed())
				{
					Chunk entityChunk = world.getChunkFromBlockCoords(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posZ));
					if(entityChunk.equals(chunk))
					{
						amountEntities++;
					}
				}
			}
		}
		return amountEntities;
	}
	
    public static boolean isEntityViewingUnderWater(World world, EntityLiving entity)
    {
    	Vec3 vector = ActiveRenderInfo.projectViewFromEntity(entity, 90.0F);
        ChunkPosition chunkPos = new ChunkPosition(vector);
        Block blockFront = world.getBlock(chunkPos.chunkPosX, chunkPos.chunkPosY, chunkPos.chunkPosZ);
        Block blockAbove = world.getBlock(chunkPos.chunkPosX, chunkPos.chunkPosY + 1, chunkPos.chunkPosZ);
        if(blockAbove.equals(Blocks.air) || blockFront.equals(Blocks.air))
        {
        	return false;
        }
        boolean frontWater = blockFront.getMaterial().equals(Material.water);
        boolean aboveWater = blockAbove.getMaterial().equals(Material.water);
		return frontWater && aboveWater;
    }
    
    public static boolean isSpaceAroundFree(World world, EntityLiving entity, int radX, int spcY, int radZ)
    {
    	return isSpaceAroundLocationFree(world, (int)entity.posX, (int)entity.posY, (int)entity.posZ, radX, spcY, radZ);
    }
    
    public static boolean isSpaceAroundLocationFree(World world, int startX, int startY, int startZ, int radX, int spcY, int radZ)
    {
    	int sPosX = startX - radX;
    	int sPosY = startY + 1;
    	int sPosZ = startZ - radZ;
    	int ePosX = startX + radX;
    	int ePosY = startY + spcY;
    	int ePosZ = startZ + radZ;
    	
		for(int x = sPosX; x <= ePosX; x++)
		{
			for(int y = sPosY; y <= ePosY; y++)
			{
				for(int z = sPosZ; z <= ePosZ; z++)
				{
			    	if(!world.isAirBlock(x, y, z))
			    	{
			    		return false;
			    	}
				}
			}
		}
		return true;
    }
}
