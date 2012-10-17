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

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.StringTranslate;

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
        chocobo = entitychocobo;
        parentGuiScreen = guiscreen;
    }

    public void updateScreen()
    {
        theGuiTextField.updateCursorCounter();
    }

    @SuppressWarnings("unchecked")
	public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, stringtranslate.translateKey("Accept")));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("gui.cancel")));
        theGuiTextField = new GuiTextField(fontRenderer, width / 2 - 100, 60, 200, 20);
        theGuiTextField.setText(chocobo.getName());
        theGuiTextField.setFocused(true);
        theGuiTextField.setMaxStringLength(20);
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
        else if (guibutton.id == 0)
        {
        	chocobo.setName(theGuiTextField.getText());
            mc.displayGuiScreen(parentGuiScreen);
        }
    }

    protected void keyTyped(char c, int i)
    {
        theGuiTextField.textboxKeyTyped(c, i);
        ((GuiButton)controlList.get(0)).enabled = theGuiTextField.getText().trim().length() > 0;
        if (c == '\r')
        {
            actionPerformed((GuiButton)controlList.get(0));
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
        drawDefaultBackground();
        String s = (chocobo instanceof EntityChicobo) ? "Chicobo" : "Chocobo";
        drawCenteredString(fontRenderer, stringtranslate.translateKey((new StringBuilder()).append("Name your ").append(s).toString()), width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawString(fontRenderer, stringtranslate.translateKey("New name:"), width / 2 - 100, 47, 0xa0a0a0);
        theGuiTextField.drawTextBox();
        super.drawScreen(i, j, f);
    }
}
