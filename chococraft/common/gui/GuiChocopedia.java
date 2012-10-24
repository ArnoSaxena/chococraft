// <copyright file="GuiChocopedia.java">
// Copyright (c) 2012 All Right Reserved, http://chococraft.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <author>EddieV (initial source)</author>
// <email>al-s@gmx.de</email>
// <date>2012-05-09</date>
// <summary>Gui for the Chocopedia item</summary>

package chococraft.common.gui;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.StringTranslate;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;

import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChocoboRideable;

public class GuiChocopedia extends GuiScreen
{
	private EntityAnimalChocobo chocobo;
	private GuiScreen parentGuiScreen;
	private GuiButton namingButton;
	private GuiButton followingButton;
	private GuiButton removeSaddleButton;

	public GuiChocopedia(GuiScreen guiscreen, EntityAnimalChocobo entitychocobo)
	{
		this.chocobo = entitychocobo;
		this.parentGuiScreen = guiscreen;
	}

	public void updateScreen()
	{
	}

	@SuppressWarnings("unchecked")
	public void initGui()
	{
		StringTranslate stringtranslate = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		this.controlList.clear();

		//int yPos = 48;
		int yPos = 24;
		int xPos = this.width / 2 - 100;

		// rename button
		this.controlList.add(this.createGuiButton(0, xPos, (yPos += 24), stringtranslate.translateKey("Rename")));

		// hide name button
		String lblNameShown = (this.chocobo.isHidename()) ? "Name Hidden" : "Name Shown";
		this.namingButton = this.createGuiButton(2, xPos, (yPos += 24), stringtranslate.translateKey(lblNameShown));
		this.controlList.add(this.namingButton);

		// following button
		String lblFollowing = (this.chocobo.isFollowing()) ? "Following" : "Not Following";
		this.followingButton = this.createGuiButton(3, xPos, (yPos += 24), stringtranslate.translateKey(lblFollowing));
		this.controlList.add(this.followingButton);

		// confirm button
		this.controlList.add(this.createGuiButton(1, xPos, (yPos += 24), stringtranslate.translateKey("Confirm")));
		
		// remove saddle button
		if(this.chocobo instanceof EntityChocoboRideable)
		{
			EntityChocoboRideable chocoboRideable = (EntityChocoboRideable)this.chocobo;
			if(chocoboRideable.isSaddled() || chocoboRideable.isPackBagged())
			{
				String lblRemoveSaddle = "Drop Gear";
				this.removeSaddleButton = this.createGuiButton(4, xPos, (yPos += 30), stringtranslate.translateKey(lblRemoveSaddle));
				this.controlList.add(this.removeSaddleButton);
			}
		}		
	}
	
	protected GuiButton createGuiButton(int id, int xPos, int yPos, String label)
	{
		return new GuiButton(id, xPos, yPos, 90, 20, label);
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
			mc.displayGuiScreen(parentGuiScreen);
		}
		else if (guibutton.id == 2)
		{
			chocobo.setHidename(!chocobo.isHidename());
			namingButton.displayString = (chocobo.isHidename()) ? "Name Hidden" : "Name Shown";
		}
		else if (guibutton.id == 3)
		{
			chocobo.toggleFollow();
			followingButton.displayString = (chocobo.isFollowing()) ? "Following" : "Not Following";
		}
		else if (guibutton.id == 0)
		{
			FMLClientHandler.instance().getClient().displayGuiScreen(new GuiNameChocobo(this, chocobo));
		}
		else if (guibutton.id == 4)
		{
			if(this.chocobo instanceof EntityChocoboRideable)
			{
				((EntityChocoboRideable)this.chocobo).sendDropSaddleAndBags();
			}
			mc.displayGuiScreen(parentGuiScreen);
		}
	}

	protected void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);
	}

	public void drawScreen(int i, int j, float f)
	{
		
		drawDefaultBackground();
		String breedStatus = "";
		String gender = this.chocobo.getGender();
		String ownerName = (new StringBuilder()).append("owner: ").append(this.chocobo.getOwnerName()).toString();
		String health = (new StringBuilder()).append("health: ").append(chocobo.getHealth()).append("/").append(chocobo.getMaxHealth()).toString();

		if (this.chocobo.getGrowingAge() > 0 || this.chocobo.isChild())
		{
			breedStatus = "cannot breed";
		}
		else
		{
			breedStatus = "can breed";
		}
		
		this.drawCenteredString(this.fontRenderer, chocobo.getName(), this.width / 2, (height / 4 - 60) + 20, 0xffffff);
				
		int posY = 24;
		int posX = this.width / 2 + 10;
		int fontColour = 0xa0a0a0;
		
		this.drawString(this.fontRenderer, ownerName,   posX, (posY += 24), fontColour);
		this.drawString(this.fontRenderer, health,      posX, (posY += 24), fontColour);
		this.drawString(this.fontRenderer, gender + " (" + breedStatus + ")",      posX, (posY += 24), fontColour);
		//this.drawString(this.fontRenderer, breedStatus, posX, (posY += 24), fontColour);
		super.drawScreen(i, j, f);
	}
}
