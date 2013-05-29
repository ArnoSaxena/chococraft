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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import chococraft.common.ModChocoCraft;
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
		float scaleFactor = 0.8F;
		GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);

		if (entitychocobo.riddenByEntity != null)
		{
			//GL11.glTranslated(0.0, 0.0, -0.5); // pre 2.0.0
			//GL11.glTranslated(0.5, 0.0, -0.5); // current 2.0.0
			//GL11.glTranslated(0.0, 0.0, -0.5); // player to wide left
			//GL11.glTranslated(1.0, 0.0, 0.0); // to the front
			GL11.glTranslated(0.5, 0.0, -0.5);
		}
	}

	protected void preRenderCallback(EntityLiving entityliving, float f)
	{
		preRenderScale((EntityChocobo)entityliving, f);
	}
}
