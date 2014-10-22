// <copyright file="ChocoboConfig.java">
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
// <summary>Writes and reades the config eintries from the defined file into and from the static variables of the mod_chocobo class</summary>

package chococraft.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import chococraft.common.helper.ChocoboBiomeHelper;
import chococraft.common.helper.ChocoboMathHelper;
import net.minecraft.world.biome.BiomeGenBase;

public class ChocoboConfig
{	
	static String CONFIG_FILE_NAME = "chocobo_config.txt";
	static String CFG_TOKEN_COMMENT = "//";

	static String CFG_KEY_SHOW_CHOCO_NAMES = "showChocoboNames";
	
	static String CFG_KEY_SPAWN_BIOMES = "spawnBiomes";
	static String CFG_KEY_SPAWN_TIME_DELAY = "spawnTimeDelay";
	static String CFG_KEY_SPAWN_GROUP_MIN = "spawnGroupMin";
	static String CFG_KEY_SPAWN_GROUP_MAX = "spawnGroupMax";
	static String CFG_KEY_SPAWN_TOTAL_MAX = "spawnTotalMax";
	static String CFG_KEY_SPAWN_PROBABILITY = "spawnProbability";
	static String CFG_KEY_SPAWN_LIMIT_CHUNK_RADIUS = "spawnLimitChunkRadius";
	static String CFG_KEY_SPAWN_DIST_NEXT_WILD = "distanceNextWild";
	
	static String CFG_KEY_CHOCOBO_WING_FLUTTER  = "chocoboWingFlutter";
	static String CFG_KEY_WILD_CAN_DESPAWN      = "wildCanDespawn";
	static String CFG_KEY_HUNGER_ENABLED        = "hungerEnabled";
	static String CFG_KEY_RIDER_BUFFS_ENABLED   = "riderBuffsEnabled";
	
	static String CFG_TOKEN_ALL                 = "all";
	static String CFG_KEY_FEATHER_DELAY_RANDOM  = "featherDelayRandom";
	static String CFG_KEY_FEATHER_DELAY_STATIC  = "featherDelayStatic";
	static String CFG_KEY_FEATHER_DROP_CHANCE   = "featherDropChance";
	static String CFG_KEY_RENDER_NAME_HEIGHT    = "renderNameHeight";
	
	static String CFG_KEY_PEN_HEAL_PROBABILITY = "penHealProbability";
	static String CFG_KEY_PEN_HEAL_CAULDRON_RANGE = "penHealCauldronRange";
	
	static String CFG_KEY_BREEDING_DELAY_MALE = "breedingDelayMale";
	static String CFG_KEY_BREEDING_DELAY_FEMALE = "breedingDelayFemale";
	static String CFG_KEY_GROWUP_DELAY_STATIC = "growUpDelayStatic";
	static String CFG_KEY_GROWUP_DELAY_RANDOM = "growUpDelayRandom";
	
	static String CFG_KEY_HUNGER_DELAY_CHOCOBO = "hungerDelayChocobo";
	static String CFG_KEY_HUNGER_DELAY_CHICOBO = "hungerDelayChicobo";
	
	static String CFG_KEY_LIVING_SOUND_PROB = "livingSoundProbability";
	
	static String CFG_KEY_GYS_GREEN_MUT_RATE = "gysahlGreenMutationRate";
	static String CFG_KEY_GYS_LOVE_MUT_RATE  = "gysahlLoverlyMutationRate";
	
	static String CFG_KEY_GYS_WORLD_GEN_RATE = "gysahlGreenWorldGenerationRate";
	
	static String CFG_KEY_SADDLED_CAN_WANDER = "saddledCanWander";
	
	static String CFG_KEY_CHOCOPEDIA_IN_DUNGEONS = "chocopediaInDungeons";
	
	static String CFG_KEY_DEBUG_MODE = "debugMode";

