// <copyright file="BlockGysahlStem.java">
// Copyright (c) 2012 All Right Reserved, http://chococraft.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <email>al-s@gmx.de</email>
// <date>2012-10-25</date>
// <summary>Block class for Gysahl Stems</summary>

package chococraft.common.items;

import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;

import chococraft.common.Constants;
import chococraft.common.ModChocoCraft;

import net.minecraft.src.Block;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class BlockGysahlStem extends BlockFlower
{
	static final int MAX_STAGE = 4; 
	
	public BlockGysahlStem(int blockId, int textureId)
    {
        super(blockId, textureId);
        blockIndexInTexture = textureId;
        setTickRandomly(true);
        float f = 0.5F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.setCreativeTab(null);
        this.disableStats();
        this.setRequiresSelfNotify();
        this.setTextureFile(Constants.CHOCOBO_ITEM_TEXTURES);
    }
	
	public void updateTick(World theWorld, int xPos, int yPos, int zPos, Random randInts)
    {
        super.updateTick(theWorld, xPos, yPos, zPos, randInts);

        if (theWorld.getBlockLightValue(xPos, yPos + 1, zPos) >= 9)
        {
            int gysahlStage = theWorld.getBlockMetadata(xPos, yPos, zPos);

            if (gysahlStage < MAX_STAGE)
            {
                float f = getGrowthRate(theWorld, xPos, yPos, zPos);

                if (randInts.nextInt((int)(25F / f) + 1) == 0)
                {
                    gysahlStage++;
                    theWorld.setBlockMetadataWithNotify(xPos, yPos, zPos, gysahlStage);
                }
            }
        }
    }
	
	private float getGrowthRate(World theWorld, int xPos, int yPos, int zPos)
    {
        float growRate = 1.0F;
        int i = theWorld.getBlockId(xPos, yPos, zPos - 1);
        int j = theWorld.getBlockId(xPos, yPos, zPos + 1);
        int k = theWorld.getBlockId(xPos - 1, yPos, zPos);
        int l = theWorld.getBlockId(xPos + 1, yPos, zPos);
        int i1 = theWorld.getBlockId(xPos - 1, yPos, zPos - 1);
        int j1 = theWorld.getBlockId(xPos + 1, yPos, zPos - 1);
        int k1 = theWorld.getBlockId(xPos + 1, yPos, zPos + 1);
        int l1 = theWorld.getBlockId(xPos - 1, yPos, zPos + 1);
        boolean samePlantLeftOrRight = k == blockID || l == blockID;
        boolean samePlantFrontOrBack = i == blockID || j == blockID;
        boolean samePlantAnyCorner = i1 == blockID || j1 == blockID || k1 == blockID || l1 == blockID;

        for (int xTmp = xPos - 1; xTmp <= xPos + 1; xTmp++)
        {
            for (int zTmp = zPos - 1; zTmp <= zPos + 1; zTmp++)
            {
                int baseBlockId = theWorld.getBlockId(xTmp, yPos - 1, zTmp);
                float tmpGrowRate = 0.0F;

                if (this.canThisPlantGrowOnThisBlockID(baseBlockId))
                {
                    tmpGrowRate = 1.0F;

                    if (theWorld.getBlockMetadata(xTmp, yPos - 1, zTmp) > 0)
                    {
                        tmpGrowRate = 3F;
                    }
                }

                if (xTmp != xPos || zTmp != zPos)
                {
                    tmpGrowRate /= 4F;
                }

                growRate += tmpGrowRate;
            }
        }

        if (samePlantAnyCorner || samePlantLeftOrRight && samePlantFrontOrBack)
        {
            growRate /= 2.0F;
        }

        return growRate;
    }
	
	protected boolean canThisPlantGrowOnThisBlockID(int blockId)
    {
		boolean canGrow = false;
		
		if(blockId == Block.tilledField.blockID)
		{
			canGrow = true;
		}
        return canGrow;
    }
	

	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		return j + 32;
	}

    public void dropBlockAsItemWithChance(World world, int i, int j, int k, int l, float f, int i1)
    {
    	super.dropBlockAsItemWithChance(world, i, j, k, l, f, 0);
    	if (Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
    	{
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
    			EntityItem entityitem = new EntityItem(world, (float)i + f2, (float)j + f3, (float)k + f4, new ItemStack(ModChocoCraft.gysahlSeedsItem));
    			entityitem.delayBeforeCanPickup = 10;
    			world.spawnEntityInWorld(entityitem);
    			EntityPlayer entityplayer = (EntityPlayer)world.playerEntities.get(0);
    			if (entityplayer != null)
    			{
    				//entityplayer.addStat(mod_chocobo.farmGreen, 1);
    			}
    		}
    	}
    }

    public int idDropped(int i, Random random, int j)
    {
    	if (i == 4)
    	{
    		if(random.nextInt(100) > ModChocoCraft.gysahlGreenMutationRate)
    		{
    			return ModChocoCraft.gysahlGreenBlock.blockID;
    		}
    		else 
    		{
    			if(random.nextInt(100) > ModChocoCraft.gysahlLoveMutationRate)
    			{
    				return ModChocoCraft.gysahlLoverlyItem.shiftedIndex;
    			}
    			else
    			{
    				return ModChocoCraft.gysahlGoldenItem.shiftedIndex;
    			}
    		}
    	}
    	else
        {
            return -1;
        }
    }

	public boolean onBonemealUse(World theWorld, int xPos, int yPos, int zPos)
	{
		if(theWorld.getBlockMetadata(xPos, yPos, zPos) < MAX_STAGE)
		{		
			theWorld.setBlockMetadataWithNotify(xPos, yPos, zPos, MAX_STAGE);
			return true;
		}
		else
		{
			return false;
		}
	}	
}
