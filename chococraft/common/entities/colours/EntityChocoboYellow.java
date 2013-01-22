// <copyright file="EntityChocoboYellow.java">
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
// <summary>Entity class for the Yellow Chocobo mobs</summary>

package chococraft.common.entities.colours;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import chococraft.common.Constants;
import chococraft.common.ModChocoCraft;
import chococraft.common.entities.EntityChocobo;
import chococraft.common.helper.ChocoboEntityHelper;

public class EntityChocoboYellow extends EntityChocobo
{

	//public final chocoboColor color = chocoboColor.YELLOW;

	public EntityChocoboYellow(World world)
	{
		super(world);
		this.setEntityHealth(this.getMaxHealth());
		this.landMovementFactor = Constants.CHOCOBO_DEFAULT_LANDMOVEFACT;
		this.flyingMovementFactor = Constants.CHOCOBO_YELLOW_FLYMOVEFACT;
		this.canClimb = false;
		this.canCrossWater = false;
		this.canJumpHigh = Constants.CHOCOBO_YELLOW_CANJUMPHIGH;
		this.canFly = false;
		this.isImmuneToFire = false;
		this.landSpeedFactor = Constants.CHOCOBO_YELLOW_LANDSPEEDFACT;
		this.waterSpeedFactor = Constants.CHOCOBO_YELLOW_WATERSPEEDFACT;
		this.airbornSpeedFactor = Constants.CHOCOBO_YELLOW_AIRSPEEDFACT;
	}

	protected void entityInit()
	{
		this.color = chocoboColor.YELLOW;
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
		return "/chocobo.png";
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
			this.landMovementFactor = Constants.CHOCOBO_YELLOW_LANDMOVEFACT;
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
			this.canJumpHigh = Constants.CHOCOBO_YELLOW_CANJUMPHIGH;
		}
		else
		{
			this.canJumpHigh = false;
		}
	}

	public boolean getCanSpawnHere()
	{
		if(ChocoboEntityHelper.countWildChocobos(this.worldObj) >= ModChocoCraft.spawnGroupMax)
		{
			return false;
		}
		
		if(this.rand.nextInt(100) > ModChocoCraft.spawnProbability)
		{
			return false;
		}		
		return super.getCanSpawnHere();
	}

	public void setRiderAbilities(boolean mounted){}

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
			chocoboColor chicoboColor = chocoboColor.YELLOW;
			switch(otherParent.color)
			{
			case YELLOW:
				if(randColor > 88)
				{
					chicoboColor = chocoboColor.BLUE;
				}
				else if (randColor > 75)
				{
					chicoboColor = bothFedGold ? chocoboColor.BLUE : chocoboColor.GREEN;
				}
				else if (randColor > 50 && bothFedGold)
				{
					chicoboColor = chocoboColor.GREEN;
				}
				break;
			case GREEN:
			case BLUE:
			case BLACK:
				if(randColor > 50)
				{
					chicoboColor = otherParent.color;
				}
				break;
			case WHITE:
				if(randColor > 75)
				{
					chicoboColor = chocoboColor.BLACK;
				}
				if(randColor > 50)
				{
					chicoboColor = bothFedGold ? chocoboColor.BLACK : chocoboColor.WHITE;
				}
				if(randColor > 38 || (randColor > 25 && bothFedGold))
				{
					chicoboColor = chocoboColor.WHITE;
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
