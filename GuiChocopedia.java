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

package net.minecraft.src;

import org.lwjgl.input.Keyboard;

public class GuiChocopedia extends GuiScreen
{
    private final EntityChocobo chocobo;
    private final EntityChicobo chicobo;
    private final EntityNetherChocobo n_chocobo;
    private final int type;
    private GuiScreen parentGuiScreen;
    private GuiButton namingButton;
    private GuiButton followingButton;

    public GuiChocopedia(GuiScreen guiscreen, EntityChocobo entitychocobo)
    {
        chocobo = entitychocobo;
        chicobo = null;
        n_chocobo = null;
        parentGuiScreen = guiscreen;
        type = 0;
    }

    public GuiChocopedia(GuiScreen guiscreen, EntityChicobo entitychicobo)
    {
        chocobo = null;
        n_chocobo = null;
        chicobo = entitychicobo;
        parentGuiScreen = guiscreen;
        type = 1;
    }

    public GuiChocopedia(GuiScreen guiscreen, EntityNetherChocobo entitynetherchocobo)
    {
        n_chocobo = entitynetherchocobo;
        chocobo = null;
        chicobo = null;
        parentGuiScreen = guiscreen;
        type = 2;
    }

    public void updateScreen()
    {
    }

    @SuppressWarnings("unchecked")
	public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        String lblNameShown = "";
        String lblFollowing = "";
        switch (type)
        {
            default:
                break;

            case 0:
                if (chocobo.hidename)
                {
                    lblNameShown = "Name Hidden";
                }
                else
                {
                    lblNameShown = "Name Shown";
                }
                if (chocobo.isFollowing())
                {
                    lblFollowing = "Following";
                }
                else
                {
                    lblFollowing = "Not Following";
                }
                break;

            case 1:
                if (chicobo.hidename)
                {
                    lblNameShown = "Name Hidden";
                }
                else
                {
                    lblNameShown = "Name Shown";
                }
                if (chicobo.isFollowing())
                {
                    lblFollowing = "Following";
                }
                else
                {
                    lblFollowing = "Not Following";
                }
                break;

            case 2:
                if (n_chocobo.hidename)
                {
                    lblNameShown = "Name Hidden";
                }
                else
                {
                    lblNameShown = "Name Shown";
                }
                if (n_chocobo.isFollowing())
                {
                    lblFollowing = "Following";
                }
                else
                {
                    lblFollowing = "Not Following";
                }
                break;
        }
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 48 + 12, stringtranslate.translateKey("Rename")));
        namingButton = new GuiButton(2, width / 2 - 100, height / 4 + 72 + 12, stringtranslate.translateKey(lblNameShown));
        followingButton = new GuiButton(3, width / 2 - 100, height / 4 + 96 + 12, stringtranslate.translateKey(lblFollowing));
        controlList.add(namingButton);
        controlList.add(followingButton);
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, stringtranslate.translateKey("Confirm")));
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
            String s = "";
            switch (type)
            {
                default:
                    break;

                case 0:
                    chocobo.hidename = !chocobo.hidename;
                    if (chocobo.hidename)
                    {
                        s = "Name Hidden";
                    }
                    else
                    {
                        s = "Name Shown";
                    }
                    break;

                case 1:
                    chicobo.hidename = !chicobo.hidename;
                    if (chicobo.hidename)
                    {
                        s = "Name Hidden";
                    }
                    else
                    {
                        s = "Name Shown";
                    }
                    break;

                case 2:
                    n_chocobo.hidename = !n_chocobo.hidename;
                    if (n_chocobo.hidename)
                    {
                        s = "Name Hidden";
                    }
                    else
                    {
                        s = "Name Shown";
                    }
                    break;
            }
            namingButton.displayString = s;
        }
        else if (guibutton.id == 3)
        {
            String s1 = "";
            switch (type)
            {
                default:
                    break;

                case 0:
                    chocobo.toggleFollow();
                    if (chocobo.isFollowing())
                    {
                        s1 = "Following";
                    }
                    else
                    {
                        s1 = "Not Following";
                    }
                    break;

                case 1:
                    chicobo.toggleFollow();
                    if (chicobo.isFollowing())
                    {
                        s1 = "Following";
                    }
                    else
                    {
                        s1 = "Not Following";
                    }
                    break;

                case 2:
                    n_chocobo.toggleFollow();
                    if (n_chocobo.isFollowing())
                    {
                        s1 = "Following";
                    }
                    else
                    {
                        s1 = "Not Following";
                    }
                    break;
            }
            followingButton.displayString = s1;
        }
        else if (guibutton.id == 0)
        {
            switch (type)
            {
                case 0:
                    mod_chocobo.mc.displayGuiScreen(new GuiNameChocobo(this, chocobo));
                    break;

                case 1:
                    mod_chocobo.mc.displayGuiScreen(new GuiNameChocobo(this, chicobo));
                    break;

                case 2:
                    mod_chocobo.mc.displayGuiScreen(new GuiNameChocobo(this, n_chocobo));
                    break;
            }
        }
    }

    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
    }

    public void drawScreen(int i, int j, float f)
    {
        //StringTranslate stringtranslate = StringTranslate.getInstance();
        drawDefaultBackground();
        String s = "";
        String s1 = "";
        int k = 0;
        int l = 0;
        switch (type)
        {
            default:
                break;

            case 0:
                if (chocobo.name.isEmpty())
                {
                    chocobo.setRandomName();
                }
                s = chocobo.name;
                k = chocobo.health;
                l = chocobo.getMaxHealth();
                if (k > l)
                {
                    k = l;
                    chocobo.health = chocobo.getMaxHealth();
                }
                if (chocobo.getDelay() > 0)
                {
                    s1 = "still cannot breed";
                }
                else
                {
                    s1 = "can already breed";
                }
                break;

            case 1:
                if (chicobo.name.isEmpty())
                {
                    chicobo.setRandomName();
                }
                s = chicobo.name;
                k = chicobo.health;
                l = chicobo.getMaxHealth();
                if (k > l)
                {
                    k = l;
                    chicobo.health = chicobo.getMaxHealth();
                }
                break;

            case 2:
                if (n_chocobo.name.isEmpty())
                {
                    n_chocobo.setRandomName();
                }
                s = n_chocobo.name;
                k = n_chocobo.health;
                l = n_chocobo.getMaxHealth();
                if (k > l)
                {
                    k = l;
                    n_chocobo.health = n_chocobo.getMaxHealth();
                }
                if (n_chocobo.getDelay() > 0)
                {
                    s1 = "still cannot breed";
                }
                else
                {
                    s1 = "can already breed";
                }
                break;
        }
        drawCenteredString(fontRenderer, s, width / 2, (height / 4 - 60) + 20, 0xffffff);
        drawCenteredString(fontRenderer, (new StringBuilder()).append("health = ").append(k).append("/").append(l).toString(), width / 2, 40, 0xa0a0a0);
        drawCenteredString(fontRenderer, s1, width / 2, 60, 0xa0a0a0);
        super.drawScreen(i, j, f);
    }
}
