// <copyright file="GuiChocopediaButtonNextPage.java">
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
// <date>2013-06-08</date>
// <summary>copy of GuiButtonNextPage except made public</summary>

package chococraft.common.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiChocopediaButtonNextPage extends GuiButton
{
    /**
     * True for pointing right (next page), false for pointing left (previous page).
     */
    private final boolean nextPage;
    private ResourceLocation resourceLocation = new ResourceLocation("textures/gui/book.png");

    public GuiChocopediaButtonNextPage(int buttonId, int xPos, int yPos, boolean nextPage)
    {
        super(buttonId, xPos, yPos, 23, 13, "");
        this.nextPage = nextPage;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int par2, int par3)
    {
            boolean flag = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            //mc.renderEngine.bindTexture("/gui/book.png");
	        mc.getTextureManager().bindTexture(this.resourceLocation);
            int k = 0;
            int l = 192;

            if (flag)
            {
                k += 23;
            }

            if (!this.nextPage)
            {
                l += 13;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, k, l, 23, 13);

    }
}
