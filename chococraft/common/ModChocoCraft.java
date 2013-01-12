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

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import chococraft.client.ClientProxyChocoCraft;
import chococraft.common.entities.EntityChicobo;
import chococraft.common.entities.colours.*;
import chococraft.common.gui.ChocoboGuiHandler;
import chococraft.common.items.*;
import chococraft.common.network.ChocoboPacketHandler;
import chococraft.common.tick.ServerSpawnTickHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid=Constants.TCC_MODID, name=Constants.TCC_NAME, version=Constants.TCC_VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, 
		channels = { Constants.PCHAN_CHOCOBO },
		packetHandler = ChocoboPacketHandler.class)
public class ModChocoCraft
{	
	public static Configuration mainConfiguration;

	//public static CreativeTabs chocoboCreativeItems;

	public static boolean debugMode = false;
	
	public static Property chocoboSaddleId;	
	public static Property gysahlSeedsId;    
	public static Property gysahlLoverlyId;
	public static Property gysahlGoldenId;
	public static Property gysahlPinkId;
	public static Property gysahlRedId;
	public static Property gysahlChibiId;
	public static Property gysahlCakeId;
	public static Property chocoboLegRawId;
	public static Property chocoboLegCookedId;
	public static Property chocoboFeatherId;
	public static Property chocoboSaddleBagsId;
	public static Property chocoboPackBagsId;
	public static Property chocoboWhistleId;
	public static Property chocopediaId;
	public static Property purpleChocoboEggId;
	public static Property chocoDisguiseHelmetId;
	public static Property chocoDisguisePlateId;
	public static Property chocoDisguiseLegsId;
	public static Property chocoDisguiseBootsId;	
	public static Property gysahlGreenBlockId;
	public static Property gysahlStemBlockId;
	public static Property strawBlockId;

	public static Item chocoboSaddleItem;
	public static Item gysahlSeedsItem;
	public static Item gysahlLoverlyItem;
	public static Item gysahlGoldenItem;
	public static Item gysahlPinkItem;
	public static Item gysahlRedItem;
	public static Item gysahlChibiItem;
	public static Item gysahlCakeItem;
	public static Item chocoboLegRawItem;
	public static Item chocoboLegCookedItem;
	public static Item chocoboFeatherItem;
	public static Item chocoboSaddleBagsItem;
	public static Item chocoboPackBagsItem;
	public static Item chocoboWhistleItem;
	public static Item chocopediaItem;
	public static Item purpleChocoboEggItem;
	public static Item chocoDisguiseHelmetItem;
	public static Item chocoDisguisePlateItem;
	public static Item chocoDisguiseLegsItem;
	public static Item chocoDisguiseBootsItem;

	public static Block gysahlStemBlock;
	public static Block gysahlGreenBlock;
	public static Block strawBlock;

	// setup
	public static boolean chocoboWingFlutter;
	public static int genderMaleChance;
	public static boolean showChocoboNames;
	public static boolean hungerEnabled;
	public static boolean riderBuffsEnabled;
	
	// chocobo size setup
	public static float chocoboHeight;
	public static float chocoboWidth;
	
