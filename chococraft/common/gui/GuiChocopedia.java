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

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.StringTranslate;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;

import chococraft.common.ChocoboHelper;
import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChocoboRideable;

public class GuiChocopedia extends GuiScreen
{
	private EntityAnimalChocobo chocobo;
	private EntityPlayer player;
	private GuiScreen parentGuiScreen;
	private GuiButton hideNameButton;
	private GuiButton followingButton;
	private GuiButton removeSaddleButton;
	private GuiButton renameButton;
	private GuiButton confirmButton;

	public GuiChocopedia(GuiScreen guiscreen, EntityAnimalChocobo entitychocobo, EntityPlayer thePlayer)
	{
		this.chocobo = entitychocobo;
		this.player = thePlayer;
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
		this.renameButton = this.createGuiButton(0, xPos, (yPos += 24), stringtranslate.translateKey("Rename"));
		this.checkButtonOwner(this.player, this.chocobo.getOwner(), this.renameButton);
		this.controlList.add(this.renameButton);

		// hide name button
		String lblNameShown = (this.chocobo.isHidename()) ? "Name Hidden" : "Name Shown";
		this.hideNameButton = this.createGuiButton(2, xPos, (yPos += 24), stringtranslate.translateKey(lblNameShown));
		this.checkButtonOwner(this.player, this.chocobo.getOwner(), this.hideNameButton);
		this.controlList.add(this.hideNameButton);
		
		// following button
		//String lblFollowing = (this.chocobo.isFollowing()) ? "Following" : "Not Following";
		String lblFollowing = this.getFollowStatus();
		this.followingButton = this.createGuiButton(3, xPos, (yPos += 24), stringtranslate.translateKey(lblFollowing));
		this.checkButtonOwner(this.player, this.chocobo.getOwner(), this.followingButton);
		this.controlList.add(this.followingButton);

		// confirm button
		this.confirmButton = this.createGuiButton(1, xPos, (yPos += 24), stringtranslate.translateKey("Confirm"));
		this.checkButtonOwner(this.player, this.chocobo.getOwner(), this.confirmButton);
		this.controlList.add(this.confirmButton);
		
		// remove saddle button
		if(this.chocobo instanceof EntityChocoboRideable  && null == this.chocobo.riddenByEntity)
		{
			EntityChocoboRideable chocoboRideable = (EntityChocoboRideable)this.chocobo;
			if(chocoboRideable.isSaddled() || chocoboRideable.isPackBagged())
			{
				String lblRemoveSaddle = "Drop Gear";
				this.removeSaddleButton = this.createGuiButton(4, xPos, (yPos += 30), stringtranslate.translateKey(lblRemoveSaddle));
				this.checkButtonOwner(this.player, this.chocobo.getOwner(), this.removeSaddleButton);
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
			this.mc.displayGuiScreen(parentGuiScreen);
		}
		else if (guibutton.id == 2)
		{
			this.chocobo.setHidename(!this.chocobo.isHidename());
			this.hideNameButton.displayString = (this.chocobo.isHidename()) ? "Name Hidden" : "Name Shown";
		}
		else if (guibutton.id == 3)
		{
			this.chocobo.toggleFollow();
			//followingButton.displayString = (chocobo.isFollowing()) ? "Following" : "Not Following";
			this.followingButton.displayString = this.getFollowStatus();
		}
		else if (guibutton.id == 0)
		{
			FMLClientHandler.instance().getClient().displayGuiScreen(new GuiNameChocobo(this, chocobo));
		}
		else if (guibutton.id == 4)
		{
			if(this.chocobo instanceof EntityChocoboRideable && null == this.chocobo.riddenByEntity)
			{
				((EntityChocoboRideable)this.chocobo).sendDropSaddleAndBags();
			}
			this.mc.displayGuiScreen(this.parentGuiScreen);
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
		
		this.drawCenteredString(this.fontRenderer, this.chocobo.getName(), this.width / 2, (height / 4 - 60) + 20, 0xffffff);
				
		int posY = 24;
		int posX = this.width / 2 + 10;
		int fontColour = 0xa0a0a0;
		
		this.drawString(this.fontRenderer, ownerName,   posX, (posY += 24), fontColour);
		this.drawString(this.fontRenderer, health,      posX, (posY += 24), fontColour);
		this.drawString(this.fontRenderer, gender + " (" + breedStatus + ")",      posX, (posY += 24), fontColour);
		
		if(this.chocobo.getOwnerName().equals("Torojima"))
		{
			// it's me, display debug information
			int amountWildChocobos = ChocoboHelper.countWildChocobos(this.chocobo.worldObj);
			int amountChocobos = ChocoboHelper.countEntities(EntityAnimalChocobo.class, this.chocobo.worldObj);
			int debugFontColour = 0x11a0a0;
			String biomeName = this.chocobo.worldObj.getBiomeGenForCoords((int)this.chocobo.posX, (int)this.chocobo.posZ).biomeName;
			String biomeDisplayString = "Biome: " + biomeName;
			
			String chocoAmountString = (new StringBuilder()).append("Chocos: ").append(amountChocobos).append(" wild: ").append(amountWildChocobos).toString();			
			this.drawString(this.fontRenderer, chocoAmountString,   posX, (posY += 15), debugFontColour);
			this.drawString(this.fontRenderer, biomeDisplayString,   posX, (posY += 15), debugFontColour);
		}		
		super.drawScreen(i, j, f);
	}
	
	private void checkButtonOwner(EntityPlayer player, EntityPlayer owner, GuiButton button)
	{
		if(!player.equals(owner))
		{
			button.enabled = false;
		}
	}
	
	private String getFollowStatus()
	{
		if(this.chocobo.isFollowing() && !this.chocobo.isWander())
		{
			return "follow";
		}
		else if(!this.chocobo.isFollowing() && this.chocobo.isWander())
		{
			return "wander";
		}
		else if(!this.chocobo.isFollowing() && !this.chocobo.isWander())
		{
			return "stay";
		}
		else
		{
			return "";
		}
	}
}
