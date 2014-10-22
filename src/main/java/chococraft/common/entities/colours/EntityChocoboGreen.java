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

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import chococraft.common.Constants;
import chococraft.common.entities.EntityChocobo;

public class EntityChocoboGreen extends EntityChocobo
{
	public EntityChocoboGreen(World world)
	{
		super(world);
		this.color = chocoboColor.GREEN;
		this.setHealth(this.getMaxHealth());
		this.canCrossWater = false;
		this.canJumpHigh = Constants.CHOCOBO_GREEN_CANJUMPHIGH;
		this.canFly = false;
		this.isImmuneToFire = false;
		this.landSpeedFactor = Constants.CHOCOBO_GREEN_LANDSPEEDFACT;
		this.waterSpeedFactor = Constants.CHOCOBO_GREEN_WATERSPEEDFACT;
		this.airbornSpeedFactor = Constants.CHOCOBO_GREEN_AIRSPEEDFACT;
	}

	public void writeSpawnData(ByteBuf data)
	{
		super.writeSpawnData(data);
	}

	public void readSpawnData(ByteBuf data)
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
		return "greenchocobo.png";
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
			this.landSpeedFactor = Constants.CHOCOBO_GREEN_LANDSPEEDFACT;
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
			this.canJumpHigh = Constants.CHOCOBO_GREEN_CANJUMPHIGH;
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
		return 30.0F;
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
