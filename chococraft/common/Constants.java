// <copyright file="Constants.java">
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
// <summary>Container class for all global used constants</summary>

package chococraft.common;

public class Constants
{
	public final static String TCC_VERSION = "2.3.5";
	public final static String TCC_MODID = "ChocoCraft";
	public final static String TCC_NAME = "Torojimas ChocoCraft";

	// default item id's (reserved 22601-22650)
	public static int CHOCOBO_SADDLE_ID          = 22601;
	public static int GYSAHL_SEEDS_ID            = 22602;
	public static int GYSAHL_LOVERLY_ID          = 22603;
	public static int GYSAHL_GOLDEN_ID           = 22604;
	public static int GYSAHL_PINK_ID             = 22605;
	public static int GYSAHL_RED_ID              = 22606;
	public static int GYSAHL_CHIBI_ID            = 22607;
	public static int GYSAHL_CAKE_ID             = 22608;
	public static int CHOCOBO_LEG_RAW_ID         = 22609;
	public static int CHOCOBO_LEG_COOKED_ID      = 22610;
	public static int CHOCOBO_FEATHER_ID         = 22611;
	public static int CHOCOBO_SADDLE_BAGS_ID     = 22612;
	public static int CHOCOBO_PACK_BAGS_ID       = 22613;
	public static int CHOCOBO_WHISTLE_ID         = 22614;
	public static int CHOCOPEDIA_ID              = 22615;
	public static int NETHER_CHOCOBO_EGG_ID      = 22616;
	public static int CHOCO_DISGUISE_HELMET_ID   = 22617;
	public static int CHOCO_DISGUISE_PLATE_ID    = 22618;
	public static int CHOCO_DISGUISE_LEGS_ID     = 22619;
	public static int CHOCO_DISGUISE_BOOTS_ID    = 22620;
	
	// default (flower) block id's (reserved 1551 to 1555)
	public static int GYSAHL_GREEN_BLOCK_ID      = 1551;
	public static int GYSAHL_STEM_BLOCK_ID       = 1552;
	public static int STRAW_BLOCK_ID             = 1553;
	
	// resource folder
	public static String CHOCOBO_RESOURCES_FOLDER = "/resources";

	// sound folder
	public static String CHOCOBO_SOUND_FOLDER = CHOCOBO_RESOURCES_FOLDER + "/sounds";
	
	// armour folder
	public static String CHOCOBO_ARMOUR_FOLDER = CHOCOBO_RESOURCES_FOLDER + "/armor";	

	// texture files
	public static String CHOCOBO_ITEM_TEXTURES = CHOCOBO_RESOURCES_FOLDER + "/chocoboItemTextures.png";
	public static String CHOCOBO_ARMOUR_TEXTURES_1 = CHOCOBO_ARMOUR_FOLDER + "/chocoDisguise_1.png";
	public static String CHOCOBO_ARMOUR_TEXTURES_2 = CHOCOBO_ARMOUR_FOLDER + "/chocoDisguise_2.png";
	public static String CHOCOBO_ENTITY_TEXTURES = CHOCOBO_RESOURCES_FOLDER + "/Chocobos";
	public static String CHICOBO_ENTITY_TEXTURES = CHOCOBO_RESOURCES_FOLDER + "/Chicobos";
	public static String CHOCOBO_ETXT_UNTAMED = "/Untamed";
	public static String CHOCOBO_ETXT_TAMED = "/Tamed";
	public static String CHOCOBO_ETXT_SADDLED = "/Saddled";
	public static String CHOCOBO_ETXT_SADDLEBAGGED = "/SaddleBagged";
	public static String CHOCOBO_ETXT_PACKBAGGED = "/PackBagged";
	public static String CHOCOBO_ETXT_MALE = "/Male";
	public static String CHOCOBO_ETXT_FEMALE = "/Female";
	public static String CHICOBO_ETXT_TAMED = CHOCOBO_ETXT_TAMED;
	
