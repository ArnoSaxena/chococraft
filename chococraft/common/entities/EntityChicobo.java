// <copyright file="EntityChicobo.java">
// Copyright (c) 2012 All Right Reserved, http://chococraft.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <author>EddieV (initial source)</author>
// <email>al-s@gmx.de</email>
// <date>2012-05-09</date>
// <summary>Entity class for the Chicobo mob. A baby Chocobo of any colour.</summary>

package chococraft.common.entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import chococraft.common.*;
import chococraft.common.network.clientSide.PacketChicoboCanGrowUp;


public class EntityChicobo extends EntityAnimalChocobo
{
	public boolean growUp;
	protected int maxHealth;

	public EntityChicobo(World world)
	{
		super(world);
		this.dataWatcher.addObject(Constants.DW_ID_CHIC_FLAGS, Byte.valueOf((byte)0));
		this.dataWatcher.addObject(Constants.DW_ID_CHIC_TIMEUNTILADULT, new Integer(rand.nextInt(2000) + 27000));
		this.setColor(chocoboColor.YELLOW);
		this.setMaxHealth(10);
		this.setSize(0.5F, 0.5F);
		this.growUp = false;
		this.canCrossWater = false;
		this.canFly = false;
		this.canClimb = false;
		this.stepHeight = 0.5F;
		this.canJumpHigh = false;
		this.landMovementFactor = 0.1F;
		this.setGrowingAge(this.getTimeUntilAdult());
		
//        this.tasks.addTask(this.taskNumber++, new EntityAISwimming(this));
//        this.tasks.addTask(this.taskNumber++, new EntityAIChocoboFollowOwner(this, 64.0F));
//        this.tasks.addTask(this.taskNumber++, new EntityAIPanic(this, 0.38F));
//        this.tasks.addTask(this.taskNumber++, new EntityAIWander(this, 0.25F));
//        this.tasks.addTask(this.taskNumber++, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
//        this.tasks.addTask(this.taskNumber++, new EntityAILookIdle(this));
	}
	
    public boolean isAIEnabled()
    {
        return false;
    }

	public void setColor(chocoboColor color)
	{
		this.color = color;
		this.texture = this.getEntityTexture();
		this.setMaxHealth(this.getColorMaxHealth());
		this.setEntityHealth(this.getColorMaxHealth());
		if (color == chocoboColor.PURPLE)
		{
			isImmuneToFire = true;
		}
	}    

	public void writeSpawnData(ByteArrayDataOutput data)
	{
		super.writeSpawnData(data);
		data.writeInt(this.color.ordinal());
		data.writeBoolean(this.isTamed());
		data.writeBoolean(this.isFollowing());
		data.writeInt(this.getHealth());
		data.writeBoolean(this.growUp);
		data.writeBoolean(this.isCanGrowUp());
		data.writeInt(this.getTimeUntilAdult());
	}

	public void readSpawnData(ByteArrayDataInput data)
	{
		super.readSpawnData(data);
		this.setColor(chocoboColor.values()[data.readInt()]);
		this.setTamed(data.readBoolean());
		this.setFollowing(data.readBoolean());
		this.health = data.readInt();
		this.growUp = data.readBoolean();
		this.setTimeUntilAdult(data.readInt());
		this.texture = this.getEntityTexture();
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setInteger("TimeToAdult", this.getTimeUntilAdult());
		nbttagcompound.setString("Color", color.toString());        
		nbttagcompound.setBoolean("CanGrow", this.isCanGrowUp());
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		this.setTimeUntilAdult(nbttagcompound.getInteger("TimeToAdult"));
		this.setColor(chocoboColor.valueOf(nbttagcompound.getString("Color")));
		this.setCanGrowUp(nbttagcompound.getBoolean("CanGrow"));
		this.setWander(!this.isFollowing());
	}

