// <copyright file="GuiStarter.java">
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
// <date>2012-10-25</date>
// <summary>Separate class for starting a GUI, so that classes derived from
// GuiScreen are not mentioned in the Entity classes and will not cause a 
// server crash.</summary>

package chococraft.common.gui;

import net.minecraft.client.Minecraft;
import chococraft.common.entities.EntityAnimalChocobo;
import cpw.mods.fml.client.FMLClientHandler;

public class GuiStarter
{
	public static void startChocopediaGui(EntityAnimalChocobo chocobo)
	{
		Minecraft client = FMLClientHandler.instance().getClient();
		client.displayGuiScreen(new GuiChocopedia(client.currentScreen, chocobo, client.thePlayer));
	}
}