	// gysahl mutation setup
	public static int gysahlGreenMutationRate;
	public static int gysahlLoveMutationRate;
	
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
		BiomeGenBase.icePlains
	};
	
	public static BiomeGenBase[] onlyYellowSpawnBiomes =
	{
		BiomeGenBase.beach, 
		BiomeGenBase.desert,
		BiomeGenBase.desertHills,
		BiomeGenBase.frozenRiver,
		BiomeGenBase.river
		
	};

	public static BiomeGenBase[] chocoboPurpleSpawnBiomes =
	{
		BiomeGenBase.hell
	};

	@Instance("ChocoCraft")
	public static ModChocoCraft instance;

	@SidedProxy(clientSide = "chococraft.client.ClientProxyChocoCraft", serverSide = "chococraft.common.CommonProxyChocoCraft")
	public static CommonProxyChocoCraft proxy;

	@Init
	public void loadChocoCraft(FMLInitializationEvent loadEvent)
	{
		//this.createCreativeTab();
		
		this.createItemInstances();

		this.createBlockInstances();

		this.addSmeltings();

		this.addRecipes();

		if(Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
		{
			ClientProxyChocoCraft.registerRenderInformation();
		}

		this.registerChocobos();

		this.addChocoboSpawns();

		GameRegistry.registerWorldGenerator(new WorldGenGysahls());
		GameRegistry.registerPlayerTracker(new ChocoboPlayerTracker());
				
		proxy.registerRenderThings();

		NetworkRegistry.instance().registerGuiHandler(this, new ChocoboGuiHandler());
		
		TickRegistry.registerScheduledTickHandler(new ServerSpawnTickHandler(), Side.SERVER);
	}

	@PreInit
	public void preLoadChocoCraft(FMLPreInitializationEvent preInitEvent)
	{
		chocoboHeight = 1.9F;
		chocoboWidth = 1.3F;
		
		mainConfiguration = new Configuration(preInitEvent.getSuggestedConfigurationFile());		
		mainConfiguration.load();
		
		chocoboSaddleId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "chocoboSaddleItem.id", Constants.CHOCOBO_SADDLE_ID);
		gysahlSeedsId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "gysahlSeedsItem.id", Constants.GYSAHL_SEEDS_ID);
		gysahlLoverlyId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "gysahlLoverlyItem.id", Constants.GYSAHL_LOVERLY_ID);
		gysahlGoldenId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "gysahlGoldenItem.id", Constants.GYSAHL_GOLDEN_ID);
		gysahlPinkId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "gysahlPinkItem.id", Constants.GYSAHL_PINK_ID);
		gysahlRedId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "gysahlRedItem.id", Constants.GYSAHL_RED_ID);
		gysahlChibiId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "gysahlChibiItem.id", Constants.GYSAHL_CHIBI_ID);
		gysahlCakeId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "gysahlCakeItem.id", Constants.GYSAHL_CAKE_ID);
		chocoboLegRawId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "chocoboLegRawItem.id", Constants.CHOCOBO_LEG_RAW_ID);
		chocoboLegCookedId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "chocoboLegCookedItem.id", Constants.CHOCOBO_LEG_COOKED_ID);
		chocoboFeatherId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "chocoboFeatherItem.id", Constants.CHOCOBO_FEATHER_ID);
		chocoboSaddleBagsId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "chocoboSaddleBagsItem.id", Constants.CHOCOBO_SADDLE_BAGS_ID);
		chocoboPackBagsId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "chocoboPackBagsItem.id", Constants.CHOCOBO_PACK_BAGS_ID);
		chocoboWhistleId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "chocoboWhistleItem.id", Constants.CHOCOBO_WHISTLE_ID);
		chocopediaId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "chocopediaItem.id", Constants.CHOCOPEDIA_ID);
		purpleChocoboEggId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "purpleChocoboEggItem.id", Constants.PURPLE_CHOCOBO_EGG_ID);

		chocoDisguiseHelmetId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "chocoDisguiseHelmetItem.id", Constants.CHOCO_DISGUISE_HELMET_ID);
		chocoDisguisePlateId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "chocoDisguisePlateItem.id", Constants.CHOCO_DISGUISE_PLATE_ID);
		chocoDisguiseLegsId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "chocoDisguiseLegsItem.id", Constants.CHOCO_DISGUISE_LEGS_ID);
		chocoDisguiseBootsId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "chocoDisguiseBootsItem.id", Constants.CHOCO_DISGUISE_BOOTS_ID);

		gysahlGreenBlockId = mainConfiguration.getBlock("gysahlGreenBlock.id", Constants.GYSAHL_GREEN_BLOCK_ID);
		gysahlStemBlockId = mainConfiguration.getBlock("gysahlStemBlock.id", Constants.GYSAHL_STEM_BLOCK_ID);
		strawBlockId = mainConfiguration.getBlock("strawBlock.id", Constants.STRAW_BLOCK_ID);
		
		mainConfiguration.save();

		chocoboWingFlutter   = Constants.DEFAULT_CHOCOBO_WING_FLUTTER;
		hungerEnabled        = Constants.DEFAULT_HUNGER_ENABLED;
		riderBuffsEnabled    = Constants.DEFAULT_RIDER_BUFFS_ENABLED;
		showChocoboNames     = Constants.DEFAULT_SHOW_CHOCOBO_NAMES;
		
		genderMaleChance     = Constants.DEFAULT_GENDER_MALE_CHANCE;
		
		gysahlGreenMutationRate = Constants.DEFAULT_GYSAHL_GREEN_MUTATION_RATE;
		gysahlLoveMutationRate  = Constants.DEFAULT_GYSAHL_LOVE_MUTATION_RATE;

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
		
    	ChocoboConfig.readConfigFile();
    	
    	proxy.registerEventListener();
	}

	@PostInit
	public void postLoadChocoCraft(FMLPostInitializationEvent postInitEvent) {}

