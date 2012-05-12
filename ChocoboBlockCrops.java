// <copyright file="ChocoboBlockCrops.java">
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
// <summary>Block class for Chocobo crops</summary>

package net.minecraft.src;

import java.util.Random;

public class ChocoboBlockCrops extends BlockFlower
{
    protected ChocoboBlockCrops(int i, int j)
    {
        super(i, j);
        blockIndexInTexture = j;
        setTickRandomly(true);
        float f = 0.5F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
    }

    protected boolean canThisPlantGrowOnThisBlockID(int i)
    {
        return i == Block.tilledField.blockID;
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        super.updateTick(world, i, j, k, random);
        if (world.getBlockLightValue(i, j + 1, k) >= 9)
        {
            int l = world.getBlockMetadata(i, j, k);
            if (l < 7)
            {
                float f = getGrowthRate(world, i, j, k);
                if (random.nextInt((int)(25F / f) + 1) == 0)
                {
                    l++;
                    world.setBlockMetadataWithNotify(i, j, k, l);
                }
            }
        }
    }

    public void fertilize(World world, int i, int j, int k)
    {
        world.setBlockMetadataWithNotify(i, j, k, 7);
    }

    private float getGrowthRate(World world, int i, int j, int k)
    {
        float f = 1.0F;
        int l = world.getBlockId(i, j, k - 1);
        int i1 = world.getBlockId(i, j, k + 1);
        int j1 = world.getBlockId(i - 1, j, k);
        int k1 = world.getBlockId(i + 1, j, k);
        int l1 = world.getBlockId(i - 1, j, k - 1);
        int i2 = world.getBlockId(i + 1, j, k - 1);
        int j2 = world.getBlockId(i + 1, j, k + 1);
        int k2 = world.getBlockId(i - 1, j, k + 1);
        boolean flag = j1 == blockID || k1 == blockID;
        boolean flag1 = l == blockID || i1 == blockID;
        boolean flag2 = l1 == blockID || i2 == blockID || j2 == blockID || k2 == blockID;
        for (int l2 = i - 1; l2 <= i + 1; l2++)
        {
            for (int i3 = k - 1; i3 <= k + 1; i3++)
            {
                int j3 = world.getBlockId(l2, j - 1, i3);
                float f1 = 0.0F;
                if (j3 == Block.tilledField.blockID)
                {
                    f1 = 1.0F;
                    if (world.getBlockMetadata(l2, j - 1, i3) > 0)
                    {
                        f1 = 3F;
                    }
                }
                if (l2 != i || i3 != k)
                {
                    f1 /= 4F;
                }
                f += f1;
            }
        }

        if (flag2 || flag && flag1)
        {
            f /= 2.0F;
        }
        return f;
    }

    public int getBlockTextureFromSideAndMetadata(int i, int j)
    {
        if (j == 0)
        {
            return mod_chocobo.gysahlCrop0;
        }
        if (j == 1 || j == 2)
        {
            return mod_chocobo.gysahlCrop1;
        }
        if (j == 3 || j == 4)
        {
            return mod_chocobo.gysahlCrop2;
        }
        if (j == 5 || j == 6)
        {
            return mod_chocobo.gysahlCrop3;
        }
        if (j == 7)
        {
            return mod_chocobo.gysahlCrop4;
        }
        else
        {
            return -1;
        }
    }

    public int getRenderType()
    {
        return 1;
    }

    public void dropBlockAsItemWithChance(World world, int i, int j, int k, int l, float f, int i1)
    {
        super.dropBlockAsItemWithChance(world, i, j, k, l, f, 0);
        if (world.isRemote)
        {
            return;
        }
        int j1 = 3 + i1;
        for (int k1 = 0; k1 < j1; k1++)
        {
            if (world.rand.nextInt(15) > l)
            {
                continue;
            }
            float f1 = 0.7F;
            float f2 = world.rand.nextFloat() * f1 + (1.0F - f1) * 0.5F;
            float f3 = world.rand.nextFloat() * f1 + (1.0F - f1) * 0.5F;
            float f4 = world.rand.nextFloat() * f1 + (1.0F - f1) * 0.5F;
            EntityItem entityitem = new EntityItem(world, (float)i + f2, (float)j + f3, (float)k + f4, new ItemStack(mod_chocobo.gysahlSeeds));
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
            EntityPlayer entityplayer = (EntityPlayer)world.playerEntities.get(0);
            if (entityplayer != null)
            {
                entityplayer.addStat(mod_chocobo.farmGreen, 1);
            }
        }
    }

    public int idDropped(int i, Random random, int j)
    {
        if (i == 7)
        {
            return mod_chocobo.gysahlGreen.blockID;
        }
        else
        {
            return -1;
        }
    }

    public int quantityDropped(Random random)
    {
        return 1;
    }
}
