// <copyright file="EntityChocoboWhite.java">
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
// <summary>Entity class for the White Chocobo mobs</summary>

package chococraft.common.entities.colours;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import chococraft.common.Constants;
import chococraft.common.entities.EntityChocobo;


public class EntityChocoboWhite extends EntityChocobo
{
	public EntityChocoboWhite(World world)
	{
		super(world);
		this.setEntityHealth(this.getMaxHealth());
		this.landMovementFactor = Constants.CHOCOBO_DEFAULT_LANDMOVEFACT;
		this.flyingMovementFactor = Constants.CHOCOBO_WHITE_FLYMOVEFACT;
		this.canClimb = true;
		this.canCrossWater = true;
		this.canJumpHigh = Constants.CHOCOBO_WHITE_CANJUMPHIGH;
		this.canFly = false;
		this.isImmuneToFire = false;
		this.landSpeedFactor = Constants.CHOCOBO_WHITE_LANDSPEEDFACT;
		this.waterSpeedFactor = Constants.CHOCOBO_WHITE_WATERSPEEDFACT;
		this.airbornSpeedFactor = Constants.CHOCOBO_WHITE_AIRSPEEDFACT;
	}
	
	protected void entityInit()
	{
		this.color = chocoboColor.WHITE;
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
		return "/whitechocobo.png";
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

	public void setLandMovementFactor(boolean mounted)
	{
		if (mounted)
		{
			this.landMovementFactor = Constants.CHOCOBO_WHITE_LANDMOVEFACT;
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
			this.canJumpHigh = Constants.CHOCOBO_WHITE_CANJUMPHIGH;
		}
		else
		{
			this.canJumpHigh = false;
		}
	}

	public void setRiderAbilities(boolean mounted){}

	@Override
	public int getMaxHealth()
	{
		return 40;
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
			chocoboColor chicoboColor = chocoboColor.WHITE;        
			switch(otherParent.color)
			{
			case YELLOW:
				if(randColor > 75 || (randColor > 50 && bothFedGold))
				{
					chicoboColor = chocoboColor.BLACK;
				}
				else if ((randColor > 25 && !bothFedGold) || randColor < 25)
				{
					chicoboColor = chocoboColor.YELLOW;
				}
				break;
			case GREEN:
			case BLUE:
				if(randColor < 75)
				{
					if ((randColor > 50 && !bothFedGold) || randColor > 38)
					{
						chicoboColor = otherParent.color;
					}
					else if (randColor > 25)
					{
						chicoboColor = bothFedGold ? otherParent.color : chocoboColor.YELLOW;
					}
					else
					{
						chicoboColor = chocoboColor.YELLOW;
					}
				}
				break;
			case BLACK:
				if(bothFedGold)
				{
					if(randColor > 50)
					{
						chicoboColor = chocoboColor.GOLD;
					}
					else if (randColor > 25)
					{
						chicoboColor = chocoboColor.BLACK;
					}
				}
				else
				{
					if(randColor > 75)
					{
						chicoboColor = chocoboColor.BLACK;
					}
					else if (randColor < 25)
					{
						chicoboColor = chocoboColor.YELLOW;
					}
				}
				break;
			case GOLD:
			case PINK:
			case RED:
				if(randColor > 75 && bothFedGold)
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