	public static void readConfigFileInit()
	{
		try
		{
			BufferedReader reader = getConfigReader();
			String line;
			
			while (null != (line = reader.readLine()))
			{
				if (0 < (line.trim().length()) && (!line.trim().startsWith(CFG_TOKEN_COMMENT)))
				{
					String[] temp = line.split("=");
					if (2 == temp.length)
					{
						String key = temp[0].trim();
						String value = temp[1].trim();

						if(key.isEmpty())
						{
							//throw new Exception("Empty key in Chocobo config file!");
							// better: error log entry ...
						}
						else if (value.isEmpty())
						{
							//throw new Exception("Empty value in Chocobo config file!");
							// better: error log entry ...
						}
						else
						{
							if(key.equalsIgnoreCase(CFG_KEY_SPAWN_BIOMES))
							{
								ModChocoCraft.spawnBiomes = getBiomeGenBaseArray(value);
							}	
						}
					}
				}
			}
			reader.close();
		}
		catch (FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void readConfigFilePreInit()
	{
		try
		{
			BufferedReader reader = getConfigReader();
			String line;

			while (null != (line = reader.readLine()))
			{
				if (0 < (line.trim().length()) && (!line.trim().startsWith(CFG_TOKEN_COMMENT)))
				{
					String[] temp = line.split("=");
					if (2 == temp.length)
					{
						String key = temp[0].trim();
						String value = temp[1].trim();

						if(key.isEmpty())
						{
							//throw new Exception("Empty key in Chocobo config file!");
							// better: error log entry ...
						}
						else if (value.isEmpty())
						{
							//throw new Exception("Empty value in Chocobo config file!");
							// better: error log entry ...
						}
						else
						{
							// now parse the key/value
							try
							{
								if(key.equalsIgnoreCase(CFG_KEY_SHOW_CHOCO_NAMES))
								{
									ModChocoCraft.showChocoboNames = Boolean.parseBoolean(value);
								}
								
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_BIOMES))
								{
									ModChocoCraft.spawnBiomes = getBiomeGenBaseArray(value);
								}
								
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_TIME_DELAY))
								{
									ModChocoCraft.spawnTimeDelay = ChocoboMathHelper.minLimit(Integer.parseInt(value), 50);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_PROBABILITY))
								{
									ModChocoCraft.spawnProbability = ChocoboMathHelper.clamp(Integer.parseInt(value), 1, 100);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_GROUP_MIN))
								{
									ModChocoCraft.spawnGroupMin = ChocoboMathHelper.minLimit(Integer.parseInt(value), 1);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_GROUP_MAX))
								{
									ModChocoCraft.spawnGroupMax = ChocoboMathHelper.minLimit(Integer.parseInt(value), 1);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_TOTAL_MAX))
								{
									ModChocoCraft.spawnTotalMax = ChocoboMathHelper.minLimit(Integer.parseInt(value), 1);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_LIMIT_CHUNK_RADIUS))
								{
									ModChocoCraft.spawnLimitChunkRadius = ChocoboMathHelper.minLimit(Integer.parseInt(value), 1);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_DIST_NEXT_WILD))
								{
									ModChocoCraft.spawnDistanceNextWild = ChocoboMathHelper.minLimit(Integer.parseInt(value), 60);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOBO_WING_FLUTTER))
								{
									ModChocoCraft.chocoboWingFlutter = Boolean.parseBoolean(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_WILD_CAN_DESPAWN))
								{
									ModChocoCraft.wildCanDespawn = Boolean.parseBoolean(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_HUNGER_ENABLED))
								{
									//ModChocoCraft.hungerEnabled = Boolean.parseBoolean(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_RIDER_BUFFS_ENABLED))
								{
									ModChocoCraft.riderBuffsEnabled = Boolean.parseBoolean(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_FEATHER_DELAY_RANDOM))
								{
									ModChocoCraft.featherDelayRandom = ChocoboMathHelper.minLimit(Integer.parseInt(value), 60);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_FEATHER_DELAY_STATIC))
								{
									ModChocoCraft.featherDelayStatic = ChocoboMathHelper.minLimit(Integer.parseInt(value), 60);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_FEATHER_DROP_CHANCE))
								{
									ModChocoCraft.featherDropChance = ChocoboMathHelper.minLimit(Integer.parseInt(value), 1);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_RENDER_NAME_HEIGHT))
								{
									double tmprnh = Double.parseDouble(value);
									if(tmprnh < 0.0)
									{
										ModChocoCraft.renderNameHeight = -2.2;
									}
									else if(tmprnh > 10.0)
									{
										ModChocoCraft.renderNameHeight = 7.7;
									}
									else
									{
										ModChocoCraft.renderNameHeight = tmprnh;
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_LIVING_SOUND_PROB))
								{
									ModChocoCraft.livingSoundProb = ChocoboMathHelper.clamp(Integer.parseInt(value), 1, 100);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_PEN_HEAL_PROBABILITY))
								{
									ModChocoCraft.penHealProbability = ChocoboMathHelper.clamp(Integer.parseInt(value), 1, 100);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_PEN_HEAL_CAULDRON_RANGE))
								{
									ModChocoCraft.penHealCauldronRange = ChocoboMathHelper.clamp(Integer.parseInt(value), 1, 15);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_BREEDING_DELAY_MALE))
								{
									ModChocoCraft.breedingDelayMale = ChocoboMathHelper.minLimit(Integer.parseInt(value), 20);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_BREEDING_DELAY_FEMALE))
								{
									ModChocoCraft.breedingDelayFemale = ChocoboMathHelper.minLimit(Integer.parseInt(value), 20);
								}
								else if(key.equals(CFG_KEY_GROWUP_DELAY_RANDOM))
								{
									ModChocoCraft.growupDelayRandom = ChocoboMathHelper.minLimit(Integer.parseInt(value), 20);
								}
								else if(key.equals(CFG_KEY_GROWUP_DELAY_STATIC))
								{
									ModChocoCraft.growupDelayStatic = ChocoboMathHelper.minLimit(Integer.parseInt(value), 20);
								}
								else if(key.equals(CFG_KEY_HUNGER_DELAY_CHICOBO))
								{
									ModChocoCraft.hungerDelayChicobo = ChocoboMathHelper.minLimit(Integer.parseInt(value), 20);
								}
								else if(key.equals(CFG_KEY_HUNGER_DELAY_CHOCOBO))
								{
									ModChocoCraft.hungerDelayChocobo = ChocoboMathHelper.minLimit(Integer.parseInt(value), 20);
								}
								else if(key.equals(CFG_KEY_GYS_GREEN_MUT_RATE))
								{
									ModChocoCraft.gysahlGreenMutationRate = ChocoboMathHelper.clamp(Integer.parseInt(value), 1, 1000);
								}
								else if(key.equals(CFG_KEY_GYS_LOVE_MUT_RATE))
								{
									ModChocoCraft.gysahlLoveMutationRate = ChocoboMathHelper.clamp(Integer.parseInt(value), 1, 1000);
								}
								else if(key.equals(CFG_KEY_GYS_WORLD_GEN_RATE))
								{
									ModChocoCraft.gysahlWorldGenRate = ChocoboMathHelper.clamp(Integer.parseInt(value), 1, 999);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SADDLED_CAN_WANDER))
								{
									ModChocoCraft.saddledCanWander = Boolean.parseBoolean(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOPEDIA_IN_DUNGEONS))
								{
									ModChocoCraft.chocopediaInDungeons = Boolean.parseBoolean(value);
								}
								
								// debug mode
								else if(key.equalsIgnoreCase(CFG_KEY_DEBUG_MODE))
								{
									ModChocoCraft.debugMode = Boolean.parseBoolean(value);
								}
							}
							catch (NumberFormatException e)
							{
								// do nothing, just keep the default config values...
							}
						}
					}
				}
			}
			reader.close();
		}
		catch (FileNotFoundException fnfe)
		{
			createNewConfigFile();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static BiomeGenBase[] getBiomeGenBaseArray(String valueString)
	{
		if(valueString.equals(CFG_TOKEN_ALL))
		{
			return ChocoboBiomeHelper.getBiomeGenBaseArray();								
		}
		String[] values = valueString.split(",");
		ArrayList<BiomeGenBase> bgbList= new ArrayList<BiomeGenBase>();

		for(String value : values)
		{
			if(!value.trim().isEmpty())
			{
				if(value.trim().equals(CFG_TOKEN_ALL))
				{
					return ChocoboBiomeHelper.getBiomeGenBaseArray();								

				}
				for(BiomeGenBase biomeGenBase : BiomeGenBase.getBiomeGenArray()) {
					if(biomeGenBase != null && value.trim().equals(biomeGenBase.biomeName))
						bgbList.add(biomeGenBase);
				}
			}
		}
		BiomeGenBase[] bgbArray = new BiomeGenBase[bgbList.size()];
		int i = 0;
		for (BiomeGenBase bgb : bgbList) {
			bgbArray[i] = bgb;
			i++;
		}
		if(0 < bgbArray.length)
		{
			return bgbArray;
		}
		else
		{
			return ChocoboBiomeHelper.getBiomeGenBaseArray();								
		}
	}

	private static void createNewConfigFile()
	{
		File file = getConfigFile();		
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"));
			
			writer.write("\n");
			writer.write(getConfigLine(CFG_KEY_SHOW_CHOCO_NAMES, Boolean.toString(Constants.DEFAULT_SHOW_CHOCOBO_NAMES)));
			writer.write(getConfigLine(CFG_KEY_CHOCOBO_WING_FLUTTER, Boolean.toString(Constants.DEFAULT_CHOCOBO_WING_FLUTTER)));
			writer.write(getCommentLine("hunger not yet active"));			
			writer.write(getConfigLine(CFG_KEY_HUNGER_ENABLED, Boolean.toString(Constants.DEFAULT_HUNGER_ENABLED)));
			writer.write(getConfigLine(CFG_KEY_RIDER_BUFFS_ENABLED, Boolean.toString(Constants.DEFAULT_RIDER_BUFFS_ENABLED)));
			writer.write(getConfigLine(CFG_KEY_SADDLED_CAN_WANDER, Boolean.toString(Constants.DEFAULT_SADDLED_CAN_WANDER)));
			
			writer.write("\n");
			writer.write(getCommentLine("The default name height is 2.3 blocks from the ground. You can add height"));			
			writer.write(getCommentLine("up to 10 blocks by the number given as " + CFG_KEY_RENDER_NAME_HEIGHT + "."));
			writer.write(getConfigLine(CFG_KEY_RENDER_NAME_HEIGHT, (Double.toString(Constants.DEFAULT_RENDER_NAME_HEIGHT))));
			
			writer.write("\n");
			writer.write(getCommentLine("The value given as " + CFG_KEY_LIVING_SOUND_PROB + "will determine the Chocobo 'Kweh' frequency."));			
			writer.write(getCommentLine("The system will call a method to trigger the living sound and the Chocobo will do a"));
			writer.write(getCommentLine("'Kweh'. The number set with this key will reduce the frequency to this number in 100."));
			writer.write(getCommentLine("Thus if it is set to 50 only every second 'Kweh' will be heared. If set to 1 only "));
			writer.write(getCommentLine("every 100th etc..."));
			writer.write(getConfigLine(CFG_KEY_LIVING_SOUND_PROB, Integer.toString(Constants.DEFAULT_LIVING_SOUND_PROB)));
			
			writer.write("\n");
			writer.write(getCommentLine("Mutation rates of planted and grown gysahl greens. There is a " + CFG_KEY_GYS_GREEN_MUT_RATE + " permille chance"));
			writer.write(getCommentLine("the gysahl plant will mutate into a breeding gysahl. If the plant has mutated into"));
			writer.write(getCommentLine("a breeding gysahl, there is a " + CFG_KEY_GYS_LOVE_MUT_RATE + " permille change of it being a golden Gysahl."));
			writer.write(getConfigLine(CFG_KEY_GYS_GREEN_MUT_RATE, Integer.toString(Constants.DEFAULT_GYSAHL_GREEN_MUTATION_RATE)));
			writer.write(getConfigLine(CFG_KEY_GYS_LOVE_MUT_RATE, Integer.toString(Constants.DEFAULT_GYSAHL_LOVE_MUTATION_RATE)));
			
			writer.write("\n");
			writer.write(getCommentLine("value between 1 and 999, with 1 for very few wild gysahl and 999 very many."));
			writer.write(getConfigLine(CFG_KEY_GYS_WORLD_GEN_RATE, Integer.toString(Constants.DEFAULT_GYSAHL_WORLD_GEN_RATE)));			
			
			writer.write("\n");
			writer.write(getCommentLine("Cool down phase between breeding"));
			writer.write(getConfigLine(CFG_KEY_BREEDING_DELAY_FEMALE, Integer.toString(Constants.DEFAULT_BREEDING_DELAY_FEMALE)));
			writer.write(getConfigLine(CFG_KEY_BREEDING_DELAY_MALE, Integer.toString(Constants.DEFAULT_BREEDING_DELAY_MALE)));
			
			writer.write("\n");
			writer.write(getCommentLine("Chicobos will grow up into Chocobos after " + CFG_KEY_GROWUP_DELAY_STATIC + " plus"));
			writer.write(getCommentLine("0 - " + CFG_KEY_GROWUP_DELAY_RANDOM + " ticks."));
			writer.write(getConfigLine(CFG_KEY_GROWUP_DELAY_STATIC, Integer.toString(Constants.DEFAULT_GROWUP_DELAY_STATIC)));
			writer.write(getConfigLine(CFG_KEY_GROWUP_DELAY_RANDOM, Integer.toString(Constants.DEFAULT_GROWUP_DELAY_RANDOM)));
			
			writer.write("\n");
			writer.write(getCommentLine("Time after which Chicobos and Chocobos will get hungry again (disabled)"));
			writer.write(getConfigLine(CFG_KEY_HUNGER_DELAY_CHICOBO, Integer.toString(Constants.DEFAULT_HUNGER_DELAY_CHICOBO)));
			writer.write(getConfigLine(CFG_KEY_HUNGER_DELAY_CHOCOBO, Integer.toString(Constants.DEFAULT_HUNGER_DELAY_CHOCOBO)));			
			
			writer.write("\n");
			writer.write(getCommentLine("Whenever a hurt Chocobo is standing on straw less than " + CFG_KEY_PEN_HEAL_CAULDRON_RANGE + " blocks away from"));
			writer.write(getCommentLine("a filled cauldron, it has a" + CFG_KEY_PEN_HEAL_PROBABILITY +" chance every 2 seconds to heal one health point."));
			writer.write(getConfigLine(CFG_KEY_PEN_HEAL_PROBABILITY, Integer.toString(Constants.DEFAULT_PEN_HEAL_PROBABILITY)));
			writer.write(getConfigLine(CFG_KEY_PEN_HEAL_CAULDRON_RANGE, Integer.toString(Constants.DEFAULT_PEN_HEAL_CAULDRON_RANGE)));
			
			writer.write("\n");
			writer.write(getCommentLine("Whenever or not wild Chocobos will be able to despawn if no player is around"));
			writer.write(getConfigLine(CFG_KEY_WILD_CAN_DESPAWN, Boolean.toString(Constants.DEFAULT_WILD_CAN_DESPAWN)));			
			
			writer.write("\n");
			writer.write(getCommentLine("Whenever or not the Chocopedia can be found in dungeon, jungle and desert pyramid, stronghold and mine chests"));
			writer.write(getConfigLine(CFG_KEY_CHOCOPEDIA_IN_DUNGEONS, Boolean.toString(Constants.DEFAULT_CHOCOPEDIA_IN_DUNGEONS)));
			
			writer.write("\n");
			writer.write(getCommentLine("add any name of the following list as comma separated values to"));
			writer.write(getCommentLine("the " + CFG_KEY_SPAWN_BIOMES + "key, to have Chocobos spawn in the"));
			writer.write(getCommentLine("designated biomes. Add the token 'all' to have Chocobos spawn in "));
			writer.write(getCommentLine("all biomes."));
			writer.write(getCommentLine("Possible biome names:"));
			writer.write(getCommentLine(getAllBiomeNames()));
			writer.write(getConfigLine(CFG_KEY_SPAWN_BIOMES, writeCSVBiomeNames(ModChocoCraft.spawnBiomes)));
			
			writer.write("\n");
			writer.write(getCommentLine("A group of " + CFG_KEY_SPAWN_GROUP_MIN + " to " + CFG_KEY_SPAWN_GROUP_MAX + " Yellow Chocobos will spawn with the"));
			writer.write(getCommentLine("probability of " + CFG_KEY_SPAWN_PROBABILITY + " around every player. There will be no additional"));
			writer.write(getCommentLine("spawning if " + CFG_KEY_SPAWN_TOTAL_MAX + " wild Chocobos are active in an area with the radius of " + CFG_KEY_SPAWN_LIMIT_CHUNK_RADIUS + " chunks."));
			writer.write(getConfigLine(CFG_KEY_SPAWN_TIME_DELAY, Integer.toString(ModChocoCraft.spawnTimeDelay)));
			writer.write(getConfigLine(CFG_KEY_SPAWN_PROBABILITY, Integer.toString(ModChocoCraft.spawnProbability)));
			writer.write(getConfigLine(CFG_KEY_SPAWN_GROUP_MIN, Integer.toString(ModChocoCraft.spawnGroupMin)));
			writer.write(getConfigLine(CFG_KEY_SPAWN_GROUP_MAX, Integer.toString(ModChocoCraft.spawnGroupMax)));
			writer.write(getConfigLine(CFG_KEY_SPAWN_TOTAL_MAX, Integer.toString(ModChocoCraft.spawnTotalMax)));
			writer.write(getConfigLine(CFG_KEY_SPAWN_LIMIT_CHUNK_RADIUS, Integer.toString(ModChocoCraft.spawnLimitChunkRadius)));
			writer.write(getConfigLine(CFG_KEY_SPAWN_DIST_NEXT_WILD, Integer.toString(ModChocoCraft.spawnDistanceNextWild)));
			
			writer.write("\n");
			writer.write(getCommentLine("tamed chocobos have a chance of "+ CFG_KEY_FEATHER_DROP_CHANCE +" in 100 to drop a feather every"));
			writer.write(getCommentLine(CFG_KEY_FEATHER_DELAY_RANDOM+"/20 to "+CFG_KEY_FEATHER_DELAY_RANDOM+"/20 plus "+CFG_KEY_FEATHER_DELAY_STATIC+"/20 seconds"));
			writer.write(getCommentLine(""));
			writer.write(getConfigLine(CFG_KEY_FEATHER_DELAY_RANDOM, Integer.toString(ModChocoCraft.featherDelayRandom)));
			writer.write(getConfigLine(CFG_KEY_FEATHER_DELAY_STATIC, Integer.toString(ModChocoCraft.featherDelayStatic)));
			writer.write(getConfigLine(CFG_KEY_FEATHER_DROP_CHANCE, Integer.toString(ModChocoCraft.featherDropChance)));
			
			writer.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static String writeCSVBiomeNames(BiomeGenBase[] yellowSpawnBiomes)
	{
		String biomesNamesCSV = "";
		boolean firstName = true;
		
		for (BiomeGenBase biomeGenBase : yellowSpawnBiomes)
		{
			if(!firstName)
			{
				biomesNamesCSV += ",";
			}
			biomesNamesCSV += biomeGenBase.biomeName;

			firstName = false;
		}
		return biomesNamesCSV;
	}

	private static String getAllBiomeNames()
	{
		String bgbNameList = "";
		Boolean firstName = true;
		for(BiomeGenBase bgb : BiomeGenBase.getBiomeGenArray())
		{
			if(null != bgb)
			{
				if(!firstName)
				{
					bgbNameList = bgbNameList + ", ";
				}
				else
				{
					firstName = false;
				}
				bgbNameList = bgbNameList + bgb.biomeName;
			}
		}
		return bgbNameList;
	}

	public static String getConfigLine(String key, String value)
	{
		return key + " = " + value + "\n";
	}

	public static String getConfigLine(String key, ArrayList<String> values)
	{
		String configLine = key + " = ";
		Boolean firstValue = true;
		for (String value : values)
		{
			if(!firstValue)
			{
				configLine = configLine + ", ";
			}
			else
			{
				firstValue = false;
			}
			configLine = configLine + value;
		}
		return configLine;
	}

	public static String getCommentLine(String comment)
	{
		if(!comment.endsWith("\n"))
		{
			comment = comment + "\n";
		}
		return CFG_TOKEN_COMMENT + " " + comment;
	}

	public static BufferedReader getConfigReader() throws UnsupportedEncodingException, FileNotFoundException
	{
		File file = getConfigFile();		
		if(file != null)
		{
			return new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
		}
		else
		{
			return null;
		}
	}
	
	private static File getConfigFile()
	{
		return new File(getConfigFolderName(), getConfigFileName());
	}
	
	public static String getConfigFolderName()
	{
		return ModChocoCraft.configFolder.toString();
	}
	
	public static String getConfigFileName()
	{
		return CONFIG_FILE_NAME;
	}

}
