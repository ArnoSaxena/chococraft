// <copyright file="RenderChocobo.java">
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
// <summary>Render class for all Chocobos</summary>

package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderChocobo extends RenderLiving
{
    public RenderChocobo(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    public void renderChocobo(EntityChocobo entitychocobo, double d, double d1, double d2, float f, float f1)
    {
    	int maxNameDistance = 20;
        super.doRenderLiving(entitychocobo, d, d1, d2, f, f1);
        if (entitychocobo.canRenderName())
        {
            super.renderLivingLabel(entitychocobo, entitychocobo.name, d, d1, d2, maxNameDistance);
        }
    }

    protected float getWingRotation(EntityChocobo entitychocobo, float f)
    {
        float f1 = entitychocobo.field_756_e + (entitychocobo.field_752_b - entitychocobo.field_756_e) * f;
        float f2 = entitychocobo.field_757_d + (entitychocobo.destPos - entitychocobo.field_757_d) * f;
        return (MathHelper.sin(f1) + 1.0F) * f2;
    }

    protected float handleRotationFloat(EntityLiving entityliving, float f)
    {
        return getWingRotation((EntityChocobo)entityliving, f);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1)
    {
        renderChocobo((EntityChocobo)entityliving, d, d1, d2, f, f1);
    }

    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1)
    {
        renderChocobo((EntityChocobo)entity, d, d1, d2, f, f1);
    }

    protected void preRenderScale(EntityChocobo entitychocobo, float f)
    {
       // float f1 = 1.28F;
    	float scaleFactor = 0.8F;
        GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);
    }

    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
        preRenderScale((EntityChocobo)entityliving, f);
    }
}
