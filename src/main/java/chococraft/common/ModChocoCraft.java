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
import chococraft.common.config.ChocoboConfig;
import chococraft.common.entities.EntityChicobo;
import chococraft.common.entities.colours.*;
import chococraft.common.gui.ChocoboGuiHandler;
import chococraft.common.items.*;
import chococraft.common.network.PacketRegistry;
import chococraft.common.tick.ServerSpawnTickHandler;
import chococraft.common.worldgen.WorldGenGysahls;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

@Mod(modid=Constants.TCC_MODID, name=Constants.TCC_NAME, version=Constants.TCC_VERSION)
public class ModChocoCraft
{	
	public static Configuration mainConfiguration;

	//public static CreativeTabs chocoboCreativeItems;

	public static boolean debugMode = false;

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
		
		this.createItemInstances();
		this.createBlockInstances();
	}
	
	@EventHandler
	public void loadChocoCraft(FMLInitializationEvent loadEvent)
	{
		//this.createCreativeTab();
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
		
		GameRegistry.registerWorldGenerator(new WorldGenGysahls(), 5);//TODO check how weights work

		FMLCommonHandler.instance().bus().register(new ChocoboPlayerTracker());
				
		proxy.registerRenderThings();

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new ChocoboGuiHandler());

		FMLCommonHandler.instance().bus().register(new ServerSpawnTickHandler());
	}

	@EventHandler
	public void postLoadChocoCraft(FMLPostInitializationEvent postInitEvent) {}

//	// initialising methods
//	private void createCreativeTab()
//	{
		//chocoboCreativeItems = new ChocoboCreativeTab("Chocobo Items");
