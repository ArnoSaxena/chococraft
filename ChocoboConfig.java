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

package net.minecraft.src;

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

import net.minecraft.client.Minecraft;

public class ChocoboConfig
{	
	static String CONFIG_FILE_NAME = "chocobo_config.txt";
	static String CFG_TOKEN_COMMENT = "//";

	static String CFG_KEY_GYSAHL_GREEN_ID = "gysahlGreenid";
	static String CFG_KEY_GYSAHL_CROP_ID = "gysahlCropid";
	static String CFG_KEY_CHOCOBO_RAW_ID = "chocoboRawid";
	static String CFG_KEY_CHOCOBO_COOKED_ID = "chocoboCookedid";
	static String CFG_KEY_CHOCOBO_FEATHER_ID = "chocoboFeatherid";
	static String CFG_KEY_CHOCOBO_SADDLE_ID = "chocoboSaddleid";
	static String CFG_KEY_CHOCOBO_SADDLE_BAGS_ID = "chocoboSaddleBagsid";
	static String CFG_KEY_CHOCOBO_PACK_BAGS_ID = "chocoboPackBagsid";
	static String CFG_KEY_CHOCOBO_WHISTLE_ID = "chocoboWhistleid";
	static String CFG_KEY_CHOCOBO_LOVERLY_ID = "chocoboLoverlyid";
	static String CFG_KEY_CHOCOBO_GOLDEN_ID = "chocoboGoldenid";
	static String CFG_KEY_CHOCOBO_CAKE_ID = "chocoboCakeid";
	static String CFG_KEY_CHOCOBO_PINK_ID = "chocoboPinkid";
	static String CFG_KEY_CHOCOBO_RED_ID = "chocoboRedid";
	static String CFG_KEY_CHIBI_GYSAHL_ID = "chibiGysahlid";
	static String CFG_KEY_CHOCOBO_FERTILIZER_ID = "chocoboFertilizerid";
	static String CFG_KEY_CHOCOPEDIA_ID = "chocopediaid";
	static String CFG_KEY_CHOCOTWEEZER_ID = "chocotweezerid";
	static String CFG_KEY_NETHER_CHOCOBO_EGG_ID = "netherChocoboEggId";
	static String CFG_KEY_SHOW_CHOCO_NAMES = "show_chocobo_names";
	static String CFG_KEY_SPAWN_BIOMES = "spawnBiomes";
	static String CFG_KEY_SPAWN_PROB = "spawnProbability";
	static String CFG_KEY_SPAWN_MIN_GROUP = "spawnMinGroup";
	static String CFG_KEY_SPAWN_MAX_GROUP = "spawnMaxGroup";
	static String CFG_KEY_SPAWN_MAX_CHOCOBOS = "maxChocobos";
	static String CFG_KEY_SURE_SPAWN_THRESHOLD = "sureSpawnThreshold";
	static String CFG_KEY_SPAWN_EVENT_TIME_CYCLE = "spawnEventTimeCycle";
	static String CFG_KEY_CHOCOBO_WING_FLUTTER = "chocobo_wing_flutter";
	static String CFG_TOKEN_ALL = "all";
	static String CFG_TOKEN_CHOCO_BIOMES = "Plains,Desert,Extreme Hills,Forest,Taiga,Swampland,Beach,DesertHills,ForestHills,TaigaHills,Extreme Hills Edge,Jungle,JungleHills";

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

