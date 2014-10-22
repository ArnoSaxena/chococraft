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

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import org.lwjgl.opengl.GL11;

import chococraft.common.entities.EntityAnimalChocobo;
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
        super.doRender(entitychicobo, d, d1, d2, f, f1);
        if (entitychicobo.canRenderName())
        {
        	String tmpName = entitychicobo.getName();
            super.func_147906_a(entitychicobo, tmpName, d, d1, d2, 20);//renderLivingLabel
        }
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1)
    {
        renderChicobo((EntityChicobo)entityliving, d, d1, d2, f, f1);
    }

    @Override
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

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		if(entity instanceof EntityAnimalChocobo)
		{
			EntityAnimalChocobo eac = (EntityAnimalChocobo)entity;
			return eac.getResourceLocation();
		}
		return null;
	}
}
