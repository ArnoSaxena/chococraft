// <copyright file="BlockGysahlGreen.java">
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
// <summary>Block class for Gysahl Greens</summary>

package chococraft.common.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import chococraft.common.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class BlockGysahlGreen extends BlockFlower
{
	public BlockGysahlGreen(int blockId)
    {
        super(blockId);
        setTickRandomly(true);
        float f = 0.5F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.disableStats();
    }

    @SideOnly(Side.CLIENT)
    public Icon getBlockTextureFromSideAndMetadata(int i, int j)
	{
    	return this.field_94336_cN;
	}
    
    public void func_94332_a(IconRegister iconRegister)
    {
    	this.field_94336_cN = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_GY_GREEN);
    }
	
	protected boolean canThisPlantGrowOnThisBlockID(int blockId)
    {
		boolean canGrow = false;
		
		if(blockId == Block.tilledField.blockID 
				|| blockId == Block.grass.blockID
				|| blockId == Block.dirt.blockID)
		{
			canGrow = true;
		}
        return canGrow;
    }
}
