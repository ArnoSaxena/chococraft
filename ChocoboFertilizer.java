// <copyright file="ChocoboFertilizer.java">
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
// <summary>Methods used by the Chocobo Fertiliser item</summary>

package net.minecraft.src;

public class ChocoboFertilizer extends Item
{
    public ChocoboFertilizer(int i)
    {
        super(i);
        setMaxStackSize(64);
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
        int i1 = world.getBlockId(i, j, k);
        if (i1 == Block.sapling.blockID)
        {
            if (!world.isRemote)
            {
                ((BlockSapling)Block.sapling).growTree(world, i, j, k, world.rand);
                itemstack.stackSize--;
            }
            return true;
        }
        if (i1 == Block.mushroomBrown.blockID || i1 == Block.mushroomRed.blockID)
        {
            if (!world.isRemote && ((BlockMushroom)Block.blocksList[i1]).fertilizeMushroom(world, i, j, k, world.rand))
            {
                itemstack.stackSize--;
            }
            return true;
        }
        if (i1 == Block.melonStem.blockID || i1 == Block.pumpkinStem.blockID)
        {
            if (!world.isRemote)
            {
                ((BlockStem)Block.blocksList[i1]).fertilizeStem(world, i, j, k);
                itemstack.stackSize--;
            }
            return true;
        }
        if (i1 == Block.crops.blockID)
        {
            if (!world.isRemote)
            {
                ((BlockCrops)Block.crops).fertilize(world, i, j, k);
                itemstack.stackSize--;
            }
            return true;
        }
        if (i1 == mod_chocobo.gysahlStem.blockID)
        {
            if (!world.isRemote)
            {
                ((ChocoboBlockCrops)mod_chocobo.gysahlStem).fertilize(world, i, j, k);
                itemstack.stackSize--;
            }
            return true;
        }
        if (i1 == Block.grass.blockID)
        {
            if (!world.isRemote)
            {
                itemstack.stackSize--;
                label0:
                for (int j1 = 0; j1 < 128; j1++)
                {
                    int k1 = i;
                    int l1 = j + 1;
                    int i2 = k;
                    for (int j2 = 0; j2 < j1 / 16; j2++)
                    {
                        k1 += itemRand.nextInt(3) - 1;
                        l1 += ((itemRand.nextInt(3) - 1) * itemRand.nextInt(3)) / 2;
                        i2 += itemRand.nextInt(3) - 1;
                        if (world.getBlockId(k1, l1 - 1, i2) != Block.grass.blockID || world.isBlockNormalCube(k1, l1, i2))
                        {
                            continue label0;
                        }
                    }

                    if (world.getBlockId(k1, l1, i2) != 0)
                    {
                        continue;
                    }
                    if (itemRand.nextInt(10) != 0)
                    {
                        world.setBlockAndMetadataWithNotify(k1, l1, i2, Block.tallGrass.blockID, 1);
                        continue;
                    }
                    if (itemRand.nextInt(3) != 0)
                    {
                        world.setBlockWithNotify(k1, l1, i2, Block.plantYellow.blockID);
                    }
                    else
                    {
                        world.setBlockWithNotify(k1, l1, i2, Block.plantRed.blockID);
                    }
                }
            }
            return true;
        }
        else
        {
            return true;
        }
    }
}
