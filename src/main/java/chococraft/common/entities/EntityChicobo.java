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

import chococraft.common.Constants;
import chococraft.common.ModChocoCraft;
import chococraft.common.helper.ChocoboEntityHelper;
import chococraft.common.network.PacketRegistry;
import chococraft.common.network.clientSide.ChicoboCanGrowUp;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


public class EntityChicobo extends EntityAnimalChocobo
{
	public boolean growUp;
	//protected float maxHealth;

	public EntityChicobo(World world)
	{
		super(world);
		this.dataWatcher.addObject(Constants.DW_ID_CHIC_FLAGS, (byte) 0);
		this.dataWatcher.addObject(Constants.DW_ID_CHIC_TIMEUNTILADULT, rand.nextInt(2000) + 27000);
		this.setColor(chocoboColor.YELLOW);
		this.setSize(0.5F, 0.5F);
		this.growUp = false;
		this.canCrossWater = false;
		this.canFly = false;
		this.canClimb = false;
		this.stepHeight = 0.5F;
		this.canJumpHigh = false;
		this.setGrowingAge(this.getTimeUntilAdult());
		
        //this.tasks.addTask(this.taskNumber++, new ChocoboAIFollowOwner(this, (float)this.getMoveHelper().getSpeed(), 20.0F));
        //this.tasks.addTask(this.taskNumber++, new ChocoboAISwimming(this));
        //this.tasks.addTask(this.taskNumber++, new EntityAIPanic(this, 0.38F));
        //this.tasks.addTask(this.taskNumber++, new ChocoboAIWander(this, 0.25F));
        //this.tasks.addTask(this.taskNumber++, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        //this.tasks.addTask(this.taskNumber++, new EntityAILookIdle(this));
	}
	
	@Override
    public boolean isAIEnabled()
    {
        return true;
    }

	public void setColor(chocoboColor color)
	{
		this.color = color;
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.getColorMaxHealth());
		this.setHealth(this.getColorMaxHealth());
		if (color == chocoboColor.PURPLE)
		{
			isImmuneToFire = true;
		}
	}
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }

	@Override
	public void writeSpawnData(ByteBuf data)
	{
		super.writeSpawnData(data);

		data.writeInt(this.color.ordinal());
		data.writeBoolean(this.growUp);
		data.writeBoolean(this.isCanGrowUp());
		data.writeInt(this.getTimeUntilAdult());
	}

	@Override
	public void readSpawnData(ByteBuf data)
	{
		super.readSpawnData(data);
		
		try
		{
			this.setColor(chocoboColor.values()[data.readInt()]);
			this.growUp = data.readBoolean();
			this.setCanGrowUp(data.readBoolean());
			this.setTimeUntilAdult(data.readInt());
		}
		catch (Exception e)
		{
			this.setDead();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setInteger("TimeToAdult", this.getTimeUntilAdult());
		nbttagcompound.setString("Color", color.toString());        
		nbttagcompound.setBoolean("CanGrow", this.isCanGrowUp());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		this.setTimeUntilAdult(nbttagcompound.getInteger("TimeToAdult"));
		this.setColor(chocoboColor.valueOf(nbttagcompound.getString("Color")));
		this.setCanGrowUp(nbttagcompound.getBoolean("CanGrow"));
		this.setWander(!this.isFollowing());
	}

	@Override
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
			return (new StringBuilder()).append(s).append("chocobo.png").toString();
		case GREEN:
			return (new StringBuilder()).append(s).append("greenchocobo.png").toString();
		case BLUE:
			return (new StringBuilder()).append(s).append("bluechocobo.png").toString();
		case WHITE:
			return (new StringBuilder()).append(s).append("whitechocobo.png").toString();
		case BLACK:
			return (new StringBuilder()).append(s).append("blackchocobo.png").toString();
		case GOLD:
			return (new StringBuilder()).append(s).append("goldchocobo.png").toString();
		case PINK:
			return (new StringBuilder()).append(s).append("pinkchocobo.png").toString();
		case RED:
			return (new StringBuilder()).append(s).append("redchocobo.png").toString();
		case PURPLE:
			return (new StringBuilder()).append(s).append("purplechocobo.png").toString();
		default:
			// todo error handling...
			return "";
		}
	}
	
	public float getColorMaxHealth()
	{
		switch(this.color)
		{
		case YELLOW:
		case GREEN:
		case BLUE:
			return 10.0F;
		case WHITE:
		case BLACK:
			return 15.0F;
		case GOLD:
		case PINK:
		case RED:
		case PURPLE:
			return 20.0F;
		default:
			return 10.0F;
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
			this.dataWatcher.updateObject(Constants.DW_ID_CHIC_FLAGS, (byte) (watchedFlags | 4));
		}
		else
		{
			this.dataWatcher.updateObject(Constants.DW_ID_CHIC_FLAGS, (byte) (watchedFlags & -5));
		}
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

		this.doGrowUp();
}
	
	protected void doGrowUp()
	{
		if (this.isServer() && this.growUp && this.isCanGrowUp())
		{
			if(ChocoboEntityHelper.isSpaceAroundFree(this.worldObj, this, 1, 3, 1))
			{
				this.lastTickPosX = this.posX;
				this.lastTickPosY = this.posY;
				this.lastTickPosZ = this.posZ;
				EntityChocobo grownUpChocobo = FactoryEntityChocobo.createChocoboFromChocobo(this.worldObj, this);
				grownUpChocobo.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				grownUpChocobo.setGrowingAge(6000);
				this.worldObj.spawnEntityInWorld(grownUpChocobo);
				this.sendParticleUpdate(Constants.PARTICLE_EXPLODE, grownUpChocobo, 7);
				this.setHealth(0);
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
				if (itemstack.getItem().equals(ModChocoCraft.gysahlCakeItem))
				{
					this.onGysahlCakeUse(entityplayer);
					interacted = true;
				}
				else if (itemstack.getItem().equals(ModChocoCraft.chocoboFeatherItem))
				{
					this.onFeatherUse(entityplayer);
					interacted = true;
				}
			}
		}
		return interacted;
	}

	protected void onGysahlCakeUse(EntityPlayer player)
	{
		if (this.isServer())
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

	@Override
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

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

	@Override
	public boolean canRenderName()
	{
		return super.canRenderName() && !this.getName().equals("");
	}

	@Override
	public EntityAgeable createChild(EntityAgeable var1)
	{
		return null;
	}

	public boolean canMateWith(EntityAnimal entityAnimal)
	{
		return false;
	}
	
	protected void sendCanGrowUpUpdate()
	{
		if(this.isClient())
		{
			ChicoboCanGrowUp packet = new ChicoboCanGrowUp(this);
			PacketRegistry.INSTANCE.sendToServer(packet);
		}		
	}
}
