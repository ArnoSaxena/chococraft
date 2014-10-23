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

import chococraft.common.config.Constants;
import chococraft.common.ModChocoCraft;
import chococraft.common.config.ChocoCraftBlocks;
import chococraft.common.config.ChocoCraftItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;


public class BlockGysahlStem extends BlockFlower
{
	static final int MAX_STAGE = 4; 
	private ArrayList<IIcon> icons;
	
	public BlockGysahlStem()
    {
        super(5);//what is this!?
        this.setTickRandomly(true);
        float f = 0.5F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.setCreativeTab(null);
        this.disableStats();
        this.setStepSound(soundTypeGrass);
		setHardness(0f);
		setBlockName("gysahlStemBlock");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j)
	{
    	if(j < this.icons.size())
    	{
        	return this.icons.get(j);    		
    	}
    	return this.icons.get(4);
	}
    
    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
    	this.icons = new ArrayList<IIcon>();
    	this.icons.add(0, iconRegister.registerIcon(Constants.TCC_MODID + ":" + Constants.KEY_GY_STEM01));
    	this.icons.add(1, iconRegister.registerIcon(Constants.TCC_MODID + ":" + Constants.KEY_GY_STEM02));
    	this.icons.add(2, iconRegister.registerIcon(Constants.TCC_MODID + ":" + Constants.KEY_GY_STEM03));
    	this.icons.add(3, iconRegister.registerIcon(Constants.TCC_MODID + ":" + Constants.KEY_GY_STEM04));
    	this.icons.add(4, iconRegister.registerIcon(Constants.TCC_MODID + ":" + Constants.KEY_GY_STEM05));
    }
	
    @Override
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
                    theWorld.setBlockMetadataWithNotify(xPos, yPos, zPos, gysahlStage, 2);
                }
            }
        }
    }
	
	private float getGrowthRate(World theWorld, int xPos, int yPos, int zPos)
    {
        float growRate = 1.0F;
        Block i = theWorld.getBlock(xPos, yPos, zPos - 1);
		Block j = theWorld.getBlock(xPos, yPos, zPos + 1);
		Block k = theWorld.getBlock(xPos - 1, yPos, zPos);
		Block l = theWorld.getBlock(xPos + 1, yPos, zPos);
		Block i1 = theWorld.getBlock(xPos - 1, yPos, zPos - 1);
		Block j1 = theWorld.getBlock(xPos + 1, yPos, zPos - 1);
		Block k1 = theWorld.getBlock(xPos + 1, yPos, zPos + 1);
		Block l1 = theWorld.getBlock(xPos - 1, yPos, zPos + 1);

        boolean samePlantLeftOrRight = k.equals(this) || l.equals(this);
        boolean samePlantFrontOrBack = i.equals(this) || j.equals(this);
        boolean samePlantAnyCorner = i1.equals(this) || j1.equals(this) || k1.equals(this) || l1.equals(this);

        for (int xTmp = xPos - 1; xTmp <= xPos + 1; xTmp++)
        {
            for (int zTmp = zPos - 1; zTmp <= zPos + 1; zTmp++)
            {
                Block baseBlockId = theWorld.getBlock(xTmp, yPos - 1, zTmp);
                float tmpGrowRate = 0.0F;

                if (this.canThisPlantGrowOnThisBlock(baseBlockId))
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


	protected boolean canThisPlantGrowOnThisBlock(Block block)
    {
        return block.equals(Blocks.farmland);
    }
    
	//TODO check this - old
    @Override
    public void dropBlockAsItemWithChance(World world, int i, int j, int k, int l, float f, int i1)
    {
    	super.dropBlockAsItemWithChance(world, i, j, k, l, f, 0);
    	if (!world.isRemote)
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
    			EntityItem entityitem = new EntityItem(world, (float)i + f2, (float)j + f3, (float)k + f4, new ItemStack(ChocoCraftItems.gysahlSeedsItem));
    			entityitem.delayBeforeCanPickup = 10;
    			world.spawnEntityInWorld(entityitem);
    		}
    	}
    }

    @Override
    public Item getItemDropped(int i, Random random, int j)
    {
    	if (i == 4)
    	{
    		if(random.nextInt(1000) > ModChocoCraft.gysahlGreenMutationRate)
    		{
    			return Item.getItemFromBlock(ChocoCraftBlocks.gysahlGreenBlock);
    		}
    		else 
    		{
    			if(random.nextInt(1000) > ModChocoCraft.gysahlLoveMutationRate)
    			{
    				return ChocoCraftItems.gysahlLoverlyItem;
    			}
    			else
    			{
    				return ChocoCraftItems.gysahlGoldenItem;
    			}
    		}
    	}
    	else
        {
            return null;
        }
    }


	//Called from a event , does not need override
	public boolean onBonemealUse(World theWorld, int xPos, int yPos, int zPos)
	{
		if(theWorld.getBlockMetadata(xPos, yPos, zPos) < MAX_STAGE)
		{		
			theWorld.setBlockMetadataWithNotify(xPos, yPos, zPos, MAX_STAGE, 2);
			return true;
		}
		else
		{
			return false;
		}
	}	
}