	//@SideOnly(Side.CLIENT)
	public String getEntityTexture()
	{
		String s = new String(Constants.CHICOBO_ENTITY_TEXTURES);
		if (this.isTamed())
		{
			s = (new StringBuilder()).append(s).append(Constants.CHICOBO_ETXT_TAMED).toString();
		}
		switch(this.color)
		{
		case YELLOW:
			return (new StringBuilder()).append(s).append("/chocobo.png").toString();
		case GREEN:
			return (new StringBuilder()).append(s).append("/greenchocobo.png").toString();
		case BLUE:
			return (new StringBuilder()).append(s).append("/bluechocobo.png").toString();
		case WHITE:
			return (new StringBuilder()).append(s).append("/whitechocobo.png").toString();
		case BLACK:
			return (new StringBuilder()).append(s).append("/blackchocobo.png").toString();
		case GOLD:
			return (new StringBuilder()).append(s).append("/goldchocobo.png").toString();
		case PINK:
			return (new StringBuilder()).append(s).append("/pinkchocobo.png").toString();
		case RED:
			return (new StringBuilder()).append(s).append("/redchocobo.png").toString();
		case PURPLE:
			return (new StringBuilder()).append(s).append("/purplechocobo.png").toString();
		default:
			// todo error handling...
			return "";
		}
	}

	public int getColorMaxHealth()
	{
		switch(this.color)
		{
		case YELLOW:
		case GREEN:
		case BLUE:
			return 10;
		case WHITE:
		case BLACK:
			return 15;
		case GOLD:
		case PINK:
		case RED:
		case PURPLE:
			return 20;
		default:
			return 10;
			// todo error handling
		}
	}

	// Data Watcher

	public boolean isCanGrowUp()
	{
		return (this.dataWatcher.getWatchableObjectByte(Constants.DW_ID_CHIC_FLAGS) & 4) != 0;
	}

	public void setCanGrowUp(boolean canGrowUp)
	{
		byte watchedFlags = this.dataWatcher.getWatchableObjectByte(Constants.DW_ID_CHIC_FLAGS);

		if (canGrowUp)
		{
			this.dataWatcher.updateObject(Constants.DW_ID_CHIC_FLAGS, Byte.valueOf((byte)(watchedFlags | 4)));
		}
		else
		{
			this.dataWatcher.updateObject(Constants.DW_ID_CHIC_FLAGS, Byte.valueOf((byte)(watchedFlags & -5)));
		}
	}

	public void setMaxHealth(int maxHealth)
	{
		this.maxHealth = maxHealth;
	}

	public int getMaxHealth()
	{
		return this.maxHealth;
	}

	public void setTimeUntilAdult(int timeUntilAdult)
	{
		this.dataWatcher.updateObject(Constants.DW_ID_CHIC_TIMEUNTILADULT, timeUntilAdult);
	}

	public int getTimeUntilAdult()
	{
		return this.dataWatcher.getWatchableObjectInt(Constants.DW_ID_CHIC_TIMEUNTILADULT);
	}        

