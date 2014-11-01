package chococraft.common.config;

import chococraft.common.ModChocoCraft;
import chococraft.common.items.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

/**
 * Created by clienthax on 23/10/2014.
 */
public class ChocoCraftItems {

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

	public static void registerItems() {

		// Chocopedia
		chocopediaItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_CHOCOPEDIA).setMaxStackSize(1);
		chocopediaItem.setCreativeTab(CreativeTabs.tabTools);
		GameRegistry.registerItem(chocopediaItem, "Chocopedia");

		// Chocobo Feather
		chocoboFeatherItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_FEATHER).setMaxStackSize(64);
		chocoboFeatherItem.setCreativeTab(CreativeTabs.tabMaterials);
		GameRegistry.registerItem(chocoboFeatherItem, "Chocobo_Feather");

		// riding gear
		// Chocobo Saddle
		chocoboSaddleItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_SADDLE).setMaxStackSize(5);
		chocoboSaddleItem.setCreativeTab(CreativeTabs.tabTransport);
		GameRegistry.registerItem(chocoboSaddleItem, "Chocobo_Saddle");

		// Chocobo Saddle Bags
		chocoboSaddleBagsItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_SADDLEBAG).setMaxStackSize(8);
		chocoboSaddleBagsItem.setCreativeTab(CreativeTabs.tabTransport);
		GameRegistry.registerItem(chocoboSaddleBagsItem, "Chocobo_Saddle_Bags");

		// Chocobo Pack Bags
		chocoboPackBagsItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_PACKBAG).setMaxStackSize(8);
		chocoboPackBagsItem.setCreativeTab(CreativeTabs.tabTransport);
		GameRegistry.registerItem(chocoboPackBagsItem, "Chocobo_Pack_Bags");

		// Gysahls
		// Gysahl seeds
		gysahlSeedsItem = (new ItemGysahlSeeds(ChocoCraftBlocks.gysahlStemBlock, Blocks.farmland));
		//gysahlSeedsItem.setCreativeTab(chocoboCreativeItems);
		GameRegistry.registerItem(gysahlSeedsItem, "Gysahl_Seeds");

		// Loverly Gysahl
		gysahlLoverlyItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_GY_LOVERLY).setMaxStackSize(64);
		gysahlLoverlyItem.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(gysahlLoverlyItem, "Loverly_Gysahl");

		// Golden Gysahl
		gysahlGoldenItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_GY_GOLDEN).setMaxStackSize(64);
		gysahlGoldenItem.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(gysahlGoldenItem, "Golden_Gysahl");

		// Pink Gysahl
		gysahlPinkItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_GY_PINK).setMaxStackSize(64);
		gysahlPinkItem.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(gysahlPinkItem, "Pink_Gysahl");

		// Red Gysahl
		gysahlRedItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_GY_RED).setMaxStackSize(64);
		gysahlRedItem.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(gysahlRedItem, "Red_Gysahl");

		// Gysahl Cake
		gysahlCakeItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_GY_CAKE).setMaxStackSize(8);
		gysahlCakeItem.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(gysahlCakeItem, "Gysahl Cake");

		// Chocob Whistle
		chocoboWhistleItem = new ChocoboItem().setUnlocalizedName(Constants.KEY_WHISTLE).setMaxStackSize(64);
		chocoboWhistleItem.setCreativeTab(CreativeTabs.tabTools);
		GameRegistry.registerItem(chocoboWhistleItem, "Chocobo_Whistle");

		// Nether Chocobo Egg
		purpleChocoboEggItem = new ItemPurpleChocoboEgg();
		GameRegistry.registerItem(purpleChocoboEggItem, "Purple_Chocobo_Egg");

		// Armour
		// Chocodisguise Helmet
		chocoDisguiseHelmetItem = new ChocoboItemDisguise(ChocoboArmourMaterial.CHOCOFEATHER, ModChocoCraft.proxy.addArmor(Constants.KEY_DISGUISE), 0);
		chocoDisguiseHelmetItem.setUnlocalizedName(Constants.KEY_DISGUISE_HEAD);
		GameRegistry.registerItem(chocoDisguiseHelmetItem, "Chocodisguise_Helmet");

		// Chocodisguise Plate
		chocoDisguisePlateItem = new ChocoboItemDisguise(ChocoboArmourMaterial.CHOCOFEATHER, ModChocoCraft.proxy.addArmor(Constants.KEY_DISGUISE), 1);
		chocoDisguisePlateItem.setUnlocalizedName(Constants.KEY_DISGUISE_BODY);
		GameRegistry.registerItem(chocoDisguisePlateItem, "Chocodisguise_Body");

		// Chocodisguise Legs
		chocoDisguiseLegsItem = new ChocoboItemDisguise(ChocoboArmourMaterial.CHOCOFEATHER, ModChocoCraft.proxy.addArmor(Constants.KEY_DISGUISE), 2);
		chocoDisguiseLegsItem.setUnlocalizedName(Constants.KEY_DISGUISE_LEGS);
		GameRegistry.registerItem(chocoDisguiseLegsItem, "Chocodisguise_Legs");

		// Chocodisguise Boots
		chocoDisguiseBootsItem = new ChocoboItemDisguise(ChocoboArmourMaterial.CHOCOFEATHER, ModChocoCraft.proxy.addArmor(Constants.KEY_DISGUISE), 3);
		chocoDisguiseBootsItem.setUnlocalizedName(Constants.KEY_DISGUISE_BOOTS);
		GameRegistry.registerItem(chocoDisguiseBootsItem, "Chocodisguise_Boots");

		// food items
		// Raw Chocobo Leg
		chocoboLegRawItem = new ChocoboItemFood(4, true);
		chocoboLegRawItem.setUnlocalizedName(Constants.KEY_LEG_RAW);
		GameRegistry.registerItem(chocoboLegRawItem, "Raw_Chocobo_Leg");

		// Cooked Chocobo Leg
		chocoboLegCookedItem = new ChocoboItemFood(8, false);
		chocoboLegCookedItem.setUnlocalizedName(Constants.KEY_LEG_COOKED);
		GameRegistry.registerItem(chocoboLegCookedItem, "Cooked_Chocobo_Leg");

		// Gysahl Pickles
		gysahlPicklesRawItem = new ChocoboItem();
		gysahlPicklesRawItem.setUnlocalizedName(Constants.KEY_GY_PICKLES_RAW).setMaxStackSize(64);
		gysahlPicklesRawItem.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(gysahlPicklesRawItem, "Gysahl_Raw_Pickles");

		gysahlPicklesItem = new ChocoboItemFood(2, false);
		gysahlPicklesItem.setUnlocalizedName(Constants.KEY_GY_PICKLES);
		gysahlPicklesItem.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(gysahlPicklesItem, "Gysahl_Pickles");
	}
}
