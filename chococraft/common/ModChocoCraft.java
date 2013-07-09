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

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import chococraft.client.ClientProxyChocoCraft;
import chococraft.common.entities.EntityChicobo;
import chococraft.common.entities.colours.EntityChocoboBlack;
import chococraft.common.entities.colours.EntityChocoboBlue;
import chococraft.common.entities.colours.EntityChocoboGold;
import chococraft.common.entities.colours.EntityChocoboGreen;
import chococraft.common.entities.colours.EntityChocoboPink;
import chococraft.common.entities.colours.EntityChocoboPurple;
import chococraft.common.entities.colours.EntityChocoboRed;
import chococraft.common.entities.colours.EntityChocoboWhite;
import chococraft.common.entities.colours.EntityChocoboYellow;
import chococraft.common.gui.ChocoboGuiHandler;
import chococraft.common.items.BlockGysahlGreen;
import chococraft.common.items.BlockGysahlStem;
import chococraft.common.items.BlockStraw;
import chococraft.common.items.ChocoboArmourMaterial;
import chococraft.common.items.ChocoboItem;
import chococraft.common.items.ChocoboItemDisguise;
import chococraft.common.items.ChocoboItemFood;
import chococraft.common.items.ItemGysahlSeeds;
import chococraft.common.items.ItemPurpleChocoboEgg;
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
	public static Property gysahlPicklesId;
	public static Property gysahlPicklesRawId;
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
	public static Item gysahlPicklesItem;
	public static Item gysahlPicklesRawItem;
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

	@Instance(Constants.TCC_MODID)
	public static ModChocoCraft instance;

	@SidedProxy(clientSide = "chococraft.client.ClientProxyChocoCraft", serverSide = "chococraft.common.CommonProxyChocoCraft")
	public static CommonProxyChocoCraft proxy;

	@Init
	public void loadChocoCraft(FMLInitializationEvent loadEvent)
	{
		//this.createCreativeTab();
    	ChocoboConfig.readConfigFileInit();
		
		this.createItemInstances();

		this.createBlockInstances();

		this.addDungeonChestHooks();
		
		this.addSmeltings();

		this.addRecipes();
		
		this.addGrassDrops();

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
		configFolder = preInitEvent.getModConfigurationDirectory();
		mainConfiguration.load();
		
		chocoboSaddleId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "chocoboSaddleItem.id", Constants.CHOCOBO_SADDLE_ID);
		gysahlSeedsId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "gysahlSeedsItem.id", Constants.GYSAHL_SEEDS_ID);
		gysahlLoverlyId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "gysahlLoverlyItem.id", Constants.GYSAHL_LOVERLY_ID);
		gysahlGoldenId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "gysahlGoldenItem.id", Constants.GYSAHL_GOLDEN_ID);
		gysahlPinkId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "gysahlPinkItem.id", Constants.GYSAHL_PINK_ID);
		gysahlRedId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "gysahlRedItem.id", Constants.GYSAHL_RED_ID);
		gysahlCakeId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "gysahlCakeItem.id", Constants.GYSAHL_CAKE_ID);
		gysahlPicklesId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "gysahlPicklesItem.id", Constants.GYSAHL_PICKLES_ID);
		gysahlPicklesRawId = mainConfiguration.getItem(Configuration.CATEGORY_ITEM, "gysahlPicklesRawItem.id", Constants.GYSAHL_PICKLES_RAW_ID);
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
		chocopediaItem = (new ChocoboItem(chocopediaId.getInt())).setUnlocalizedName(Constants.KEY_CHOCOPEDIA).setMaxStackSize(1);
		LanguageRegistry.addName(chocopediaItem, "Chocopedia");
		chocopediaItem.setCreativeTab(CreativeTabs.tabTools);

		// Chocobo Feather
		chocoboFeatherItem = (new ChocoboItem(chocoboFeatherId.getInt())).setUnlocalizedName(Constants.KEY_FEATHER).setMaxStackSize(64);
		LanguageRegistry.addName(chocoboFeatherItem, "Chocobo Feather");
		chocoboFeatherItem.setCreativeTab(CreativeTabs.tabMaterials);
		
		// riding gear
		// Chocobo Saddle
		chocoboSaddleItem = (new ChocoboItem(chocoboSaddleId.getInt())).setUnlocalizedName(Constants.KEY_SADDLE).setMaxStackSize(5);
		LanguageRegistry.addName(chocoboSaddleItem, "Chocobo Saddle");
		chocoboSaddleItem.setCreativeTab(CreativeTabs.tabTransport);

		// Chocobo Saddle Bags
		chocoboSaddleBagsItem = (new ChocoboItem(chocoboSaddleBagsId.getInt())).setUnlocalizedName(Constants.KEY_SADDLEBAG).setMaxStackSize(8);
		LanguageRegistry.addName(chocoboSaddleBagsItem, "Chocobo Saddle Bags");
		chocoboSaddleBagsItem.setCreativeTab(CreativeTabs.tabTransport);

		// Chocobo Pack Bags
		chocoboPackBagsItem = (new ChocoboItem(chocoboPackBagsId.getInt())).setUnlocalizedName(Constants.KEY_PACKBAG).setMaxStackSize(8);
		LanguageRegistry.addName(chocoboPackBagsItem, "Chocobo Pack Bags");
		chocoboPackBagsItem.setCreativeTab(CreativeTabs.tabTransport);

		// Gysahls
		// Gysahl seeds
		gysahlSeedsItem = (new ItemGysahlSeeds(gysahlSeedsId.getInt(), gysahlStemBlockId.getInt(), Block.tilledField.blockID));
		LanguageRegistry.addName(gysahlSeedsItem, "Gysahl Seeds");
		//gysahlSeedsItem.setCreativeTab(chocoboCreativeItems);

		// Loverly Gysahl
		gysahlLoverlyItem = (new ChocoboItem(gysahlLoverlyId.getInt())).setUnlocalizedName(Constants.KEY_GY_LOVERLY).setMaxStackSize(64);
		LanguageRegistry.addName(gysahlLoverlyItem, "Loverly Gysahl");
		gysahlLoverlyItem.setCreativeTab(CreativeTabs.tabMisc);

		// Golden Gysahl
		gysahlGoldenItem = (new ChocoboItem(gysahlGoldenId.getInt())).setUnlocalizedName(Constants.KEY_GY_GOLDEN).setMaxStackSize(64);
		LanguageRegistry.addName(gysahlGoldenItem, "Golden Gysahl");
		gysahlGoldenItem.setCreativeTab(CreativeTabs.tabMisc);

		// Pink Gysahl
		gysahlPinkItem = (new ChocoboItem(gysahlPinkId.getInt())).setUnlocalizedName(Constants.KEY_GY_PINK).setMaxStackSize(64);
		LanguageRegistry.addName(gysahlPinkItem, "Pink Gysahl");
		gysahlPinkItem.setCreativeTab(CreativeTabs.tabMisc);

		// Red Gysahl
		gysahlRedItem = (new ChocoboItem(gysahlRedId.getInt())).setUnlocalizedName(Constants.KEY_GY_RED).setMaxStackSize(64);
		LanguageRegistry.addName(gysahlRedItem, "Red Gysahl");
		gysahlRedItem.setCreativeTab(CreativeTabs.tabMisc);

		// Gysahl Cake
		gysahlCakeItem = (new ChocoboItem(gysahlCakeId.getInt())).setUnlocalizedName(Constants.KEY_GY_CAKE).setMaxStackSize(8);
		LanguageRegistry.addName(gysahlCakeItem, "Gysahl Cake");
		gysahlCakeItem.setCreativeTab(CreativeTabs.tabMisc);

		// Chocob Whistle
		chocoboWhistleItem = (new ChocoboItem(chocoboWhistleId.getInt())).setUnlocalizedName(Constants.KEY_WHISTLE).setMaxStackSize(64);
		LanguageRegistry.addName(chocoboWhistleItem, "Chocobo Whistle");
		chocoboWhistleItem.setCreativeTab(CreativeTabs.tabTools);

		// Nether Chocobo Egg
		purpleChocoboEggItem = new ItemPurpleChocoboEgg(purpleChocoboEggId.getInt());
		LanguageRegistry.addName(purpleChocoboEggItem, "Purple Chocobo Egg");

		// Armour
		// Chocodisguise Helmet
		chocoDisguiseHelmetItem = new ChocoboItemDisguise(chocoDisguiseHelmetId.getInt(), ChocoboArmourMaterial.CHOCOFEATHER, proxy.addArmor(Constants.KEY_DISGUISE), 0);
		chocoDisguiseHelmetItem.setUnlocalizedName(Constants.KEY_DISGUISE_HEAD);
		LanguageRegistry.addName(chocoDisguiseHelmetItem, "Chocodisguise Helmet");

		// Chocodisguise Plate
		chocoDisguisePlateItem = new ChocoboItemDisguise(chocoDisguisePlateId.getInt(), ChocoboArmourMaterial.CHOCOFEATHER, proxy.addArmor(Constants.KEY_DISGUISE), 1);
		chocoDisguisePlateItem.setUnlocalizedName(Constants.KEY_DISGUISE_BODY);
		LanguageRegistry.addName(chocoDisguisePlateItem, "Chocodisguise Body");

		// Chocodisguise Legs
		chocoDisguiseLegsItem = new ChocoboItemDisguise(chocoDisguiseLegsId.getInt(), ChocoboArmourMaterial.CHOCOFEATHER, proxy.addArmor(Constants.KEY_DISGUISE), 2);
		chocoDisguiseLegsItem.setUnlocalizedName(Constants.KEY_DISGUISE_LEGS);
		LanguageRegistry.addName(chocoDisguiseLegsItem, "Chocodisguise Legs");

		// Chocodisguise Boots
		chocoDisguiseBootsItem = new ChocoboItemDisguise(chocoDisguiseBootsId.getInt(), ChocoboArmourMaterial.CHOCOFEATHER, proxy.addArmor(Constants.KEY_DISGUISE), 3);
		chocoDisguiseBootsItem.setUnlocalizedName(Constants.KEY_DISGUISE_BOOTS);
		LanguageRegistry.addName(chocoDisguiseBootsItem, "Chocodisguise Boots");

		// food items
		// Raw Chocobo Leg
		chocoboLegRawItem = new ChocoboItemFood(chocoboLegRawId.getInt(), 4, true);
		chocoboLegRawItem.setUnlocalizedName(Constants.KEY_LEG_RAW);
		LanguageRegistry.addName(chocoboLegRawItem, "Raw Chocobo Leg");

		// Cooked Chocobo Leg
		chocoboLegCookedItem = new ChocoboItemFood(chocoboLegCookedId.getInt(), 8, false);
		chocoboLegCookedItem.setUnlocalizedName(Constants.KEY_LEG_COOKED);
		LanguageRegistry.addName(chocoboLegCookedItem, "Cooked Chocobo Leg");
		
		// Gysahl Pickles
		gysahlPicklesRawItem = (new ChocoboItem(gysahlPicklesRawId.getInt()));
		gysahlPicklesRawItem.setUnlocalizedName(Constants.KEY_GY_PICKLES_RAW).setMaxStackSize(64);
		LanguageRegistry.addName(gysahlPicklesRawItem, "Gysahl Raw Pickles");
		gysahlPicklesRawItem.setCreativeTab(CreativeTabs.tabMisc);

		gysahlPicklesItem = new ChocoboItemFood(gysahlPicklesId.getInt(), 2, false);
		gysahlPicklesItem.setUnlocalizedName(Constants.KEY_GY_PICKLES);
		LanguageRegistry.addName(gysahlPicklesItem, "Gysahl Pickles");
		gysahlPicklesItem.setCreativeTab(CreativeTabs.tabMisc);
	}

	private void createBlockInstances()
	{
		String gysahlStemBlockName = "item.gysahlStemBlock";
		gysahlStemBlock = new BlockGysahlStem(gysahlStemBlockId.getInt()).setStepSound(Block.soundGrassFootstep).setHardness(0.0F).setUnlocalizedName(gysahlStemBlockName);
		GameRegistry.registerBlock(gysahlStemBlock, gysahlStemBlockName);
		LanguageRegistry.addName(gysahlStemBlock, "Gysahl Stem");

		String gysahlGreenBlockName = "item.gysahlGreenBlock";
		gysahlGreenBlock = new BlockGysahlGreen(gysahlGreenBlockId.getInt()).setStepSound(Block.soundGrassFootstep).setHardness(0.0F).setUnlocalizedName(gysahlGreenBlockName);
		GameRegistry.registerBlock(gysahlGreenBlock, gysahlGreenBlockName);
		LanguageRegistry.addName(gysahlGreenBlock, "Gysahl Green");
		
		String strawBlockName = "item.strawBlock";
		strawBlock = new BlockStraw(strawBlockId.getInt()).setStepSound(Block.soundGrassFootstep).setHardness(0.0F).setUnlocalizedName(strawBlockName);
		GameRegistry.registerBlock(strawBlock, strawBlockName);
		LanguageRegistry.addName(strawBlock, "Straw");
	}

	private void addSmeltings()
	{
		GameRegistry.addSmelting(chocoboLegRawItem.itemID, new ItemStack(chocoboLegCookedItem), 0.1F);
		GameRegistry.addSmelting(gysahlPicklesRawItem.itemID, new ItemStack(gysahlPicklesItem), 0.1F);
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

		// transforming vanilla saddle into chocobo saddle
		GameRegistry.addShapelessRecipe(new ItemStack(chocoboSaddleItem, 1), new Object[]
		{
			Item.saddle,
		    new ItemStack(chocoboFeatherItem, 1)
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
		
		// gysahl pickles
		GameRegistry.addShapelessRecipe(new ItemStack(gysahlPicklesRawItem, 2), new Object[]
		{
			new ItemStack(gysahlGreenBlock, 1),
			new ItemStack(Item.sugar, 1)
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
		
		GameRegistry.addRecipe(new ItemStack(strawBlock, 8), new Object[]
		{
			"WW",
			Character.valueOf('W'), Item.wheat
		});
		                                                        		
		// recipes for vanilla items with chocobo feathers
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
	
	private void addGrassDrops()
	{
		MinecraftForge.addGrassSeed(new ItemStack(gysahlSeedsItem), gysahlSeedGrassDropWeight);
	}

	private void registerChocobos()
	{
		this.registerChocoboEntityClass(EntityChicobo.class, "Chicobo", this.getRGBInt(170, 70, 250), this.getRGBInt(250, 250, 0), "Chicobo", Constants.CHOCOBO_CHICOBO_ID);
		this.registerChocoboEntityClass(EntityChocoboYellow.class, "ChocoboYellow", this.getRGBInt(250, 250, 50), this.getRGBInt(180, 150, 1), "Yellow Chocobo", Constants.CHOCOBO_YELLOW_ID);
		this.registerChocoboEntityClass(EntityChocoboGreen.class, "ChocoboGreen", this.getRGBInt(50, 250, 50), this.getRGBInt(1, 100, 1), "Green Chocobo", Constants.CHOCOBO_GREEN_ID);
		this.registerChocoboEntityClass(EntityChocoboBlue.class, "ChocoboBlue", this.getRGBInt(100, 100, 250), this.getRGBInt(100, 100, 200), "Blue Chocobo", Constants.CHOCOBO_BLUE_ID);
		this.registerChocoboEntityClass(EntityChocoboWhite.class, "ChocoboWhite", this.getRGBInt(240, 240, 240), this.getRGBInt(252, 252, 252), "White Chocobo", Constants.CHOCOBO_WHITE_ID);
		this.registerChocoboEntityClass(EntityChocoboBlack.class, "ChocoboBlack", this.getRGBInt(1, 1, 1), this.getRGBInt(150, 150, 150), "Black Chocobo", Constants.CHOCOBO_BLACK_ID);
		this.registerChocoboEntityClass(EntityChocoboGold.class, "ChocoboGold", this.getRGBInt(250, 130, 70), this.getRGBInt(111, 90, 33), "Gold Chocobo", Constants.CHOCOBO_GOLD_ID);
		this.registerChocoboEntityClass(EntityChocoboPink.class, "ChocoboPink", this.getRGBInt(222, 20, 222), this.getRGBInt(250, 200, 250), "Pink Chocobo", Constants.CHOCOBO_PINK_ID);
		this.registerChocoboEntityClass(EntityChocoboRed.class, "ChocoboRed", this.getRGBInt(250, 20, 20), this.getRGBInt(100, 20, 20), "Red Chocobo", Constants.CHOCOBO_RED_ID);
		this.registerChocoboEntityClass(EntityChocoboPurple.class, "ChocoboPurple", this.getRGBInt(170, 70, 160), this.getRGBInt(60, 50, 111), "Purple Chocobo", Constants.CHOCOBO_PURPLE_ID);
	}

	@SuppressWarnings("unchecked")
	private void registerChocoboEntityClass(Class <? extends Entity> entityClass, String entityName, int eggColor, int eggDotsColor, String visibleName, int entityID)
	{
		LanguageRegistry.instance().addStringLocalization("entity." + Constants.TCC_MODID  + "." + entityName + ".name", "en_US", visibleName);
		EntityRegistry.registerModEntity(entityClass, entityName, entityID, instance, 128, 1, true);		
		EntityList.IDtoClassMapping.put(entityID, entityClass);
		EntityList.entityEggs.put(entityID, new EntityEggInfo(entityID, eggColor, eggDotsColor));
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
	
	private void addDungeonChestHooks()
	{
		if(chocopediaInDungeons)
		{
			ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(chocopediaItem),Constants.CHOCOPEDIA_DUNGEON_MIN,Constants.CHOCOPEDIA_DUNGEON_MAX,Constants.CHOCOPEDIA_DUNGEON_WEIGHT));
			ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(chocopediaItem),Constants.CHOCOPEDIA_DUNGEON_MIN,Constants.CHOCOPEDIA_DUNGEON_MAX,Constants.CHOCOPEDIA_DUNGEON_WEIGHT));
			ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(chocopediaItem),Constants.CHOCOPEDIA_DUNGEON_MIN,Constants.CHOCOPEDIA_DUNGEON_MAX,Constants.CHOCOPEDIA_DUNGEON_WEIGHT));
			ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(chocopediaItem),Constants.CHOCOPEDIA_DUNGEON_MIN,Constants.CHOCOPEDIA_DUNGEON_MAX,Constants.CHOCOPEDIA_DUNGEON_WEIGHT));
			ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(chocopediaItem),Constants.CHOCOPEDIA_DUNGEON_MIN,Constants.CHOCOPEDIA_DUNGEON_MAX,Constants.CHOCOPEDIA_DUNGEON_WEIGHT));
			ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(chocopediaItem),Constants.CHOCOPEDIA_DUNGEON_MIN,Constants.CHOCOPEDIA_DUNGEON_MAX,Constants.CHOCOPEDIA_DUNGEON_WEIGHT));
			ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(new ItemStack(chocopediaItem),Constants.CHOCOPEDIA_DUNGEON_MIN,Constants.CHOCOPEDIA_DUNGEON_MAX,Constants.CHOCOPEDIA_DUNGEON_WEIGHT));
		}
	}
}
