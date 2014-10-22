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

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import chococraft.common.Constants;
import chococraft.common.ModChocoCraft;
import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChicobo;
import chococraft.common.entities.EntityChocobo;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class EntityChocoboPurple extends EntityChocobo
{	
	public EntityChocoboPurple(World world)
	{
		super(world);
		this.color = chocoboColor.PURPLE;
		this.setHealth(this.getMaxHealth());
		this.canClimb = true;
		this.canCrossWater = false;
		this.canJumpHigh = Constants.CHOCOBO_PURPLE_CANJUMPHIGH;
		this.canFly = true;
		this.isImmuneToFire = true;
		this.landSpeedFactor = Constants.CHOCOBO_PURPLE_LANDSPEEDFACT;
		this.waterSpeedFactor = Constants.CHOCOBO_PURPLE_WATERSPEEDFACT;
		this.airbornSpeedFactor = Constants.CHOCOBO_PURPLE_AIRSPEEDFACT;
	}

	@Override
	public void writeSpawnData(ByteArrayDataOutput data)
	{
		super.writeSpawnData(data);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data)
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
	public String getEntityColourTexture()
	{
		return "purplechocobo.png";
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
	
    public boolean handleLavaMovement()
    {
        return false;
    }
	
	public void setLandSpeedFactor(boolean mounted)
	{
		if (mounted)
		{
			this.landSpeedFactor = Constants.CHOCOBO_PURPLE_LANDSPEEDFACT;
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
			this.canJumpHigh = Constants.CHOCOBO_PURPLE_CANJUMPHIGH;
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
			rider.addPotionEffect(new PotionEffect(12, 100, -1, true));
			rider.extinguish();
		}
	}

	@Override
	public float getChocoboMaxHealth()
	{
		return 50.0F;
	}

	@Override
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
			}
			return chicoboColor;
		}
		else
		{
			return null;
		}
	}

	@Override
	protected int getDropItemId()
	{
		return ModChocoCraft.chocoboLegRawItem.itemID;
	}

	// purple special
	@Override
	public void procreate(EntityAnimalChocobo otherParent)
	{
		if(this.isServer())
		{
			this.setGrowingAge(this.isMale() ? 3000 : 9000);
			otherParent.setGrowingAge(otherParent.isMale() ? 3000 : 9000);
			this.setInLove(false);
			otherParent.setInLove(false);
			this.breeding = 0;
			otherParent.breeding = 0;
			this.entityToAttack = null;
			otherParent.entityToAttack = null;
			int eggAmount = this.rand.nextInt(10) == 0 ? 3 : 1;
			this.dropItem(ModChocoCraft.purpleChocoboEggItem.itemID, eggAmount);
		}
	}

	public EntityChicobo spawnBabyAnimal(EntityAnimalChocobo entityanimalchoco)
	{
		return null;
	}

	@Override
	public boolean isBurning()
	{
		return false;
	}
}