	// setup
	public static boolean DEFAULT_CHOCOBO_WING_FLUTTER = false;
	public static boolean DEFAULT_SHOW_CHOCOBO_NAMES = true;
	public static int DEFAULT_GENDER_MALE_CHANCE = 50;
	public static int DEFAULT_FEATHER_DROP_CHANCE = 15;
	public static int DEFAULT_FEATHER_DELAY_RANDOM = 600;
	public static int DEFAULT_FEATHER_DELAY_STATIC = 600;
	public static int DEFAULT_GHYSAL_SPAWN_RATE = 100;
	public static int DEFAULT_PEN_HEAL_PROBABILITY = 20;
	public static int DEFAULT_PEN_HEAL_CAULDRON_RANGE = 6;

	
	public static int DEFAULT_SPAWN_TIME_DELAY = 200;
	public static int DEFAULT_SPAWN_GROUP_MIN = 2;
	public static int DEFAULT_SPAWN_GROUP_MAX = 4;
	public static int DEFAULT_SPAWN_TOTAL_MAX = 3;
	public static int DEFAULT_SPAWN_PROBABILITY = 2;
	public static int DEFAULT_SPAWN_LIMIT_CHUNK_RADIUS = 100;
	public static int DEFAULT_SPAWN_DIST_NEXT_WILD = 300;
	
	public static double DEFAULT_RENDER_NAME_HEIGHT = 0.0;
	public static int DEFAULT_LIVING_SOUND_PROB = 100;
	
	//
	public static float CHOCOBO_DEFAULT_LANDMOVEFACT  = 0.2F;
	public static float CHOCOBO_YELLOW_LANDMOVEFACT   = 0.2F;
	public static float CHOCOBO_GREEN_LANDMOVEFACT    = 0.25F;
	public static float CHOCOBO_BLUE_LANDMOVEFACT     = 0.25F;
	public static float CHOCOBO_WHITE_LANDMOVEFACT    = 0.35F;
	public static float CHOCOBO_BLACK_LANDMOVEFACT    = 0.35F;
	public static float CHOCOBO_GOLD_LANDMOVEFACT     = 0.45F;
	public static float CHOCOBO_PINK_LANDMOVEFACT     = 0.45F;
	public static float CHOCOBO_RED_LANDMOVEFACT      = 0.45F;
	public static float CHOCOBO_PURPLE_LANDMOVEFACT   = 0.4F;
	
	public static float CHOCOBO_DEFAULT_WATERMOVEFACT  = 0.1F;
	public static float CHOCOBO_YELLOW_WATERMOVEFACT   = 0.1F;
	public static float CHOCOBO_GREEN_WATERMOVEFACT    = 0.1F;
	public static float CHOCOBO_BLUE_WATERMOVEFACT     = 0.45F;
	public static float CHOCOBO_WHITE_WATERMOVEFACT    = 0.40F;
	public static float CHOCOBO_BLACK_WATERMOVEFACT    = 0.2F;
	public static float CHOCOBO_GOLD_WATERMOVEFACT     = 0.2F;
	public static float CHOCOBO_PINK_WATERMOVEFACT     = 0.2F;
	public static float CHOCOBO_RED_WATERMOVEFACT      = 0.2F;
	public static float CHOCOBO_PURPLE_WATERMOVEFACT   = 0.1F;
	
	public static float CHOCOBO_DEFAULT_FLYMOVEFACT   = 0.0F;
	public static float CHOCOBO_YELLOW_FLYMOVEFACT    = 0.0F;
	public static float CHOCOBO_GREEN_FLYMOVEFACT     = 0.0F;
	public static float CHOCOBO_BLUE_FLYMOVEFACT      = 0.0F;
	public static float CHOCOBO_WHITE_FLYMOVEFACT     = 0.0F;
	public static float CHOCOBO_BLACK_FLYMOVEFACT     = 0.0F;
	public static float CHOCOBO_GOLD_FLYMOVEFACT      = 0.25F;
	public static float CHOCOBO_PINK_FLYMOVEFACT      = 0.25F;
	public static float CHOCOBO_RED_FLYMOVEFACT       = 0.25F;
	public static float CHOCOBO_PURPLE_FLYMOVEFACT    = 0.25F;
	
	public static double CHOCOBO_YELLOW_LANDSPEEDFACT = 20;
	public static double CHOCOBO_GREEN_LANDSPEEDFACT  = 27;
	public static double CHOCOBO_BLUE_LANDSPEEDFACT   = 27;
	public static double CHOCOBO_WHITE_LANDSPEEDFACT  = 35;
	public static double CHOCOBO_BLACK_LANDSPEEDFACT  = 40;
	public static double CHOCOBO_GOLD_LANDSPEEDFACT   = 50;
	public static double CHOCOBO_PINK_LANDSPEEDFACT   = 55;
	public static double CHOCOBO_RED_LANDSPEEDFACT    = 55;
	public static double CHOCOBO_PURPLE_LANDSPEEDFACT = 40;
	
