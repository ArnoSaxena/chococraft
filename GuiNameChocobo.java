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

package net.minecraft.src;

import org.lwjgl.input.Keyboard;

public class GuiNameChocobo extends GuiScreen
{
    private GuiTextField theGuiTextField;
    private final EntityChocobo chocobo;
    private final EntityChicobo chicobo;
    private final EntityNetherChocobo n_chocobo;
    private final int type;
    private GuiScreen parentGuiScreen;

    public GuiNameChocobo(GuiScreen guiscreen, EntityChocobo entitychocobo)
    {
        chocobo = entitychocobo;
        chicobo = null;
        n_chocobo = null;
        parentGuiScreen = guiscreen;
        type = 0;
    }

    public GuiNameChocobo(GuiScreen guiscreen, EntityChicobo entitychicobo)
    {
        chocobo = null;
        chicobo = entitychicobo;
        n_chocobo = null;
        parentGuiScreen = guiscreen;
        type = 1;
    }

    public GuiNameChocobo(GuiScreen guiscreen, EntityNetherChocobo entitynetherchocobo)
    {
        n_chocobo = entitynetherchocobo;
        chocobo = null;
        chicobo = null;
        parentGuiScreen = guiscreen;
        type = 2;
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
        String s = "";
        switch (type)
        {
            case 0:
                s = chocobo.name;
                break;

            case 1:
                s = chicobo.name;
                break;

            case 2:
                s = n_chocobo.name;
                break;
        }
        theGuiTextField = new GuiTextField(fontRenderer, width / 2 - 100, 60, 200, 20);
        theGuiTextField.setText(s);
        theGuiTextField.func_50033_b(true);
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
            switch (type)
            {
                case 0:
                    chocobo.setName(theGuiTextField.getText());
                    break;

                case 1:
                    chicobo.setName(theGuiTextField.getText());
                    break;

                case 2:
                    n_chocobo.setName(theGuiTextField.getText());
                    break;
            }
            mc.displayGuiScreen(parentGuiScreen);
        }
    }

    protected void keyTyped(char c, int i)
    {
        theGuiTextField.func_50037_a(c, i);
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
        String s = "";
        if (type == 1)
        {
            s = "Chicobo";
        }
        else
        {
            s = "Chocobo";
        }
        drawCenteredString(fontRenderer, stringtranslate.translateKey((new StringBuilder()).append("Name your ").append(s).toString()), width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawString(fontRenderer, stringtranslate.translateKey("New name:"), width / 2 - 100, 47, 0xa0a0a0);
        theGuiTextField.drawTextBox();
        super.drawScreen(i, j, f);
    }
}
