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

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import net.minecraft.src.EntityAgeable;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import chococraft.common.ChocoboHelper;
import chococraft.common.Constants;
import chococraft.common.entities.EntityChocobo;
import chococraft.common.entities.FactoryEntityChocobo;

public class EntityChocoboGold extends EntityChocobo
{
	public EntityChocoboGold(World world)
	{
		super(world);
		this.setEntityHealth(this.getMaxHealth());
		this.flyingMovementFactor = Constants.CHOCOBO_GOLD_FLYMOVEFACT;
		this.landMovementFactor = Constants.CHOCOBO_DEFAULT_LANDMOVEFACT;
		this.canClimb = true;
		this.canCrossWater = true;
		this.canFly = true;
		this.canJumpHigh = false;
		this.isImmuneToFire = false;
		this.landSpeedFactor = Constants.CHOCOBO_GOLD_LANDSPEEDFACT;
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
		return "/goldchocobo.png";
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
			this.landMovementFactor = Constants.CHOCOBO_GOLD_LANDMOVEFACT;
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
			this.canJumpHigh = Constants.CHOCOBO_GOLD_CANJUMPHIGH;
		}
		else
		{
			this.canJumpHigh = false;
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

	protected void onPaint(EntityPlayer entityplayer, boolean isPink)
	{
		lastTickPosX = posX;
		lastTickPosY = posY;
		lastTickPosZ = posZ;

		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			EntityChocobo entitychocobo = FactoryEntityChocobo.createChocobo(this.worldObj, isPink ? chocoboColor.PINK : chocoboColor.RED, this.getName(), this.getOwnerName(), this.isHidename(), this.isTamed(), this.isFollowing(), this.isMale());
			entitychocobo.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
			entitychocobo.setGrowingAge(this.getGrowingAge());
			entitychocobo.setSaddled(this.isSaddled());
			entitychocobo.setSaddleBagged(this.isSaddleBagged());
			entitychocobo.setPackBagged(this.isPackBagged());
			//entitychocobo.injectInventory(this.bagsInventory);
			worldObj.spawnEntityInWorld(entitychocobo);
			health = 0;
			this.setDead();
		}
		else
		{
			for (int i = 0; i < 20; i++)
			{
				ChocoboHelper.showParticleAroundEntityFx("explode", this);
			}
		}
		this.useItem(entityplayer);
	}
}
