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

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChicobo;
import chococraft.common.helper.ChocoboMathHelper;

public class GuiSelectNewOwner extends GuiScreen
{
	private final int plViewListSize = 5;
	private ArrayList<GuiClickTextField> tfArPlayerList;
	private ArrayList<String> currentSelPlayers;
	private int selPlListIdx;
	
	private GuiTextField tfNewOnwer;
	private final EntityAnimalChocobo chocobo;
	private GuiScreen pGuiScreen;
	
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
		//StringTranslate stringtranslate = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();		
//		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, 80, 20, stringtranslate.translateKey("Accept")));
//		this.buttonList.add(new GuiButton(1, this.width / 2, this.height / 4 + 120 + 12, 80, 20, stringtranslate.translateKey("gui.cancel")));
//
//		this.buttonList.add(new GuiButton(2, this.width / 2 + 90, this.height / 2 - 50, 40, 20, stringtranslate.translateKey("last")));
//		this.buttonList.add(new GuiButton(3, this.width / 2 + 90, this.height / 2 - 20, 40, 20, stringtranslate.translateKey("next")));

		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, 80, 20, "Accept"));
		this.buttonList.add(new GuiButton(1, this.width / 2, this.height / 4 + 120 + 12, 80, 20, "Cancel"));

		this.buttonList.add(new GuiButton(2, this.width / 2 + 90, this.height / 2 - 50, 40, 20, "last"));
		this.buttonList.add(new GuiButton(3, this.width / 2 + 90, this.height / 2 - 20, 40, 20, "next"));

		this.tfNewOnwer = new GuiTextField(this.mc.fontRenderer, this.width / 2 - 100, this.height / 4 + 96 + 12, 180, 20);
		this.tfNewOnwer.setText("");
		this.tfNewOnwer.setFocused(true);
		this.tfNewOnwer.setCanLoseFocus(false);
		this.tfNewOnwer.setMaxStringLength(20);
		
		this.tfArPlayerList = this.createPlayerListTextFields(this.width/2 - 100, 55);
		this.currentSelPlayers = this.findAllPlayerNames();
		this.selPlListIdx = 0;
		this.showPlayerListAtCurrentIndex();		
	}
	
	private ArrayList<GuiClickTextField> createPlayerListTextFields(int x, int y)
	{
		ArrayList<GuiClickTextField> gtfAr = new ArrayList<GuiClickTextField>();
		for(int i = 0; i < this.plViewListSize; i++)
		{
			gtfAr.add(this.createPlayerListTextField(x, y + i * 21));
		}
		return gtfAr;
	}

	private GuiClickTextField createPlayerListTextField(int x, int y)
	{
		GuiClickTextField gctf = new GuiClickTextField(this.mc.fontRenderer, x, y, 180, 20);
		gctf.setReceiver(this);
		gctf.setFocused(false);
		return gctf;
	}
	
	private void showPlayerListAtCurrentIndex()
	{
		for(int i = 0; i < this.plViewListSize; i++)
		{
			if(this.currentSelPlayers.size() > this.selPlListIdx + i)
			{
				String nameAtIdx = this.currentSelPlayers.get(this.selPlListIdx + i);
				this.tfArPlayerList.get(i).setText(nameAtIdx);
			}
			else
			{
				this.tfArPlayerList.get(i).setText("");
			}
		}
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
			this.selectedPlayerListMove(-this.plViewListSize);
			this.showPlayerListAtCurrentIndex();
		}
		else if(guibutton.id == 3)
		{
			this.selectedPlayerListMove(this.plViewListSize);
			this.showPlayerListAtCurrentIndex();
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
			((GuiButton)this.buttonList.get(0)).enabled = true;
			String startNameNewOwner = this.tfNewOnwer.getText().trim();
			
			if(startNameNewOwner.equals(""))
			{
				this.currentSelPlayers = this.findAllPlayerNames();
			}
			else
			{
				this.currentSelPlayers = this.findPlayerNamesStartingWith(startNameNewOwner);
			}

			if (c == '\r')
			{
				this.actionPerformed((GuiButton)this.buttonList.get(0));
			}
			else
			{
				this.selPlListIdx = 0;
				this.showPlayerListAtCurrentIndex();
			}
		}
		else
		{
			((GuiButton)this.buttonList.get(0)).enabled = false;
		}
	}
	
	public void fillNameIntoNewOwnerTf(String name)
	{
		this.tfNewOnwer.setText(name);
		this.tfNewOnwer.drawTextBox();
		((GuiButton)this.buttonList.get(0)).enabled = !this.tfNewOnwer.getText().isEmpty();
	}
	
	protected void mouseClicked(int x, int y, int k)
	{
		super.mouseClicked(x, y, k);
		tfNewOnwer.mouseClicked(x, y, k);
		for(int i = 0; i < this.plViewListSize; i++)
		{
			if(i < this.tfArPlayerList.size())
			{
				this.tfArPlayerList.get(i).mouseClicked(x, y, k);
			}
		}
	}

	public void drawScreen(int i, int j, float f)
	{
		//StringTranslate stringtranslate = StringTranslate.getInstance();
		this.drawDefaultBackground();

		StringBuilder messageSB = new StringBuilder();
		messageSB.append("Give your ");
		messageSB.append((this.chocobo instanceof EntityChicobo) ? "Chicobo" : "Chocobo");
		//String message = stringtranslate.translateKey(messageSB.toString());
		String message = messageSB.toString();
		this.drawCenteredString(this.mc.fontRenderer, message, this.width / 2, (this.height / 4 - 60) + 20, 0xffffff);
		//this.drawString(this.fontRenderer, stringtranslate.translateKey("to new owner:"), this.width / 2 - 100, 30, 0xa0a0a0);		
		this.drawString(this.mc.fontRenderer, "to new owner:", this.width / 2 - 100, 30, 0xa0a0a0);
		this.tfNewOnwer.drawTextBox();
		for(GuiTextField gtf : this.tfArPlayerList)
		{
			gtf.drawTextBox();
		}
		super.drawScreen(i, j, f);
	}
	
	private ArrayList<String> findAllPlayerNames()
	{
		return findPlayerNamesStartingWith(null);
	}

	private ArrayList<String> findPlayerNamesStartingWith(String nameStart)
	{
		ArrayList<String> playerNames = new ArrayList<String>();

		for(Object playerObj : this.chocobo.worldObj.playerEntities)
		{
			if(playerObj instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)playerObj;
				if(nameStart == null || player.getDisplayName().startsWith(nameStart))
				{
					//if(!player.equals(this.chocobo.getOwner()))
					{
						playerNames.add(player.getDisplayName());
					}
				}
			}
		}
		return playerNames;
	}
}
