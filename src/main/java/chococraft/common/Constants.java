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
	public final static String TCC_VERSION = "4.0.3";
	public final static String TCC_MODID = "chococraft";
	public final static String TCC_NAME = "Clienthax's ChocoCraft";
	
	// default entity id's - not really needed as forge gives 255 per mod now
	public static int CHOCOBO_YELLOW_ID  = 600;
	public static int CHOCOBO_GREEN_ID   = 601;
	public static int CHOCOBO_BLUE_ID    = 602;
	public static int CHOCOBO_WHITE_ID   = 603;
	public static int CHOCOBO_BLACK_ID   = 604;
	public static int CHOCOBO_GOLD_ID    = 605;
	public static int CHOCOBO_RED_ID     = 606;
	public static int CHOCOBO_PINK_ID    = 607;
	public static int CHOCOBO_PURPLE_ID  = 608;
	public static int CHOCOBO_CHICOBO_ID = 609;
	
	// resource folder
	public static String CHOCOBO_RESOURCES_FOLDER = "textures/entities/";
	
	// sound folder
	public static String CHOCOBO_SOUND_FOLDER = CHOCOBO_RESOURCES_FOLDER + "sound/";
	
	// armour folder
	public static String CHOCOBO_ARMOUR_FOLDER = "chococraft:textures/armor";

	public static String KEY_CHOCOPEDIA     = "chocopedia";
	public static String KEY_DISGUISE       = "chocoDisguise";
	public static String KEY_DISGUISE_HEAD  = "disguiseHead";
	public static String KEY_DISGUISE_BODY  = "disguiseBody";
	public static String KEY_DISGUISE_LEGS  = "disguiseLegs";
	public static String KEY_DISGUISE_BOOTS = "disguiseBoots";
	public static String KEY_EGG_PURPLE     = "eggPurple";
	public static String KEY_FEATHER        = "chocoboFeather";
	public static String KEY_GY_CAKE        = "gysahl_cake";
	public static String KEY_GY_PICKLES     = "gysahl_pickles";
	public static String KEY_GY_PICKLES_RAW = "gysahl_rawpickles";
	public static String KEY_GY_GOLDEN      = "gysahl_golden";
	public static String KEY_GY_LOVERLY     = "gysahl_loverly";
	public static String KEY_GY_PINK        = "gysahl_pink";
	public static String KEY_GY_RED         = "gysahl_red";
	public static String KEY_GY_SEEDS       = "gysahl_seeds";
	public static String KEY_LEG_COOKED     = "legCooked";
	public static String KEY_LEG_RAW        = "legRaw";
	public static String KEY_PACKBAG        = "packbag";
	public static String KEY_SADDLE         = "chocoboSaddle";
	public static String KEY_SADDLEBAG      = "saddlebag";
	public static String KEY_WHISTLE        = "whistle";
	public static String KEY_LIQ_CHOCOP     = "liquefiedchocopedia";
	
	public static String KEY_STRAW           = "chocoboStraw";
	public static String KEY_GY_STEM         = "gysahlStem";
	public static String KEY_GY_STEM01       = "gysahl_stem01";
	public static String KEY_GY_STEM02       = "gysahl_stem02";
	public static String KEY_GY_STEM03       = "gysahl_stem03";
	public static String KEY_GY_STEM04       = "gysahl_stem04";
	public static String KEY_GY_STEM05       = "gysahl_stem05";
	public static String KEY_GY_GREEN        = "gysahl_green";
	
	public static String CHOCOBO_ARMOUR_TEXTURES_1 = CHOCOBO_ARMOUR_FOLDER + "/chocoDisguise_1.png";
	public static String CHOCOBO_ARMOUR_TEXTURES_2 = CHOCOBO_ARMOUR_FOLDER + "/chocoDisguise_2.png";
	public static String CHOCOBO_ENTITY_TEXTURES = CHOCOBO_RESOURCES_FOLDER + "Chocobos/";
	public static String CHICOBO_ENTITY_TEXTURES = CHOCOBO_RESOURCES_FOLDER + "Chicobos/";
	public static String CHOCOBO_ETXT_UNTAMED = "Untamed/";
	public static String CHOCOBO_ETXT_TAMED = "Tamed/";
	public static String CHOCOBO_ETXT_SADDLED = "Saddled/";
	public static String CHOCOBO_ETXT_SADDLEBAGGED = "SaddleBagged/";
	public static String CHOCOBO_ETXT_PACKBAGGED = "PackBagged/";
	public static String CHOCOBO_ETXT_MALE = "Male/";
	public static String CHOCOBO_ETXT_FEMALE = "Female/";

	public static String CHICOBO_ETXT_TAMED = CHOCOBO_ETXT_TAMED;
	
	// setup
	public static boolean DEFAULT_CHOCOBO_WING_FLUTTER = false;
	public static boolean DEFAULT_SHOW_CHOCOBO_NAMES   = true;
	public static boolean DEFAULT_HUNGER_ENABLED       = false;
	public static boolean DEFAULT_RIDER_BUFFS_ENABLED  = true;
	public static boolean DEFAULT_SADDLED_CAN_WANDER   = false;
	public static boolean DEFAULT_WILD_CAN_DESPAWN     = false;
	
	public static int CHOCOPEDIA_DUNGEON_WEIGHT = 2;
	public static int CHOCOPEDIA_DUNGEON_MIN = 1;
	public static int CHOCOPEDIA_DUNGEON_MAX = 1;
	public static int CHOCOPEDIA_MOB_DROP_RATE = 3;
	public static boolean DEFAULT_CHOCOPEDIA_IN_DUNGEONS = true;
	
	public static int DEFAULT_GYSAHL_GREEN_MUTATION_RATE =  100;
	public static int DEFAULT_GYSAHL_LOVE_MUTATION_RATE  =  100;
	
	public static int DEFAULT_GYSAHL_WORLD_GEN_RATE   = 160;
	public static int DEFAULT_GYSAHL_SEED_GRASS_DROP_WEIGHT = 3;
	
	public static int DEFAULT_GENDER_MALE_CHANCE      =  50;
	public static int DEFAULT_FEATHER_DROP_CHANCE     =  15;
	public static int DEFAULT_FEATHER_DELAY_RANDOM    = 600;
	public static int DEFAULT_FEATHER_DELAY_STATIC    = 600;
	public static int DEFAULT_GHYSAL_SPAWN_RATE       = 100;
	public static int DEFAULT_PEN_HEAL_PROBABILITY    =  20;
	public static int DEFAULT_PEN_HEAL_CAULDRON_RANGE =   6;
	
	public static int DEFAULT_BREEDING_DELAY_MALE   =  3000;
	public static int DEFAULT_BREEDING_DELAY_FEMALE =  9000;
	public static int DEFAULT_GROWUP_DELAY_STATIC   = 27000;
	public static int DEFAULT_GROWUP_DELAY_RANDOM   =  2000;
	public static int DEFAULT_HUNGER_DELAY_CHOCOBO  =  3000;
	public static int DEFAULT_HUNGER_DELAY_CHICOBO  =  1000;
	
	public static int DEFAULT_SPAWN_TIME_DELAY         = 200;
	public static int DEFAULT_SPAWN_GROUP_MIN          =   2;
	public static int DEFAULT_SPAWN_GROUP_MAX          =   4;
	public static int DEFAULT_SPAWN_TOTAL_MAX          =   3;
	public static int DEFAULT_SPAWN_PROBABILITY        =   2;
	public static int DEFAULT_SPAWN_LIMIT_CHUNK_RADIUS = 100;
	public static int DEFAULT_SPAWN_DIST_NEXT_WILD     = 300;
	
	public static double DEFAULT_RENDER_NAME_HEIGHT = 0.0;
	public static int DEFAULT_LIVING_SOUND_PROB     = 100;
	
	//
