// <copyright file="ChocopediaPages.java">
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
// <date>2013-06-08</date>
// <summary>content of "The Chocopedia" book</summary>

package chococraft.common.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;

public class ChocopediaPages
{
	private static ChocopediaPages instance;

	private List<String> chocopediaPages;
	private int nPages;

	public static ChocopediaPages Instance()
	{
		if(null == ChocopediaPages.instance)
		{
			ChocopediaPages.instance = new ChocopediaPages();
		}
		return ChocopediaPages.instance;
	}
	
	private ChocopediaPages()
	{
		this.chocopediaPages = new ArrayList<String>();
		this.nPages = 0;
		this.chocopediaPages.add("page 00 dummy");
		this.nPages++;
		this.chocopediaPages.add( 
				"Table of Contents\n\n" +
				"I. Chocobos\n" + 
				"II. In the wild\n" + 
				"III. Gysahl Greens\n" +
				" .1 Gysahl Mutations\n" +
				" .2 Gysahl Pickles\n" +
				"IV. Domestication\n" +
				" .1 Breeding Chocobos\n" +
				" .2 Chocobo Colours\n" +
				"V. The Nether\n" +
				"VI. Chocobo Pen\n" +
				"VII. Chocobo Cake\n");
		this.nPages++;
		this.chocopediaPages.add(
				"I. Chocobos\n" +
				"\n" +
				"These large, herbiv- orous birds are largely unable " +
				"to fly, but their powerful legs allow them to run at " +
				"high speeds. Their size and impressive speed make them " +
				"a popular choice when looking for a mount.\n");
		this.nPages++;
		this.chocopediaPages.add(
				"Although largely too small to allow real flight, the Chocobo's " +
				"wings enable the birds to glide and avoid fall damage to itself " +
				"and its rider up to a cer- tain hight.\n\nDomesticated Chocobos " +
				"can be deployed as mounts and transport animals.");
		this.nPages++;
		this.chocopediaPages.add(
				"There are even re- ports of desperate adventurers having to rely" +
				"to their Chocobos as a source of food.");
		this.nPages++;
		this.chocopediaPages.add(
				"II. In the wild\n\nChocobos, in their yellow variety, found wild " +
				"in most parts of the world. Flocks of mostly three, some- times five " +
				"animals roam free in the wild. How- ever they are very scarce and an " +
				"inter- ested ornithologist would have to search");
		this.nPages++;
		this.chocopediaPages.add(
				"wide areas to find a flock.");
		this.nPages++;
		this.chocopediaPages.add(
				"III. Gysahl Greens\n\n" +
				"The Chocobo's fa- " +
				"vorite food are " +
				"Gysahl Greens, these " +
				"thick red root vegetables " +
				"can be found " +
				"growing widespread in " +
				"all fertile areas. " +
				"These plants can be " +
				"crafted into seeds. " +
				"The seeds then can " +
				"be used to domestically grow");
		this.nPages++;
		this.chocopediaPages.add(
				"Gysahl Greens on tilted and moisturised land.");
		this.nPages++;
		this.chocopediaPages.add(
				"III.1 Gysahl Mutations\n\nWhen domestically growing Gysahl Greens, there " +
				"is a chance of the root mutating into a Loverly or even Golden Gysahl. " +
				"These special roots can be feed to two Chocobos of opposite gender, to " +
				"initialise the mating process.");
		this.nPages++;
		this.chocopediaPages.add(
				"III.2 Gysahl Pickles\n\nGysahl Greens and sugar can be crafted into raw " +
				"Gysahl pickles, which can be cooked in a furnace to receive Gysahl pickles. " +
				"These treats are not only tasty, but also very nourishing.");
		this.nPages++;
		this.chocopediaPages.add(
				"IV. Domestication\n\nChocobos can be tamed by feeding" +
				" them Gysahl Greens, the birds are very fond of the root vegetable and will " +
				"sometimes form a bond to whomever is feeding them their fa- vorite food item. If " +
				"tamed, the owner can fit either a chocobo");
		this.nPages++;
		this.chocopediaPages.add(
				"saddle or pack bags on a Chocobo. Fitted with " +
				"a saddle the Chocobo can be additionally equipped with saddle bags. (" +
				EnumChatFormatting.ITALIC +
				"Pack bags and saddle bags can be accessed by shift-right click" +
				EnumChatFormatting.RESET +
				").");
		this.nPages++;
		this.chocopediaPages.add(
				"IV.1 Chocobo Breeding\n\nIf feed with Loverly or Golden Gysahls, Chocobos of " +
				"different gender can be breed to produce an off- spring. These infant Chocobos are " +
				"called Chicobos and even- tually grow into a Chocobo. Besides in- creasing the number");
		this.nPages++;
		this.chocopediaPages.add(
				"of Chocobos in a domesticated flock, breeding is the only way " +
				"to obtain the different varieties of Chocobos available. There is a slight chance " +
				"of mutation, which can be in- creased with the use of Golden Gysahls. The following " +
				"combinations of parents can cause");
		this.nPages++;
		this.chocopediaPages.add(
				"their offspring to mutate into these subspecies:\n\n " +
				"1. " +
				EnumChatFormatting.GOLD +
				"Yellow" +
				EnumChatFormatting.BLACK +
				" + " +
				EnumChatFormatting.GOLD +
				"Yellow" +
				EnumChatFormatting.BLACK +
				"\n = " +
				EnumChatFormatting.GREEN +
				"Green" +
				EnumChatFormatting.BLACK +
				" or " +
				EnumChatFormatting.BLUE +
				"Blue" +
				EnumChatFormatting.BLACK +
				"\n\n 2. " +
				EnumChatFormatting.GREEN +
				"Green" +
				EnumChatFormatting.BLACK +
				" + " +
				EnumChatFormatting.BLUE +
				"Blue" +
				EnumChatFormatting.BLACK +
				"\n = " +
				EnumChatFormatting.GRAY +
				"White" +
				EnumChatFormatting.BLACK +
				"\n\n 3. " +
				EnumChatFormatting.GOLD +
				"Yellow" +
				EnumChatFormatting.BLACK +
				" + " +
				EnumChatFormatting.GRAY +
				"White" +
				EnumChatFormatting.BLACK +
				"\n = " +
				"Black");
		this.nPages++;
		this.chocopediaPages.add(
				"4. " +
				"Black" +
				" + " +
				EnumChatFormatting.GRAY +
				"White" +
				EnumChatFormatting.BLACK +
				"\n = " +
				EnumChatFormatting.GOLD +
				"Gold" +
				EnumChatFormatting.BLACK +
				"\n\nTo enable the off- spring to mutate into a golden " +
				"Chocobo, the use of Golden Gysahls is mandatory.");
		this.nPages++;
		this.chocopediaPages.add(
				"IV.2 Chocobo Colours\n\n" +
				EnumChatFormatting.GOLD +
				"Yellow Chocobos" +
				EnumChatFormatting.BLACK +
				" are the average variety and have no " +
				"clear abilities, they can be used however as fast riding and transport animals." +
				"\n\n" +
				EnumChatFormatting.GREEN +
				"Green Chocobos" +
				EnumChatFormatting.BLACK +
				" have the ability to climb, which means they do");
		this.nPages++;
		this.chocopediaPages.add(
				"not have the need to jump when ascending a block level. They are slightly faster " +
				"than their yellow cousins.\n\n" +
				EnumChatFormatting.BLUE +
				"Blue Chocobos" +
				EnumChatFormatting.BLACK +
				" have the ability to travel fast on water. " +
				"Al- though all Chocobos have the ability to swim, the blue ones can do it very " +
				"fast,");
		this.nPages++;
		this.chocopediaPages.add(
				"thus are the first choice of transpor- tation when traveling by water route." +
				"\n\n" +
				EnumChatFormatting.GRAY +
				"White Chocobos" +
				EnumChatFormatting.BLACK +
				" have the combined abilities to climb like the green Chocobos " +
				"and to travel fast on water like the blue. In ad- dition they are slightly faster " +
				"than the blue");
		this.nPages++;
		this.chocopediaPages.add(
				"or green varieties.\n\n" +
				"Black Chocobos" +
				" have the " +
				"abilities to jump very hight, climb and travel fast on water. Also they are " +
				"faster than the white ones.\n\n" +
				EnumChatFormatting.GOLD +
				"Golden Chocobos" +
				EnumChatFormatting.BLACK +
				" are the fastes variety and are not " +
				"only able to glide like the other");
		this.nPages++;
		this.chocopediaPages.add(
				"subspecies, but can fully fly.\n\nAll " +
				"Chocobo sub- species can glide and avoid fall damage up to a certain hight. " +
				"Golden Chocobos don't take fall damage at all.");
		this.nPages++;
		this.chocopediaPages.add(
				"V. The Nether\n\nThe Nether is popu- lated by a special breed of Chocobo. The " +
				"Purple Chocobos roaming the hostile tunnels and caves of the nether in " +
				"search of the scarce avail- able food. This makes them even harder to find than their yellow");
		this.nPages++;
		this.chocopediaPages.add(
				"cousins. Living in the nether had the purple Chocobos develop " +
				"the ability to swim in lava and fly.");
		this.nPages++;
		this.chocopediaPages.add(
				"VI. Chocobo Pen\n\nAn area covered with straw and equipped with a water filled " +
				"cauldron will count as a Chocobo pen. If a hurt Chocobo will stand on the straw " +
				"near the cauldron it will automatically heal over time.");
		this.nPages++;
		this.chocopediaPages.add(
				"VII. Chocobo Cake\n\nChocoob Cakes can be used to have Chicobos instantly grow up " +
				"into Chocobos.");
		this.nPages++;
	}
	
	public String getPage(int pageIndex)
	{
		if(pageIndex >= 0 && pageIndex < this.nPages)
		{
			return this.chocopediaPages.get(pageIndex);
		}
		return "";
	}
	
	public int getNPages()
	{
		return this.nPages;
	}
}