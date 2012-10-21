// <copyright file="EntityChocoboGreen.java">
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
// <summary>Entity class for the Green Chocobo mobs</summary>

package chococraft.common.entities.colours;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import net.minecraft.src.EntityAnimal;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import chococraft.common.Constants;
import chococraft.common.entities.EntityChocobo;

public class EntityChocoboGreen extends EntityChocobo
{
	public EntityChocoboGreen(World world)
	{
		super(world);
		this.setEntityHealth(this.getMaxHealth());
		this.flyingMovementFactor = Constants.CHOCOBO_GREEN_FLYMOVEFACT;
		this.landMovementFactor = Constants.CHOCOBO_DEFAULT_LANDMOVEFACT;
		this.canCrossWater = false;
		this.canJumpHigh = false;
		this.canFly = false;
		this.isImmuneToFire = false;
		this.landSpeedFactor = Constants.CHOCOBO_GREEN_LANDSPEEDFACT;
		this.airbornSpeedFactor = Constants.CHOCOBO_GREEN_AIRSPEEDFACT;
	}

	protected void entityInit()
	{
		this.color = chocoboColor.GREEN;
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

    @SideOnly(Side.CLIENT)
	public String getEntityColourTexture()
	{
		return "/greenchocobo.png";
	}

	@Override
	public void setStepHeight(boolean mounted)
	{
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
	}
	
	public void setLandMovementFactor(boolean mounted)
	{
		if (mounted)
		{
			this.landMovementFactor = Constants.CHOCOBO_GREEN_LANDMOVEFACT;
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
			this.canJumpHigh = Constants.CHOCOBO_GREEN_CANJUMPHIGH;
		}
		else
		{
			this.canJumpHigh = false;
		}
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

    public chocoboColor getBabyAnimalColor(EntityAnimal otherAnimalParent)
	{
		if(otherAnimalParent instanceof EntityChocobo)
		{
			EntityChocobo otherParent = (EntityChocobo) otherAnimalParent;

			boolean bothFedGold = this.fedGold && otherParent.fedGold;

			int randColor = rand.nextInt(100);
			chocoboColor chicoboColor = chocoboColor.GREEN;        
			switch(otherParent.color)
			{
			case YELLOW:
				if(randColor < 50)
				{
					chicoboColor = chocoboColor.YELLOW;
				}
				break;
			case BLUE:
				if(randColor > 75)
				{
					chicoboColor = chocoboColor.WHITE;
				}
				else if (randColor > 50)
				{
					chicoboColor = bothFedGold ? chocoboColor.WHITE : chocoboColor.BLUE;
				}
				else if (randColor > 38 || (randColor > 25 && bothFedGold))
				{
					chicoboColor = chocoboColor.BLUE;
				}
				break;
			case BLACK:
			case WHITE:
				if(randColor > 75 || (randColor > 50 && bothFedGold))
				{
					chicoboColor = otherParent.color;
				}
				else if ((randColor > 25 && !bothFedGold) || randColor < 25)
				{
					chicoboColor = chocoboColor.YELLOW;
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
