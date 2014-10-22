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

import io.netty.buffer.ByteBuf;
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
		this.color = chocoboColor.YELLOW;
		this.setHealth(this.getMaxHealth());
		this.canClimb = false;
		this.canCrossWater = false;
		this.canJumpHigh = Constants.CHOCOBO_YELLOW_CANJUMPHIGH;
		this.canFly = false;
		this.isImmuneToFire = false;
		this.landSpeedFactor = Constants.CHOCOBO_YELLOW_LANDSPEEDFACT;
		this.waterSpeedFactor = Constants.CHOCOBO_YELLOW_WATERSPEEDFACT;
		this.airbornSpeedFactor = Constants.CHOCOBO_YELLOW_AIRSPEEDFACT;
	}

	@Override
	public void writeSpawnData(ByteBuf data)
	{
		super.writeSpawnData(data);
	}

	@Override
	public void readSpawnData(ByteBuf data)
	{
		super.readSpawnData(data);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
	}

	//@SideOnly(Side.CLIENT)
	@Override
	public String getEntityColourTexture()
	{
		return "yellowchocobo.png";
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

	@Override
	public void setLandSpeedFactor(boolean mounted)
	{
		if (mounted)
		{
			this.landSpeedFactor = Constants.CHOCOBO_YELLOW_LANDSPEEDFACT;
		}
		else
		{
			this.landSpeedFactor = Constants.CHOCOBO_DEFAULT_LANDSPEEDFACT;			
		}
	}

	@Override
	public void setJumpHigh(boolean mounted)
	{
		this.canJumpHigh = mounted && Constants.CHOCOBO_YELLOW_CANJUMPHIGH;
	}

	@Override
	public boolean getCanSpawnHere()
	{
		return ChocoboEntityHelper.countWildChocobos(this.worldObj) < ModChocoCraft.spawnGroupMax && this.rand.nextInt(100) <= ModChocoCraft.spawnProbability && super.getCanSpawnHere();

	}

	@Override
	public void setRiderAbilities(boolean mounted){}

	@Override
	public float getChocoboMaxHealth()
	{
		return 30.0F;
	}

	@Override
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
				if(bothFedGold)
				{
					if(randColor > 80)
					{
						chicoboColor = chocoboColor.BLUE;
					}
					else if(randColor > 60)
					{
						chicoboColor = chocoboColor.GREEN;
					}
				}
				else
				{
					if(randColor > 60)
					{
						chicoboColor = chocoboColor.BLUE;
					}
					else if(randColor > 20)
					{
						chicoboColor = chocoboColor.GREEN;
					}
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
				if(bothFedGold)
				{
					if(randColor > 60)
					{
						chicoboColor = chocoboColor.BLACK;
					}
					else if(randColor > 30)
					{
						chicoboColor = chocoboColor.WHITE;
					}
				}
				else
				{
					if(randColor > 80)
					{
						chicoboColor = chocoboColor.BLACK;
					}
					else if(randColor > 40)
					{
						chicoboColor = chocoboColor.WHITE;
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
