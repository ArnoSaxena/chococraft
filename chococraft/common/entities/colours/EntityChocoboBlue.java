// <copyright file="EntityChocoboBlue.java">
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
// <summary>Entity class for the Blue Chocobo mobs</summary>

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

public class EntityChocoboBlue extends EntityChocobo
{
	public EntityChocoboBlue(World world)
	{
		super(world);
		this.setEntityHealth(this.getMaxHealth());
		this.flyingMovementFactor = Constants.CHOCOBO_BLUE_FLYMOVEFACT;
		this.landMovementFactor = Constants.CHOCOBO_DEFAULT_LANDMOVEFACT;
		this.canCrossWater = true;
		this.canClimb = false;
		this.canJumpHigh = Constants.CHOCOBO_BLUE_CANJUMPHIGH;
		this.canFly = false;
		this.isImmuneToFire = false;
		this.landSpeedFactor = Constants.CHOCOBO_BLUE_LANDSPEEDFACT;
		this.waterSpeedFactor = Constants.CHOCOBO_BLUE_WATERSPEEDFACT;
		this.airbornSpeedFactor = Constants.CHOCOBO_BLUE_AIRSPEEDFACT;		
	}

	protected void entityInit()
	{
		this.color = chocoboColor.BLUE;
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
	
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
	}

    //@SideOnly(Side.CLIENT)
	public String getEntityColourTexture()
	{
		return "/bluechocobo.png";
	}

	@Override
	public void setStepHeight(boolean mounted)
	{
		if (mounted)
		{
			this.stepHeight = 1.0F;            
		}
		else
		{
			this.stepHeight = 0.5F;
		}
	}

	public void setLandMovementFactor(boolean mounted)
	{
		if (mounted)
		{
			this.landMovementFactor = Constants.CHOCOBO_BLUE_LANDMOVEFACT;
		}
		else
		{
			this.landMovementFactor = Constants.CHOCOBO_DEFAULT_LANDMOVEFACT;			
		}
	}

	public void setJumpHigh(boolean mounted)
	{
		if(mounted)
		{
			this.canJumpHigh = Constants.CHOCOBO_BLUE_CANJUMPHIGH;
		}
		else
		{
			this.canJumpHigh = false;
		}
	}
	
	@Override
	public void setRiderAbilities(boolean mounted)
	{
		if(this.riddenByEntity instanceof EntityPlayer && ModChocoCraft.riderBuffsEnabled)
		{
			EntityPlayer rider = (EntityPlayer)this.riddenByEntity;			
			rider.addPotionEffect(new PotionEffect(13, 100, -1, true));
		}
	}
	
	@Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }

	@Override
	public int getMaxHealth()
	{
		return 30;
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
			chocoboColor chicoboColor = chocoboColor.BLUE;        
			switch(otherParent.color)
			{
			case YELLOW:
				if(randColor < 50)
				{
					chicoboColor = chocoboColor.YELLOW;
				}
				break;
			case BLUE:
				if(bothFedGold)
				{
					if(randColor > 60)
					{
						chicoboColor = chocoboColor.WHITE;
					}
					else if(randColor > 30)
					{
						chicoboColor = chocoboColor.BLUE;
					}
				}
				else
				{
					if(randColor > 80)
					{
						chicoboColor = chocoboColor.WHITE;
					}
					else if(randColor > 40)
					{
						chicoboColor = chocoboColor.BLUE;
					}
				}
				break;
			case BLACK:
			case WHITE:
				if(bothFedGold)
				{
					if(randColor > 70)
					{
						chicoboColor = chocoboColor.YELLOW;
					}
					else if (randColor > 35)
					{
						chicoboColor = otherParent.color;
					}					
				}
				else
				{
					if(randColor > 90)
					{
						chicoboColor = chocoboColor.YELLOW;
					}
					else if (randColor > 45)
					{
						chicoboColor = otherParent.color;
					}
				}
				break;
			}
			return chicoboColor;
		}
		else
		{
			return null;
		}
	}
}
