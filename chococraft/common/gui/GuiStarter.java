package chococraft.common.gui;

import net.minecraft.client.Minecraft;
import chococraft.common.entities.EntityAnimalChocobo;
import cpw.mods.fml.client.FMLClientHandler;

public class GuiStarter
{
	public static void startChocopediaGui(EntityAnimalChocobo chocobo)
	{
		Minecraft client = FMLClientHandler.instance().getClient();
		client.displayGuiScreen(new GuiChocopedia(client.currentScreen, chocobo));
	}
}
