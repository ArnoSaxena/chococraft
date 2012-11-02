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

import chococraft.client.ClientProxyChocoCraft;
import chococraft.common.entities.EntityChicobo;
import chococraft.common.entities.colours.*;
import chococraft.common.gui.ChocoboGuiHandler;
import chococraft.common.items.*;
import chococraft.common.network.ChocoboPacketHandler;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Entity;
import net.minecraft.src.EnumCreatureType;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemSeeds;
import net.minecraft.src.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;

@Mod(modid="ChocoCraft", name="Torojimas ChocoCraft", version="2.1.3")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, 
		channels = { Constants.PCHAN_CHOCOBO },
		packetHandler = ChocoboPacketHandler.class)
public class ModChocoCraft
{	
	public static Configuration mainConfiguration;

	public static Property chocoboSaddleId;	
	public static Property gysahlSeedsId;    
	public static Property gysahlLoverlyId;
	public static Property gysahlGoldenId;
	public static Property gysahlPinkId;
	public static Property gysahlRedId;
	public static Property gysahlChibiId;
	public static Property chocoboCakeId;
	public static Property chocoboLegRawId;
	public static Property chocoboLegCookedId;
	public static Property chocoboFeatherId;
	public static Property chocoboSaddleBagsId;
	public static Property chocoboPackBagsId;
	public static Property chocoboWhistleId;
	public static Property chocopediaId;
	public static Property netherChocoboEggId;
	public static Property chocoDisguiseHelmetId;
	public static Property chocoDisguisePlateId;
	public static Property chocoDisguiseLegsId;
	public static Property chocoDisguiseBootsId;	
	public static Property gysahlGreenBlockId;
	public static Property gysahlStemBlockId;

	public static Item chocoboSaddleItem;
	public static Item gysahlSeedsItem;
	public static Item gysahlLoverlyItem;
	public static Item gysahlGoldenItem;
	public static Item gysahlPinkItem;
	public static Item gysahlRedItem;
	public static Item gysahlChibiItem;
	public static Item chocoboCakeItem;
	public static Item chocoboLegRawItem;
	public static Item chocoboLegCookedItem;
	public static Item chocoboFeatherItem;
	public static Item chocoboSaddleBagsItem;
	public static Item chocoboPackBagsItem;
	public static Item chocoboWhistleItem;
	public static Item chocopediaItem;
	public static Item netherChocoboEggItem;
	public static Item chocoDisguiseHelmetItem;
	public static Item chocoDisguisePlateItem;
	public static Item chocoDisguiseLegsItem;
	public static Item chocoDisguiseBootsItem;

	public static Block gysahlStemBlock;
	public static Block gysahlGreenBlock;

	// setup
	public static boolean chocoboWingFlutter;
	public static int genderMaleChance;
	public static boolean showChocoboNames;
	public static int featherDropChance;
	public static int featherDelayRandom;
	public static int featherDelayStatic;
	public static int yellowSpawnRate;
	public static int yellowSpawnMin;
	public static int yellowSpawnMax;

	public static BiomeGenBase[] yellowSpawnBiomes = {
		BiomeGenBase.beach, 
		BiomeGenBase.desert,
		BiomeGenBase.desertHills, 
		BiomeGenBase.extremeHills,
		BiomeGenBase.extremeHillsEdge,
		BiomeGenBase.forest,
		BiomeGenBase.forestHills,
		BiomeGenBase.jungle,
		BiomeGenBase.jungleHills,
		BiomeGenBase.mushroomIslandShore,
		BiomeGenBase.plains,
		BiomeGenBase.river,
		BiomeGenBase.swampland,
		BiomeGenBase.taiga,
		BiomeGenBase.taigaHills
	};

	public static BiomeGenBase[] chocoboPurpleSpawnBiomes = {
		BiomeGenBase.hell
	};

	@Instance("ChocoCraft")
	public static ModChocoCraft instance;

	@SidedProxy(clientSide = "chococraft.client.ClientProxyChocoCraft", serverSide = "chococraft.common.CommonProxyChocoCraft")
	public static CommonProxyChocoCraft proxy;

	@Init
	public void loadChocoCraft(FMLInitializationEvent loadEvent)
	{
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

		proxy.registerRenderThings();

		NetworkRegistry.instance().registerGuiHandler(this, new ChocoboGuiHandler());
	}

