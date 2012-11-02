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
	static String CFG_KEY_YELLOW_SPAWN_BIOMES = "yellowSpawnBiomes";
	static String CFG_KEY_YELLOW_SPAWN_RATE = "yellowSpawnRate";
	static String CFG_KEY_YELLOW_SPAWN_MIN = "yellowSpawnMin";
	static String CFG_KEY_YELLOW_SPAWN_MAX = "yellowSpawnMax";
	static String CFG_KEY_CHOCOBO_WING_FLUTTER = "chocoboWingFlutter";
	static String CFG_TOKEN_ALL = "all";
	//static String CFG_TOKEN_CHOCO_BIOMES = "Plains,Desert,Extreme Hills,Forest,Taiga,Swampland,Beach,DesertHills,ForestHills,TaigaHills,Extreme Hills Edge,Jungle,JungleHills";
	static String CFG_KEY_FEATHER_DELAY_RANDOM = "featherDelayRandom";
	static String CFG_KEY_FEATHER_DELAY_STATIC = "featherDelayStatic";
	static String CFG_KEY_FEATHER_DROP_CHANCE = "featherDropChance";

	public static void readConfigFile()
	{
		try {
			BufferedReader reader = getConfigReader();
			String line;

			while (null != (line = reader.readLine())) {
				if (0 < (line.trim().length()) && (!line.trim().startsWith(CFG_TOKEN_COMMENT))) {
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
							try {

								if(key.equalsIgnoreCase(CFG_KEY_SHOW_CHOCO_NAMES))
								{
									ModChocoCraft.showChocoboNames = Boolean.parseBoolean(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_YELLOW_SPAWN_BIOMES))
								{
									ModChocoCraft.yellowSpawnBiomes = getBiomeGenBaseArray(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_YELLOW_SPAWN_RATE))
								{
									int tmpProb = Integer.parseInt(value);
									if(tmpProb > 200)
									{
										ModChocoCraft.yellowSpawnRate = 200;
									}
									else if(tmpProb < 1)
									{
										ModChocoCraft.yellowSpawnRate = 1;
									}
									else
									{
										ModChocoCraft.yellowSpawnRate = tmpProb;
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_YELLOW_SPAWN_MIN))
								{
									int tmpsming = Integer.parseInt(value);
									if(tmpsming < 1)
									{
										ModChocoCraft.yellowSpawnMin = 1;
									}
									else
									{
										ModChocoCraft.yellowSpawnMin = tmpsming;	
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_YELLOW_SPAWN_MAX))
								{
									int tmpsmaxg = Integer.parseInt(value);
									if(tmpsmaxg < 1)
									{
										ModChocoCraft.yellowSpawnMax = 1;
									}
									else
									{
										ModChocoCraft.yellowSpawnMax = tmpsmaxg;	
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
							} catch (NumberFormatException e) {
								// do nothing, just keep the default config values...
							}
						}
					}
				}
			}
			reader.close();
		} catch (FileNotFoundException fnfe) {
			createNewConfigFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static BiomeGenBase[] getBiomeGenBaseArray(String valueString)
	{
		if(valueString.equals(CFG_TOKEN_ALL))
		{
			return ChocoboHelper.getBiomeGenBaseArray();
		}
		String[] values = valueString.split(",");
		ArrayList<BiomeGenBase> bgbList= new ArrayList<BiomeGenBase>();

		for(String value : values)
		{
			if(!value.trim().isEmpty())
			{
				if(value.trim().equals(CFG_TOKEN_ALL))
				{
					return ChocoboHelper.getBiomeGenBaseArray();
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
			return ChocoboHelper.getBiomeGenBaseArray();
		}
	}

	private static void createNewConfigFile()
	{
		File file = getConfigFile();		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"));
			
			writer.write("\n");
			writer.write(getConfigLine(CFG_KEY_SHOW_CHOCO_NAMES, Boolean.toString(Constants.DEFAULT_SHOW_CHOCOBO_NAMES)));
			writer.write(getConfigLine(CFG_KEY_CHOCOBO_WING_FLUTTER, Boolean.toString(Constants.DEFAULT_CHOCOBO_WING_FLUTTER)));
			
			writer.write("\n");
			writer.write(getCommentLine("add any name of the following list as comma separated values to"));
			writer.write(getCommentLine("the " + CFG_KEY_YELLOW_SPAWN_BIOMES + "key, to have Chocobos spawn in the"));
			writer.write(getCommentLine("designated biomes. Add the token 'all' to have Chocobos spawn in "));
			writer.write(getCommentLine("all biomes."));
			writer.write(getCommentLine("Possible biome names:"));
			writer.write(getCommentLine(getAllBiomeNames()));
			writer.write(getConfigLine(CFG_KEY_YELLOW_SPAWN_BIOMES, writeCSVBiomeNames(ModChocoCraft.yellowSpawnBiomes)));
			
			writer.write("\n");
			writer.write(getCommentLine("Yellow Chocobos will spawn with a weighted spawn rate given as " + CFG_KEY_YELLOW_SPAWN_RATE + ". "));
			writer.write(getCommentLine("They will spawn in groups with at least " + CFG_KEY_YELLOW_SPAWN_MIN + " and up to " + CFG_KEY_YELLOW_SPAWN_MAX));
			writer.write(getCommentLine("individuals."));
			writer.write(getConfigLine(CFG_KEY_YELLOW_SPAWN_RATE, Integer.toString(ModChocoCraft.yellowSpawnRate)));
			writer.write(getConfigLine(CFG_KEY_YELLOW_SPAWN_MIN, Integer.toString(ModChocoCraft.yellowSpawnMin)));
			writer.write(getConfigLine(CFG_KEY_YELLOW_SPAWN_MAX, Integer.toString(ModChocoCraft.yellowSpawnMax)));
			
			writer.write("\n");
			writer.write(getCommentLine("tamed chocobos have a chance of "+ CFG_KEY_FEATHER_DROP_CHANCE +" in 100 to drop a feather every"));
			writer.write(getCommentLine(CFG_KEY_FEATHER_DELAY_RANDOM+"/20 to "+CFG_KEY_FEATHER_DELAY_RANDOM+"/20 plus "+CFG_KEY_FEATHER_DELAY_STATIC+"/20 seconds"));
			writer.write(getCommentLine(""));
			writer.write(getConfigLine(CFG_KEY_FEATHER_DELAY_RANDOM, Integer.toString(ModChocoCraft.featherDelayRandom)));
			writer.write(getConfigLine(CFG_KEY_FEATHER_DELAY_STATIC, Integer.toString(ModChocoCraft.featherDelayStatic)));
			writer.write(getConfigLine(CFG_KEY_FEATHER_DROP_CHANCE, Integer.toString(ModChocoCraft.featherDropChance)));
			
			writer.close();

		} catch (Exception e) {
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
		for (String value : values) {
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
		if(!comment.endsWith("\n")) {
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
