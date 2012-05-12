// <copyright file="ModelChicobo.java">
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
// <summary>Model class for all coloured chicobos</summary>

package net.minecraft.src;

public class ModelChicobo extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer rightleg;
    public ModelRenderer leftleg;

    public ModelChicobo()
    {
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-1.5F, -3F, -1.5F, 3, 3, 3, 0.0F);
        head.setRotationPoint(0.0F, 18F, -2.5F);
        body = new ModelRenderer(this, 0, 6);
        body.addBox(-2F, -2.5F, -2F, 4, 4, 4, 0.0F);
        body.setRotationPoint(0.0F, 20F, 0.0F);
        rightleg = new ModelRenderer(this, 12, 0);
        rightleg.addBox(-0.5F, 0.0F, -1F, 1, 2, 1, 0.0F);
        rightleg.setRotationPoint(-1F, 22F, 0.5F);
        leftleg = new ModelRenderer(this, 12, 0);
        leftleg.addBox(-0.5F, 0.0F, -1F, 1, 2, 1, 0.0F);
        leftleg.setRotationPoint(1.0F, 22F, 0.5F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        head.render(f5);
        body.render(f5);
        rightleg.render(f5);
        leftleg.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        head.rotateAngleX = -(f4 / 57.29578F);
        head.rotateAngleY = f3 / 57.29578F;
        rightleg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        leftleg.rotateAngleY = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
    }
}
