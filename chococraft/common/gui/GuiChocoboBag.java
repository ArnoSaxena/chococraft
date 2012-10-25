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
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.StatCollector;

import org.lwjgl.opengl.GL11;

import chococraft.common.bags.ChocoBagContainer;
import chococraft.common.bags.ChocoBagInventory;
import chococraft.common.bags.ChocoPackBagInventory;

public class GuiChocoboBag extends GuiContainer
{
	private InventoryPlayer playerInv;
	private ChocoBagInventory chocoBagInv;
	private int invHeight;

	public GuiChocoboBag (InventoryPlayer playerInv, ChocoBagInventory chocoBagInv)
	{
		super(new ChocoBagContainer(playerInv, chocoBagInv));
		this.playerInv = playerInv;
		this.chocoBagInv = chocoBagInv;

		if(this.chocoBagInv instanceof ChocoPackBagInventory)
		{
			this.invHeight = 106;
		}
		else
		{
			this.invHeight = 96;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer()
	{
		fontRenderer.drawString(StatCollector.translateToLocal(this.chocoBagInv.getInvName()), 8, 6, 0x404040);
		int labelYPos;
		if(this.chocoBagInv instanceof ChocoPackBagInventory)
		{
			labelYPos = this.ySize - 50;
		}
		else
		{
			labelYPos = this.ySize - 74;
		}

		fontRenderer.drawString(StatCollector.translateToLocal(this.playerInv.getInvName()), 8, labelYPos, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		int chocoInvRows = this.chocoBagInv.getSizeInventory() / 9;

		int i = mc.renderEngine.getTexture("/gui/container.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(i);
		int j = (width - xSize) / 2;
		int k = (height - ySize) / 2;
		this.drawTexturedModalRect(j, k, 0, 0, xSize, chocoInvRows * 18 + 17);
		this.drawTexturedModalRect(j, k + chocoInvRows * 18 + 17, 0, 126, xSize, this.invHeight);
	}
}
