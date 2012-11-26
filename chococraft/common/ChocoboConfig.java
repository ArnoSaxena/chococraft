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
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.BiomeGenBase;

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
	
	static String CFG_KEY_CHOCOBO_WING_FLUTTER = "chocoboWingFlutter";
	static String CFG_TOKEN_ALL = "all";
	static String CFG_KEY_FEATHER_DELAY_RANDOM = "featherDelayRandom";
	static String CFG_KEY_FEATHER_DELAY_STATIC = "featherDelayStatic";
	static String CFG_KEY_FEATHER_DROP_CHANCE = "featherDropChance";
	static String CFG_KEY_RENDER_NAME_HEIGHT = "renderNameHeight";
	
	static String CFG_KEY_PEN_HEAL_PROBABILITY = "penHealProbability";
	static String CFG_KEY_PEN_HEAL_CAULDRON_RANGE = "penHealCauldronRange";
	
	static String CFG_KEY_LIVING_SOUND_PROB = "livingSoundProbability";

	public static void readConfigFile()
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
									int tmpstd = Integer.parseInt(value);
									if(tmpstd < 50)
									{
										ModChocoCraft.spawnTimeDelay = 50;
									}
									else
									{
										ModChocoCraft.spawnTimeDelay = tmpstd;
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_PROBABILITY))
								{
									int tmpProb = Integer.parseInt(value);
									if(tmpProb > 100)
									{
										ModChocoCraft.spawnProbability = 100;
									}
									else if(tmpProb < 1)
									{
										ModChocoCraft.spawnProbability = 1;
									}
									else
									{
										ModChocoCraft.spawnProbability = tmpProb;
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_GROUP_MIN))
								{
									int tmpsming = Integer.parseInt(value);
									if(tmpsming < 1)
									{
										ModChocoCraft.spawnGroupMin = 1;
									}
									else
									{
										ModChocoCraft.spawnGroupMin = tmpsming;	
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_GROUP_MAX))
								{
									int tmpsmaxg = Integer.parseInt(value);
									if(tmpsmaxg < 1)
									{
										ModChocoCraft.spawnGroupMax = 1;
									}
									else
									{
										ModChocoCraft.spawnGroupMax = tmpsmaxg;	
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_TOTAL_MAX))
								{
									int tmpsmaxg = Integer.parseInt(value);
									if(tmpsmaxg < 1)
									{
										ModChocoCraft.spawnTotalMax = 1;
									}
									else
									{
										ModChocoCraft.spawnTotalMax = tmpsmaxg;	
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_LIMIT_CHUNK_RADIUS))
								{
									int tmpslcr = Integer.parseInt(value);
									if(tmpslcr < 1)
									{
										ModChocoCraft.spawnLimitChunkRadius = 1;
									}
									else
									{
										ModChocoCraft.spawnLimitChunkRadius = tmpslcr;	
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_DIST_NEXT_WILD))
								{
									int tmpdnw = Integer.parseInt(value);
									if(tmpdnw < 60)
									{
										ModChocoCraft.spawnDistanceNextWild = 60;
									}
									else
									{
										ModChocoCraft.spawnDistanceNextWild = tmpdnw;	
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOBO_WING_FLUTTER))
								{
									ModChocoCraft.chocoboWingFlutter = Boolean.parseBoolean(value);
								}

								else if(key.equalsIgnoreCase(CFG_KEY_FEATHER_DELAY_RANDOM))
								{
									int tmpfdr = Integer.parseInt(value);
									if(tmpfdr < 60)
									{
										ModChocoCraft.featherDelayRandom = 60;
									}
									else
									{
										ModChocoCraft.featherDelayRandom = tmpfdr;	
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_FEATHER_DELAY_STATIC))
								{
									int tmpfdr = Integer.parseInt(value);
									if(tmpfdr < 60)
									{
										ModChocoCraft.featherDelayStatic = 60;
									}
									else
									{
										ModChocoCraft.featherDelayStatic = tmpfdr;	
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_FEATHER_DROP_CHANCE))
								{
									int tmpfdr = Integer.parseInt(value);
									if(tmpfdr < 1)
									{
										ModChocoCraft.featherDropChance = 1;
									}
									else
									{
										ModChocoCraft.featherDropChance = tmpfdr;	
									}
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
									ModChocoCraft.livingSoundProb = ChocoboMathHelper.clamp(Integer.parseInt(value), 0, 100);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_PEN_HEAL_PROBABILITY))
								{
									ModChocoCraft.penHealProbability = ChocoboMathHelper.clamp(Integer.parseInt(value), 0, 100);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_PEN_HEAL_CAULDRON_RANGE))
								{
									ModChocoCraft.penHealCauldronRange = ChocoboMathHelper.clamp(Integer.parseInt(value), 1, 15);
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
				for(int i = 0; i < BiomeGenBase.biomeList.length; i++)
				{
					if(null != BiomeGenBase.biomeList[i])
					{
						if(value.trim().equals(BiomeGenBase.biomeList[i].biomeName))
						{
							bgbList.add(BiomeGenBase.biomeList[i]);
						}
					}
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
			writer.write(getCommentLine("Whenever a hurt Chocobo is standing on straw less than " + CFG_KEY_PEN_HEAL_CAULDRON_RANGE + " blocks away from"));
			writer.write(getCommentLine("a filled cauldron, it has a" + CFG_KEY_PEN_HEAL_PROBABILITY +" chance every 5 seconds to heal one health point."));
			writer.write(getConfigLine(CFG_KEY_PEN_HEAL_PROBABILITY, Integer.toString(Constants.DEFAULT_PEN_HEAL_PROBABILITY)));
			writer.write(getConfigLine(CFG_KEY_PEN_HEAL_CAULDRON_RANGE, Integer.toString(Constants.DEFAULT_PEN_HEAL_CAULDRON_RANGE)));
			
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
		for(BiomeGenBase bgb : BiomeGenBase.biomeList)
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
		File file = null;
		String fileName = getConfigFileName();
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if (side == Side.SERVER)
		{
			file = new File(MinecraftServer.getServer().getFolderName(), fileName);
		}
    	else if (side == Side.CLIENT)
		{
    		file = new File(Minecraft.getMinecraftDir(), fileName);
		}
		return file;		
	}

	public static String getConfigFileName()
	{
		return CONFIG_FILE_NAME;
	}

}
