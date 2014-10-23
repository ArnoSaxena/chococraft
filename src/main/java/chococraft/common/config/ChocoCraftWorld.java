package chococraft.common.config;

import chococraft.common.ModChocoCraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by clienthax on 24/10/2014.
 */
public class ChocoCraftWorld {

	public static void registerDungeonLoot()
	{
		if(ModChocoCraft.chocopediaInDungeons)
		{
			ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(ChocoCraftItems.chocopediaItem),Constants.CHOCOPEDIA_DUNGEON_MIN,Constants.CHOCOPEDIA_DUNGEON_MAX,Constants.CHOCOPEDIA_DUNGEON_WEIGHT));
			ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(ChocoCraftItems.chocopediaItem),Constants.CHOCOPEDIA_DUNGEON_MIN,Constants.CHOCOPEDIA_DUNGEON_MAX,Constants.CHOCOPEDIA_DUNGEON_WEIGHT));
			ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(ChocoCraftItems.chocopediaItem),Constants.CHOCOPEDIA_DUNGEON_MIN,Constants.CHOCOPEDIA_DUNGEON_MAX,Constants.CHOCOPEDIA_DUNGEON_WEIGHT));
			ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(ChocoCraftItems.chocopediaItem),Constants.CHOCOPEDIA_DUNGEON_MIN,Constants.CHOCOPEDIA_DUNGEON_MAX,Constants.CHOCOPEDIA_DUNGEON_WEIGHT));
			ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(ChocoCraftItems.chocopediaItem),Constants.CHOCOPEDIA_DUNGEON_MIN,Constants.CHOCOPEDIA_DUNGEON_MAX,Constants.CHOCOPEDIA_DUNGEON_WEIGHT));
			ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(ChocoCraftItems.chocopediaItem),Constants.CHOCOPEDIA_DUNGEON_MIN,Constants.CHOCOPEDIA_DUNGEON_MAX,Constants.CHOCOPEDIA_DUNGEON_WEIGHT));
			ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(new ItemStack(ChocoCraftItems.chocopediaItem),Constants.CHOCOPEDIA_DUNGEON_MIN,Constants.CHOCOPEDIA_DUNGEON_MAX,Constants.CHOCOPEDIA_DUNGEON_WEIGHT));
		}
	}

	public static void addGrassDrops()
	{
		MinecraftForge.addGrassSeed(new ItemStack(ChocoCraftItems.gysahlSeedsItem), ModChocoCraft.gysahlSeedGrassDropWeight);
	}
}
