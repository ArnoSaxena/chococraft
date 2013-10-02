// <copyright file="ModelChocobo.java">
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
// <author>firas333 (Techne model)</author>
// <email>al-s@gmx.de</email>
// <date>2012-05-09</date>
// <summary>Model class for all Chocobos</summary>

package chococraft.common.entities.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import chococraft.common.ModChocoCraft;


public class ModelChocobo extends ModelBase
{
	ModelRenderer head;
	ModelRenderer feather;
	ModelRenderer feather_2;

	ModelRenderer neck;

	ModelRenderer body;

	ModelRenderer tail;
	ModelRenderer tail_2;
	ModelRenderer tail_3;

	ModelRenderer rightWing;
	ModelRenderer leftWing;

	ModelRenderer rightThigh;
	ModelRenderer leftThigh;

	ModelRenderer rightShin;
	ModelRenderer leftShin;

	ModelRenderer talonRB;
	ModelRenderer talonRR;
	ModelRenderer talonRL;

	ModelRenderer talonLL;
	ModelRenderer talonLR;
	ModelRenderer talonLB;
	
	ModelRenderer rightSaddleBag;
	ModelRenderer leftSaddleBag;
	ModelRenderer packBag;

	public ModelChocobo()
	{
		textureWidth  = 128;
		textureHeight = 128;

		// x = wide
		// y = height
		// z = length

		head = new ModelRenderer(this, 87, 2);
		neck = new ModelRenderer(this, 20, 45);
		feather = new ModelRenderer(this, 25, 18);
		feather_2 = new ModelRenderer(this, 45, 21);

		head.addBox(-3F, -12F, -9F, 6, 6, 12);
		head.setRotationPoint(1F, -8F, -5F);
		head.setTextureSize(textureWidth, textureHeight);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);

		neck.addBox(-2F, -9F, -2F, 4, 14, 4);
		neck.setRotationPoint(1F, -8F, -5F);
		neck.setTextureSize(textureWidth, textureHeight);
		neck.mirror = true;
		setRotation(neck, 0F, 0F, 0F);

		feather.addBox(-3F, -12F, 3F, 6, 1, 5);
		feather.setRotationPoint(1F, -8F, -5F);
		feather.setTextureSize(textureWidth, textureHeight);
		feather.mirror = true;
		setRotation(feather, 0.1745329F, 0F, 0F);

		feather_2.addBox(-3F, -12F, 3F, 6, 6, 3);
		feather_2.setRotationPoint(1F, -8F, -5F);
		feather_2.setTextureSize(textureWidth, textureHeight);
		feather_2.mirror = true;
		setRotation(feather_2, 0F, 0F, 0F);

		body = new ModelRenderer(this, 77, 31);
		body.addBox(-3F, -4F, -3F, 12, 20, 11);
		body.setRotationPoint(-2F, 1F, -2F);
		body.setTextureSize(textureWidth, textureHeight);
		body.mirror = true;
		setRotation(body, 1.37881F, 0F, 0F);
		
		// x = wide     + = left
		// y = height   + = down
		// z = length   + = back
		
		// body (1=4px)
		// x = wide     + = left
		// y = length   + = 
		// z = height   + = up
		
		rightSaddleBag = new ModelRenderer(this, 2, 72);
		rightSaddleBag.addBox(-4.0F, 10.1F, 0.5F, 2, 6, 8);
		rightSaddleBag.setRotationPoint(-2F, 1F, -2F);
		rightSaddleBag.setTextureSize(textureWidth, textureHeight);
		rightSaddleBag.mirror = true;
		setRotation(rightSaddleBag, 1.37881F, 0F, 0F);

		leftSaddleBag = new ModelRenderer(this, 2, 96);
		leftSaddleBag.addBox(7.8F, 10.1F, 0.5F, 2, 6, 8);
		leftSaddleBag.setRotationPoint(-2F, 1F, -2F);
		leftSaddleBag.setTextureSize(textureWidth, textureHeight);
		leftSaddleBag.mirror = true;
		setRotation(leftSaddleBag, 1.37881F, 0F, 0F);
		
		packBag = new ModelRenderer(this, 50, 66);
		packBag.addBox(-2.0F, 3.0F, 8.0F, 10, 12, 6);
		packBag.setRotationPoint(-2F, 1F, -2F);
		packBag.setTextureSize(textureWidth, textureHeight);
		packBag.mirror = true;
		setRotation(packBag, 1.37881F, 0F, 0F);


