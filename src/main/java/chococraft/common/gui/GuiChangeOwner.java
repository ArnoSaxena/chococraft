// <copyright file="GuiChangeOwner">
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
// <date>2012-12-05</date>
// <summary>Gui for the ChocoCraft owner change dialog</summary>

package chococraft.common.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChicobo;

public class GuiChangeOwner extends GuiScreen
{
	private String newOwner;
	private final EntityAnimalChocobo chocobo;
	private GuiScreen pGuiScreen;
	private GuiScreen ppGuiScreen;

	public GuiChangeOwner(GuiScreen pScreen, GuiScreen ppScreen, EntityAnimalChocobo chocobo, String newOwner)
	{
		this.newOwner = newOwner;
		this.chocobo = chocobo;
		this.pGuiScreen = pScreen;
		this.ppGuiScreen = ppScreen;
	}

	public void updateScreen()
	{
	}

	@SuppressWarnings("unchecked")
	public void initGui()
	{
		//StringTranslate stringtranslate = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		//this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, stringtranslate.translateKey("Accept")));
		//this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Accept"));
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Cancel"));
	}

	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton guibutton)
	{
		if (!guibutton.enabled)
		{
			return;
		}
		if (guibutton.id == 1)
		{
			this.mc.displayGuiScreen(this.ppGuiScreen);
		}
		else if (guibutton.id == 0)
		{
			if(this.isInPlayerList(this.newOwner))
			{
				this.chocobo.changeOwner(this.newOwner);
			}
			else
			{
				this.mc.displayGuiScreen(this.pGuiScreen);
			}
			
			this.mc.displayGuiScreen(this.ppGuiScreen);
		}
	}

	protected void mouseClicked(int mouseX, int mouseY, int which)
	{
		super.mouseClicked(mouseX, mouseY, which);
	}

	public void drawScreen(int scrX, int scrY, float f)
	{
		//StringTranslate stringtranslate = StringTranslate.getInstance();
		this.drawDefaultBackground();

		//String message = stringtranslate.translateKey(messageSB.toString());
		
		this.drawCenteredString(this.mc.fontRenderer, "Give your " + ((this.chocobo instanceof EntityChicobo) ? "Chicobo" : "Chocobo") + " to " + this.newOwner + "?", this.width / 2, (this.height / 4 - 60) + 20, 0xffffff);
		super.drawScreen(scrX, scrY, f);
	}
	
	private boolean isInPlayerList(String name)
	{
		for(Object playerObj : this.chocobo.worldObj.playerEntities)
		{
			if(playerObj instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)playerObj;
				if(player.getDisplayName().equals(name))
				{
					return true;
				}
			}
		}
		return false;
	}
}
