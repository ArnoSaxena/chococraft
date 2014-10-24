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

import chococraft.common.config.ChocoCraftCreativeTabs;
import chococraft.common.config.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;


public class BlockGysahlGreen extends BlockBush
{
	public BlockGysahlGreen()
    {
		setTickRandomly(true);
        float f = 0.5F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
		this.disableStats();
		setStepSound(Block.soundTypeGrass);
		setHardness(0.0F);
		setBlockName("gysahlGreenBlock");
		this.setCreativeTab(ChocoCraftCreativeTabs.tabChococraft);

    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j)
	{
    	return this.blockIcon;
	}
    
    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
    	this.blockIcon = iconRegister.registerIcon(Constants.TCC_MODID + ":" + Constants.KEY_GY_GREEN);
    }

}
