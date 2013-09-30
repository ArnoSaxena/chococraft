// <copyright file="EntityChocoboGold.java">
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
// <summary>Entity class for the Gold Chocobo mobs</summary>

package chococraft.common.entities.colours;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import chococraft.common.Constants;
import chococraft.common.entities.EntityChocobo;
import chococraft.common.entities.FactoryEntityChocobo;
import chococraft.common.helper.ChocoboParticleHelper;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class EntityChocoboGold extends EntityChocobo
{
	public EntityChocoboGold(World world)
	{
		super(world);
		this.setHealth(this.getMaxHealth());
//		this.flyingMovementFactor = Constants.CHOCOBO_GOLD_FLYMOVEFACT;
//		this.landMovementFactor = Constants.CHOCOBO_DEFAULT_LANDMOVEFACT;
		this.canClimb = true;
		this.canCrossWater = true;
		this.canFly = true;
		this.canJumpHigh = Constants.CHOCOBO_GOLD_CANJUMPHIGH;
		this.isImmuneToFire = false;
		this.landSpeedFactor = Constants.CHOCOBO_GOLD_LANDSPEEDFACT;
		this.waterSpeedFactor = Constants.CHOCOBO_GOLD_WATERSPEEDFACT;
		this.airbornSpeedFactor = Constants.CHOCOBO_GOLD_AIRSPEEDFACT;
	}

	protected void entityInit()
	{
		this.color = chocoboColor.GOLD;
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
		return "goldchocobo.png";
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
			this.landSpeedFactor = Constants.CHOCOBO_GOLD_LANDSPEEDFACT;
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
			this.canJumpHigh = Constants.CHOCOBO_GOLD_CANJUMPHIGH;
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

	protected void onPaint(EntityPlayer entityplayer, boolean isPink)
	{
		lastTickPosX = posX;
		lastTickPosY = posY;
		lastTickPosZ = posZ;

		if (this.isServer())
		{
			EntityChocobo entitychocobo = FactoryEntityChocobo.createChocobo(this.worldObj, isPink ? chocoboColor.PINK : chocoboColor.RED, this.getName(), this.getOwnerName(), this.isHidename(), this.isTamed(), this.isFollowing(), this.isWander(), this.isMale());
			entitychocobo.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
			entitychocobo.setGrowingAge(this.getGrowingAge());
			entitychocobo.setSaddled(this.isSaddled());
			entitychocobo.setSaddleBagged(this.isSaddleBagged());
			entitychocobo.setPackBagged(this.isPackBagged());
			entitychocobo.setWander(this.isWander());
			entitychocobo.setFollowing(this.isFollowing());
			entitychocobo.injectInventory(this.bagsInventory);
			this.worldObj.spawnEntityInWorld(entitychocobo);
			this.setHealth(0);
			this.setDead();
		}
		else
		{
			for (int i = 0; i < 20; i++)
			{
				ChocoboParticleHelper.showParticleAroundEntityFx(Constants.PARTICLE_EXPLODE, this);
			}
		}
		this.useItem(entityplayer);
	}
}
