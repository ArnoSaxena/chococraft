// <copyright file="WorldGenGysahls.java">
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
// <summary>distributing wild Gysahl greens in a newly generated chunk</summary>

package net.minecraft.src;

import java.util.Random;

public class WorldGenGysahls extends WorldGenerator
{
    private int plantBlockId;

    public WorldGenGysahls(int i)
    {
        plantBlockId = i;
    }

    public boolean generate(World world, Random random, int i, int j, int k)
    {
        for (int l = 0; l < 40; l++)
        {
            int i1 = (i + random.nextInt(8)) - random.nextInt(8);
            int j1 = (j + random.nextInt(4)) - random.nextInt(4);
            int k1 = (k + random.nextInt(8)) - random.nextInt(8);
            if (world.isAirBlock(i1, j1, k1) && ((BlockFlower)Block.blocksList[plantBlockId]).canBlockStay(world, i1, j1, k1))
            {
                world.setBlock(i1, j1, k1, plantBlockId);
            }
        }

        return true;
    }
}