		tail = new ModelRenderer(this, 59, 2);
		tail.addBox(0F, 0F, 0F, 12, 14, 1);
		tail.setRotationPoint(-5F, -11F, 26.6F);
		tail.setTextureSize(textureWidth, textureHeight);
		tail.mirror = true;
		setRotation(tail, -0.9948377F, 0F, 0F);
		tail_2 = new ModelRenderer(this, 31, 2);
		tail_2.addBox(0F, 0F, 0F, 12, 9, 1);
		tail_2.setRotationPoint(-5F, -4F, 23F);
		tail_2.setTextureSize(textureWidth, textureHeight);
		tail_2.mirror = true;
		setRotation(tail_2, -1.308997F, 0F, 0F);
		tail_3 = new ModelRenderer(this, 31, 2);
		tail_3.addBox(0F, 0F, 0F, 12, 9, 1);
		tail_3.setRotationPoint(-5F, -1F, 23F);
		tail_3.setTextureSize(textureWidth, textureHeight);
		tail_3.mirror = true;
		setRotation(tail_3, -1.37881F, 0F, 0F);

		// x = wide    + = left
		// y = height   + = down
		// z = length  + = back		
		
		rightWing = new ModelRenderer(this, 40, 37);
		leftWing = new ModelRenderer(this, 40, 37);

		rightWing.addBox(-1F, 0F, -3F, 1, 10, 16);
		rightWing.setRotationPoint(-5F, -7F, 0F);
		rightWing.setTextureSize(textureWidth, textureHeight);
		rightWing.mirror = true;
		setRotation(rightWing, 0F, -0.0174533F, 0F);

		leftWing.addBox(-1F, 0F, -3F, 1, 10, 16);
		leftWing.setRotationPoint(8F, -7F, 0F);
		leftWing.setTextureSize(textureWidth, textureHeight);
		leftWing.mirror = true;
		setRotation(leftWing, 0F, 0.0174533F, 0F);

		// right leg
		rightThigh = new ModelRenderer(this, 20, 27);
		rightShin = new ModelRenderer(this, 17, 2);
		talonRB = new ModelRenderer(this, 111, 22);
		talonRR = new ModelRenderer(this, 67, 20);
		talonRL = new ModelRenderer(this, 67, 20);

		rightThigh.addBox(-1F, 0F, -3F, 4, 9, 4);
		rightThigh.setRotationPoint(-4F, 4F, 5F);
		rightThigh.setTextureSize(textureWidth, textureHeight);
		rightThigh.mirror = true;

		rightShin.addBox(-1F, 8F, 1F, 3, 9, 3);
		rightShin.setRotationPoint(-4F, 4F, 5F);
		rightShin.setTextureSize(textureWidth, textureHeight);
		rightShin.mirror = true;

		talonRR.addBox(-1F, 14F, -14F, 2, 2, 7);
		talonRR.setRotationPoint(-4F, 4F, 5F);
		talonRR.setTextureSize(textureWidth, textureHeight);
		talonRR.mirror = true;

		talonRL.addBox(0.5F, 14F, -14F, 2, 2, 7);
		talonRL.setRotationPoint(-4F, 4F, 5F);
		talonRL.setTextureSize(textureWidth, textureHeight);
		talonRL.mirror = true;

		talonRB.addBox(-0.5F, 13F, 9F, 2, 2, 4);
		talonRB.setRotationPoint(-4F, 4F, 5F);
		talonRB.setTextureSize(textureWidth, textureHeight);
		talonRB.mirror = true;

		leftThigh = new ModelRenderer(this, 20, 27);
		leftShin = new ModelRenderer(this, 17, 2);
		talonLL = new ModelRenderer(this, 67, 20);
		talonLR = new ModelRenderer(this, 67, 20);
		talonLB = new ModelRenderer(this, 111, 22);

		leftThigh.addBox(-1F, 0F, -3F, 4, 9, 4);
		leftThigh.setRotationPoint(4F, 4F, 5F);
		leftThigh.setTextureSize(textureWidth, textureHeight);
		leftThigh.mirror = true;

		leftShin.addBox(0F, 8F, 1F, 3, 9, 3);
		leftShin.setRotationPoint(4F, 4F, 5F);
		leftShin.setTextureSize(textureWidth, textureHeight);
		leftShin.mirror = true;

