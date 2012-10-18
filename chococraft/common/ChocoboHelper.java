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

package chococraft.common;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.Player;

import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChocobo;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

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
	
	public static boolean isFullChocoboDisguise(EntityPlayer entityPlayer)
	{
		return true;
		
//		ItemStack armorInventory[] = entityPlayer.inventory.armorInventory;
//        for (int i = 0; i < armorInventory.length; i++)
//        {
//            if (armorInventory[i] == null || !(armorInventory[i].getItem() instanceof ItemChocoDisguise))
//            {
//            	return false;
//            }
//        }		
//		return true;
	}
	
//    public static void checkColorAchievements(int i)
//    {
//    	if(!FMLClientHandler.instance().getClient.isSingleplayer())
//    	{
//    		return;
//    	}    	
//        EntityPlayer player = (EntityPlayer)ModChocoCraft.mc.theWorld.playerEntities.get(0);
//        if (player != null)
//        {
//            player.addStat(ModChocoCraft.breedChoco, 1);
//            switch (i)
//            {
//                case EntityAnimalChoco.COLOR_GREEN:
//                    player.addStat(ModChocoCraft.greenChoco, 1);
//                    break;
//
//                case EntityAnimalChoco.COLOR_BLUE:
//                    player.addStat(mod_chocobo.blueChoco, 1);
//                    break;
//
//                case EntityAnimalChoco.COLOR_WHITE:
//                    player.addStat(mod_chocobo.whiteChoco, 1);
//                    break;
//
//                case EntityAnimalChoco.COLOR_BLACK:
//                    player.addStat(mod_chocobo.blackChoco, 1);
//                    break;
//
//                case EntityAnimalChoco.COLOR_GOLD:
//                    player.addStat(mod_chocobo.goldChoco, 1);
//                    break;
//
//                case EntityAnimalChoco.COLOR_PINK:
//                    player.addStat(mod_chocobo.pinkChoco, 1);
//                    break;
//
//                case EntityAnimalChoco.COLOR_RED:
//                    player.addStat(mod_chocobo.redChoco, 1);
//                    break;
//            }
//        }
//    }

	// "bubble", "suspended", "depthsuspend", "townaura", "crit", "magicCrit",
	// "smoke", "mobSpell", "spell", "instantSpell", "note", "portal", 
	// "enchantmenttable", "explode", "flame", "lava", "footstep", "splash",
	// "largesmoke", "cloud", "reddust", "snowballpoof", "dripWater", "dripLava",
	// "snowshovel", "slime", "heart"
    public static void showParticleAroundEntityFx(String particleName, Entity entity)
    {
    	if(Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
    	{
    		Random rand = new Random();

    		double partPosX = (entity.posX + (double)(rand.nextFloat() * entity.width * 2.0F)) - (double)entity.width;
    		double partPosY = entity.posY + 0.5D + (double)(rand.nextFloat() * entity.height);
    		double partPosZ = (entity.posZ + (double)(rand.nextFloat() * entity.width * 2.0F)) - (double)entity.width;
    		double partVelX = rand.nextGaussian() * 0.02D;
    		double partVelY = rand.nextGaussian() * 0.02D;
    		double partVelZ = rand.nextGaussian() * 0.02D;
    		entity.worldObj.spawnParticle(particleName, partPosX, partPosY, partPosZ, partVelX, partVelY, partVelZ);
    	}
    }
    
    public static void showParticleAroundEntityFxDebugger(String particleName, Entity entity)
    {
    	showParticleAroundEntityFx(particleName, entity);
    }
    
    public static boolean isHostile(Entity entity)
    {
    	if(entity instanceof IMob)
    	{
    		return true;
    	}
    	return false;
    }
    
    public static boolean isBlockIce(double posX, double posY, double posZ)
    {
        int blockPosX = MathHelper.floor_double(posX);
        int blockPosY = MathHelper.floor_double(posY);
        int blockPosZ = MathHelper.floor_double(posZ);
        return ModChocoCraft.mcc.theWorld.getBlockId(blockPosX, blockPosY, blockPosZ) == Block.ice.blockID;
    }
    
	public static boolean canChocoboSpawnAtLocation(World world, EntityAnimalChocobo chocobo, int posX, int posY, int posZ)
	{
		Boolean normalCubeBelow = world.isBlockNormalCube(posX, posY - 1, posZ);
		Boolean thisNotNormalCube = !world.isBlockNormalCube(posX, posY, posZ); 
		Boolean thisNotLiquidCube = !world.getBlockMaterial(posX, posY, posZ).isLiquid(); 
		Boolean notNormalAbove = !world.isBlockNormalCube(posX, posY + 1, posZ);
        Boolean isPosPathWeight = chocobo.getBlockPathWeight(posX, posY, posZ) >= 0.0F;
		
		return normalCubeBelow && thisNotNormalCube && thisNotLiquidCube && notNormalAbove && isPosPathWeight;
	}
	
	public static EntityAnimalChocobo getChocoboByID(int entityId, Player player)
	{
		Entity targetEntity = null;

		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.SERVER)
		{
			if(player instanceof EntityPlayer)
			{
				EntityPlayerMP playerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(((EntityPlayer)player).username);
				for (Object object : playerMP.worldObj.loadedEntityList )
				{
					if(object instanceof Entity)
					{
						if(((Entity)object).entityId == entityId)
						{
							targetEntity = ((Entity)object);
						}
					}
				}
			}
		}
    	else if (side == Side.CLIENT)
		{
			targetEntity = (Entity)FMLClientHandler.instance().getClient().theWorld.getEntityByID(entityId);
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
	
	public static EntityPlayer getPlayer(int searchedPlayerId, String searchedPlayerName, Player searchingPlayer)
	{
		Entity targetEntity = null;

		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.SERVER)
		{
			if(searchingPlayer instanceof EntityPlayer)
			{
				EntityPlayerMP searchingPlayerMP = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(((EntityPlayer)searchingPlayer).username);
				for (Object object : searchingPlayerMP.worldObj.loadedEntityList )
				{
					if(object instanceof Entity)
					{
						if(((Entity)object).entityId == searchedPlayerId)
						{
							targetEntity = ((Entity)object);
						}
					}
				}
			}
		}
    	else if (side == Side.CLIENT)
		{
    		targetEntity =  FMLClientHandler.instance().getClient().theWorld.getPlayerEntityByName(searchedPlayerName);
		}
    	
    	
    	if(null != targetEntity && targetEntity instanceof EntityPlayer)
    	{
    		return (EntityPlayer)targetEntity;
    	}
    	else
    	{
    		return null;
    	}
	}
}
