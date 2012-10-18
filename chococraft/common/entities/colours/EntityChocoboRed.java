// <copyright file="EntityChocoboRed.java">
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
// <summary>Entity class for the Red Chocobo mobs</summary>

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

public class EntityChocoboRed extends EntityChocobo
{
	public EntityChocoboRed(World world)
	{
		super(world);
		this.setEntityHealth(this.getMaxHealth());
		this.flyingMovementFactor = Constants.CHOCOBO_RED_FLYMOVEFACT;
		this.landMovementFactor = Constants.CHOCOBO_DEFAULT_LANDMOVEFACT;
		this.canClimb = true;
		this.canCrossWater = true;
		this.canFly = true;
		this.canJumpHigh = false;
		this.isImmuneToFire = false;
		this.landSpeedFactor = Constants.CHOCOBO_RED_LANDSPEEDFACT;
		this.airbornSpeedFactor = Constants.CHOCOBO_RED_AIRSPEEDFACT;
	}

	protected void entityInit()
	{
		this.color = chocoboColor.RED;
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
		return "/redchocobo.png";
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
			this.landMovementFactor = Constants.CHOCOBO_RED_LANDMOVEFACT;
		}
		else
		{
			this.landMovementFactor = Constants.CHOCOBO_DEFAULT_LANDMOVEFACT;			
		}
	}

	@Override
	public int getMaxHealth()
	{
		return 50;
	}

	protected void fall(float fallHeight)
	{
		return;
	}

	@Override
    public chocoboColor getBabyAnimalColor(EntityAnimal otherAnimalParent)
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
				chicoboColor = chocoboColor.YELLOW;
				break;
			case GREEN:
			case BLUE:
			case WHITE:
			case BLACK:
				if(randColor < 75 || !bothFedGold)
				{
					chicoboColor = otherParent.color;
				}
				break;
			case GOLD:
			case PINK:
			case RED:
				if(!bothFedGold)
				{
					chicoboColor = chocoboColor.YELLOW;
				}
				break;
			case PURPLE:
				chicoboColor = chocoboColor.PURPLE;
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
