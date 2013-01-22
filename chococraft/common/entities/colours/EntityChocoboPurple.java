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

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import chococraft.common.Constants;
import chococraft.common.ModChocoCraft;
import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChicobo;
import chococraft.common.entities.EntityChocobo;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityChocoboPurple extends EntityChocobo
{
	
	//public final chocoboColor color = chocoboColor.PURPLE;	

	public EntityChocoboPurple(World world)
	{
		super(world);
		this.setEntityHealth(this.getMaxHealth());
		this.landMovementFactor = Constants.CHOCOBO_DEFAULT_LANDMOVEFACT;
		this.flyingMovementFactor = Constants.CHOCOBO_PURPLE_FLYMOVEFACT;
		this.canClimb = true;
		this.canCrossWater = false;
		this.canJumpHigh = Constants.CHOCOBO_PURPLE_CANJUMPHIGH;
		this.canFly = true;
		this.isImmuneToFire = true;
		this.landSpeedFactor = Constants.CHOCOBO_PURPLE_LANDSPEEDFACT;
		this.waterSpeedFactor = Constants.CHOCOBO_PURPLE_WATERSPEEDFACT;
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

    //@SideOnly(Side.CLIENT)
	public String getEntityColourTexture()
	{
		return "/purplechocobo.png";
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
	
	public void setLandMovementFactor(boolean mounted)
	{
		if (mounted)
		{
			this.landMovementFactor = Constants.CHOCOBO_PURPLE_LANDMOVEFACT;
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
			this.canJumpHigh = Constants.CHOCOBO_PURPLE_CANJUMPHIGH;
		}
		else
		{
			this.canJumpHigh = false;
		}
	}

	public void setRiderAbilities(boolean mounted)
	{
		if(this.riddenByEntity instanceof EntityPlayer  && ModChocoCraft.riderBuffsEnabled)
		{
			EntityPlayer rider = (EntityPlayer)this.riddenByEntity;			
			rider.addPotionEffect(new PotionEffect(12, 100, -1, true));
			rider.extinguish();
		}
		
//		Class<? extends Entity> riddenByEntityClass = this.riddenByEntity.getClass();
//		java.lang.reflect.Field isImmuneToFireField;
//		try
//		{
//			isImmuneToFireField = riddenByEntityClass.getField("isImmuneToFire");
//			isImmuneToFireField.set(this.riddenByEntity, mounted);
//		}
//		catch (SecurityException e)
//		{
//		}
//		catch (NoSuchFieldException e)
//		{
//		}
//		catch (IllegalArgumentException e)
//		{
//		}
//		catch (IllegalAccessException e)
//		{
//		}
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
		return ModChocoCraft.chocoboLegRawItem.itemID;
	}

	// purple special
	@Override
	public void procreate(EntityAnimalChocobo otherParent)
	{
		if(Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
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

	public boolean isBurning()
	{
		return false;
	}
}
