// <copyright file="GuiNameChocobo.java">
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
// <summary>Gui for the ChocoCraft name change dialog</summary>

package chococraft.common.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.StringTranslate;

import org.lwjgl.input.Keyboard;

import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChicobo;

public class GuiNameChocobo extends GuiScreen
{
	private GuiTextField theGuiTextField;
	private final EntityAnimalChocobo chocobo;
	private GuiScreen parentGuiScreen;

	public GuiNameChocobo(GuiScreen guiscreen, EntityAnimalChocobo entitychocobo)
	{
		this.chocobo = entitychocobo;
		this.parentGuiScreen = guiscreen;
	}

	public void updateScreen()
	{
		this.theGuiTextField.updateCursorCounter();
	}

	@SuppressWarnings("unchecked")
	public void initGui()
	{
		StringTranslate stringtranslate = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		this.controlList.clear();
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, stringtranslate.translateKey("Accept")));
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
		this.theGuiTextField = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 60, 200, 20);
		this.theGuiTextField.setText(this.chocobo.getName());
		this.theGuiTextField.setFocused(true);
		this.theGuiTextField.setMaxStringLength(20);
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
			this.mc.displayGuiScreen(this.parentGuiScreen);
		}
		else if (guibutton.id == 0)
		{
			this.chocobo.setName(this.theGuiTextField.getText());
			this.mc.displayGuiScreen(this.parentGuiScreen);
		}
	}

	protected void keyTyped(char c, int i)
	{
		this.theGuiTextField.textboxKeyTyped(c, i);
		((GuiButton)this.controlList.get(0)).enabled = this.theGuiTextField.getText().trim().length() > 0;
		if (c == '\r')
		{
			this.actionPerformed((GuiButton)this.controlList.get(0));
		}
	}

	protected void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);
		theGuiTextField.mouseClicked(i, j, k);
	}

	public void drawScreen(int i, int j, float f)
	{
		StringTranslate stringtranslate = StringTranslate.getInstance();
		this.drawDefaultBackground();
		String s = (this.chocobo instanceof EntityChicobo) ? "Chicobo" : "Chocobo";
		this.drawCenteredString(this.fontRenderer, stringtranslate.translateKey((new StringBuilder()).append("Name your ").append(s).toString()), this.width / 2, (this.height / 4 - 60) + 20, 0xffffff);
		this.drawString(this.fontRenderer, stringtranslate.translateKey("New name:"), this.width / 2 - 100, 47, 0xa0a0a0);
		this.theGuiTextField.drawTextBox();
		super.drawScreen(i, j, f);
	}
}