	public boolean isChild()
	{
		return true;
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if (!this.growUp)
		{
			this.setTimeUntilAdult(this.getGrowingAge());
			this.setTimeUntilAdult(this.getTimeUntilAdult() -1);
			this.setGrowingAge(this.getTimeUntilAdult());
			if (this.getTimeUntilAdult() <= 0)
			{
				this.growUp = true;
				this.setCanGrowUp(true);
			}
		}
		
		if (this.growUp && this.isCanGrowUp())
		{
			if(Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
			{
				this.lastTickPosX = this.posX;
				this.lastTickPosY = this.posY;
				this.lastTickPosZ = this.posZ;
				EntityChocobo grownUpChocobo = FactoryEntityChocobo.createChocoboFromChocobo(this.worldObj, this);
				grownUpChocobo.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				grownUpChocobo.setGrowingAge(6000);
				grownUpChocobo.setFollowing(this.isFollowing());
				grownUpChocobo.setWander(this.isWander());
				this.worldObj.spawnEntityInWorld(grownUpChocobo);
				this.sendParticleUpdate("explode", grownUpChocobo, 7);
				this.setEntityHealth(0);
				this.setDead();
				this.growUp = false;
			}
		}
	}

	protected void entityInit()
	{
		super.entityInit();
	}

	protected String getLivingSound()
	{
		if (rand.nextInt(2) == 0)
		{
			return "choco_kweh";
		}
		else
		{
			return "";
		}
	}

	protected String getHurtSound()
	{
		return "choco_kweh";
	}

	protected String getDeathSound()
	{
		return "choco_kweh";
	}

	public boolean interact(EntityPlayer entityplayer)
	{
		boolean interacted = false;
		interacted = super.interact(entityplayer);
		if(!interacted)
		{
			ItemStack itemstack = entityplayer.inventory.getCurrentItem();
			if(itemstack != null)
			{
				if (itemstack.itemID == ModChocoCraft.gysahlCakeItem.shiftedIndex)
				{
					this.onGysahlCakeUse(entityplayer);
					interacted = true;
				} 
//				else if (itemstack.itemID == ModChocoCraft.gysahlChibiItem.shiftedIndex)
//				{
//					this.onGysahlChibiUse(entityplayer);
//					interacted = true;
//				}
				else if (itemstack.itemID == ModChocoCraft.chocoboFeatherItem.shiftedIndex)
				{
					this.onFeatherUse(entityplayer);
					interacted = true;
				}
			}
		}
		return interacted;
	}

	protected void onGysahlChibiUse(EntityPlayer entityplayer)
	{
		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			if (this.isTamed())
			{
				this.useItem(entityplayer);
				this.setCanGrowUp(false);
				this.sendCanGrowUpUpdate();
			}
			else
			{
				this.showAmountHeartsOrSmokeFx(false, 7);
			}
		}
	}

	protected void onGysahlCakeUse(EntityPlayer player)
	{
		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			if (this.isTamed() && this.isOwner(player))
			{
				this.useItem(player);
				this.setCanGrowUp(true);
				this.growUp = true;
				this.sendCanGrowUpUpdate();
			}
			else
			{
				this.showAmountHeartsOrSmokeFx(false, 7);
			}
		}
	}
	
	public void onFeatherUse(EntityPlayer player)
	{
		if (this.isTamed() && this.isOwner(player))
		{
			this.toggleFollowWanderStay();
		}
		else
		{
			this.showAmountHeartsOrSmokeFx(false, 7);
		}
	}

	protected int getDropItemId()
	{
		return -1;
	}

	public void updateEntityActionState()
	{
		if (!this.hasPath() && this.isFollowing() && this.isTamed())
		{
			EntityPlayer owner = this.getOwner();

			if (owner != null)
			{
				if (owner.isDead)
				{
					this.setFollowing(false);
					this.setWander(false);
					return;
				}
				float distanceToOwner = owner.getDistanceToEntity(this);
				if (distanceToOwner > 10F)
				{
					this.getPathOrWalkableBlock(owner, distanceToOwner);
				}
				else
				{
					super.updateEntityActionState();
					if(this.isInWater())
					{
						this.motionY = 0.1D;
						this.setJumping(true);
					}
					return;
				}
			}
		}
		else
		{
			super.updateEntityActionState();
			if(this.isInWater())
			{
				this.motionY = 0.1D;
				this.setJumping(true);
			}
			return;
		}
	}

	protected boolean canDespawn()
	{
		return false;
	}

	public boolean canRenderName()
	{
		return super.canRenderName() && this.getName() != "";
	}

	@Override
	//public EntityAnimal spawnBabyAnimal(EntityAnimal dummy)
	public EntityAgeable func_90011_a(EntityAgeable var1)
	{
		return null;
	}

	public boolean canMateWith(EntityAnimal entityAnimal)
	{
		return false;
	}
	
	protected void sendCanGrowUpUpdate()
	{
		if(Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
		{
			PacketChicoboCanGrowUp packet = new PacketChicoboCanGrowUp(this);
			PacketDispatcher.sendPacketToServer(packet.getPacket());
		}		
	}
}