//	// initialising methods
//	private void createCreativeTab()
//	{
//		chocoboCreativeItems = new ChocoboCreativeTab("Chocobo Items");
//		LanguageRegistry.instance().addStringLocalization("itemGroup." + chocoboCreativeItems.getTabLabel(), "en_US", "ChocoCraft");		
//	}

	private void createItemInstances()
	{
		// Chocopedia
		chocopediaItem = (new ChocoboItem(Integer.parseInt(chocopediaId.value))).setItemName("chocopedia").setMaxStackSize(1);
		chocopediaItem.setIconIndex(14);
		LanguageRegistry.addName(chocopediaItem, "Chocopedia");
		chocopediaItem.setCreativeTab(CreativeTabs.tabTools);
		//chocopediaItem.setCreativeTab(chocoboCreativeItems);
		//DungeonHooks.addDungeonLoot(new ItemStack(chocopediaItem), 1, 1, 1);

		// Chocobo Feather
		chocoboFeatherItem = (new ChocoboItem(Integer.parseInt(chocoboFeatherId.value))).setItemName("chocoboFeather").setMaxStackSize(64);
		chocoboFeatherItem.setIconIndex(10);
		LanguageRegistry.addName(chocoboFeatherItem, "Chocobo Feather");
		chocoboFeatherItem.setCreativeTab(CreativeTabs.tabMaterials);
		//chocoboFeatherItem.setCreativeTab(chocoboCreativeItems);
		//DungeonHooks.addDungeonLoot(new ItemStack(chocoboFeatherItem), 10, 5, 15);

		
		// riding gear
		// Chocobo Saddle
		chocoboSaddleItem = (new ChocoboItem(Integer.parseInt(chocoboSaddleId.value))).setItemName("chocoboSaddle").setMaxStackSize(5);
		chocoboSaddleItem.setIconIndex(0);
		LanguageRegistry.addName(chocoboSaddleItem, "Chocobo Saddle");
		chocoboSaddleItem.setCreativeTab(CreativeTabs.tabTransport);
		//chocoboSaddleItem.setCreativeTab(chocoboCreativeItems);

		// Chocobo Saddle Bags
		chocoboSaddleBagsItem = (new ChocoboItem(Integer.parseInt(chocoboSaddleBagsId.value))).setItemName("chocoboSaddleBags").setMaxStackSize(8);
		chocoboSaddleBagsItem.setIconIndex(11);
		LanguageRegistry.addName(chocoboSaddleBagsItem, "Chocobo Saddle Bags");
		chocoboSaddleBagsItem.setCreativeTab(CreativeTabs.tabTransport);
		//chocoboSaddleBagsItem.setCreativeTab(chocoboCreativeItems);

		// Chocobo Pack Bags
		chocoboPackBagsItem = (new ChocoboItem(Integer.parseInt(chocoboPackBagsId.value))).setItemName("chocoboPackBags").setMaxStackSize(8);
		chocoboPackBagsItem.setIconIndex(12);
		LanguageRegistry.addName(chocoboPackBagsItem, "Chocobo Pack Bags");
		chocoboPackBagsItem.setCreativeTab(CreativeTabs.tabTransport);
		//chocoboPackBagsItem.setCreativeTab(chocoboCreativeItems);

		// Gysahls
		// Gysahl seeds
		gysahlSeedsItem = (new ItemSeeds(Integer.parseInt(gysahlSeedsId.value), Integer.parseInt(gysahlStemBlockId.value), Block.tilledField.blockID)).setItemName("gysahlSeeds");
		gysahlSeedsItem.setTextureFile(Constants.CHOCOBO_ITEM_TEXTURES);
		gysahlSeedsItem.setIconIndex(1);
		LanguageRegistry.addName(gysahlSeedsItem, "Gysahl Seeds");
		gysahlSeedsItem.setCreativeTab(CreativeTabs.tabMaterials);
		//gysahlSeedsItem.setCreativeTab(chocoboCreativeItems);

		// Loverly Gysahl
		gysahlLoverlyItem = (new ChocoboItem(Integer.parseInt(gysahlLoverlyId.value))).setItemName("gysahlLoverly").setMaxStackSize(64);
		gysahlLoverlyItem.setIconIndex(2);
		LanguageRegistry.addName(gysahlLoverlyItem, "Loverly Gysahl");
		gysahlLoverlyItem.setCreativeTab(CreativeTabs.tabMisc);
		//gysahlLoverlyItem.setCreativeTab(chocoboCreativeItems);

		// Golden Gysahl
		gysahlGoldenItem = (new ChocoboItem(Integer.parseInt(gysahlGoldenId.value))).setItemName("gysahlGolden").setMaxStackSize(64);
		gysahlGoldenItem.setIconIndex(3);
		LanguageRegistry.addName(gysahlGoldenItem, "Golden Gysahl");
		gysahlGoldenItem.setCreativeTab(CreativeTabs.tabMisc);
		//gysahlGoldenItem.setCreativeTab(chocoboCreativeItems);
		//DungeonHooks.addDungeonLoot(new ItemStack(gysahlGoldenItem), 1, 2, 8);

		// Pink Gysahl
		gysahlPinkItem = (new ChocoboItem(Integer.parseInt(gysahlPinkId.value))).setItemName("gysahlPink").setMaxStackSize(64);
		gysahlPinkItem.setIconIndex(4);
		LanguageRegistry.addName(gysahlPinkItem, "Pink Gysahl");
		gysahlPinkItem.setCreativeTab(CreativeTabs.tabMisc);
		//gysahlPinkItem.setCreativeTab(chocoboCreativeItems);

		// Red Gysahl
		gysahlRedItem = (new ChocoboItem(Integer.parseInt(gysahlRedId.value))).setItemName("gysahlRed").setMaxStackSize(64);
		gysahlRedItem.setIconIndex(5);
		LanguageRegistry.addName(gysahlRedItem, "Red Gysahl");
		gysahlRedItem.setCreativeTab(CreativeTabs.tabMisc);
		//gysahlRedItem.setCreativeTab(chocoboCreativeItems);

//		// Chibi Gysahl
//		gysahlChibiItem = (new ChocoboItem(Integer.parseInt(gysahlChibiId.value))).setItemName("gysahlChibi").setMaxStackSize(64);
//		gysahlChibiItem.setIconIndex(6);
//		LanguageRegistry.addName(gysahlChibiItem, "Chibi Gysahl");
//		gysahlChibiItem.setCreativeTab(CreativeTabs.tabMisc);
//		//gysahlChibiItem.setCreativeTab(chocoboCreativeItems);

		// Gysahl Cake
		gysahlCakeItem = (new ChocoboItem(Integer.parseInt(gysahlCakeId.value))).setItemName("gysahlCake").setMaxStackSize(8);
		gysahlCakeItem.setIconIndex(7);
		LanguageRegistry.addName(gysahlCakeItem, "Gysahl Cake");
		gysahlCakeItem.setCreativeTab(CreativeTabs.tabMisc);
		//gysahlCakeItem.setCreativeTab(chocoboCreativeItems);

		// Chocob Whistle
		chocoboWhistleItem = (new ChocoboItem(Integer.parseInt(chocoboWhistleId.value))).setItemName("chocoboWhistle").setMaxStackSize(64);
		chocoboWhistleItem.setIconIndex(13);
		LanguageRegistry.addName(chocoboWhistleItem, "Chocobo Whistle");
		chocoboWhistleItem.setCreativeTab(CreativeTabs.tabTools);
		//chocoboWhistleItem.setCreativeTab(chocoboCreativeItems);

		// Nether Chocobo Egg
		purpleChocoboEggItem = (new ItemPurpleChocoboEgg(Integer.parseInt(purpleChocoboEggId.value))).setItemName("purpleChocoboEgg").setMaxStackSize(64);
		purpleChocoboEggItem.setIconIndex(15);
		LanguageRegistry.addName(purpleChocoboEggItem, "Purple Chocobo Egg");
		purpleChocoboEggItem.setCreativeTab(CreativeTabs.tabMisc);

		// Armour
		// Chocodisguise Helmet
		chocoDisguiseHelmetItem = (new ChocoboItemDisguise(Integer.parseInt(chocoDisguiseHelmetId.value), ChocoboArmourMaterial.CHOCOFEATHER, proxy.addArmor("chocoDisguise"), 0));
		chocoDisguiseHelmetItem.setIconIndex(17);
		chocoDisguiseHelmetItem.setItemName("chocoDisguiseHelmet");
		LanguageRegistry.addName(chocoDisguiseHelmetItem, "Chocodisguise Helmet");

		// Chocodisguise Plate
		chocoDisguisePlateItem = (new ChocoboItemDisguise(Integer.parseInt(chocoDisguisePlateId.value), ChocoboArmourMaterial.CHOCOFEATHER, proxy.addArmor("chocoDisguise"), 1));
		chocoDisguisePlateItem.setIconIndex(18);
		chocoDisguisePlateItem.setItemName("chocoDisguisePlate");
		LanguageRegistry.addName(chocoDisguisePlateItem, "Chocodisguise Body");

		// Chocodisguise Legs
		chocoDisguiseLegsItem = (new ChocoboItemDisguise(Integer.parseInt(chocoDisguiseLegsId.value), ChocoboArmourMaterial.CHOCOFEATHER, proxy.addArmor("chocoDisguise"), 2));
		chocoDisguiseLegsItem.setIconIndex(19);
		chocoDisguiseLegsItem.setItemName("chocoDisguiseLegs");
		LanguageRegistry.addName(chocoDisguiseLegsItem, "Chocodisguise Legs");

		// Chocodisguise Boots
		chocoDisguiseBootsItem = (new ChocoboItemDisguise(Integer.parseInt(chocoDisguiseBootsId.value), ChocoboArmourMaterial.CHOCOFEATHER, proxy.addArmor("chocoDisguise"), 3));
		chocoDisguiseBootsItem.setIconIndex(20);
		chocoDisguiseBootsItem.setItemName("chocoDisguiseBoots");
		LanguageRegistry.addName(chocoDisguiseBootsItem, "Chocodisguise Boots");

		// food items
		// Raw Chocobo Leg
		chocoboLegRawItem = (new ItemFood(Integer.parseInt(chocoboLegRawId.value), 4, true)).setItemName("chocoboLegRaw").setMaxStackSize(64);
		chocoboLegRawItem.setTextureFile(Constants.CHOCOBO_ITEM_TEXTURES);
		chocoboLegRawItem.setIconIndex(8);
		LanguageRegistry.addName(chocoboLegRawItem, "Raw Chocobo Leg");

		// Cooked Chocobo Leg
		chocoboLegCookedItem = (new ItemFood(Integer.parseInt(chocoboLegCookedId.value), 8, false)).setItemName("chocoboLegCooked").setMaxStackSize(64);
		chocoboLegCookedItem.setTextureFile(Constants.CHOCOBO_ITEM_TEXTURES);
		chocoboLegCookedItem.setIconIndex(9);
		LanguageRegistry.addName(chocoboLegCookedItem, "Cooked Chocobo Leg");
	}

	private void createBlockInstances()
	{
		String gysahlStemBlockName = "gysahlStemBlock";
		gysahlStemBlock = new BlockGysahlStem(Integer.parseInt(gysahlStemBlockId.value), 35).setStepSound(Block.soundGrassFootstep).setHardness(0.0F).setBlockName(gysahlStemBlockName);
		GameRegistry.registerBlock(gysahlStemBlock, gysahlStemBlockName);
		LanguageRegistry.addName(gysahlStemBlock, "Gysahl Stem");

		String gysahlGreenBlockName = "gysahlGreenBlock";
		gysahlGreenBlock = new BlockGysahlGreen(Integer.parseInt(gysahlGreenBlockId.value), 36).setStepSound(Block.soundGrassFootstep).setHardness(0.0F).setBlockName(gysahlGreenBlockName);
		GameRegistry.registerBlock(gysahlGreenBlock, gysahlGreenBlockName);
		LanguageRegistry.addName(gysahlGreenBlock, "Gysahl Green");
		
		String strawBlockName = "strawBlock";
		strawBlock = new BlockStraw(Integer.parseInt(strawBlockId.value), 21).setStepSound(Block.soundGrassFootstep).setHardness(0.0F).setBlockName(strawBlockName);
		GameRegistry.registerBlock(strawBlock, strawBlockName);
		LanguageRegistry.addName(strawBlock, "Straw");
	}

	private void addSmeltings()
	{
		GameRegistry.addSmelting(chocoboLegRawItem.itemID, new ItemStack(chocoboLegCookedItem), 0.1F);
	}

	private void addRecipes()
	{
		// chocobo saddle
		GameRegistry.addRecipe(new ItemStack(chocoboSaddleItem, 1), new Object[]
		{
			"-X-", 
			" Y ", 
			Character.valueOf('X'), Item.leather, 
			Character.valueOf('Y'), chocoboFeatherItem, 
			Character.valueOf('-'), Item.silk
		});

		// chocobo saddle bag
		GameRegistry.addRecipe(new ItemStack(chocoboSaddleBagsItem, 1), new Object[]
		{
			" Y ",
			"X X", 
			" X ", 
			Character.valueOf('X'), Item.leather, 
			Character.valueOf('Y'), chocoboFeatherItem
		});

		// chocobo pack bag
		GameRegistry.addRecipe(new ItemStack(chocoboPackBagsItem, 1), new Object[]
		{
			"-Y-",
			"C C", 
			"-X-", 
			Character.valueOf('C'), Block.cloth, 
			Character.valueOf('X'), Item.leather, 
			Character.valueOf('Y'), chocoboFeatherItem,
			Character.valueOf('-'), Item.silk
		});

//		// loverly gysahl
//		GameRegistry.addRecipe(new ItemStack(gysahlLoverlyItem, 1), new Object[]
//		{
//			"RY", 
//			"X ", 
//			Character.valueOf('X'), gysahlGreenBlock, 
//			Character.valueOf('Y'), chocoboFeatherItem, 
//			Character.valueOf('R'), Block.plantRed
//		});
//
//		// golden gysahl
//		GameRegistry.addRecipe(new ItemStack(gysahlGoldenItem, 1), new Object[]
//		{
//			"FGF", 
//			"GXG", 
//			"FGF", 
//			Character.valueOf('X'), gysahlGreenBlock, 
//			Character.valueOf('F'), chocoboFeatherItem, 
//			Character.valueOf('G'), Item.goldNugget
//		});

//		// chibi gysahl
//		GameRegistry.addRecipe(new ItemStack(gysahlChibiItem, 1), new Object[]
//		{
//			" R ", 
//			"RGR", 
//			" R ", 
//			Character.valueOf('R'), Item.redstone, 
//			Character.valueOf('G'), gysahlGreenBlock
//		});

		// chocopedia
		GameRegistry.addRecipe(new ItemStack(chocopediaItem, 1), new Object[]
		{
			"FGF", 
			"IBI", 
			"FLF", 
			Character.valueOf('B'), Item.book,
			Character.valueOf('F'), chocoboFeatherItem,
			Character.valueOf('I'), new ItemStack(Item.dyePowder, 1, 0),
			Character.valueOf('L'), new ItemStack(Item.dyePowder, 1, 4),
			Character.valueOf('G'), Item.goldNugget
		});

		// chocobo whistle
		GameRegistry.addShapelessRecipe(new ItemStack(chocoboWhistleItem, 1), new Object[]
		{
			chocoboFeatherItem, 
			new ItemStack(Item.ingotGold, 1)
		});

		// gysahl seeds
		GameRegistry.addShapelessRecipe(new ItemStack(gysahlSeedsItem, 3), new Object[]
		{
			new ItemStack(gysahlGreenBlock, 1)
		});

		// gysahl cake
		GameRegistry.addRecipe(new ItemStack(gysahlCakeItem, 1), new Object[]
		{
			"MGM", 
			"SES", 
			"WGW", 
			Character.valueOf('G'), gysahlGreenBlock, 
			Character.valueOf('M'), Item.bucketMilk, 
			Character.valueOf('S'), Item.sugar, 
			Character.valueOf('W'), Item.wheat,
			Character.valueOf('E'), Item.egg
		});

		// pink gysahl
		GameRegistry.addShapelessRecipe(new ItemStack(gysahlPinkItem, 1), new Object[]
		{
			new ItemStack(gysahlGreenBlock, 1), 
			new ItemStack(Item.dyePowder, 1, 9)
		});

		// red gysahl
		GameRegistry.addShapelessRecipe(new ItemStack(gysahlRedItem, 1), new Object[]
		{
			new ItemStack(gysahlGreenBlock, 1), 
			new ItemStack(Item.dyePowder, 1, 1)
		});

		// chocobo disguise
		GameRegistry.addRecipe(new ItemStack(chocoDisguiseHelmetItem, 1), new Object[]
		{
			"YYY",
			"Y Y",
			Character.valueOf('Y'), chocoboFeatherItem
		});

		GameRegistry.addRecipe(new ItemStack(chocoDisguisePlateItem, 1), new Object[]
		{
			"Y Y",
			"YYY",
			"YYY",
			Character.valueOf('Y'), chocoboFeatherItem
		});

		GameRegistry.addRecipe(new ItemStack(chocoDisguiseLegsItem, 1), new Object[]
		{
			"YYY",
			"Y Y",
			"Y Y",
			Character.valueOf('Y'), chocoboFeatherItem
		});

		GameRegistry.addRecipe(new ItemStack(chocoDisguiseBootsItem, 1), new Object[]
		{
			"Y Y",
			"Y Y",
			Character.valueOf('Y'), chocoboFeatherItem
		});
		
		GameRegistry.addShapelessRecipe(new ItemStack(strawBlock, 4), new Object[]
		{
			new ItemStack(Item.wheat, 1)
		});
		                                                        		
		// vanilla recipes with chocobo feathers
		// arrows
		GameRegistry.addRecipe(new ItemStack(Item.arrow, 4), new Object[]
		{
			" F ",
		    " S ",
		    " Y ",
		    Character.valueOf('F'), Item.flint, 
		    Character.valueOf('S'), Item.stick, 
		    Character.valueOf('Y'), chocoboFeatherItem
		});
		                                                        		
		GameRegistry.addShapelessRecipe(new ItemStack(Item.writableBook, 1), new Object[]
		{
			new ItemStack(Item.book, 1),
			new ItemStack(Item.dyePowder, 1, 0),
			new ItemStack(chocoboFeatherItem, 1)
		});		
	}

	private void registerChocobos()
	{
		this.registerChocoboEntityClass(EntityChicobo.class, "Chicobo", this.getRGBInt(170, 70, 250), this.getRGBInt(250, 250, 0), "Chicobo");
		this.registerChocoboEntityClass(EntityChocoboYellow.class, "ChocoboYellow", this.getRGBInt(250, 250, 50), this.getRGBInt(180, 150, 1), "Yellow Chocobo");
		this.registerChocoboEntityClass(EntityChocoboGreen.class, "ChocoboGreen", this.getRGBInt(50, 250, 50), this.getRGBInt(1, 100, 1), "Green Chocobo");
		this.registerChocoboEntityClass(EntityChocoboBlue.class, "ChocoboBlue", this.getRGBInt(100, 100, 250), this.getRGBInt(100, 100, 200), "Blue Chocobo");
		this.registerChocoboEntityClass(EntityChocoboWhite.class, "ChocoboWhite", this.getRGBInt(240, 240, 240), this.getRGBInt(252, 252, 252), "White Chocobo");
		this.registerChocoboEntityClass(EntityChocoboBlack.class, "ChocoboBlack", this.getRGBInt(1, 1, 1), this.getRGBInt(150, 150, 150), "Black Chocobo");
		this.registerChocoboEntityClass(EntityChocoboGold.class, "ChocoboGold", this.getRGBInt(250, 130, 70), this.getRGBInt(111, 90, 33), "Gold Chocobo");
		this.registerChocoboEntityClass(EntityChocoboPink.class, "ChocoboPink", this.getRGBInt(222, 20, 222), this.getRGBInt(250, 200, 250), "Pink Chocobo");
		this.registerChocoboEntityClass(EntityChocoboRed.class, "ChocoboRed", this.getRGBInt(250, 20, 20), this.getRGBInt(100, 20, 20), "Red Chocobo");
		this.registerChocoboEntityClass(EntityChocoboPurple.class, "ChocoboPurple", this.getRGBInt(170, 70, 160), this.getRGBInt(60, 50, 111), "Purple Chocobo");
	}

	private void registerChocoboEntityClass(Class <? extends Entity> entityClass, String entityName, int eggColor, int eggDotsColor, String visibleName)
	{
		int entityID = EntityRegistry.findGlobalUniqueEntityId();
		LanguageRegistry.instance().addStringLocalization("entity." + entityName  + ".name", "en_US", visibleName);
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, entityID, eggColor, eggDotsColor);
		EntityRegistry.registerModEntity(entityClass, entityName, entityID, instance, 128, 1, true);
	}

	private int getRGBInt(int rInt, int gInt, int bInt)
	{
		return (rInt << 16) + (gInt << 8) + bInt;
	}

	private void addChocoboSpawns()
	{
		//EntityRegistry.addSpawn(EntityChocoboYellow.class, spawnWeightedProb, spawnGroupMin, spawnGroupMax, EnumCreatureType.creature, spawnBiomes);
		//EntityRegistry.addSpawn(EntityChocoboPurple.class, 5, 5, 8, EnumCreatureType.creature, chocoboPurpleSpawnBiomes);
	}
}