								if(key.equalsIgnoreCase(CFG_KEY_GYSAHL_GREEN_ID))
								{
									mod_chocobo.gysahlGreenid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_GYSAHL_CROP_ID))
								{
									mod_chocobo.gysahlCropid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOBO_RAW_ID))
								{
									mod_chocobo.chocoboRawid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOBO_COOKED_ID))
								{
									mod_chocobo.chocoboCookedid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOBO_FEATHER_ID))
								{
									mod_chocobo.chocoboFeatherid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOBO_SADDLE_ID))
								{
									mod_chocobo.chocoboSaddleid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOBO_SADDLE_BAGS_ID))
								{
									mod_chocobo.chocoboSaddleBagsid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOBO_PACK_BAGS_ID))
								{
									mod_chocobo.chocoboPackBagsid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOBO_WHISTLE_ID))
								{
									mod_chocobo.chocoboWhistleid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOBO_LOVERLY_ID))
								{
									mod_chocobo.chocoboLoverlyid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOBO_GOLDEN_ID))
								{
									mod_chocobo.chocoboGoldenid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOBO_CAKE_ID))
								{
									mod_chocobo.chocoboCakeid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOBO_PINK_ID))
								{
									mod_chocobo.chocoboPinkid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOBO_RED_ID))
								{
									mod_chocobo.chocoboRedid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHIBI_GYSAHL_ID))
								{
									mod_chocobo.chibiGysahlid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOBO_FERTILIZER_ID))
								{
									mod_chocobo.chocoboFertilizerid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOPEDIA_ID))
								{
									mod_chocobo.chocopediaid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOTWEEZER_ID))
								{
									mod_chocobo.chocotweezerid = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_NETHER_CHOCOBO_EGG_ID))
								{
									mod_chocobo.netherChocoboEggId = Integer.parseInt(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SHOW_CHOCO_NAMES))
								{
									mod_chocobo.show_chocobo_names = Boolean.parseBoolean(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_BIOMES))
								{
									mod_chocobo.chocoboSpawnBiomes = getBiomeGenBaseArray(value);
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_PROB))
								{
									int tmpProb = Integer.parseInt(value);
									if(tmpProb > 100)
									{
										mod_chocobo.spawnProbability = 100;
									}
									else if(tmpProb < 1)
									{
										mod_chocobo.spawnProbability = 1;
									}
									else
									{
										mod_chocobo.spawnProbability = tmpProb;
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SURE_SPAWN_THRESHOLD))
								{
									int tmpsst = Integer.parseInt(value);
									if(tmpsst < 0)
									{
										mod_chocobo.sureSpawnThreshold = 0;
									}
									else
									{
										mod_chocobo.sureSpawnThreshold = tmpsst;
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_EVENT_TIME_CYCLE))
								{
									int tmpsetc = Integer.parseInt(value);
									if(tmpsetc < 1)
									{
										mod_chocobo.spawnEventTimeCycle = 1;
									}
									else
									{
										mod_chocobo.spawnEventTimeCycle = tmpsetc;	
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_MIN_GROUP))
								{
									int tmpsming = Integer.parseInt(value);
									if(tmpsming < 1)
									{
										mod_chocobo.chocoboSpawnMinGroupCount = 1;
									}
									else
									{
										mod_chocobo.chocoboSpawnMinGroupCount = tmpsming;	
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_MAX_GROUP))
								{
									int tmpsmaxg = Integer.parseInt(value);
									if(tmpsmaxg < 1)
									{
										mod_chocobo.chocoboSpawnMaxGroupCount = 1;
									}
									else
									{
										mod_chocobo.chocoboSpawnMaxGroupCount = tmpsmaxg;	
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_SPAWN_MAX_CHOCOBOS))
								{
									int tmpsmc = Integer.parseInt(value);
									if(tmpsmc < 1)
									{
										mod_chocobo.maxChocobos = 1;
									}
									else
									{
										mod_chocobo.maxChocobos = tmpsmc;	
									}
								}
								else if(key.equalsIgnoreCase(CFG_KEY_CHOCOBO_WING_FLUTTER))
								{
									mod_chocobo.chocobo_wing_flutter = Boolean.parseBoolean(value);
								}
								
								// debuging
								else if(key.equals("size_x"))
								{
									mod_chocobo.size_x = Integer.parseInt(value);
								}
								else if(key.equals("size_y"))
								{
									mod_chocobo.size_y = Integer.parseInt(value);
								}
								else if(key.equals("size_z"))
								{
									mod_chocobo.size_z = Integer.parseInt(value);
								}
								else if(key.equals("pos_x"))
								{
									mod_chocobo.pos_x = Float.parseFloat(value);
								}
								else if(key.equals("pos_y"))
								{
									mod_chocobo.pos_y = Float.parseFloat(value);
								}
								else if(key.equals("pos_z"))
								{
									mod_chocobo.pos_z = Float.parseFloat(value);
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
		String fileName = getConfigFileName();
		File file = new File(Minecraft.getMinecraftDir(), fileName);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"));

			writer.write(getCommentLine("item ids"));
			writer.write(getConfigLine(CFG_KEY_GYSAHL_GREEN_ID, Integer.toString(mod_chocobo.gysahlGreenid)));
			writer.write(getConfigLine(CFG_KEY_GYSAHL_CROP_ID, Integer.toString(mod_chocobo.gysahlCropid)));
			writer.write(getConfigLine(CFG_KEY_CHOCOBO_RAW_ID, Integer.toString(mod_chocobo.chocoboRawid)));
			writer.write(getConfigLine(CFG_KEY_CHOCOBO_COOKED_ID, Integer.toString(mod_chocobo.chocoboCookedid)));
			writer.write(getConfigLine(CFG_KEY_CHOCOBO_FEATHER_ID, Integer.toString(mod_chocobo.chocoboFeatherid)));
			writer.write(getConfigLine(CFG_KEY_CHOCOBO_SADDLE_ID, Integer.toString(mod_chocobo.chocoboSaddleid)));
			writer.write(getConfigLine(CFG_KEY_CHOCOBO_SADDLE_BAGS_ID, Integer.toString(mod_chocobo.chocoboSaddleBagsid)));
			writer.write(getConfigLine(CFG_KEY_CHOCOBO_PACK_BAGS_ID, Integer.toString(mod_chocobo.chocoboPackBagsid)));
			writer.write(getConfigLine(CFG_KEY_CHOCOBO_WHISTLE_ID, Integer.toString(mod_chocobo.chocoboWhistleid)));
			writer.write(getConfigLine(CFG_KEY_CHOCOBO_LOVERLY_ID, Integer.toString(mod_chocobo.chocoboLoverlyid)));
			writer.write(getConfigLine(CFG_KEY_CHOCOBO_GOLDEN_ID, Integer.toString(mod_chocobo.chocoboGoldenid)));
			writer.write(getConfigLine(CFG_KEY_CHOCOBO_CAKE_ID, Integer.toString(mod_chocobo.chocoboCakeid)));
			writer.write(getConfigLine(CFG_KEY_CHOCOBO_PINK_ID, Integer.toString(mod_chocobo.chocoboPinkid)));
			writer.write(getConfigLine(CFG_KEY_CHOCOBO_RED_ID, Integer.toString(mod_chocobo.chocoboRedid)));
			writer.write(getConfigLine(CFG_KEY_CHIBI_GYSAHL_ID, Integer.toString(mod_chocobo.chibiGysahlid)));
			writer.write(getConfigLine(CFG_KEY_CHOCOBO_FERTILIZER_ID, Integer.toString(mod_chocobo.chocoboFertilizerid)));
			writer.write(getConfigLine(CFG_KEY_CHOCOPEDIA_ID, Integer.toString(mod_chocobo.chocopediaid)));
			writer.write(getConfigLine(CFG_KEY_CHOCOTWEEZER_ID, Integer.toString(mod_chocobo.chocotweezerid)));
			writer.write(getConfigLine(CFG_KEY_NETHER_CHOCOBO_EGG_ID, Integer.toString(mod_chocobo.netherChocoboEggId)));
			writer.write("\n");
			writer.write(getConfigLine(CFG_KEY_SHOW_CHOCO_NAMES, Boolean.toString(true)));
			writer.write(getConfigLine(CFG_KEY_CHOCOBO_WING_FLUTTER, Boolean.toString(true)));
			writer.write("\n");
			writer.write(getCommentLine("add any name of the following list as comma separated values to"));
			writer.write(getCommentLine("the " + CFG_KEY_SPAWN_BIOMES + "key, to have Chocobos spawn in the"));
			writer.write(getCommentLine("designated biomes. Add the token 'all' to have Chocobos spawn in "));
			writer.write(getCommentLine("all biomes."));
			writer.write(getCommentLine(getAllBiomeNames()));
			writer.write(getConfigLine(CFG_KEY_SPAWN_BIOMES, CFG_TOKEN_CHOCO_BIOMES));
			writer.write("\n");
			writer.write(getCommentLine("spawn properties x in a hundred"));
			writer.write(getConfigLine(CFG_KEY_SPAWN_PROB, Integer.toString(mod_chocobo.spawnProbability)));
			writer.write(getConfigLine(CFG_KEY_SPAWN_MIN_GROUP, Integer.toString(mod_chocobo.chocoboSpawnMinGroupCount)));
			writer.write(getConfigLine(CFG_KEY_SPAWN_MAX_GROUP, Integer.toString(mod_chocobo.chocoboSpawnMaxGroupCount)));
			writer.write(getConfigLine(CFG_KEY_SPAWN_MAX_CHOCOBOS, Integer.toString(mod_chocobo.maxChocobos)));
			writer.write(getConfigLine(CFG_KEY_SURE_SPAWN_THRESHOLD, Integer.toString(mod_chocobo.sureSpawnThreshold)));
			writer.write(getConfigLine(CFG_KEY_SPAWN_EVENT_TIME_CYCLE, Integer.toString(mod_chocobo.spawnEventTimeCycle)));
			
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
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
		String fileName = getConfigFileName();
		File file = new File(Minecraft.getMinecraftDir(), fileName);
		return new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
	}

	public static String getConfigFileName()
	{
		return CONFIG_FILE_NAME;
	}

}
