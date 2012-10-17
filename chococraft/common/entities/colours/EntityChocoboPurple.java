// <copyright file="EntityChocoboPurple.java">
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
// <summary>Entity class for the Purple Chocobo mobs</summary>

package chococraft.common.entities.colours;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import chococraft.common.Constants;
import chococraft.common.ModChocoCraft;
import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChicobo;
import chococraft.common.entities.EntityChocobo;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityChocoboPurple extends EntityChocobo
{
	public EntityChocoboPurple(World world)
	{
		super(world);
		this.flyingMovementFactor = Constants.CHOCOBO_BLACK_FLYMOVEFACT;
		this.landMovementFactor = Constants.CHOCOBO_DEFAULT_LANDMOVEFACT;
		this.canClimb = true;
		this.canCrossWater = false;
		this.canFly = true;
		this.canJumpHigh = false;
		this.isImmuneToFire = true;
		this.landSpeedFactor = Constants.CHOCOBO_PURPLE_LANDSPEEDFACT;
		this.airbornSpeedFactor = Constants.CHOCOBO_PURPLE_AIRSPEEDFACT;
	}

	protected void entityInit()
	{
		this.color = chocoboColor.PURPLE;
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
		return "/purplechocobo.png";
	}

	@Override
	protected void setStepHeight(boolean mounted)
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
	
	protected void setLandMovementFactor(boolean mounted)
	{
		if (mounted)
		{
			this.landMovementFactor = Constants.CHOCOBO_BLACK_LANDMOVEFACT;
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

    public chocoboColor getBabyAnimalColor(EntityAnimal otherAnimalParent)
	{
		if(otherAnimalParent instanceof EntityChocobo)
		{
			EntityChocobo otherParent = (EntityChocobo) otherAnimalParent;

			chocoboColor chicoboColor = chocoboColor.PURPLE;
			switch(otherParent.color)
			{
			case YELLOW:
			case GREEN:
			case BLUE:
			case WHITE:
			case BLACK:
				chicoboColor = otherParent.color;
				break;
			case GOLD:
			case PINK:
			case RED:
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

	protected int getDropItemId()
	{
		return ModChocoCraft.chocoboLegRawItem.shiftedIndex;
	}

	// purple special
	public void procreate(EntityAnimalChocobo otherParent)
	{
		this.setGrowingAge(9000);
		otherParent.setGrowingAge(9000);
		this.setInLove(false);
		otherParent.setInLove(false);
		this.breeding = 0;
		otherParent.breeding = 0;
		this.entityToAttack = null;
		otherParent.entityToAttack = null;

		dropItem(ModChocoCraft.netherChocoboEggItem.shiftedIndex, 1);
	}

	public EntityChicobo spawnBabyAnimal(EntityAnimalChocobo entityanimalchoco)
	{
		return null;
	}

	public boolean isBurning()
	{
		return false;
	}
}