	@PreInit
	public void preLoadChocoCraft(FMLPreInitializationEvent preInitEvent)
	{
		mainConfiguration = new Configuration(new File(preInitEvent.getModConfigurationDirectory(), "chococraft/main.conf"));

		chocoboSaddleId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "chocoboSaddleItem.id", Constants.CHOCOBO_SADDLE_ID);
		gysahlSeedsId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "gysahlSeedsItem.id", Constants.GYSAHL_SEEDS_ID);
		gysahlLoverlyId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "gysahlLoverlyItem.id", Constants.GYSAHL_LOVERLY_ID);
		gysahlGoldenId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "gysahlGoldenItem.id", Constants.GYSAHL_GOLDEN_ID);
		gysahlPinkId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "gysahlPinkItem.id", Constants.GYSAHL_PINK_ID);
		gysahlRedId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "gysahlRedItem.id", Constants.GYSAHL_RED_ID);
		gysahlChibiId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "gysahlChibiItem.id", Constants.GYSAHL_CHIBI_ID);
		chocoboCakeId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "chocoboCakeItem.id", Constants.CHOCOBO_CAKE_ID);
		chocoboLegRawId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "chocoboLegRawItem.id", Constants.CHOCOBO_LEG_RAW_ID);
		chocoboLegCookedId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "chocoboLegCookedItem.id", Constants.CHOCOBO_LEG_COOKED_ID);
		chocoboFeatherId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "chocoboFeatherItem.id", Constants.CHOCOBO_FEATHER_ID);
		chocoboSaddleBagsId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "chocoboSaddleBagsItem.id", Constants.CHOCOBO_SADDLE_BAGS_ID);
		chocoboPackBagsId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "chocoboPackBagsItem.id", Constants.CHOCOBO_PACK_BAGS_ID);
		chocoboWhistleId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "chocoboWhistleItem.id", Constants.CHOCOBO_WHISTLE_ID);
		chocopediaId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "chocopediaItem.id", Constants.CHOCOPEDIA_ID);
		netherChocoboEggId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "netherChocoboEggItem.id", Constants.NETHER_CHOCOBO_EGG_ID);
		chocoDisguiseHelmetId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "chocoDisguiseHelmetItem.id", Constants.CHOCO_DISGUISE_HELMET_ID);
		chocoDisguisePlateId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "chocoDisguisePlateItem.id", Constants.CHOCO_DISGUISE_PLATE_ID);
		chocoDisguiseLegsId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "chocoDisguiseLegsItem.id", Constants.CHOCO_DISGUISE_LEGS_ID);
		chocoDisguiseBootsId = mainConfiguration.get(Configuration.CATEGORY_ITEM, "chocoDisguiseBootsItem.id", Constants.CHOCO_DISGUISE_BOOTS_ID);

		gysahlGreenBlockId = mainConfiguration.getBlock("gysahlGreenBlock.id", Constants.GYSAHL_GREEN_BLOCK_ID);
		gysahlStemBlockId = mainConfiguration.getBlock("gysahlStemBlock.id", Constants.GYSAHL_STEM_BLOCK_ID);

		chocoboWingFlutter = Constants.DEFAULT_CHOCOBO_WING_FLUTTER;
		genderMaleChance = Constants.DEFAULT_GENDER_MALE_CHANCE;
		showChocoboNames = Constants.DEFAULT_SHOW_CHOCOBO_NAMES;
		featherDropChance = Constants.DEFAULT_FEATHER_DROP_CHANCE;
		featherDelayRandom = Constants.DEFAULT_FEATHER_DELAY_RANDOM;
		featherDelayStatic = Constants.DEFAULT_FEATHER_DELAY_STATIC;
		yellowSpawnRate = Constants.DEFAULT_YELLOW_SPAWN_RATE;
		yellowSpawnMin = Constants.DEFAULT_YELLOW_SPAWN_MIN;
		yellowSpawnMax = Constants.DEFAULT_YELLOW_SPAWN_MAX;
    	ChocoboConfig.readConfigFile();

		MinecraftForge.EVENT_BUS.register(new ChocoCraftEvent());
	}

	@PostInit
	public void postLoadChocoCraft(FMLPostInitializationEvent postInitEvent)
	{
	}

	// initialising methods
	private void createItemInstances()
	{
		// chocobo saddle
		chocoboSaddleItem = (new ChocoboItem(Integer.parseInt(chocoboSaddleId.value))).setItemName("chocoboSaddle").setMaxStackSize(5);
		chocoboSaddleItem.setIconIndex(0);
		LanguageRegistry.addName(chocoboSaddleItem, "Chocobo Saddle");		
		chocoboSaddleItem.setCreativeTab(CreativeTabs.tabTransport);

		// Gysahl seeds
		gysahlSeedsItem = (new ItemSeeds(Integer.parseInt(gysahlSeedsId.value), Integer.parseInt(gysahlStemBlockId.value), Block.tilledField.blockID)).setItemName("gysahlSeeds");
		gysahlSeedsItem.setTextureFile(Constants.CHOCOBO_ITEM_TEXTURES);
		gysahlSeedsItem.setIconIndex(1);
		LanguageRegistry.addName(gysahlSeedsItem, "Gysahl Seeds");
		gysahlSeedsItem.setCreativeTab(CreativeTabs.tabMaterials);

		// Loverly Gysahl
		gysahlLoverlyItem = (new ChocoboItem(Integer.parseInt(gysahlLoverlyId.value))).setItemName("gysahlLoverly").setMaxStackSize(64);
		gysahlLoverlyItem.setIconIndex(2);
		LanguageRegistry.addName(gysahlLoverlyItem, "Loverly Gysahl");
		gysahlLoverlyItem.setCreativeTab(CreativeTabs.tabMisc);

		// Golden Gysahl
		gysahlGoldenItem = (new ChocoboItem(Integer.parseInt(gysahlGoldenId.value))).setItemName("gysahlGolden").setMaxStackSize(64);
		gysahlGoldenItem.setIconIndex(3);
		LanguageRegistry.addName(gysahlGoldenItem, "Golden Gysahl");
		gysahlGoldenItem.setCreativeTab(CreativeTabs.tabMisc);

		// Pink Gysahl
		gysahlPinkItem = (new ChocoboItem(Integer.parseInt(gysahlPinkId.value))).setItemName("gysahlPink").setMaxStackSize(64);
		gysahlPinkItem.setIconIndex(4);
		LanguageRegistry.addName(gysahlPinkItem, "Pink Gysahl");
		gysahlPinkItem.setCreativeTab(CreativeTabs.tabMisc);

		// Red Gysahl
		gysahlRedItem = (new ChocoboItem(Integer.parseInt(gysahlRedId.value))).setItemName("gysahlRed").setMaxStackSize(64);
		gysahlRedItem.setIconIndex(5);
		LanguageRegistry.addName(gysahlRedItem, "Red Gysahl");
		gysahlRedItem.setCreativeTab(CreativeTabs.tabMisc);

		// Chibi Gysahl
		gysahlChibiItem = (new ChocoboItem(Integer.parseInt(gysahlChibiId.value))).setItemName("gysahlChibi").setMaxStackSize(64);
		gysahlChibiItem.setIconIndex(6);
		LanguageRegistry.addName(gysahlChibiItem, "Chibi Gysahl");
		gysahlChibiItem.setCreativeTab(CreativeTabs.tabMisc);

		// Chocobo Cake
		chocoboCakeItem = (new ChocoboItem(Integer.parseInt(chocoboCakeId.value))).setItemName("chocoboCake").setMaxStackSize(8);
		chocoboCakeItem.setIconIndex(7);
		LanguageRegistry.addName(chocoboCakeItem, "Chocobo Cake");
		chocoboCakeItem.setCreativeTab(CreativeTabs.tabMisc);

		// Chocobo Feather
		chocoboFeatherItem = (new ChocoboItem(Integer.parseInt(chocoboFeatherId.value))).setItemName("chocoboFeather").setMaxStackSize(64);
		chocoboFeatherItem.setIconIndex(10);
		LanguageRegistry.addName(chocoboFeatherItem, "Chocobo Feather");
		chocoboFeatherItem.setCreativeTab(CreativeTabs.tabMaterials);

		// Chocobo Saddle Bags
		chocoboSaddleBagsItem = (new ChocoboItem(Integer.parseInt(chocoboSaddleBagsId.value))).setItemName("chocoboSaddleBags").setMaxStackSize(8);
		chocoboSaddleBagsItem.setIconIndex(11);
		LanguageRegistry.addName(chocoboSaddleBagsItem, "Chocobo Saddle Bags");
		chocoboSaddleBagsItem.setCreativeTab(CreativeTabs.tabTransport);

		// Chocobo Pack Bags
		chocoboPackBagsItem = (new ChocoboItem(Integer.parseInt(chocoboPackBagsId.value))).setItemName("chocoboPackBags").setMaxStackSize(8);
		chocoboPackBagsItem.setIconIndex(12);
		LanguageRegistry.addName(chocoboPackBagsItem, "Chocobo Pack Bags");
		chocoboPackBagsItem.setCreativeTab(CreativeTabs.tabTransport);

		// Chocob Whistle
		chocoboWhistleItem = (new ChocoboItem(Integer.parseInt(chocoboWhistleId.value))).setItemName("chocoboWhistle").setMaxStackSize(64);
		chocoboWhistleItem.setIconIndex(13);
		LanguageRegistry.addName(chocoboWhistleItem, "Chocobo Whistle");
		chocoboWhistleItem.setCreativeTab(CreativeTabs.tabTools);

		// Chocopedia
		chocopediaItem = (new ChocoboItem(Integer.parseInt(chocopediaId.value))).setItemName("chocopedia").setMaxStackSize(1);
		chocopediaItem.setIconIndex(14);
		LanguageRegistry.addName(chocopediaItem, "Chocopedia");
		chocopediaItem.setCreativeTab(CreativeTabs.tabTools);

		//        // Nether Chocobo Egg
		//        netherChocoboEggItem = (new ChocoboItem(Integer.parseInt(netherChocoboEggId.value))).setItemName("netherChocoboEgg").setMaxStackSize(8);
		//    	netherChocoboEggItem.setIconIndex(15);
		//        LanguageRegistry.addName(netherChocoboEggItem, "Nether Chocobo Egg");
		//        netherChocoboEggItem.setTabToDisplayOn(CreativeTabs.tabMisc);

		// Armour
		// Cocodisguise Helmet
		chocoDisguiseHelmetItem = (new ChocoboItem(Integer.parseInt(chocoDisguiseHelmetId.value))).setItemName("chocoDisguiseHelmet").setMaxStackSize(1);
		chocoDisguiseHelmetItem.setIconIndex(17);
		LanguageRegistry.addName(chocoDisguiseHelmetItem, "Chocodisguise Helmet");
		chocoDisguiseHelmetItem.setCreativeTab(CreativeTabs.tabTools);

		// Chocodisguise Plate
		chocoDisguisePlateItem = (new ChocoboItem(Integer.parseInt(chocoDisguisePlateId.value))).setItemName("chocoDisguisePlate").setMaxStackSize(1);
		chocoDisguisePlateItem.setIconIndex(18);
		LanguageRegistry.addName(chocoDisguisePlateItem, "Chocodisguise Plate");
		chocoDisguisePlateItem.setCreativeTab(CreativeTabs.tabTools);

		// Chocodisguise Legs
		chocoDisguiseLegsItem = (new ChocoboItem(Integer.parseInt(chocoDisguiseLegsId.value))).setItemName("chocoDisguiseLegs").setMaxStackSize(1);
		chocoDisguiseLegsItem.setIconIndex(19);
		LanguageRegistry.addName(chocoDisguiseLegsItem, "Chocodisguise Legs");
		chocoDisguiseLegsItem.setCreativeTab(CreativeTabs.tabTools);

		// Chocodisguise Boots
		chocoDisguiseBootsItem = (new ChocoboItem(Integer.parseInt(chocoDisguiseBootsId.value))).setItemName("chocoDisguiseBoots").setMaxStackSize(1);    	
		chocoDisguiseBootsItem.setIconIndex(20);
		LanguageRegistry.addName(chocoDisguiseBootsItem, "Chocodisguise Boots");
		chocoDisguiseBootsItem.setCreativeTab(CreativeTabs.tabTools);

		// food items
		// Raw Chocobo Leg
		chocoboLegRawItem = (new ItemFood(Integer.parseInt(chocoboLegRawId.value), 4, true)).setItemName("chocoboLegRaw").setMaxStackSize(8);
		chocoboLegRawItem.setTextureFile(Constants.CHOCOBO_ITEM_TEXTURES);
		chocoboLegRawItem.setIconIndex(8);
		LanguageRegistry.addName(chocoboLegRawItem, "Raw Chocobo Leg");

		// Cooked Chocobo Leg
		chocoboLegCookedItem = (new ItemFood(Integer.parseInt(chocoboLegCookedId.value), 8, false)).setItemName("chocoboLegCooked").setMaxStackSize(8);
		chocoboLegCookedItem.setTextureFile(Constants.CHOCOBO_ITEM_TEXTURES);
		chocoboLegCookedItem.setIconIndex(9);
		LanguageRegistry.addName(chocoboLegCookedItem, "Cooked Chocobo Leg");
	}

	private void createBlockInstances()
	{
		gysahlStemBlock = new BlockGysahlStem(Integer.parseInt(gysahlStemBlockId.value), 35).setStepSound(Block.soundGrassFootstep).setHardness(0.0F).setBlockName("gysahlStemBlock");
		GameRegistry.registerBlock(gysahlStemBlock);
		LanguageRegistry.addName(gysahlStemBlock, "Gysahl Stem");

		gysahlGreenBlock = new BlockGysahlGreen(Integer.parseInt(gysahlGreenBlockId.value), 36).setStepSound(Block.soundGrassFootstep).setHardness(0.0F).setBlockName("gysahlGreenBlock");
		GameRegistry.registerBlock(gysahlGreenBlock);        
		LanguageRegistry.addName(gysahlGreenBlock, "Gysahl Green");
	}

	private void addSmeltings()
	{
		GameRegistry.addSmelting(chocoboLegRawItem.shiftedIndex, new ItemStack(chocoboLegCookedItem), 0.1F);
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

		// loverly ghysal
		GameRegistry.addRecipe(new ItemStack(gysahlLoverlyItem, 1), new Object[]
		{
			"RY", 
			"X ", 
			Character.valueOf('X'), gysahlGreenBlock, 
			Character.valueOf('Y'), chocoboFeatherItem, 
			Character.valueOf('R'), Block.plantRed
		});

		// golden ghysal
		GameRegistry.addRecipe(new ItemStack(gysahlGoldenItem, 1), new Object[]
		{
			"FGF", 
			"GXG", 
			"FGF", 
			Character.valueOf('X'), gysahlGreenBlock, 
			Character.valueOf('F'), chocoboFeatherItem, 
			Character.valueOf('G'), Item.goldNugget
		});

		// chibi ghysal
		GameRegistry.addRecipe(new ItemStack(gysahlChibiItem, 1), new Object[]
		{
			" R ", 
			"RGR", 
			" R ", 
			Character.valueOf('R'), Item.redstone, 
			Character.valueOf('G'), gysahlGreenBlock
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

		// ghysal seeds
		GameRegistry.addShapelessRecipe(new ItemStack(gysahlSeedsItem, 3), new Object[]
		{
			new ItemStack(gysahlGreenBlock, 1)
		});

		// chocobo cake
		GameRegistry.addRecipe(new ItemStack(chocoboCakeItem, 1), new Object[]
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

		// pink ghysal
		GameRegistry.addShapelessRecipe(new ItemStack(gysahlPinkItem, 1), new Object[]
		{
			new ItemStack(gysahlGreenBlock, 1), 
			new ItemStack(Item.dyePowder, 1, 9)
		});

		// red ghysal
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
		//this.registerChocoboEntityClass(EntityChocoboPurple.class, "ChocoboPurple", this.getRGBInt(170, 70, 160), this.getRGBInt(60, 50, 111), "Purple Chocobo");
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
		EntityRegistry.addSpawn(EntityChocoboYellow.class, yellowSpawnRate, yellowSpawnMin, yellowSpawnMax, EnumCreatureType.creature, yellowSpawnBiomes);
		//EntityRegistry.addSpawn(EntityChocoboPurple.class, 5, 5, 8, EnumCreatureType.creature, chocoboPurpleSpawnBiomes);
	}
}
