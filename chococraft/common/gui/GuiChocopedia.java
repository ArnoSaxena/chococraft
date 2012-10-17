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

import chococraft.common.ModChocoCraft;
import chococraft.common.entities.EntityAnimalChocobo;

public class GuiChocopedia extends GuiScreen
{
    private EntityAnimalChocobo chocobo;
    private GuiScreen parentGuiScreen;
    private GuiButton namingButton;
    private GuiButton followingButton;

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
        String lblNameShown = "";
        String lblFollowing = "";
        
        lblNameShown = (this.chocobo.isHidename()) ? "Name Hidden" : "Name Shown";
        lblFollowing = (this.chocobo.isFollowing()) ? "Following" : "Not Following";
        
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 48 + 12, stringtranslate.translateKey("Rename")));
        this.namingButton = new GuiButton(2, this.width / 2 - 100, this.height / 4 + 72 + 12, stringtranslate.translateKey(lblNameShown));
        this.followingButton = new GuiButton(3, this.width / 2 - 100, this.height / 4 + 96 + 12, stringtranslate.translateKey(lblFollowing));
        this.controlList.add(this.namingButton);
        this.controlList.add(this.followingButton);
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, stringtranslate.translateKey("Confirm")));
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
        	ModChocoCraft.mcc.displayGuiScreen(new GuiNameChocobo(this, chocobo));
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
        String gender = "";
        
        if (this.chocobo.getGrowingAge() > 0 || this.chocobo.isChild())
        {
            breedStatus = "Cannot breed";
        }
        else
        {
            breedStatus = "Can breed";
        }
        gender = chocobo.getGender();

        drawCenteredString(fontRenderer, chocobo.getName(), width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawCenteredString(fontRenderer, (new StringBuilder()).append("health = ").append(chocobo.getHealth()).append("/").append(chocobo.getMaxHealth()).toString(), width / 2, 40, 0xa0a0a0);
        drawCenteredString(fontRenderer, breedStatus, width / 2, 60, 0xa0a0a0);
        drawCenteredString(fontRenderer, gender, width / 2, 80, 0xa0a0a0);
        super.drawScreen(i, j, f);
    }
}