//		LanguageRegistry.instance().addStringLocalization("itemGroup." + chocoboCreativeItems.getTabLabel(), "en_US", "ChocoCraft");		
//	}

	private void createItemInstances()
	{
		// Chocopedia
		chocopediaItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_CHOCOPEDIA).setMaxStackSize(1);
		LanguageRegistry.addName(chocopediaItem, "Chocopedia");
		chocopediaItem.setCreativeTab(CreativeTabs.tabTools);
		GameRegistry.registerItem(chocopediaItem, "Chocopedia");

		// Chocobo Feather
		chocoboFeatherItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_FEATHER).setMaxStackSize(64);
		LanguageRegistry.addName(chocoboFeatherItem, "Chocobo Feather");
		chocoboFeatherItem.setCreativeTab(CreativeTabs.tabMaterials);
		GameRegistry.registerItem(chocoboFeatherItem, "Chocobo_Feather");
		
		// riding gear
		// Chocobo Saddle
		chocoboSaddleItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_SADDLE).setMaxStackSize(5);
		LanguageRegistry.addName(chocoboSaddleItem, "Chocobo Saddle");
		chocoboSaddleItem.setCreativeTab(CreativeTabs.tabTransport);
		GameRegistry.registerItem(chocoboSaddleItem, "Chocobo_Saddle");

		// Chocobo Saddle Bags
		chocoboSaddleBagsItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_SADDLEBAG).setMaxStackSize(8);
		LanguageRegistry.addName(chocoboSaddleBagsItem, "Chocobo Saddle Bags");
		chocoboSaddleBagsItem.setCreativeTab(CreativeTabs.tabTransport);
		GameRegistry.registerItem(chocoboSaddleBagsItem, "Chocobo_Saddle_Bags");

		// Chocobo Pack Bags
		chocoboPackBagsItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_PACKBAG).setMaxStackSize(8);
		LanguageRegistry.addName(chocoboPackBagsItem, "Chocobo Pack Bags");
		chocoboPackBagsItem.setCreativeTab(CreativeTabs.tabTransport);
		GameRegistry.registerItem(chocoboPackBagsItem, "Chocobo_Pack_Bags");

		// Gysahls
		// Gysahl seeds
		gysahlSeedsItem = (new ItemGysahlSeeds(gysahlStemBlock, Blocks.farmland));
		LanguageRegistry.addName(gysahlSeedsItem, "Gysahl Seeds");
		//gysahlSeedsItem.setCreativeTab(chocoboCreativeItems);
		GameRegistry.registerItem(gysahlSeedsItem, "Gysahl_Seeds");

		// Loverly Gysahl
		gysahlLoverlyItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_GY_LOVERLY).setMaxStackSize(64);
		LanguageRegistry.addName(gysahlLoverlyItem, "Loverly Gysahl");
		gysahlLoverlyItem.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(gysahlLoverlyItem, "Loverly_Gysahl");

		// Golden Gysahl
		gysahlGoldenItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_GY_GOLDEN).setMaxStackSize(64);
		LanguageRegistry.addName(gysahlGoldenItem, "Golden Gysahl");
		gysahlGoldenItem.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(gysahlGoldenItem, "Golden_Gysahl");

		// Pink Gysahl
		gysahlPinkItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_GY_PINK).setMaxStackSize(64);
		LanguageRegistry.addName(gysahlPinkItem, "Pink Gysahl");
		gysahlPinkItem.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(gysahlPinkItem, "Pink_Gysahl");

		// Red Gysahl
		gysahlRedItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_GY_RED).setMaxStackSize(64);
		LanguageRegistry.addName(gysahlRedItem, "Red Gysahl");
		gysahlRedItem.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(gysahlRedItem, "Red_Gysahl");

		// Gysahl Cake
		gysahlCakeItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_GY_CAKE).setMaxStackSize(8);
		LanguageRegistry.addName(gysahlCakeItem, "Gysahl Cake");
		gysahlCakeItem.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(gysahlCakeItem, "Gysahl Cake");

		// Chocob Whistle
		chocoboWhistleItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_WHISTLE).setMaxStackSize(64);
		LanguageRegistry.addName(chocoboWhistleItem, "Chocobo Whistle");
		chocoboWhistleItem.setCreativeTab(CreativeTabs.tabTools);
		GameRegistry.registerItem(chocoboWhistleItem, "Chocobo_Whistle");

		// Nether Chocobo Egg
		purpleChocoboEggItem = new ItemPurpleChocoboEgg();
		LanguageRegistry.addName(purpleChocoboEggItem, "Purple Chocobo Egg");
		GameRegistry.registerItem(purpleChocoboEggItem, "Purple_Chocobo_Egg");

		// Armour
		// Chocodisguise Helmet
		chocoDisguiseHelmetItem = new ChocoboItemDisguise(ChocoboArmourMaterial.CHOCOFEATHER, proxy.addArmor(Constants.KEY_DISGUISE), 0);
		chocoDisguiseHelmetItem.setUnlocalizedName(Constants.KEY_DISGUISE_HEAD);
		LanguageRegistry.addName(chocoDisguiseHelmetItem, "Chocodisguise Helmet");
		GameRegistry.registerItem(chocoDisguiseHelmetItem, "Chocodisguise_Helmet");

		// Chocodisguise Plate
		chocoDisguisePlateItem = new ChocoboItemDisguise(ChocoboArmourMaterial.CHOCOFEATHER, proxy.addArmor(Constants.KEY_DISGUISE), 1);
		chocoDisguisePlateItem.setUnlocalizedName(Constants.KEY_DISGUISE_BODY);
		LanguageRegistry.addName(chocoDisguisePlateItem, "Chocodisguise Body");
		GameRegistry.registerItem(chocoDisguisePlateItem, "Chocodisguise_Body");

		// Chocodisguise Legs
		chocoDisguiseLegsItem = new ChocoboItemDisguise(ChocoboArmourMaterial.CHOCOFEATHER, proxy.addArmor(Constants.KEY_DISGUISE), 2);
		chocoDisguiseLegsItem.setUnlocalizedName(Constants.KEY_DISGUISE_LEGS);
		LanguageRegistry.addName(chocoDisguiseLegsItem, "Chocodisguise Legs");
		GameRegistry.registerItem(chocoDisguiseLegsItem, "Chocodisguise_Legs");

		// Chocodisguise Boots
		chocoDisguiseBootsItem = new ChocoboItemDisguise(ChocoboArmourMaterial.CHOCOFEATHER, proxy.addArmor(Constants.KEY_DISGUISE), 3);
		chocoDisguiseBootsItem.setUnlocalizedName(Constants.KEY_DISGUISE_BOOTS);
		LanguageRegistry.addName(chocoDisguiseBootsItem, "Chocodisguise Boots");
		GameRegistry.registerItem(chocoDisguiseBootsItem, "Chocodisguise_Boots");

		// food items
		// Raw Chocobo Leg
		chocoboLegRawItem = new ChocoboItemFood(4, true);
		chocoboLegRawItem.setUnlocalizedName(Constants.KEY_LEG_RAW);
		LanguageRegistry.addName(chocoboLegRawItem, "Raw Chocobo Leg");
		GameRegistry.registerItem(chocoboLegRawItem, "Raw_Chocobo_Leg");

		// Cooked Chocobo Leg
		chocoboLegCookedItem = new ChocoboItemFood(8, false);
		chocoboLegCookedItem.setUnlocalizedName(Constants.KEY_LEG_COOKED);
		LanguageRegistry.addName(chocoboLegCookedItem, "Cooked Chocobo Leg");
		GameRegistry.registerItem(chocoboLegCookedItem, "Cooked_Chocobo_Leg");
		
		// Gysahl Pickles
		gysahlPicklesRawItem = new ChocoboItem();
		gysahlPicklesRawItem.setUnlocalizedName(Constants.KEY_GY_PICKLES_RAW).setMaxStackSize(64);
		LanguageRegistry.addName(gysahlPicklesRawItem, "Gysahl Raw Pickles");
		gysahlPicklesRawItem.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(gysahlPicklesRawItem, "Gysahl_Raw_Pickles");

		gysahlPicklesItem = new ChocoboItemFood(2, false);
		gysahlPicklesItem.setUnlocalizedName(Constants.KEY_GY_PICKLES);
		LanguageRegistry.addName(gysahlPicklesItem, "Gysahl Pickles");
		gysahlPicklesItem.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(gysahlPicklesItem, "Gysahl_Pickles");
	}

	private void createBlockInstances()
	{
		String gysahlStemBlockName = "item.gysahlStemBlock";

		gysahlStemBlock = new BlockGysahlStem().setStepSound(Block.soundTypeGrass).setHardness(0.0F).setBlockName(gysahlStemBlockName);
		GameRegistry.registerBlock(gysahlStemBlock, gysahlStemBlockName);
		LanguageRegistry.addName(gysahlStemBlock, "Gysahl Stem");

		String gysahlGreenBlockName = "item.gysahlGreenBlock";
		gysahlGreenBlock = new BlockGysahlGreen().setStepSound(Block.soundTypeGrass).setHardness(0.0F).setBlockName(gysahlGreenBlockName);
		GameRegistry.registerBlock(gysahlGreenBlock, gysahlGreenBlockName);
		LanguageRegistry.addName(gysahlGreenBlock, "Gysahl Green");
		
		String strawBlockName = "item.strawBlock";
		strawBlock = new BlockStraw().setStepSound(Block.soundTypeGrass).setHardness(0.0F).setBlockName(strawBlockName);
		GameRegistry.registerBlock(strawBlock, strawBlockName);
		LanguageRegistry.addName(strawBlock, "Straw");
	}

	private void addSmeltings()
	{
		GameRegistry.addSmelting(chocoboLegRawItem, new ItemStack(chocoboLegCookedItem), 0.1F);
		GameRegistry.addSmelting(gysahlPicklesRawItem, new ItemStack(gysahlPicklesItem), 0.1F);
	}

	private void addRecipes()
	{
		// chocobo saddle
		GameRegistry.addRecipe(new ItemStack(chocoboSaddleItem, 1), "-X-",
				" Y ",
				'X', Items.leather,
				'Y', chocoboFeatherItem,
				'-', Items.string);
		
		// transforming vanilla saddle into chocobo saddle
		GameRegistry.addShapelessRecipe(new ItemStack(chocoboSaddleItem, 1), Items.saddle,
				new ItemStack(chocoboFeatherItem, 1));

		// chocobo saddle bag
		GameRegistry.addRecipe(new ItemStack(chocoboSaddleBagsItem, 1), " Y ",
				"X X",
				" X ",
				'X', Items.leather,
				'Y', chocoboFeatherItem);

		// chocobo pack bag
		GameRegistry.addRecipe(new ItemStack(chocoboPackBagsItem, 1), "-Y-",
				"C C",
				"-X-",
				'C', Blocks.wool,
				'X', Items.leather,
				'Y', chocoboFeatherItem,
				'-', Items.string);

		// chocopedia
		GameRegistry.addRecipe(new ItemStack(chocopediaItem, 1), "FGF",
				"IBI",
				"FLF",
				'B', Items.book,
				'F', chocoboFeatherItem,
				'I', new ItemStack(Items.dye, 1, 0),
				'L', new ItemStack(Items.dye, 1, 4),
				'G', Items.gold_nugget);

		// chocobo whistle
		GameRegistry.addShapelessRecipe(new ItemStack(chocoboWhistleItem, 1), chocoboFeatherItem,
				new ItemStack(Items.gold_ingot, 1));

		// gysahl seeds
		GameRegistry.addShapelessRecipe(new ItemStack(gysahlSeedsItem, 3), new ItemStack(gysahlGreenBlock, 1));

		// gysahl cake
		GameRegistry.addRecipe(new ItemStack(gysahlCakeItem, 1), "MGM",
				"SES",
				"WGW",
				'G', gysahlGreenBlock,
				'M', Items.milk_bucket,
				'S', Items.sugar,
				'W', Items.wheat,
				'E', Items.egg);
		
		// gysahl pickles
		GameRegistry.addShapelessRecipe(new ItemStack(gysahlPicklesRawItem, 2), new ItemStack(gysahlGreenBlock, 1),
				new ItemStack(Items.sugar, 1));

		// pink gysahl
		GameRegistry.addShapelessRecipe(new ItemStack(gysahlPinkItem, 1), new ItemStack(gysahlGreenBlock, 1),
				new ItemStack(Items.dye, 1, 9));

		// red gysahl
		GameRegistry.addShapelessRecipe(new ItemStack(gysahlRedItem, 1), new ItemStack(gysahlGreenBlock, 1),
				new ItemStack(Items.dye, 1, 1));

		// chocobo disguise
		GameRegistry.addRecipe(new ItemStack(chocoDisguiseHelmetItem, 1), "YYY",
				"Y Y",
				'Y', chocoboFeatherItem);

		GameRegistry.addRecipe(new ItemStack(chocoDisguisePlateItem, 1), "Y Y",
				"YYY",
				"YYY",
				'Y', chocoboFeatherItem);

		GameRegistry.addRecipe(new ItemStack(chocoDisguiseLegsItem, 1), "YYY",
				"Y Y",
				"Y Y",
				'Y', chocoboFeatherItem);

		GameRegistry.addRecipe(new ItemStack(chocoDisguiseBootsItem, 1), "Y Y",
				"Y Y",
				'Y', chocoboFeatherItem);
		
		GameRegistry.addRecipe(new ItemStack(strawBlock, 8), "WW",
				'W', Items.wheat);
		                                                        		
		// alternative recipes for vanilla items with chocobo feathers
		// arrows
		GameRegistry.addRecipe(new ItemStack(Items.arrow, 4), " F ",
				" S ",
				" Y ",
				'F', Items.flint,
				'S', Items.stick,
				'Y', chocoboFeatherItem);

		// writable Book
		GameRegistry.addShapelessRecipe(new ItemStack(Items.writable_book, 1), new ItemStack(Items.book, 1),
				new ItemStack(Items.dye, 1, 0),
				new ItemStack(chocoboFeatherItem, 1));

		// leash (lead)
		GameRegistry.addRecipe(new ItemStack(Items.lead, 2), "SS ",
				"SF ",
				"  S",
				'S', Items.string,
				'F', chocoboFeatherItem);
		
	}
	
	private void addGrassDrops()
	{
		MinecraftForge.addGrassSeed(new ItemStack(gysahlSeedsItem), gysahlSeedGrassDropWeight);
	}

	private void registerChocobos()
	{
		this.registerChocoboEntityClass(EntityChicobo.class, "Chicobo", this.getRGBInt(170, 70, 250), this.getRGBInt(250, 250, 0));
		this.registerChocoboEntityClass(EntityChocoboYellow.class, "ChocoboYellow", this.getRGBInt(250, 250, 50), this.getRGBInt(180, 150, 1));
		this.registerChocoboEntityClass(EntityChocoboGreen.class, "ChocoboGreen", this.getRGBInt(50, 250, 50), this.getRGBInt(1, 100, 1));
		this.registerChocoboEntityClass(EntityChocoboBlue.class, "ChocoboBlue", this.getRGBInt(100, 100, 250), this.getRGBInt(100, 100, 200));
		this.registerChocoboEntityClass(EntityChocoboWhite.class, "ChocoboWhite", this.getRGBInt(240, 240, 240), this.getRGBInt(252, 252, 252));
		this.registerChocoboEntityClass(EntityChocoboBlack.class, "ChocoboBlack", this.getRGBInt(1, 1, 1), this.getRGBInt(150, 150, 150));
		this.registerChocoboEntityClass(EntityChocoboGold.class, "ChocoboGold", this.getRGBInt(250, 130, 70), this.getRGBInt(111, 90, 33));
		this.registerChocoboEntityClass(EntityChocoboPink.class, "ChocoboPink", this.getRGBInt(222, 20, 222), this.getRGBInt(250, 200, 250));
		this.registerChocoboEntityClass(EntityChocoboRed.class, "ChocoboRed", this.getRGBInt(250, 20, 20), this.getRGBInt(100, 20, 20));
		this.registerChocoboEntityClass(EntityChocoboPurple.class, "ChocoboPurple", this.getRGBInt(170, 70, 160), this.getRGBInt(60, 50, 111));
	}

	private void registerChocoboEntityClass(Class <? extends Entity> entityClass, String entityName, int eggColor, int eggDotsColor)
	{
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, EntityRegistry.findGlobalUniqueEntityId(), eggColor, eggDotsColor);
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
