// <copyright file="EntityChocoboBlack.java">
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
// <date>2012-09-10</date>
// <summary>Entity class for the Black Chocobo mobs</summary>

package chococraft.common.entities.colours;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import chococraft.common.Constants;
import chococraft.common.ModChocoCraft;
import chococraft.common.entities.EntityChocobo;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class EntityChocoboBlack extends EntityChocobo
{
	public EntityChocoboBlack(World world)
	{
		super(world);
		this.setHealth(this.getMaxHealth());
		this.canClimb = true;
		this.canCrossWater = true;
		this.canJumpHigh = Constants.CHOCOBO_BLACK_CANJUMPHIGH;
		this.canFly = false;
		this.isImmuneToFire = false;
		this.landSpeedFactor = Constants.CHOCOBO_BLACK_LANDSPEEDFACT;
		this.waterSpeedFactor = Constants.CHOCOBO_BLACK_WATERSPEEDFACT;
		this.airbornSpeedFactor = Constants.CHOCOBO_BLACK_AIRSPEEDFACT;
	}

	protected void entityInit()
	{
		this.color = chocoboColor.BLACK;
		super.entityInit();
	}
	
	public void writeSpawnData(ByteArrayDataOutput data)
	{
		super.writeSpawnData(data);
	}

	public void readSpawnData(ByteArrayDataInput data)
	{
		super.readSpawnData(data);
	}	
	
    //@SideOnly(Side.CLIENT)
	public String getEntityColourTexture()
	{
		return "blackchocobo.png";
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
	}

	@Override
	public void setStepHeight(boolean mounted)
	{
		if (mounted)
		{
			this.stepHeight = 2.0F;
		}
		else
		{
			this.stepHeight = 0.5F;
		}
	}

//	public void setLandMovementFactor(boolean mounted)
//	{
//		if (mounted)
//		{
//			this.landMovementFactor = Constants.CHOCOBO_BLACK_LANDMOVEFACT;
//		}
//		else
//		{
//			this.landMovementFactor = Constants.CHOCOBO_DEFAULT_LANDMOVEFACT;			
//		}
//	}
	
	public void setLandSpeedFactor(boolean mounted)
	{
		if (mounted)
		{
			this.landSpeedFactor = Constants.CHOCOBO_BLACK_LANDSPEEDFACT;
		}
		else
		{
			this.landSpeedFactor = Constants.CHOCOBO_DEFAULT_LANDSPEEDFACT;			
		}
	}
	
	public void setJumpHigh(boolean mounted)
	{
		if(mounted)
		{
			this.canJumpHigh = Constants.CHOCOBO_BLACK_CANJUMPHIGH;
		}
		else
		{
			this.canJumpHigh = false;
		}
	}
	
	public void setRiderAbilities(boolean mounted)
	{
		if(this.riddenByEntity instanceof EntityPlayer && ModChocoCraft.riderBuffsEnabled)
		{
			EntityPlayer rider = (EntityPlayer)this.riddenByEntity;			
			rider.addPotionEffect(new PotionEffect(16, 100, -1, true));	
		}
	}

	@Override
	public float getChocoboMaxHealth()
	{
		return 40.0F;
	}

	protected void fall(float fallHeight)
	{
		super.fall(fallHeight);
	}

	@Override
    public chocoboColor getBabyAnimalColor(EntityAgeable otherAnimalParent)
	{
		if(otherAnimalParent instanceof EntityChocobo)
		{
			EntityChocobo otherParent = (EntityChocobo) otherAnimalParent;

			boolean bothFedGold = this.fedGold && otherParent.fedGold;

			int randColor = rand.nextInt(100);
			chocoboColor chicoboColor = chocoboColor.BLACK;        
			switch(otherParent.color)
			{
			case YELLOW:
			case GREEN:
			case BLUE:
				if(randColor < 50)
				{
					chicoboColor = otherParent.color;
				}
				break;
			case WHITE:
				if(bothFedGold)
				{
					if(randColor > 50)
					{
						chicoboColor = chocoboColor.GOLD;
					}
					else if (randColor > 25)
					{
						chicoboColor = chocoboColor.WHITE;
					}
				}
				else
				{
					if(randColor > 75)
					{
						chicoboColor = chocoboColor.YELLOW;
					}
					else if (randColor > 38)
					{
						chicoboColor = chocoboColor.WHITE;
					}
				}
			case GOLD:
			case PINK:
			case RED:
				if(randColor > 90 && bothFedGold)
				{
					chicoboColor = chocoboColor.GOLD;
				}
			}
			return chicoboColor;
		}
		else
		{
			return null;
		}
	}
}
