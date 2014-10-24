// <copyright file="ModChocoCraft.java">
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
// <date>2012-09-10</date>
// <summary>Base mod class for the ChocoCraft mod</summary>

package chococraft.common;

import chococraft.client.ClientProxyChocoCraft;
import chococraft.common.config.*;
import chococraft.common.events.ChocoboPlayerTracker;
import chococraft.common.gui.ChocoboGuiHandler;
import chococraft.common.network.PacketRegistry;
import chococraft.common.proxy.CommonProxyChocoCraft;
import chococraft.common.tick.ServerSpawnTickHandler;
import chococraft.common.worldgen.WorldGenGysahls;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

@Mod(modid= Constants.TCC_MODID, name=Constants.TCC_NAME, version=Constants.TCC_VERSION)
public class ModChocoCraft
{	
	public static Configuration mainConfiguration;

	public static boolean debugMode = false;

	// setup
	public static File configFolder;
	public static boolean chocoboWingFlutter;
	public static int genderMaleChance;
	public static boolean showChocoboNames;
	public static boolean hungerEnabled;
	public static boolean riderBuffsEnabled;
	public static boolean wildCanDespawn;
	
	// chocobo size setup
	public static float chocoboHeight;
	public static float chocoboWidth;
	
	// gysahl mutation setup
	public static int gysahlGreenMutationRate;
	public static int gysahlLoveMutationRate;
	
	// gysahl world generation setup
	public static int gysahlWorldGenRate;
	public static int gysahlSeedGrassDropWeight;
	
	// feather drop setup
	public static int featherDropChance;
	public static int featherDelayRandom;
	public static int featherDelayStatic;
	
	// pen heal setup
	public static int penHealProbability;
	public static int penHealCauldronRange;
	
	// spawn setup
	public static int spawnTimeDelay;
	public static int spawnGroupMin;
	public static int spawnGroupMax;
	public static int spawnTotalMax;
	public static int spawnProbability;
	public static int spawnLimitChunkRadius;
	public static int spawnDistanceNextWild;
	
	// procreate setup
	public static int breedingDelayMale;
	public static int breedingDelayFemale;
	public static int growupDelayStatic;
	public static int growupDelayRandom;
	
	// hunger setup
	public static int hungerDelayChocobo;
	public static int hungerDelayChicobo;
	
	// movement setup
	public static boolean saddledCanWander;
	
	// chocopedia setup
	public static boolean chocopediaInDungeons;
	
	// debug
	public static long spawnDbTimeDelay;
	public static String spawnDbStatus;
	
	public static double renderNameHeight;
	public static int livingSoundProb;
	
	public static boolean isRemoteClient = false;

	public static BiomeGenBase[] spawnBiomes =
	{
		BiomeGenBase.extremeHills,
		BiomeGenBase.extremeHillsEdge,
		BiomeGenBase.forest,
		BiomeGenBase.forestHills,
		BiomeGenBase.jungle,
		BiomeGenBase.jungleHills,
		BiomeGenBase.plains,
		BiomeGenBase.swampland,
		BiomeGenBase.taiga,
		BiomeGenBase.taigaHills,
		BiomeGenBase.iceMountains,
		BiomeGenBase.icePlains,
		BiomeGenBase.hell
	};

	@Instance(Constants.TCC_MODID)
	public static ModChocoCraft instance;

	@SidedProxy(clientSide = "chococraft.client.ClientProxyChocoCraft", serverSide = "chococraft.common.CommonProxyChocoCraft")
	public static CommonProxyChocoCraft proxy;

