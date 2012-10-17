// <copyright file="GuiChocoboBag.java">
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
// <date>2012-05-09</date>
// <summary>Gui class for all chocobo containers</summary>

package chococraft.common.gui;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.IInventory;
import net.minecraft.src.StatCollector;

import org.lwjgl.opengl.GL11;

import chococraft.common.bags.ChocoBagContainer;
import chococraft.common.bags.ChocoPackBagInventory;

public class GuiChocoboBag extends GuiContainer
{
    private IInventory chocoboInventory;
    private IInventory playerInventory;
    private int playerInvRows;
    private int invHeight;

    public GuiChocoboBag(IInventory chocoboIInventory, IInventory playerIInventory)
    {
        //super(new ContainerChest(chocoboIInventory, playerIInventory));
        super(new ChocoBagContainer(chocoboIInventory, playerIInventory));
        playerInvRows = 0;
        chocoboInventory = chocoboIInventory;
        playerInventory = playerIInventory;
        allowUserInput = false;
        char c = '\336';
        int i = c - 108;
        playerInvRows = playerIInventory.getSizeInventory() / 9;
        ySize = i + playerInvRows * 18;
        if(this.chocoboInventory instanceof ChocoPackBagInventory)
        {
        	this.invHeight = 106;
        }
        else
        {
        	this.invHeight = 96;
        }
    }

    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString(StatCollector.translateToLocal(playerInventory.getInvName()), 8, 6, 0x404040);
        fontRenderer.drawString(StatCollector.translateToLocal(chocoboInventory.getInvName()), 8, (ySize - 96) + 2, 0x404040);
    }

    protected void drawGuiContainerBackgroundLayer(float dummy01, int dummy02, int dummy03)
    {
        int i = mc.renderEngine.getTexture("/gui/container.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, playerInvRows * 18 + 17);
        drawTexturedModalRect(j, k + playerInvRows * 18 + 17, 0, 126, xSize, this.invHeight);
    }
}
