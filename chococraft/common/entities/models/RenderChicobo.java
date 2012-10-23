// <copyright file="RenderChicobo.java">
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
// <summary>render class for Chicobos of all colours</summary>

package chococraft.common.entities.models;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;

import org.lwjgl.opengl.GL11;

import chococraft.common.entities.EntityChicobo;

public class RenderChicobo extends RenderLiving
{
    public RenderChicobo(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    public void renderChicobo(EntityChicobo entitychicobo, double d, double d1, double d2,
            float f, float f1)
    {
        super.doRenderLiving(entitychicobo, d, d1, d2, f, f1);
        if (entitychicobo.canRenderName())
        {
        	String tmpName = entitychicobo.getName() + " "+ entitychicobo.getTimeUntilAdult();
            super.renderLivingLabel(entitychicobo, tmpName, d, d1, d2, 20);
        }
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1)
    {
        renderChicobo((EntityChicobo)entityliving, d, d1, d2, f, f1);
    }

    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1)
    {
        renderChicobo((EntityChicobo)entity, d, d1, d2, f, f1);
    }

    protected void preRenderScale(EntityChicobo entitychicobo, float f)
    {
        float f1 = 1.32F;
        GL11.glScalef(f1, f1, f1);
    }

    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
        preRenderScale((EntityChicobo)entityliving, f);
    }
}
