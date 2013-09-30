// <copyright file="EntityChocoboPink.java">
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
// <summary>Entity class for the Pink Chocobo mobs</summary>

package chococraft.common.entities.colours;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import chococraft.common.Constants;
import chococraft.common.entities.EntityChocobo;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class EntityChocoboPink extends EntityChocobo
{
	public EntityChocoboPink(World world)
	{
		super(world);
		this.setHealth(this.getMaxHealth());
//		this.flyingMovementFactor = Constants.CHOCOBO_PINK_FLYMOVEFACT;
//		this.landMovementFactor = Constants.CHOCOBO_DEFAULT_LANDMOVEFACT;
		this.canClimb = true;
		this.canCrossWater = true;
		this.canFly = true;
		this.canJumpHigh = Constants.CHOCOBO_PINK_CANJUMPHIGH;
		this.isImmuneToFire = false;
		this.landSpeedFactor = Constants.CHOCOBO_PINK_LANDSPEEDFACT;
		this.waterSpeedFactor = Constants.CHOCOBO_PINK_WATERSPEEDFACT;
		this.airbornSpeedFactor = Constants.CHOCOBO_PINK_AIRSPEEDFACT;
	}

	protected void entityInit()
	{
		this.color = chocoboColor.PINK;
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
		return "pinkchocobo.png";
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

	public void setLandSpeedFactor(boolean mounted)
	{
		if (mounted)
		{
			this.landSpeedFactor = Constants.CHOCOBO_PINK_LANDSPEEDFACT;
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
			this.canJumpHigh = Constants.CHOCOBO_PINK_CANJUMPHIGH;
		}
		else
		{
			this.canJumpHigh = false;
		}
	}

	public void setRiderAbilities(boolean mounted){}

	@Override
	public float getChocoboMaxHealth()
	{
		return 50.0F;
	}

	protected void fall(float fallHeight)
	{
		return;
	}

	@Override
    public chocoboColor getBabyAnimalColor(EntityAgeable otherAnimalParent)
	{
		if(otherAnimalParent instanceof EntityChocobo)
		{
			EntityChocobo otherParent = (EntityChocobo) otherAnimalParent;

			boolean bothFedGold = this.fedGold && otherParent.fedGold;

			int randColor = rand.nextInt(100);
			chocoboColor chicoboColor = chocoboColor.GOLD;
			switch(otherParent.color)
			{
			case YELLOW:
			case GREEN:
			case BLUE:
			case WHITE:
			case BLACK:
			case PURPLE:
				chicoboColor = otherParent.color;
				break;
			case GOLD:
			case PINK:
			case RED:
				if(!bothFedGold || randColor > 20)
				{
					chicoboColor = chocoboColor.YELLOW;
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
