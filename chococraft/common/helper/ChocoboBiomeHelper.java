package chococraft.common.helper;

import java.util.ArrayList;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.BiomeGenEnd;
import net.minecraft.src.BiomeGenHell;
import net.minecraft.src.World;

public class ChocoboBiomeHelper
{
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static BiomeGenBase[] getBiomeGenBaseArray()
	{
		ArrayList bgbList= new ArrayList();
		for(BiomeGenBase bgb : BiomeGenBase.biomeList)
		{
			if(null != bgb)
			{
				if(!(bgb instanceof BiomeGenHell) && !(bgb instanceof BiomeGenEnd))
				{
					bgbList.add(bgb);
				}
			}
		}		
		BiomeGenBase[] returnBgbList = new BiomeGenBase[bgbList.size()];
		int i = 0;
		for (Object tmpBgb : bgbList) {
			if(tmpBgb instanceof BiomeGenBase)
			{
				returnBgbList[i] = (BiomeGenBase)tmpBgb;
			}
			i++;
		}
		return returnBgbList;
	}
	
	public static String[] getBiomeGenBaseNameArray()
	{
		return getBiomeGenNameArray(getBiomeGenBaseArray());
	}
	
	public static String[] getBiomeGenNameArray(BiomeGenBase[] bgbArray)
	{
		if(null != bgbArray)
		{
			if (0 != bgbArray.length)
			{
				String[] bgbNames = new String[bgbArray.length];
				int idx = 0;
				for (BiomeGenBase bgb : bgbArray) {
					if(null != bgb)
					{
						bgbNames[idx] = bgb.biomeName;
						idx++;
					}
				}
				return bgbNames;
			}
			return null;
		}
		return getBiomeGenBaseNameArray();
	}
	
	public static boolean isBiomeHell(World world, int posX, int posZ)
	{
		BiomeGenBase chocoBgb = world.getBiomeGenForCoords(posX, posZ);
		if(chocoBgb instanceof BiomeGenHell)
		{
			return true;
		}
		return false;
	}
	
	public static boolean isWorldHell(World world)
	{
		return isBiomeHell(world, 0, 0);
	}
}
