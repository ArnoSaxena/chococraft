// <copyright file="ChocoCraftEventCommon.java">
// Copyright (c) 2013 All Right Reserved, http://chococraft.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <email>al-s@gmx.de</email>
// <date>2013-06-08</date>
// <summary>Class for registering event listeners</summary>

package chococraft.common;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import chococraft.common.entities.colours.EntityChocoboPurple;
import chococraft.common.gui.GuiStarter;
import chococraft.common.helper.ChocoboPlayerHelper;
import chococraft.common.items.BlockGysahlStem;
import chococraft.common.network.serverSide.PacketChocoboSpawnItem;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public class ChocoCraftEventCommon
{
	@ForgeSubscribe
	public void onUseBonemeal(BonemealEvent event)
	{
		if (event.ID == ModChocoCraft.gysahlStemBlock.blockID)
		{
			if (((BlockGysahlStem)ModChocoCraft.gysahlStemBlock).onBonemealUse(event.world, event.X, event.Y, event.Z))
			{
				event.setResult(Result.ALLOW);
			}
		}
	}
    
    @ForgeSubscribe
    public void onBurnDamage(LivingAttackEvent event)
    {
    	if(event.entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)event.entity;
    		if(event.source.equals(DamageSource.lava) 
    				|| event.source.equals(DamageSource.inFire) 
    				|| event.source.equals(DamageSource.onFire))
    		{
    			if(player.ridingEntity instanceof EntityChocoboPurple)
    			{
    				event.setCanceled(true);
    			}
    		}
    	}
    }
    
    @ForgeSubscribe
    public void onChocopediaRightClick(PlayerInteractEvent event)
    {
    	if(event.action == Action.RIGHT_CLICK_AIR)
    	{
        	if(event.entity instanceof EntityPlayer)
        	{
        		EntityPlayer player = (EntityPlayer)event.entity;
        		ItemStack currentItem = player.getCurrentEquippedItem();
        		if(currentItem != null)
        		{        			
        			if(currentItem.itemID == ModChocoCraft.chocopediaItem.itemID)
        			{
        				GuiStarter.startChocopediaGui(null);
        				if(event.isCancelable())
        				{
        					event.setCanceled(true);
        				}
        			}
        		}
        	}
    	}
    }
    
    @ForgeSubscribe
    public void onFeatherRightClick(PlayerInteractEvent event)
    {
    	if(event.action == Action.RIGHT_CLICK_BLOCK)
    	{
        	if(event.entity instanceof EntityPlayer)
        	{
        		EntityPlayer player = (EntityPlayer)event.entity;
        		ItemStack currentItem = player.getCurrentEquippedItem();
        		if(currentItem != null)
        		{        			
        			if(currentItem.itemID == ModChocoCraft.chocoboFeatherItem.itemID)
        			{
        				if(player.worldObj.getBlockId(event.x, event.y, event.z) == Block.bookShelf.blockID)
        				{
           	        		player.worldObj.setBlockToAir(event.x, event.y, event.z);
           	        		ChocoboPlayerHelper.useCurrentItem(player);
           	        		ItemStack itemstack = new ItemStack(ModChocoCraft.chocopediaItem.itemID, 1, 0);
           	        		this.sendCreateChocopediaItem(player.worldObj, itemstack, (double)event.x, (double)event.y, (double)event.z);
        				}
        			}
    			}
    		}
    	}
    }
    
	protected void sendCreateChocopediaItem(World world, ItemStack stack, double posX, double posY, double posZ)
	{
		if(Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
		{
			PacketChocoboSpawnItem packet = new PacketChocoboSpawnItem(world, stack, posX, posY, posZ);
			PacketDispatcher.sendPacketToServer(packet.getPacket());			
		}
	}
	
	@ForgeSubscribe
	public void onSkeletonAndZombiDropEvent(LivingDropsEvent event)
	{
		if (this.isChocopediaDropEntity(event.entityLiving))
		{
			double d100 = Math.random() * 100;
			if (d100 < Constants.CHOCOPEDIA_MOB_DROP_RATE)
			{
				event.entityLiving.dropItem(ModChocoCraft.chocopediaItem.itemID, 1);
			}
		}
	}
	
	protected boolean isChocopediaDropEntity(EntityLiving entity)
	{
		if(entity instanceof EntityZombie
				|| entity instanceof EntitySkeleton
				|| entity instanceof EntityPigZombie
				|| entity instanceof EntityWitch
				|| entity instanceof EntityVillager)
		{
			return true;
		}
		return false;
	}
}