	@EventHandler
	public void preLoadChocoCraft(FMLPreInitializationEvent preInitEvent)
	{

		PacketRegistry.registerPackets();

		chocoboHeight = 1.9F;
		chocoboWidth = 1.3F;

		configFolder = preInitEvent.getModConfigurationDirectory();

		chocoboWingFlutter   = Constants.DEFAULT_CHOCOBO_WING_FLUTTER;
		hungerEnabled        = Constants.DEFAULT_HUNGER_ENABLED;
		riderBuffsEnabled    = Constants.DEFAULT_RIDER_BUFFS_ENABLED;
		showChocoboNames     = Constants.DEFAULT_SHOW_CHOCOBO_NAMES;
		wildCanDespawn       = Constants.DEFAULT_WILD_CAN_DESPAWN;
		
		genderMaleChance     = Constants.DEFAULT_GENDER_MALE_CHANCE;
		
		gysahlGreenMutationRate = Constants.DEFAULT_GYSAHL_GREEN_MUTATION_RATE;
		gysahlLoveMutationRate  = Constants.DEFAULT_GYSAHL_LOVE_MUTATION_RATE;

		gysahlWorldGenRate   = Constants.DEFAULT_GYSAHL_WORLD_GEN_RATE;
		gysahlSeedGrassDropWeight = Constants.DEFAULT_GYSAHL_SEED_GRASS_DROP_WEIGHT;

		featherDropChance    = Constants.DEFAULT_FEATHER_DROP_CHANCE;
		featherDelayRandom   = Constants.DEFAULT_FEATHER_DELAY_RANDOM;
		featherDelayStatic   = Constants.DEFAULT_FEATHER_DELAY_STATIC;
		
		penHealProbability   = Constants.DEFAULT_PEN_HEAL_PROBABILITY;
		penHealCauldronRange = Constants.DEFAULT_PEN_HEAL_CAULDRON_RANGE;

		spawnTimeDelay = Constants.DEFAULT_SPAWN_TIME_DELAY;
		spawnGroupMin = Constants.DEFAULT_SPAWN_GROUP_MIN;
		spawnGroupMax = Constants.DEFAULT_SPAWN_GROUP_MAX;
		spawnTotalMax = Constants.DEFAULT_SPAWN_TOTAL_MAX;
		spawnProbability = Constants.DEFAULT_SPAWN_PROBABILITY;
		spawnLimitChunkRadius = Constants.DEFAULT_SPAWN_LIMIT_CHUNK_RADIUS;
		spawnDistanceNextWild = Constants.DEFAULT_SPAWN_DIST_NEXT_WILD;
		
		spawnDbTimeDelay = 0;
		spawnDbStatus = "";
		
		breedingDelayMale = Constants.DEFAULT_BREEDING_DELAY_MALE;
		breedingDelayFemale = Constants.DEFAULT_BREEDING_DELAY_FEMALE;
		growupDelayRandom = Constants.DEFAULT_GROWUP_DELAY_RANDOM;
		growupDelayStatic = Constants.DEFAULT_GROWUP_DELAY_STATIC;
		
		hungerDelayChicobo = Constants.DEFAULT_HUNGER_DELAY_CHICOBO;
		hungerDelayChocobo = Constants.DEFAULT_HUNGER_DELAY_CHOCOBO;
		
		renderNameHeight = Constants.DEFAULT_RENDER_NAME_HEIGHT;
		livingSoundProb = Constants.DEFAULT_LIVING_SOUND_PROB;
		
		saddledCanWander = Constants.DEFAULT_SADDLED_CAN_WANDER;
		
		chocopediaInDungeons = Constants.DEFAULT_CHOCOPEDIA_IN_DUNGEONS;
				
    	ChocoboConfig.readConfigFilePreInit();
    	
    	proxy.registerEventListener();
    	
    	ChocoboConfig.readConfigFileInit();

		ChocoCraftItems.registerItems();
		ChocoCraftBlocks.registerBlocks();
	}
	
	@EventHandler
	public void loadChocoCraft(FMLInitializationEvent loadEvent)
	{
		//this.createCreativeTab();
		ChocoCraftWorld.registerDungeonLoot();
		ChocoCraftWorld.addGrassDrops();

		ChocoCraftRecipes.registerRecipes();
		ChocoCraftRecipes.registerSmeltingRecipes();

		ChocoCraftEntities.registerChocobos();

		proxy.registerRenderInformation();
		proxy.registerRenderThings();

		GameRegistry.registerWorldGenerator(new WorldGenGysahls(), 5);//TODO check how weights work

		FMLCommonHandler.instance().bus().register(new ChocoboPlayerTracker());

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new ChocoboGuiHandler());

		FMLCommonHandler.instance().bus().register(new ServerSpawnTickHandler());
	}
	

}