	public static double CHOCOBO_YELLOW_WATERSPEEDFACT = 10;
	public static double CHOCOBO_GREEN_WATERSPEEDFACT  = 10;
	public static double CHOCOBO_BLUE_WATERSPEEDFACT   = 55;
	public static double CHOCOBO_WHITE_WATERSPEEDFACT  = 45;
	public static double CHOCOBO_BLACK_WATERSPEEDFACT  = 20;
	public static double CHOCOBO_GOLD_WATERSPEEDFACT   = 20;
	public static double CHOCOBO_PINK_WATERSPEEDFACT   = 25;
	public static double CHOCOBO_RED_WATERSPEEDFACT    = 25;
	public static double CHOCOBO_PURPLE_WATERSPEEDFACT = 10;
	
	public static double CHOCOBO_YELLOW_AIRSPEEDFACT  =  0;
	public static double CHOCOBO_GREEN_AIRSPEEDFACT   =  0;
	public static double CHOCOBO_BLUE_AIRSPEEDFACT    =  0;
	public static double CHOCOBO_WHITE_AIRSPEEDFACT   =  0;
	public static double CHOCOBO_BLACK_AIRSPEEDFACT   =  0;
	public static double CHOCOBO_GOLD_AIRSPEEDFACT    = 55;
	public static double CHOCOBO_PINK_AIRSPEEDFACT    = 60;
	public static double CHOCOBO_RED_AIRSPEEDFACT     = 60;
	public static double CHOCOBO_PURPLE_AIRSPEEDFACT  = 55;
	
	public static boolean CHOCOBO_YELLOW_CANJUMPHIGH  = false;
	public static boolean CHOCOBO_GREEN_CANJUMPHIGH   = false;
	public static boolean CHOCOBO_BLUE_CANJUMPHIGH    = false;
	public static boolean CHOCOBO_WHITE_CANJUMPHIGH   = false;
	public static boolean CHOCOBO_BLACK_CANJUMPHIGH   = true;
	public static boolean CHOCOBO_GOLD_CANJUMPHIGH    = true;
	public static boolean CHOCOBO_PINK_CANJUMPHIGH    = true;
	public static boolean CHOCOBO_RED_CANJUMPHIGH     = true;
	public static boolean CHOCOBO_PURPLE_CANJUMPHIGH  = false;
	
	// max length of channel name is 16                     "xxxxxxxxxxxxxxxx"
	public static final String PCHAN_CHOCOBO              = "ChocoCraftPChan";
	
	// dataWatcher
	// EAC - EntityAnimalChocobo
	public static int DW_ID_EAC_NAME = 18;
	public static int DW_ID_EAC_FLAGS = 19;
	//public static int DW_ID_EAC_HEALTH = 20;
	
	public static int DW_VAL_EAC_ISMALE_ON = 1;
	public static int DW_VAL_EAC_ISMALE_OFF = -2;
	public static int DW_VAL_EAC_INLOVE_ON = 2;
	public static int DW_VAL_EAC_INLOVE_OFF = -3;
	public static int DW_VAL_EAC_HIDENAME_ON = 4;
	public static int DW_VAL_EAC_HIDENAME_OFF = -5;
	public static int DW_VAL_EAC_WANDER_ON = 8;
	public static int DW_VAL_EAC_WANDER_OFF = -9;
	public static int DW_VAL_EAC_FOLLOWING_ON = 16;
	public static int DW_VAL_EAC_FOLLOWING_OFF = -17;


	// CHIC - EntityChicobo
	public static int DW_ID_CHIC_FLAGS = 21;
	public static int DW_ID_CHIC_TIMEUNTILADULT = 22;
	
	public static int DW_VAL_CHIC_CANGROWUP_ON = 1;
	public static int DW_VAL_CHIC_CANGROWUP_OFF = -2;

	
	// ECR - EntityChocoboRideable
	public static int DW_ID_ECR_FLAGS = 22;

	public static int DW_VAL_ECR_SADDLED_ON = 1;
	public static int DW_VAL_ECR_SADDLED_OFF = -2;
	public static int DW_VAL_ECR_SADDLEBAGGED_ON = 2;
	public static int DW_VAL_ECR_SADDLEBAGGED_OFF = -3;
	public static int DW_VAL_ECR_PACKBAGGED_ON = 4;
	public static int DW_VAL_ECR_PACKBAGGED_OFF = -5;

	
	// CHOC - EntityChocobo
	public static int DW_ID_CHOC_FLAGS = 21;
	
	public static int DW_VAL_CHOC_BREEDING_ON = 1;
	public static int DW_VAL_CHOC_BREEDING_OFF = -2;

	// ECC - EntityChocobo<Color>

}
