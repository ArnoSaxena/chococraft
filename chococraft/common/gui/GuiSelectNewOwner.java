// <copyright file="GuiChocoboOwner.java">
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

import java.util.ArrayList;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.StringTranslate;

import org.lwjgl.input.Keyboard;

import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChicobo;
import chococraft.common.helper.ChocoboMathHelper;

public class GuiSelectNewOwner extends GuiScreen
{
	private GuiTextField tfNewOnwer;
	private GuiTextField tfPlayerList;
	private final EntityAnimalChocobo chocobo;
	private GuiScreen pGuiScreen;
	private ArrayList<String> currentSelPlayers;
	private int selPlListIdx;
	private final int plViewListSize = 5;

	public GuiSelectNewOwner(GuiScreen guiscreen, EntityAnimalChocobo entitychocobo)
	{
		this.chocobo = entitychocobo;
		this.pGuiScreen = guiscreen;
	}

	public void updateScreen()
	{
		this.tfNewOnwer.updateCursorCounter();
	}

	@SuppressWarnings("unchecked")
	public void initGui()
	{
		StringTranslate stringtranslate = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		this.controlList.clear();		
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, stringtranslate.translateKey("Accept")));
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));

		this.controlList.add(new GuiButton(2, this.width / 2 + 100, this.height / 2 + 20, 20, 20, stringtranslate.translateKey("v")));
		this.controlList.add(new GuiButton(3, this.width / 2 + 100, this.height / 2 - 20, 20, 20, stringtranslate.translateKey("^")));

		// add empty text field ...
		this.tfPlayerList = new GuiTextField(this.fontRenderer, this.width/2 - 110, 60, 170, 60);
		this.tfPlayerList.setText("");
		this.tfPlayerList.setFocused(false);		

		this.tfNewOnwer = new GuiTextField(this.fontRenderer, this.width / 2 - 110, this.height / 4 + 72 + 12, 190, 20);
		this.tfNewOnwer.setText("");
		this.tfNewOnwer.setFocused(true);
		this.tfNewOnwer.setCanLoseFocus(false);
		this.tfNewOnwer.setMaxStringLength(20);
		this.currentSelPlayers = new ArrayList<String>();
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

		if(guibutton.id == 1)
		{
			this.mc.displayGuiScreen(this.pGuiScreen);
		}
		else if(guibutton.id == 0)
		{
			GuiChangeOwner guiChangeOwner = new GuiChangeOwner(this, this.pGuiScreen, this.chocobo, this.tfNewOnwer.getText());
			this.mc.displayGuiScreen(guiChangeOwner);
		}
		else if(guibutton.id == 2)
		{
			this.selectedPlayerListMove(this.plViewListSize);
			// TODO update currentSelPlayer view
		}
		else if(guibutton.id == 3)
		{
			this.selectedPlayerListMove(-this.plViewListSize);
			// TODO update currentSelPlayer view
		}
	}

	private void selectedPlayerListMove(int step)
	{
		int selPlayerSize = this.currentSelPlayers.size();

		if(selPlayerSize < step)
		{
			return;
		}
		this.selPlListIdx = ChocoboMathHelper.clamp((this.selPlListIdx + step), 0, (selPlayerSize - step + 1));
	}

	protected void keyTyped(char c, int i)
	{
		this.tfNewOnwer.textboxKeyTyped(c, i);

		if(this.tfNewOnwer.getText().trim().length() > 0)
		{
			((GuiButton)this.controlList.get(0)).enabled = true;
			this.currentSelPlayers = this.findFirstPlayerNamesStartingWith(this.tfNewOnwer.getText().trim());

			if (c == '\r')
			{
				this.actionPerformed((GuiButton)this.controlList.get(0));
			}
			else
			{
				String playerListText = "";
				for(int j = this.selPlListIdx; j < this.selPlListIdx + this.plViewListSize; j++)
				{
					if(j < this.currentSelPlayers.size())
					{
						playerListText += this.currentSelPlayers.get(j) + "\n";
					}
				}
				this.tfPlayerList.setText(playerListText);
			}
		}
		else
		{
			((GuiButton)this.controlList.get(0)).enabled = false;

		}
	}

	protected void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);
		tfNewOnwer.mouseClicked(i, j, k);
	}

	public void drawScreen(int i, int j, float f)
	{
		StringTranslate stringtranslate = StringTranslate.getInstance();
		this.drawDefaultBackground();

		StringBuilder messageSB = new StringBuilder();
		messageSB.append("Give your ");
		messageSB.append((this.chocobo instanceof EntityChicobo) ? "Chicobo" : "Chocobo");
		String message = stringtranslate.translateKey(messageSB.toString());
		this.drawCenteredString(this.fontRenderer, message, this.width / 2, (this.height / 4 - 60) + 20, 0xffffff);		
		this.drawString(this.fontRenderer, stringtranslate.translateKey("to new owner:"), this.width / 2 - 100, 47, 0xa0a0a0);		
		this.tfNewOnwer.drawTextBox();
		this.tfPlayerList.drawTextBox();
		super.drawScreen(i, j, f);
	}

	private ArrayList<String> findFirstPlayerNamesStartingWith(String nameStart)
	{
		ArrayList<String> playerNames = new ArrayList<String>();

		for(Object playerObj : this.chocobo.worldObj.playerEntities)
		{
			if(playerObj instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)playerObj;
				if(player.username.startsWith(nameStart))
				{
					playerNames.add(player.username);					
				}
			}
		}
		return playerNames;
	}
}