		talonLR.addBox(0F, 14F, -14F, 2, 2, 7);
		talonLR.setRotationPoint(4F, 4F, 5F);
		talonLR.setTextureSize(textureWidth, textureHeight);
		talonLR.mirror = true;

		talonLL.addBox(1.5F, 14F, -14F, 2, 2, 7);
		talonLL.setRotationPoint(4F, 4F, 5F);
		talonLL.setTextureSize(textureWidth, textureHeight);
		talonLL.mirror = true;

		talonLB.addBox(0.5F, 13F, 9F, 2, 2, 4);
		talonLB.setRotationPoint(4F, 4F, 5F);
		talonLB.setTextureSize(textureWidth, textureHeight);
		talonLB.mirror = true;

		this.setRightLegXRotation(0.0F);
		this.setLeftLegXRotation(0.0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		// this super method is empty
		super.render(entity, f, f1, f2, f3, f4, f5);
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		head.render(f5);
		feather.render(f5);
		feather_2.render(f5);

		neck.render(f5);
		body.render(f5);
		leftSaddleBag.render(f5);
		rightSaddleBag.render(f5);
		packBag.render(f5);

		rightWing.render(f5);
		leftWing.render(f5);

		tail.render(f5);
		tail_2.render(f5);
		tail_3.render(f5);

		rightThigh.render(f5);
		leftThigh.render(f5);

		rightShin.render(f5);
		leftShin.render(f5);

		talonRB.render(f5);
		talonRR.render(f5);
		talonRL.render(f5);

		talonLL.render(f5);
		talonLR.render(f5);
		talonLB.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		// this super method is empty ...
		
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		// f2 = wing z movement (flutter)
		// f3 = head y movement
		// f4 = head x movement
		// cos(f) and f1 = leg movement

		float pi = (float)Math.PI;

		// head/neck movement
		head.rotateAngleX = f4 * (pi/180F);
		head.rotateAngleY = f3 * (pi/180F);
		neck.rotateAngleX = 0.0F;
		neck.rotateAngleY = head.rotateAngleY;
		feather.rotateAngleX = head.rotateAngleX + 0.1745329F;
		feather.rotateAngleY = head.rotateAngleY;
		feather_2.rotateAngleX = head.rotateAngleX;
		feather_2.rotateAngleY = head.rotateAngleY;

		// walking animation
		this.setRightLegXRotation(MathHelper.cos(f * 0.6662F) * 0.8F * f1);
		this.setLeftLegXRotation(MathHelper.cos(f * 0.6662F + pi) * 0.8F * f1);
		
		// flying animation
		if (Math.abs(entity.motionY) > 0.1F)
		{
			if(ModChocoCraft.chocoboWingFlutter)
			{
				setRotation(rightWing, (pi/2F) - (pi/12), -0.0174533F, -f2);
				setRotation(leftWing, (pi/2F) - (pi/12), 0.0174533F, f2);
			}
			else
			{
				setRotation(rightWing, (pi/2F), -0.0174533F, -1.2F);
				setRotation(leftWing, (pi/2F), 0.0174533F, 1.2F);				
			}
			this.setLeftLegXRotation(0.6F);
			this.setRightLegXRotation(0.6F);
		}
		else
		{
			// reset wings
			setRotation(rightWing, 0F, -0.0174533F, 0F);
			setRotation(leftWing, 0F, 0.0174533F, 0F);
		}
	}

	private void setLeftLegXRotation(float deltaX)
	{
		setRotation(leftThigh, 0.2094395F + deltaX, 0F, 0F);
		setRotation(leftShin, -0.1919862F + deltaX, 0F, 0F);
		setRotation(talonLR, 0.3490659F + deltaX, 0.1570796F, 0F);
		setRotation(talonLL, 0.3490659F + deltaX, -0.1919862F, 0F);
		setRotation(talonLB, -0.5235988F + deltaX, 0F, 0F);
	}

	private void setRightLegXRotation(float deltaX)
	{
		setRotation(rightThigh, 0.2094395F + deltaX, 0F, 0F);
		setRotation(rightShin, -0.1919862F + deltaX, 0F, 0F);
		setRotation(talonRR, 0.3490659F + deltaX, 0.1919862F, 0F);
		setRotation(talonRL, 0.3490659F + deltaX, -0.1570796F, 0F);
		setRotation(talonRB, -0.5235988F + deltaX, 0F, 0F);
	}
}
