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

package chococraft.common.entities.models;


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import chococraft.common.ModChocoCraft;
import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChocobo;

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
			super.renderLivingLabel(entitychocobo, entitychocobo.getName(), d, (d1 + ModChocoCraft.renderNameHeight), d2, maxNameDistance);
		}
	}

	protected float getWingRotation(EntityChocobo entitychocobo, float f)
	{
		return (MathHelper.sin(entitychocobo.wingRotation) + 1.0F) * entitychocobo.destPos;
	}

	protected float handleRotationFloat(EntityLiving entityliving, float f)
	{
		return getWingRotation((EntityChocobo)entityliving, f);
	}

	@Override
	public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
			float f, float f1)
	{
		renderChocobo((EntityChocobo)entityliving, d, d1, d2, f, f1);
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2,
			float f, float f1)
	{
		renderChocobo((EntityChocobo)entity, d, d1, d2, f, f1);
	}

	protected void preRenderScale(EntityChocobo entitychocobo, float f)
	{
		float scaleFactor = 0.8F;
		GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);

		if (entitychocobo.riddenByEntity != null)
		{
			GL11.glTranslated(0.5, 0.0, -0.5);
		}
	}

	@Override
	protected void preRenderCallback(EntityLivingBase entityliving, float f)
	{
		preRenderScale((EntityChocobo)entityliving, f);
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
