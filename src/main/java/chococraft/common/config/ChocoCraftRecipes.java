package chococraft.common.config;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Created by clienthax on 24/10/2014.
 */
public class ChocoCraftRecipes {

	public static void registerRecipes()
	{
		// chocobo saddle
		GameRegistry.addRecipe(
				new ItemStack(ChocoCraftItems.chocoboSaddleItem, 1),
				"-X-",
				" Y ",
				'X', Items.leather,
				'Y', ChocoCraftItems.chocoboFeatherItem,
				'-', Items.string
		);

		// transforming vanilla saddle into chocobo saddle
		GameRegistry.addShapelessRecipe(
				new ItemStack(ChocoCraftItems.chocoboSaddleItem, 1),
				Items.saddle,
				new ItemStack(ChocoCraftItems.chocoboFeatherItem, 1)
		);

		// chocobo saddle bag
		GameRegistry.addRecipe(
				new ItemStack(ChocoCraftItems.chocoboSaddleBagsItem, 1),
				" Y ",
				"X X",
				" X ",
				'X', Items.leather,
				'Y', ChocoCraftItems.chocoboFeatherItem
		);

		// chocobo pack bag
		GameRegistry.addRecipe(
				new ItemStack(ChocoCraftItems.chocoboPackBagsItem, 1),
				"-Y-",
				"C C",
				"-X-",
				'C', Blocks.wool,
				'X', Items.leather,
				'Y', ChocoCraftItems.chocoboFeatherItem,
				'-', Items.string
		);

		// chocopedia
		GameRegistry.addRecipe(
				new ItemStack(ChocoCraftItems.chocopediaItem, 1),
				"FGF",
				"IBI",
				"FLF",
				'B', Items.book,
				'F', ChocoCraftItems.chocoboFeatherItem,
				'I', new ItemStack(Items.dye, 1, 0),
				'L', new ItemStack(Items.dye, 1, 4),
				'G', Items.gold_nugget
		);

		// chocobo whistle
		GameRegistry.addShapelessRecipe(
				new ItemStack(ChocoCraftItems.chocoboWhistleItem, 1),
				ChocoCraftItems.chocoboFeatherItem,
				new ItemStack(Items.gold_ingot, 1)
		);

		// gysahl seeds
		GameRegistry.addShapelessRecipe(
				new ItemStack(ChocoCraftItems.gysahlSeedsItem, 3),
				new ItemStack(ChocoCraftBlocks.gysahlGreenBlock, 1)
		);

		// gysahl cake
		GameRegistry.addRecipe(
				new ItemStack(ChocoCraftItems.gysahlCakeItem, 1),
				"MGM",
				"SES",
				"WGW",
				'G', ChocoCraftBlocks.gysahlGreenBlock,
				'M', Items.milk_bucket,
				'S', Items.sugar,
				'W', Items.wheat,
				'E', Items.egg
		);

		// gysahl pickles
		GameRegistry.addShapelessRecipe(
				new ItemStack(ChocoCraftItems.gysahlPicklesRawItem, 2),
				new ItemStack(ChocoCraftBlocks.gysahlGreenBlock, 1),
				new ItemStack(Items.sugar, 1)
		);

		// pink gysahl
		GameRegistry.addShapelessRecipe(
				new ItemStack(ChocoCraftItems.gysahlPinkItem, 1),
				new ItemStack(ChocoCraftBlocks.gysahlGreenBlock, 1),
				new ItemStack(Items.dye, 1, 9)
		);

		// red gysahl
		GameRegistry.addShapelessRecipe(
				new ItemStack(ChocoCraftItems.gysahlRedItem, 1),
				new ItemStack(ChocoCraftBlocks.gysahlGreenBlock, 1),
				new ItemStack(Items.dye, 1, 1)
		);

		// chocobo disguise
		GameRegistry.addRecipe(
				new ItemStack(ChocoCraftItems.chocoDisguiseHelmetItem, 1),
				"YYY",
				"Y Y",
				'Y', ChocoCraftItems.chocoboFeatherItem
		);

		GameRegistry.addRecipe(
				new ItemStack(ChocoCraftItems.chocoDisguisePlateItem, 1),
				"Y Y",
				"YYY",
				"YYY",
				'Y', ChocoCraftItems.chocoboFeatherItem
		);

		GameRegistry.addRecipe(
				new ItemStack(ChocoCraftItems.chocoDisguiseLegsItem, 1),
				"YYY",
				"Y Y",
				"Y Y",
				'Y', ChocoCraftItems.chocoboFeatherItem
		);

		GameRegistry.addRecipe(
				new ItemStack(ChocoCraftItems.chocoDisguiseBootsItem, 1),
				"Y Y",
				"Y Y",
				'Y', ChocoCraftItems.chocoboFeatherItem
		);

		GameRegistry.addRecipe(
				new ItemStack(ChocoCraftBlocks.strawBlock, 8),
				"WW",
				'W', Items.wheat
		);

		// alternative recipes for vanilla items with chocobo feathers
		// arrows
		GameRegistry.addRecipe(
				new ItemStack(Items.arrow, 4),
				" F ",
				" S ",
				" Y ",
				'F', Items.flint,
				'S', Items.stick,
				'Y', ChocoCraftItems.chocoboFeatherItem
		);

		// writable Book
		GameRegistry.addShapelessRecipe(
				new ItemStack(Items.writable_book, 1),
				new ItemStack(Items.book, 1),
				new ItemStack(Items.dye, 1, 0),
				new ItemStack(ChocoCraftItems.chocoboFeatherItem, 1)
		);

		// leash (lead)
		GameRegistry.addRecipe(
				new ItemStack(Items.lead, 2),
				"SS ",
				"SF ",
				"  S",
				'S', Items.string,
				'F', ChocoCraftItems.chocoboFeatherItem
		);

	}

	public static void registerSmeltingRecipes()
	{
		GameRegistry.addSmelting(ChocoCraftItems.chocoboLegRawItem, new ItemStack(ChocoCraftItems.chocoboLegCookedItem), 0.1F);
		GameRegistry.addSmelting(ChocoCraftItems.gysahlPicklesRawItem, new ItemStack(ChocoCraftItems.gysahlPicklesItem), 0.1F);
	}
}