//	public static float CHOCOBO_DEFAULT_LANDMOVEFACT  = 0.2F;
//	public static float CHOCOBO_YELLOW_LANDMOVEFACT   = 0.2F;
//	public static float CHOCOBO_GREEN_LANDMOVEFACT    = 0.25F;
//	public static float CHOCOBO_BLUE_LANDMOVEFACT     = 0.25F;
//	public static float CHOCOBO_WHITE_LANDMOVEFACT    = 0.35F;
//	public static float CHOCOBO_BLACK_LANDMOVEFACT    = 0.35F;
//	public static float CHOCOBO_GOLD_LANDMOVEFACT     = 0.45F;
//	public static float CHOCOBO_PINK_LANDMOVEFACT     = 0.45F;
//	public static float CHOCOBO_RED_LANDMOVEFACT      = 0.45F;
//	public static float CHOCOBO_PURPLE_LANDMOVEFACT   = 0.4F;
//	
//	public static float CHOCOBO_DEFAULT_WATERMOVEFACT  = 0.1F;
//	public static float CHOCOBO_YELLOW_WATERMOVEFACT   = 0.1F;
//	public static float CHOCOBO_GREEN_WATERMOVEFACT    = 0.1F;
//	public static float CHOCOBO_BLUE_WATERMOVEFACT     = 0.45F;
//	public static float CHOCOBO_WHITE_WATERMOVEFACT    = 0.40F;
//	public static float CHOCOBO_BLACK_WATERMOVEFACT    = 0.2F;
//	public static float CHOCOBO_GOLD_WATERMOVEFACT     = 0.2F;
//	public static float CHOCOBO_PINK_WATERMOVEFACT     = 0.2F;
//	public static float CHOCOBO_RED_WATERMOVEFACT      = 0.2F;
//	public static float CHOCOBO_PURPLE_WATERMOVEFACT   = 0.1F;
//	
//	public static float CHOCOBO_DEFAULT_FLYMOVEFACT   = 0.0F;
//	public static float CHOCOBO_YELLOW_FLYMOVEFACT    = 0.0F;
//	public static float CHOCOBO_GREEN_FLYMOVEFACT     = 0.0F;
//	public static float CHOCOBO_BLUE_FLYMOVEFACT      = 0.0F;
//	public static float CHOCOBO_WHITE_FLYMOVEFACT     = 0.0F;
//	public static float CHOCOBO_BLACK_FLYMOVEFACT     = 0.0F;
//	public static float CHOCOBO_GOLD_FLYMOVEFACT      = 0.25F;
//	public static float CHOCOBO_PINK_FLYMOVEFACT      = 0.25F;
//	public static float CHOCOBO_RED_FLYMOVEFACT       = 0.25F;
//	public static float CHOCOBO_PURPLE_FLYMOVEFACT    = 0.25F;
	
	public static double CHOCOBO_DEFAULT_LANDSPEEDFACT  = 20;
	public static double CHOCOBO_YELLOW_LANDSPEEDFACT   = 20;
	public static double CHOCOBO_GREEN_LANDSPEEDFACT    = 27;
	public static double CHOCOBO_BLUE_LANDSPEEDFACT     = 27;
	public static double CHOCOBO_WHITE_LANDSPEEDFACT    = 35;
	public static double CHOCOBO_BLACK_LANDSPEEDFACT    = 40;
	public static double CHOCOBO_GOLD_LANDSPEEDFACT     = 50;
	public static double CHOCOBO_PINK_LANDSPEEDFACT     = 55;
	public static double CHOCOBO_RED_LANDSPEEDFACT      = 55;
	public static double CHOCOBO_PURPLE_LANDSPEEDFACT   = 40;
	
	public static double CHOCOBO_DEFAULT_WATERSPEEDFACT = 10;
	public static double CHOCOBO_YELLOW_WATERSPEEDFACT  = 10;
	public static double CHOCOBO_GREEN_WATERSPEEDFACT   = 10;
	public static double CHOCOBO_BLUE_WATERSPEEDFACT    = 55;
	public static double CHOCOBO_WHITE_WATERSPEEDFACT   = 45;
	public static double CHOCOBO_BLACK_WATERSPEEDFACT   = 20;
	public static double CHOCOBO_GOLD_WATERSPEEDFACT    = 20;
	public static double CHOCOBO_PINK_WATERSPEEDFACT    = 25;
	public static double CHOCOBO_RED_WATERSPEEDFACT     = 25;
	public static double CHOCOBO_PURPLE_WATERSPEEDFACT  = 10;
	
	public static double CHOCOBO_DEFAULT_AIRSPEEDFACT   =  0;
	public static double CHOCOBO_YELLOW_AIRSPEEDFACT    =  0;
	public static double CHOCOBO_GREEN_AIRSPEEDFACT     =  0;
	public static double CHOCOBO_BLUE_AIRSPEEDFACT      =  0;
	public static double CHOCOBO_WHITE_AIRSPEEDFACT     =  0;
	public static double CHOCOBO_BLACK_AIRSPEEDFACT     =  0;
	public static double CHOCOBO_GOLD_AIRSPEEDFACT      = 55;
	public static double CHOCOBO_PINK_AIRSPEEDFACT      = 60;
	public static double CHOCOBO_RED_AIRSPEEDFACT       = 60;
	public static double CHOCOBO_PURPLE_AIRSPEEDFACT    = 55;
	
	public static boolean CHOCOBO_DEFAULT_CANJUMPHIGH   = false;
	public static boolean CHOCOBO_YELLOW_CANJUMPHIGH    = false;
	public static boolean CHOCOBO_GREEN_CANJUMPHIGH     = false;
	public static boolean CHOCOBO_BLUE_CANJUMPHIGH      = false;
	public static boolean CHOCOBO_WHITE_CANJUMPHIGH     = true;
	public static boolean CHOCOBO_BLACK_CANJUMPHIGH     = true;
	public static boolean CHOCOBO_GOLD_CANJUMPHIGH      = false;
	public static boolean CHOCOBO_PINK_CANJUMPHIGH      = false;
	public static boolean CHOCOBO_RED_CANJUMPHIGH       = false;
	public static boolean CHOCOBO_PURPLE_CANJUMPHIGH    = false;
	
	public static String PARTICLE_HEART        = "heart";
	public static String PARTICLE_SMOKE        = "smoke";
	public static String PARTICLE_EXPLODE      = "explode";
	public static String PARTICLE_SNOWBALLPOOF = "snowballpoof";
	
	// dataWatcher
	// EAC - EntityAnimalChocobo
	public static int DW_ID_EAC_NAME = 18;
	public static int DW_ID_EAC_FLAGS = 19;
	public static int DW_ID_EAC_TIME_UNTIL_HUNGER = 20;
	
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
