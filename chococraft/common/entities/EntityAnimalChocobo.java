// <copyright file="EntityAnimalChoco.java">
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
// <summary>abstract entity class for all chococraft living beings</summary>

package chococraft.common.entities;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import chococraft.common.Constants;
import chococraft.common.ModChocoCraft;
import chococraft.common.gui.GuiStarter;
import chococraft.common.helper.ChocoboMathHelper;
import chococraft.common.helper.ChocoboParticleHelper;
import chococraft.common.network.clientSide.PacketChocoboHealth;
import chococraft.common.network.clientSide.PacketChocoboHunger;
import chococraft.common.network.clientSide.PacketChocoboParticles;
import chococraft.common.network.clientSide.PacketChocoboTamed;
import chococraft.common.network.serverSide.PacketChocoboAttribute;
import chococraft.common.network.serverSide.PacketChocoboChangeOwner;
import chococraft.common.network.serverSide.PacketChocoboRiderJump;
import chococraft.common.network.serverSide.PacketChocoboSetInLove;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public abstract class EntityAnimalChocobo extends EntityTameable implements IEntityAdditionalSpawnData
{
	public chocoboColor color;
	
	public static enum chocoboColor
	{
		YELLOW,
		GREEN,
		BLUE,
		WHITE,
		BLACK,
		GOLD,
		PINK,
		RED,
		PURPLE
	}
	

    protected int inLove;
	protected boolean hasMate;
    public int breeding;
    protected PathEntity pathToEntity;
    public Entity entityToAttack;
    protected boolean hasAttacked;
    protected int fleeingTick;
    protected int taskNumber = 0;
	public float flyingMovementFactor;
	public boolean canClimb;
	public boolean canCrossWater;
	public boolean canJumpHigh;
	public boolean canFly;
    public double landSpeedFactor;
    public double waterSpeedFactor;
    public double airbornSpeedFactor;
	
	public EntityAnimalChocobo(World world)
    {
        super(world);
        //this.dataWatcher.addObject(DW_ID_EAC_HEALTH, new Integer(this.getHealth()));
        this.hasAttacked = false;
        this.fleeingTick = 0;
        this.breeding = 0;
        this.setHidename(true);
        this.setWander(true);
        this.setTamed(false);
        this.setFollowing(false);
        this.setIsMale(this.getRandomIsMale());
        this.getNavigator().setAvoidsWater(true);
		this.hasMate = false;
        
//        tasks.addTask(this.taskNumber++, new EntityAISwimming(this));
//        tasks.addTask(this.taskNumber++, new EntityAIChocoboPanic(this, 0.38F));
//        tasks.addTask(this.taskNumber++, new EntityAIChocoboWander(this, 0.25F));
//        tasks.addTask(this.taskNumber++, new EntityAIChocoboWatchClosest(this, EntityPlayer.class, 6F));
//        tasks.addTask(this.taskNumber++, new EntityAIChocoboLookIdle(this));
//        tasks.addTask(this.taskNumber++, new EntityAIChocoboFollowOwner(this, 5F));
    }
	
	// for my sanity sake
    protected boolean isClient()
    {
        return this.worldObj.isRemote;
    }
    
    protected boolean isServer()
    {
        return !this.worldObj.isRemote;
    }
	
    @Override
	protected void entityInit()
    {
		super.entityInit();
		this.dataWatcher.addObject(Constants.DW_ID_EAC_NAME, "");
		this.dataWatcher.addObject(Constants.DW_ID_EAC_FLAGS, Byte.valueOf((byte)0));		
		this.dataWatcher.addObject(Constants.DW_ID_EAC_TIME_UNTIL_HUNGER, new Integer(0));
    }
	
    @Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setString("Name", this.getName());
		nbttagcompound.setInteger("Age", this.getGrowingAge());
		nbttagcompound.setBoolean("isMale", this.isMale());
		nbttagcompound.setBoolean("Follow", this.isFollowing());
		nbttagcompound.setBoolean("wander", this.isWander());
		nbttagcompound.setBoolean("hidename", this.isHidename());
		nbttagcompound.setBoolean("tamed", this.isTamed());
		nbttagcompound.setInteger("timeUntilHunger", this.getTimeUntilHunger());
	}

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        this.setName(nbttagcompound.getString("Name"));
        this.setGrowingAge(nbttagcompound.getInteger("Age"));
        this.setHidename(nbttagcompound.getBoolean("hidename"));
        this.setFollowing(nbttagcompound.getBoolean("Follow"));
        this.setWander(nbttagcompound.getBoolean("wander"));
        this.setIsMale(nbttagcompound.getBoolean("isMale"));
        this.setTamed(nbttagcompound.getBoolean("tamed"));
        this.setTimeUntilHunger(nbttagcompound.getInteger("timeUntilHunger"));
    }
    
    @Override
    public void writeSpawnData(ByteArrayDataOutput data)
    {
    	data.writeUTF(this.getName());
    	data.writeBoolean(this.isMale());
    	data.writeBoolean(this.isHidename());
    	data.writeBoolean(this.isWander());
    	data.writeBoolean(this.isFollowing());
	}

    @Override
	public void readSpawnData(ByteArrayDataInput data)
	{
		this.setName(data.readUTF());
		this.setIsMale(data.readBoolean());
		this.setHidename(data.readBoolean());
		this.setWander(data.readBoolean());
		this.setFollowing(data.readBoolean());
	}
	
	abstract public String getEntityTexture();
	abstract public int getMaxHealth();
	
	public void resetEntityTexture()
	{
		this.texture = this.getEntityTexture();
	}

    @Override
    public boolean isAIEnabled()
    {
        return false;
    }
    
    public void setName(String name)
    {
    	this.dataWatcher.updateObject(Constants.DW_ID_EAC_NAME, name);

        if (this.isClient())
            this.sendAttributeUpdate();
    }
    
    public String getName()
    {
    	String name;
    	name = this.dataWatcher.getWatchableObjectString(Constants.DW_ID_EAC_NAME);
    	if(name.isEmpty())
    	{
    		name = ChocoboNames.getRandomName(this.isMale());
        	this.dataWatcher.updateObject(Constants.DW_ID_EAC_NAME, name);
    	}
    	return name;
    }
    
    public int getTimeUntilHunger()
    {
		return this.dataWatcher.getWatchableObjectInt(Constants.DW_ID_EAC_TIME_UNTIL_HUNGER);
    }

    public void setTimeUntilHunger(int timeUntilHunger)
    {
    	if(timeUntilHunger > 0)
    	{
    		this.dataWatcher.updateObject(Constants.DW_ID_EAC_TIME_UNTIL_HUNGER, timeUntilHunger);
    	}
    	else
    	{
    		this.dataWatcher.updateObject(Constants.DW_ID_EAC_TIME_UNTIL_HUNGER, 0);
    	}

    }
    
    public boolean isHungry()
    {
    	boolean hungry = this.getTimeUntilHunger() <= 0;    	
    	return hungry && ModChocoCraft.hungerEnabled;
    }
    
    public void feeding()
    {
    	int timeUntilHunger = this.isChild() ? ModChocoCraft.hungerDelayChicobo : ModChocoCraft.hungerDelayChocobo;
    	this.setTimeUntilHunger(timeUntilHunger);
    	this.sendParticleUpdate("mobSpell", this, 7);
    }
    
    @Override
    public void setGrowingAge(int growingAge)
    {
    	if(!this.isHungry())
    	{
    		super.setGrowingAge(growingAge);
    	}
    }
    
	public boolean isInLove()
    {
        return (this.dataWatcher.getWatchableObjectByte(Constants.DW_ID_EAC_FLAGS) & Constants.DW_VAL_EAC_INLOVE_ON) != 0;
    }
    
    public void setInLove(boolean inLove)
    {
        byte eacFlags = this.dataWatcher.getWatchableObjectByte(Constants.DW_ID_EAC_FLAGS);

        if (inLove)
        {
            this.dataWatcher.updateObject(Constants.DW_ID_EAC_FLAGS, Byte.valueOf((byte)(eacFlags | Constants.DW_VAL_EAC_INLOVE_ON)));
        }
        else
        {
            this.dataWatcher.updateObject(Constants.DW_ID_EAC_FLAGS, Byte.valueOf((byte)(eacFlags & Constants.DW_VAL_EAC_INLOVE_OFF)));
        }
        this.sendSetInLoveUpdate();
    }
    
    public boolean isMale()
    {
        return (this.dataWatcher.getWatchableObjectByte(Constants.DW_ID_EAC_FLAGS) & Constants.DW_VAL_EAC_ISMALE_ON) != 0;
    }

    public void setIsMale(boolean isMale)
    {
        byte eacFlags = this.dataWatcher.getWatchableObjectByte(Constants.DW_ID_EAC_FLAGS);

        if (isMale)
        {
            this.dataWatcher.updateObject(Constants.DW_ID_EAC_FLAGS, Byte.valueOf((byte)(eacFlags | Constants.DW_VAL_EAC_ISMALE_ON)));
        }
        else
        {
            this.dataWatcher.updateObject(Constants.DW_ID_EAC_FLAGS, Byte.valueOf((byte)(eacFlags & Constants.DW_VAL_EAC_ISMALE_OFF)));
        }
    }
    
    public boolean getRandomIsMale()
    {
    	return (new Random()).nextInt(100) < ModChocoCraft.genderMaleChance ? true : false;
    }
    
    public boolean isHidename()
    {
        return (this.dataWatcher.getWatchableObjectByte(Constants.DW_ID_EAC_FLAGS) & Constants.DW_VAL_EAC_HIDENAME_ON) != 0;
    }

    public void setHidename(boolean hidename)
    {
        byte eacFlags = this.dataWatcher.getWatchableObjectByte(Constants.DW_ID_EAC_FLAGS);

        if (hidename)
        {
            this.dataWatcher.updateObject(Constants.DW_ID_EAC_FLAGS, Byte.valueOf((byte)(eacFlags | Constants.DW_VAL_EAC_HIDENAME_ON)));
        }
        else
        {
            this.dataWatcher.updateObject(Constants.DW_ID_EAC_FLAGS, Byte.valueOf((byte)(eacFlags & Constants.DW_VAL_EAC_HIDENAME_OFF)));
        }

        if (this.isClient())
            this.sendAttributeUpdate();
    }
    
    public boolean isWander()
    {
        return (this.dataWatcher.getWatchableObjectByte(Constants.DW_ID_EAC_FLAGS) & Constants.DW_VAL_EAC_WANDER_ON) != 0;
    }

    public void setWander(boolean wander)
    {
        byte eacFlags = this.dataWatcher.getWatchableObjectByte(Constants.DW_ID_EAC_FLAGS);

        if (wander)
        {
            this.dataWatcher.updateObject(Constants.DW_ID_EAC_FLAGS, Byte.valueOf((byte)(eacFlags | Constants.DW_VAL_EAC_WANDER_ON)));
        }
        else
        {
            this.dataWatcher.updateObject(Constants.DW_ID_EAC_FLAGS, Byte.valueOf((byte)(eacFlags & Constants.DW_VAL_EAC_WANDER_OFF)));
        }

        if (this.isClient())
            this.sendAttributeUpdate();
    }

    public Boolean isFollowing()
    {
        return (this.dataWatcher.getWatchableObjectByte(Constants.DW_ID_EAC_FLAGS) & Constants.DW_VAL_EAC_FOLLOWING_ON) != 0;
    }
    
    public void setFollowing(Boolean following)
    {
        byte eacFlags = this.dataWatcher.getWatchableObjectByte(Constants.DW_ID_EAC_FLAGS);

        if (following)
        {
            this.dataWatcher.updateObject(Constants.DW_ID_EAC_FLAGS, Byte.valueOf((byte)(eacFlags | Constants.DW_VAL_EAC_FOLLOWING_ON)));
        }
        else
        {
            this.dataWatcher.updateObject(Constants.DW_ID_EAC_FLAGS, Byte.valueOf((byte)(eacFlags & Constants.DW_VAL_EAC_FOLLOWING_OFF)));
        }

        if (this.isClient())
            this.sendAttributeUpdate();
    }
    
    public String getGender()
    {
    	return (this.isMale() ? "Male" : "Female");
    }
	
    protected boolean isMovementCeased()
    {
        return false;
    }

	public void toggleFollowWanderStay()
	{
		if(this.isFollowing() && !this.isWander())
		{
			this.setFollowing(false);
			this.setWander(false);
		}
		else if(!this.isFollowing() && !this.isWander())
		{
			this.setFollowing(false);
			this.setWander(true);
		}
		else
		{
			this.setFollowing(true);
			this.setWander(false);
		}
		
		this.showAmountHeartsOrSmokeFx(this.isFollowing(), 7);
	}
	
	public void toggleFollowStay()
	{
		if(!this.isFollowing() && !this.isWander())
		{
			this.setFollowing(true);
			this.setWander(false);
		}
		else
		{
			this.setFollowing(false);
			this.setWander(false);
		}
		
		this.showAmountHeartsOrSmokeFx(this.isFollowing(), 7);
	}
	
	public EntityPlayer getOwner()
    {
		EntityPlayer owner = this.worldObj.getPlayerEntityByName(this.getOwnerName());		
		if(null == owner)
		{

			if(this.worldObj.playerEntities.size() == 1)
			{
				if(this.getOwnerName().equals("Player"))
				{
					Object playerObj = this.worldObj.playerEntities.get(0);
					if(playerObj instanceof EntityPlayer)
					{
						EntityPlayer updatedOwner = (EntityPlayer)playerObj;
						this.setOwner(updatedOwner.username);
						owner = updatedOwner;
					}
				}
				else
				{
					Object playerObj = this.worldObj.playerEntities.get(0);
					if(playerObj instanceof EntityPlayer)
					{
						EntityPlayer offlinePlayer = (EntityPlayer)playerObj;
						if(offlinePlayer.username.equals("Player"))
						{
							owner = offlinePlayer;	
						}
					}
				}
			}
		}		
		return owner;
    }
	
	public boolean isOwner(EntityPlayer player)
	{
		if(ModChocoCraft.isRemoteClient)
		{
			return player.equals(this.getOwner());
		}
		else
		{
			return true;
		}
	}
	
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		this.setTimeUntilHunger(this.getTimeUntilHunger() -1);

		this.doStrawHealing();
	}
	
	private void doStrawHealing()
	{		
		if (this.isServer())
		{
			if(this.isTamed() && this.getHealth() < this.getMaxHealth())
			{
				if(this.worldObj.getWorldTime() % 40 == 0)
				{
					int d100 = this.rand.nextInt(100);
					if(d100 < ModChocoCraft.penHealProbability)
					{
						int blockBelowId = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY), MathHelper.floor_double(this.posZ));
						if(blockBelowId == ModChocoCraft.strawBlock.blockID)
						{
							int range = ModChocoCraft.penHealCauldronRange;
							if(this.isFilledCauldronNear(this.posX, this.posY, this.posZ, range, 2, range))
							{
								this.heal(this.rand.nextInt(3) + 1);
								this.sendHealthUpdate();
								this.sendParticleUpdate(Constants.PARTICLE_HEART, this, 1);
							}
						}
					}
				}
			}
		}
	}
	
	private boolean isFilledCauldronNear(double posX, double posY, double posZ, int rangeX, int rangeY, int rangeZ)
	{
		int iPosX = MathHelper.floor_double(posX);
		int iPosY = MathHelper.floor_double(posY);
		int iPosZ = MathHelper.floor_double(posZ);
		
		// first, check the most obvious level of y
		if(this.isFilledCauldronNearAtYLevel(iPosX, iPosY + 1, iPosZ, rangeX, rangeZ))
		{
			return true;
		}
		
		// next all above
		for(int y = iPosY + 2; y <= iPosY + rangeY; y++)
		{
			if(this.isFilledCauldronNearAtYLevel(iPosX, y, iPosZ, rangeX, rangeZ))
			{
				return true;
			}			
		}
		
		// last all below
		for(int y = iPosY; y >= iPosY - rangeY; y--)
		{
			if(this.isFilledCauldronNearAtYLevel(iPosX, y, iPosZ, rangeX, rangeZ))
			{
				return true;
			}
		}
		return false;
	}
	
	private boolean isFilledCauldronNearAtYLevel(int posX, int posY, int posZ, int rangeX,int rangeZ)
	{
		for(int x = posX - rangeX; x <= posX + rangeX; x++)
		{
			for(int z = posZ - rangeZ; z <= posZ + rangeZ; z++)
			{
				int blockId = this.worldObj.getBlockId(x, posY, z);
				if(blockId == Block.cauldron.blockID)
				{
		            int fillStatus = this.worldObj.getBlockMetadata(x, posY, z);
		            if(fillStatus > 0)
		            {
		            	return true;
		            }
				}
			}
		}
		return false;
	}

	@Override
    public boolean interact(EntityPlayer entityplayer)
    {
    	boolean interacted = false;
    	ItemStack itemstack = entityplayer.inventory.getCurrentItem();
    	if(itemstack != null)
    	{
    		if (itemstack.itemID == ModChocoCraft.chocopediaItem.itemID)
    		{
    			this.onChocopediaUse();
    			interacted = true;
    		}
    		else if (itemstack.itemID == ModChocoCraft.gysahlGreenBlock.blockID)
    		{
    			this.onGysahlGreenUse(entityplayer);
    			interacted = true;
    		}
    	}
    	
    	return interacted;
    }
    
	protected boolean onEmptyHandInteraction(EntityPlayer entityplayer)
	{
		boolean interacted = false;
		return interacted;
	}
	
    protected void onChocopediaUse()
    {
    	if (this.isClient())
    	{
    		if (this.isTamed())
    		{
    			GuiStarter.startChocopediaGui(this);
    		}
    		else
    		{
    			this.showAmountHeartsOrSmokeFx(false, 7);
    		}
    	}
    }

    protected void onGysahlGreenUse(EntityPlayer entityplayer)
    {
    	if (!this.isTamed())
    	{
    		this.useItem(entityplayer);

    		if(this.isServer())
    		{
    			if (rand.nextInt(3) == 0)
    			{
    				this.setTamed(true);
    				this.setWander(true);
    				this.setFollowing(false);
    				this.setHidename(true);
    				this.worldObj.setEntityState(this, (byte)7);
    				this.setOwner(entityplayer.username);
    				this.sendTamedUpdate();
    			}
    			else
    			{
    				this.worldObj.setEntityState(this, (byte)6);
    			}
    		}
    		this.texture = this.getEntityTexture();
    		this.showAmountHeartsOrSmokeFx(this.isTamed(), 7);
    	}
    	else if (this.getHealth() < this.getMaxHealth())
    	{
			this.useItem(entityplayer);
    		if(!this.worldObj.isRemote)
    		{
    			this.heal(10);
    			this.sendHealthUpdate();
    		}
			this.showAmountHeartsOrSmokeFx(true, 7);
    	}
    	else if (this.isHungry())
    	{
    		this.useItem(entityplayer);
    		if(!this.worldObj.isRemote)
    		{
    			this.feeding();
    			this.sendHungerUpdate();
    		}
    		
    	}
    }

    @Override
	protected void attackEntity(Entity entity, float targetDistance)
    {
    	if (entity instanceof EntityAnimalChocobo)
        {
            EntityAnimalChocobo otherChocobo = (EntityAnimalChocobo)entity;
            
            if (this.getGrowingAge() > 0 && otherChocobo.getGrowingAge() < 0)
            {
                if ((double)targetDistance < 2.5D)
                {
                    this.hasAttacked = true;
                }
            }
            else if (this.isInLove() && otherChocobo.isInLove())
            {
            	if (otherChocobo.entityToAttack == null)
                {
                    otherChocobo.entityToAttack = this;
                }
                
                if (otherChocobo.entityToAttack == this && (double)targetDistance < 5.0D)
                {
                    otherChocobo.setInLove(true);
                    this.setInLove(true);
                    this.breeding++;
                    if (this.breeding % 4 == 0)
                    {
                    	if (this.isClient())
                    	{
                    		ChocoboParticleHelper.showParticleAroundEntityFx(Constants.PARTICLE_HEART, this);
                    	}
                    }
                    if (this.breeding == 60)
                    {
                    	this.procreate((EntityAnimalChocobo)entity);
                    }
                }
                else
                {
                	otherChocobo.entityToAttack = null;
                    breeding = 0;
                }
            }
            else
            {
                breeding = 0;
            }
        }
    }

    public void procreate(EntityAnimalChocobo otherParent)
    {
    	if(this.isServer())
    	{
    		//EntityChicobo babyChicobo = (EntityChicobo) this.spawnBabyAnimal(otherParent);
    		EntityChicobo babyChicobo = (EntityChicobo) this.createChild(otherParent);

    		if (babyChicobo != null)
    		{
    			babyChicobo.setTimeUntilAdult(rand.nextInt(ModChocoCraft.growupDelayRandom) + ModChocoCraft.growupDelayStatic);
    			babyChicobo.setGrowingAge(babyChicobo.getTimeUntilAdult());
    			babyChicobo.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
    			this.worldObj.spawnEntityInWorld(babyChicobo);

    			this.setGrowingAge(this.isMale() ? ModChocoCraft.breedingDelayMale : ModChocoCraft.breedingDelayFemale);
    			otherParent.setGrowingAge(otherParent.isMale() ? ModChocoCraft.breedingDelayMale : ModChocoCraft.breedingDelayFemale);
    			this.entityToAttack = null;
    			otherParent.entityToAttack = null;
    			this.breeding = 0;
    			otherParent.breeding = 0;
    			this.setInLove(false);
    			otherParent.setInLove(false);

    			this.sendParticleUpdate(Constants.PARTICLE_HEART, this, 7);
    		}
    	}
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource damageSource, int tmpNaturalArmorRating)
    {
        this.fleeingTick = 60;
        this.entityToAttack = null;
        this.setInLove(false);
        
        if(this.isTamed())
        {
        	// do not suffocate in walls workaround
        	if(damageSource.getDamageType().equals(DamageSource.inWall))
        	{
        		return false;
        	}
        	
        	if(damageSource.getEntity() instanceof EntityPlayer)
        	{
        		EntityPlayer entityPlayer = (EntityPlayer)damageSource.getEntity();        		
        		if(null != entityPlayer)
        		{
        			if(null != this.getOwner() && this.isOwner(entityPlayer))
        			{
        				if(entityPlayer.isSneaking())
        				{
        					return false;
        				}
        			}

        			if(null != this.riddenByEntity && this.riddenByEntity.equals(entityPlayer))
        			{
        				return false;
        			}
        		}
        	}
        }
        
        boolean check = super.attackEntityFrom(damageSource, tmpNaturalArmorRating);
        
        if(this.isServer())
        {
        	this.sendHealthUpdate();
        }
        return check;
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected Entity findPlayerToAttack()
    {
        if (this.fleeingTick > 0)
        {
            return null;
        }
        
        if (this.isInLove())
        {
            List list = this.worldObj.getEntitiesWithinAABB(EntityChocobo.class, this.boundingBox.expand(8F, 8F, 8F));
            for (int i = 0; i < list.size(); i++)
            {
                EntityChocobo otherChoco = (EntityChocobo)list.get(i);
                
                if(otherChoco != this)
                {
                	boolean canMate = otherChoco.isInLove() && otherChoco.isMale() != this.isMale();
                	
                	if(canMate && !otherChoco.isChild())
                	{
                		this.hasMate = true;
                		otherChoco.hasMate = true;
                		return otherChoco;
                	}
                }
            }
        }
        return null;
    }

    
    @Override
    public int getMaxSpawnedInChunk()
    {
        return ModChocoCraft.spawnTotalMax;
    }
    
    @Override
    public boolean getCanSpawnHere()
    {
        int x = MathHelper.floor_double(this.posX);
        int y = MathHelper.floor_double(this.boundingBox.minY);
        int z = MathHelper.floor_double(this.posZ);        
        Boolean isPosPathWeight = getBlockPathWeight(x, y, z) >= 0.0F;
        return  isPosPathWeight && super.getCanSpawnHere();
    }
    
    public boolean canSpawnAtLoc(int x, int y, int z)
    {
    	if(!this.worldObj.doesBlockHaveSolidTopSurface(x, y - 1, z))
    	{
    		return false;
    	}
    	
    	for(int i = 0; i < 3; i++)
    	{
    		if(isNormalCubesAround(this.worldObj, x, y + i, z))
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
	private static boolean isNormalCubesAround(World world, int posX, int posY, int posZ)
	{
		for(int x = posX -1; x <= posX +1; x++)
		{
			for(int z = posZ -1; z <= posZ +1; z++)
			{
				if(!world.isBlockNormalCube(x, posY, z))
				{
					return false;
				}
			}
		}
		return true;
	}
    
    public boolean teleportToOwner()
    {
    	if(this.getOwner() != null)
    	{
    		int ownerPosX = MathHelper.floor_double(this.getOwner().posX) - 2;
    		int ownerPosZ = MathHelper.floor_double(this.getOwner().posZ) - 2;
    		int ownerPosY = MathHelper.floor_double(this.getOwner().boundingBox.minY);

    		for (int xOffset = 0; xOffset <= 4; ++xOffset)
    		{
    			for (int yOffset = 0; yOffset <= 4; ++yOffset)
    			{
    				if ((xOffset < 1 || yOffset < 1 || xOffset > 3 || yOffset > 3))
    				{
    					int xTele = ownerPosX + xOffset;
    					int yTele = ownerPosY;
    					int zTele = ownerPosZ + yOffset;
    					
    					if(this.canSpawnAtLoc(xTele, yTele, zTele))
    					{
    						double finXTele = (double)xTele + 0.5D;
    						double finYTele = (double)yTele;
    						double finZTele = (double)zTele + 0.5D;    						
    						this.setLocationAndAngles(finXTele, finYTele, finZTele, this.rotationYaw, this.rotationPitch);
    						this.getNavigator().clearPathEntity();
    						return true;
    					}
    				}		
    			}
    		}
    	}
    	return false;
    }
    
    public void changeOwner(String newOwner)
    {
    	this.setOwner(newOwner);
    	this.sendChangeOwnerUpdate();
    }

    @Override
    public int getTalkInterval()
    {
        return 150;
    }

    @Override
	protected boolean canDespawn()
	{
		if(ModChocoCraft.wildCanDespawn && !this.isTamed())
		{
			return true;
		}
		return false;
	}
    
    @Override
    public void setDead()
    {
    	if(this.isTamed() || !ModChocoCraft.wildCanDespawn)
    	{
    		if(this.getAge() > 600)
    		{
    			if(this.playerInRange(1000.0))
    			{
    				this.isDead = true;
    			}
    		}
    		else
    		{
    			if(this.playerInRange(16000.0))
    			{
    				this.isDead = true;
    			}
    		}
    	}
    	else
    	{
        	this.isDead = true;
    	}
    }
    
    boolean playerInRange(double range)
    {
    	EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, -1);
    	if(null != player)
    	{
            double dX = player.posX - this.posX;
            double dY = player.posY - this.posY;
            double dZ = player.posZ - this.posZ;
            double delta = dX * dX + dY * dY + dZ * dZ;
            
            if(delta < range)
            {
            	return true;
            }    		
    	}
        return false;
    }

    protected boolean isLoverlyGysahl(ItemStack itemstack)
    {
        return itemstack.itemID == ModChocoCraft.gysahlLoverlyItem.itemID;
    }

    protected boolean isGoldenGysahl(ItemStack itemstack)
    {
        return itemstack.itemID == ModChocoCraft.gysahlGoldenItem.itemID;
    }

	protected void useItem(EntityPlayer entityplayer)
	{
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		itemstack.stackSize--;
		if (itemstack.stackSize <= 0)
		{
			entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
		}
	}

	public boolean isChild()
    {
		return this instanceof EntityChicobo;
    }
    
	@Override
    protected void fall(float fallHeight)
    {
        int i = (int)Math.ceil(fallHeight - 6F);
        if (i > 0)
        {
            if (i > 4)
            {
                this.worldObj.playSoundAtEntity(this, "damage.fallbig", 1.0F, 1.0F);
            }
            else
            {
                this.worldObj.playSoundAtEntity(this, "damage.fallsmall", 1.0F, 1.0F);
            }
            attackEntityFrom(DamageSource.fall, i);
            
            int blockPosX = MathHelper.floor_double(this.posX);
            int blockPosY = MathHelper.floor_double(this.posY - 0.2D - (double)this.yOffset);
            int blockPosZ = MathHelper.floor_double(this.posZ);
            int blockId = this.worldObj.getBlockId(blockPosX, blockPosY, blockPosZ);
            
            if (blockId > 0)
            {
                StepSound stepsound = Block.blocksList[blockId].stepSound;
                this.worldObj.playSoundAtEntity(this, stepsound.getStepSound(), stepsound.getVolume() * 0.5F, stepsound.getPitch() * 0.75F);
            }
        }
    }

	protected void getPathOrWalkableBlock(Entity entity, float distance)
	{
		boolean canDrown = true;
		PathEntity pathentity = this.worldObj.getPathEntityToEntity(this, entity, 16F, this.canCrossWater, this.canClimb, this.canFly, canDrown);
		if (pathentity == null && distance > 12F)
		{
			int entityPosX = MathHelper.floor_double(entity.posX) - 2;
			int entityPosY = MathHelper.floor_double(entity.boundingBox.minY);
			int entityPosZ = MathHelper.floor_double(entity.posZ) - 2;
			for (int i = 0; i <= 4; i++)
			{
				for (int j = 0; j <= 4; j++)
				{
					if ((i < 1 || j < 1 || i > 3 || j > 3) 
							&& this.worldObj.isBlockOpaqueCube(entityPosX + i, entityPosY - 1, entityPosZ + j) 
							&& !this.worldObj.isBlockOpaqueCube(entityPosX + i, entityPosY, entityPosZ + j) 
							&& !this.worldObj.isBlockOpaqueCube(entityPosX + i, entityPosY + 1, entityPosZ + j))
					{
						float gPosX = (float)(entityPosX + i) + 0.5F;
						float gPosY = entityPosY;
						float gPosZ = (float)(entityPosZ + j) + 0.5F;
						this.setLocationAndAngles(gPosX, gPosY, gPosZ, this.rotationYaw, this.rotationPitch);
						return;
					}
				}
			}
		}
		else
		{
			this.setPathToEntity(pathentity);
		}
	}
    
	@Override
    protected void updateWanderPath()
    {
        for (int i = 0; i < 10; i++)
        {
            int wanderX = MathHelper.floor_double((this.posX + (double)this.rand.nextInt(13)) - 6D);
            int wanderY = MathHelper.floor_double((this.posY + (double)this.rand.nextInt(7)) - 3D);
            int wanderZ = MathHelper.floor_double((this.posZ + (double)this.rand.nextInt(13)) - 6D);
            
            if (getBlockPathWeight(wanderX, wanderY, wanderZ) > -99999F)
            {
                this.pathToEntity = this.worldObj.getEntityPathToXYZ(this, wanderX, wanderY, wanderZ, 10F, false, false, false, false);
                break;
            }
        }
    }

	@Override
    protected void updateEntityActionState()
    {
        if (this.fleeingTick > 0)
        {
        	this.fleeingTick--;
        }
        
        EntityPlayer nearestPlayer = this.getNearestPlayer();
        if(!this.isTamed() && nearestPlayer != null && this.getDistanceToEntity(nearestPlayer) < 16D)
        {
        	if(!this.isChild())
        	{
        		if(this.getEntitySenses().canSee(nearestPlayer))
        		{
//        			if(!ChocoboHelper.isFullChocoboDisguise(nearestPlayer))
//        			{
//        				this.fleeingTick = 60;
//        				//Vec3D vec3d = RandomPositionGenerator.func_48623_b(this, 16, 7, Vec3D.createVector(nearestPlayer.posX, nearestPlayer.posY, nearestPlayer.posZ));
//        				//PathEntity pathEntity = this.getNavigator().getPathToXYZ(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
//        				//pathEntity.func_48639_a(vec3d);
//        			}
        		}
        	}
        }
        
        this.hasAttacked = this.isMovementCeased();
        
        float f = 16F;
        
        if (this.entityToAttack == null)
        {
        	this.entityToAttack = this.findPlayerToAttack();
            if (this.entityToAttack != null)
            {
            	this.pathToEntity = this.worldObj.getPathEntityToEntity(this, this.entityToAttack, f, false, false, false, false);
            }
        }
        else if (!this.entityToAttack.isEntityAlive())
        {
        	this.entityToAttack = null;
        }
        else
        {
            float distToAttackEntity = this.entityToAttack.getDistanceToEntity(this);
            if (this.canEntityBeSeen(this.entityToAttack))
            {
                this.attackEntity(this.entityToAttack, distToAttackEntity);
            }
        }
        
        
        if (!this.hasAttacked && this.entityToAttack != null && (this.pathToEntity == null || this.rand.nextInt(20) == 0))
        {
        	this.pathToEntity = this.worldObj.getPathEntityToEntity(this, this.entityToAttack, f, false, false, false, false);
        }
        else if (!this.hasAttacked && this.isWander() && (this.pathToEntity == null && this.rand.nextInt(180) == 0 || this.rand.nextInt(120) == 0 || this.fleeingTick > 0) && this.entityAge < 100)
        {
            this.updateWanderPath();
        }
        
        
        int i = MathHelper.floor_double(boundingBox.minY + 0.5D);
        this.rotationPitch = 0.0F;
        
        if (this.pathToEntity == null || this.rand.nextInt(100) == 0)
        {
            super.updateEntityActionState();
            this.pathToEntity = null;
            return;
        }
        
        Vec3 vector = this.pathToEntity.getPosition(this);
        
        for (double d = this.width * 2.0F; vector != null && vector.squareDistanceTo(posX, vector.yCoord, posZ) < d * d;)
        {
            this.pathToEntity.incrementPathIndex();
            if (this.pathToEntity.isFinished())
            {
                vector = null;
                this.pathToEntity = null;
            }
            else
            {
                vector = this.pathToEntity.getPosition(this);
            }
        }

        this.isJumping = false;
        if (vector != null)
        {
            double d1 = vector.xCoord - this.posX;
            double d2 = vector.zCoord - this.posZ;
            double d3 = vector.yCoord - (double)i;
            float f2 = (float)((Math.atan2(d2, d1) * 180D) / Math.PI) - 90F;
            float f3 = f2 - this.rotationYaw;
            this.moveForward = this.moveSpeed;
            for (; f3 < -180F; f3 += 360F) { }
            for (; f3 >= 180F; f3 -= 360F) { }
            
            ChocoboMathHelper.clamp(f3, -30F, 30F);
            
            this.rotationYaw += f3;
            if (this.hasAttacked && this.entityToAttack != null)
            {
                double distToAttackX = this.entityToAttack.posX - this.posX;
                double distToAttackZ = this.entityToAttack.posZ - this.posZ;
                float tmpRotYaw = this.rotationYaw;
                this.rotationYaw = (float)((Math.atan2(distToAttackZ, distToAttackX) * 180D) / Math.PI) - 90F;
                float f5 = (((tmpRotYaw - this.rotationYaw) + 90F) * (float)Math.PI) / 180F;
                this.moveStrafing = -MathHelper.sin(f5) * this.moveForward * 1.0F;
                this.moveForward = MathHelper.cos(f5) * this.moveForward * 1.0F;
            }
            if (d3 > 0.0D)
            {
            	this.isJumping = true;
            }
        }

        if (this.entityToAttack != null)
        {
        	this.faceEntity(this.entityToAttack, 30F, 30F);
        }
        
        if (this.isCollidedHorizontally && !this.hasPath())
        {
        	this.isJumping = true;
        }
        
        if (this.rand.nextFloat() < 0.8F && (this.isInWater() || this.handleLavaMovement()))
        {
        	this.isJumping = true;
        }
    }
    
    protected void showAmountHeartsOrSmokeFx(boolean showHeart, int amount)
    {
    	if (this.isClient())
    	{
    		for (int i = 0; i < amount; i++)
    		{
    			ChocoboParticleHelper.showParticleAroundEntityFx(showHeart ? Constants.PARTICLE_HEART : Constants.PARTICLE_SMOKE, this);
    		}
    	}
    }
    
    protected EntityPlayer getNearestPlayer()
    {
        @SuppressWarnings("rawtypes")
		List list1 = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox.expand(8F, 8F, 8F));
        double distance = 9999;
        EntityPlayer nearestPlayer = null;

        for (int j = 0; j < list1.size(); j++)
        {
            EntityPlayer player = (EntityPlayer)list1.get(j);
			double tmpDist = this.getDistanceToEntity(player);
			if(tmpDist < distance)
			{
				distance = tmpDist;
				nearestPlayer = player;
			}
        }
        return nearestPlayer;
    }
    
	public boolean canRenderName()
	{
		return this.isClient()
				&& ModChocoCraft.showChocoboNames && this.isTamed()
				&& this.riddenByEntity == null
				&& !this.isHidename() && !this.getName().isEmpty();
	}
	
	protected void sendChangeOwnerUpdate()
	{
		if(this.isClient())
		{
			PacketChocoboChangeOwner packet = new PacketChocoboChangeOwner(this);
			PacketDispatcher.sendPacketToServer(packet.getPacket());
		}
	}
	
    protected void sendTamedUpdate()
    {
		if (this.isServer())
		{
			PacketChocoboTamed packet = new PacketChocoboTamed(this);
			int dimension = this.worldObj.provider.dimensionId;
			PacketDispatcher.sendPacketToAllAround(this.lastTickPosX, this.lastTickPosY, this.lastTickPosZ, 16*5, dimension, packet.getPacket());
		}
	}
	
    protected void sendAttributeUpdate()
    {
    	if (this.isClient())
		{
			PacketChocoboAttribute packet = new PacketChocoboAttribute(this);
			PacketDispatcher.sendPacketToServer(packet.getPacket());
		}
	}
    
    protected void sendHungerUpdate()
    {
		if (this.isServer())
		{
			PacketChocoboHunger packet = new PacketChocoboHunger(this);
			int dimension = this.worldObj.provider.dimensionId;
			PacketDispatcher.sendPacketToAllAround(this.lastTickPosX, this.lastTickPosY, this.lastTickPosZ, 16*5, dimension, packet.getPacket());
		}
    }
    
	protected void sendHealthUpdate()
	{
		if (this.isServer())
		{
			PacketChocoboHealth packet = new PacketChocoboHealth(this);
			int dimension = this.worldObj.provider.dimensionId;
			PacketDispatcher.sendPacketToAllAround(this.lastTickPosX, this.lastTickPosY, this.lastTickPosZ, 16*5, dimension, packet.getPacket());
		}
	}
	
	protected void sendRiderJumpUpdate()
	{
		if(this.isClient())
		{
			if(null != this.riddenByEntity && this.riddenByEntity instanceof EntityPlayer)
			{
				if(this instanceof EntityChocobo)
				{
					PacketChocoboRiderJump packet = new PacketChocoboRiderJump((EntityPlayer)this.riddenByEntity, (EntityChocobo)this);
					PacketDispatcher.sendPacketToServer(packet.getPacket());
				}
			}
		}		
	}
	
	protected void sendParticleUpdate(String particleName, EntityAnimalChocobo chocobo, int amount)
	{
		if (this.isServer())
		{
			PacketChocoboParticles packet = new PacketChocoboParticles(chocobo, particleName, amount);
			int dimension = chocobo.worldObj.provider.dimensionId;
			PacketDispatcher.sendPacketToAllAround(chocobo.lastTickPosX, chocobo.lastTickPosY, chocobo.lastTickPosZ, 16*5, dimension, packet.getPacket());
		}		
	}
	
	protected void sendSetInLoveUpdate()
	{
		if(this.isClient())
		{
			PacketChocoboSetInLove packet = new PacketChocoboSetInLove(this);
			PacketDispatcher.sendPacketToServer(packet.getPacket());			
		}
	}
}
