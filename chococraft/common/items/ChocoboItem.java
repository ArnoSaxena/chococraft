// <copyright file="ChocoboItem.java">
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
// <summary>Item class for ChocoCraft mod</summary>

package chococraft.common.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chococraft.common.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ChocoboItem extends Item
{
	public ChocoboItem(int itemId)
	{
		super(itemId);
		//this.setTextureFile(Constants.CHOCOBO_ITEM_TEXTURES);
	}

	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		super.onItemRightClick(itemStack, world, player);		
		return itemStack;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void func_94581_a(IconRegister iconRegister)
	{
		String name = this.getUnlocalizedName();
		if(name.equals("item.chocopedia"))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_CHOCOPEDIA);
		}
		else if(name.equals("item.chocoboFeather"))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_FEATHER);
		}
		else if(name.equals("item.chocoboSaddle"))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_SADDLE);
		}
		else if(name.equals("item.chocoboSaddleBags"))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_SADDLEBAG);
		}
		else if(name.equals("item.chocoboPackBags"))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_PACKBAG);
		}
		else if(name.equals("item.gysahlLoverly"))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_GY_LOVERLY);
		}
		else if(name.equals("item.gysahlGolden"))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_GY_GOLDEN);
		}
		else if(name.equals("item.gysahlPink"))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_GY_PINK);
		}
		else if(name.equals("item.gysahlRed"))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_GY_RED);
		}
		else if(name.equals("item.gysahlCake"))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_GY_CAKE);
		}
		else if(name.equals("item.chocoboWhistle"))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_WHISTLE);
		}
	}	
}
