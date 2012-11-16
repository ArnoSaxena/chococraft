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

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

import chococraft.common.*;
import chococraft.common.gui.GuiStarter;
import chococraft.common.helper.ChocoboParticleHelper;
import chococraft.common.network.clientSide.PacketChocoboHealth;
import chococraft.common.network.clientSide.PacketChocoboTamed;
import chococraft.common.network.serverSide.PacketChocoboAttribute;
import chococraft.common.network.serverSide.PacketChocoboRiderJump;
import net.minecraft.src.Block;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityTameable;
import net.minecraft.src.EntityWolf;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.PathEntity;
import net.minecraft.src.StepSound;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;

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
        
//        tasks.addTask(this.taskNumber++, new EntityAISwimming(this));
//        tasks.addTask(this.taskNumber++, new EntityAIChocoboPanic(this, 0.38F));
//        tasks.addTask(this.taskNumber++, new EntityAIChocoboWander(this, 0.25F));
//        tasks.addTask(this.taskNumber++, new EntityAIChocoboWatchClosest(this, EntityPlayer.class, 6F));
//        tasks.addTask(this.taskNumber++, new EntityAIChocoboLookIdle(this));
//        tasks.addTask(this.taskNumber++, new EntityAIChocoboFollowOwner(this, 5F));
    }
	
	protected void entityInit()
    {
		super.entityInit();
		this.dataWatcher.addObject(Constants.DW_ID_EAC_NAME, "");
		this.dataWatcher.addObject(Constants.DW_ID_EAC_FLAGS, Byte.valueOf((byte)0));
    }
	
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setString("Name", this.getName());
		nbttagcompound.setInteger("Age", this.getGrowingAge());
		nbttagcompound.setInteger("InLove", this.inLove);
		nbttagcompound.setBoolean("isMale", this.isMale());
		nbttagcompound.setBoolean("Follow", this.isFollowing());
		nbttagcompound.setBoolean("hidename", this.isHidename());
		nbttagcompound.setBoolean("tamed", this.isTamed());
	}

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        this.setName(nbttagcompound.getString("Name"));
        this.setGrowingAge(nbttagcompound.getInteger("Age"));
        this.setHidename(nbttagcompound.getBoolean("hidename"));
        this.inLove = nbttagcompound.getInteger("InLove");
        this.setFollowing(nbttagcompound.getBoolean("Follow"));
        this.setIsMale(nbttagcompound.getBoolean("isMale"));
        this.setTamed(nbttagcompound.getBoolean("tamed"));
    }
    
    public void writeSpawnData(ByteArrayDataOutput data)
    {
    	data.writeUTF(this.getName());
    	data.writeBoolean(this.isMale());
	}

	public void readSpawnData(ByteArrayDataInput data)
	{
    	String name = data.readUTF();
    	boolean isMale = data.readBoolean();
		this.setName(name);
		this.setIsMale(isMale);
	}
	
	abstract public String getEntityTexture();
	abstract public void setStepHeight(boolean mounting);
	abstract public void setLandMovementFactor(boolean mounted);
	abstract public int getMaxHealth();
	abstract public void setJumpHigh(boolean mounting);
	
	public void resetEntityTexture()
	{
		this.texture = this.getEntityTexture();
	}

    public boolean isAIEnabled()
    {
        return false;
    }
    
    public void setName(String name)
    {
    	this.dataWatcher.updateObject(Constants.DW_ID_EAC_NAME, name);
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

	public void toggleFollow()
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
    public EntityPlayer getOwner()
    {
    	return this.worldObj.getPlayerEntityByName(this.getOwnerName());
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }
    
    public boolean isInLove()
    {
    	return this.inLove == 1;
    }
    
    public void setInLove(boolean inLove)
    {
    	if(inLove)
    	{
    		this.inLove = 1;
    	}
    	else
    	{
    		this.inLove = 0;
    	}
    }

    public boolean interact(EntityPlayer entityplayer)
    {
    	boolean interacted = false;
    	ItemStack itemstack = entityplayer.inventory.getCurrentItem();
    	if(itemstack != null)
    	{
    		if (itemstack.itemID == ModChocoCraft.chocopediaItem.shiftedIndex)
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
    	if (Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
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

    		if(Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
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
    	else if (this.health < getMaxHealth())
    	{
    		if(Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
    		{
    			this.useItem(entityplayer);
    			this.heal(10);
    			this.showAmountHeartsOrSmokeFx(true, 7);
    		}
    	}
    }

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
                    	Side side = FMLCommonHandler.instance().getEffectiveSide();
                    	if (side == Side.CLIENT)
                    	{
                    		ChocoboParticleHelper.showParticleAroundEntityFx("heart", this);
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
    	if(Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
    	{
    		//EntityChicobo babyChicobo = (EntityChicobo) this.spawnBabyAnimal(otherParent);
    		EntityChicobo babyChicobo = (EntityChicobo) this.func_90011_a(otherParent);

    		if (babyChicobo != null)
    		{
    			babyChicobo.setTimeUntilAdult(rand.nextInt(2000) + 27000);
    			babyChicobo.setGrowingAge(babyChicobo.getTimeUntilAdult());
    			babyChicobo.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
    			this.worldObj.spawnEntityInWorld(babyChicobo);

    			this.setGrowingAge(9000);
    			otherParent.setGrowingAge(9000);
    			this.entityToAttack = null;
    			otherParent.entityToAttack = null;
    			this.breeding = 0;
    			otherParent.breeding = 0;
    			this.setInLove(false);
    			otherParent.setInLove(false);

    			this.showAmountHeartsOrSmokeFx(true, 7);
    		}
    	}
    }
    
    public boolean attackEntityFrom(DamageSource damageSource, int tmpNaturalArmorRating)
    {
        this.fleeingTick = 60;
        this.entityToAttack = null;
        this.setInLove(false);
        
        if(this.isTamed())
        {
        	if(damageSource.getEntity() instanceof EntityWolf)
        	{
        		EntityWolf attackingWolf = (EntityWolf)damageSource.getEntity();
        		if(attackingWolf.isTamed())
        		{
        			attackingWolf.getOwner().setLastAttackingEntity(attackingWolf);
        			attackingWolf.setAttackTarget(null);
        			return false;
        		}
        	}
        	
        	if(damageSource.getEntity() instanceof EntityPlayer)
        	{
        		EntityPlayer entityPlayer = (EntityPlayer)damageSource.getEntity();        		
        		if(null != entityPlayer)
        		{
        			if(null != this.getOwner() && this.getOwner().equals(entityPlayer))
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
        
        if(Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
        {
        	this.sendHealthUpdate();
        }
        return check;
    }

    @SuppressWarnings("rawtypes")
    protected Entity findPlayerToAttack()
    {
        if (this.fleeingTick > 0)
        {
            return null;
        }
        
        if (this.isInLove() || getGrowingAge() > 0)
        {
            List list = this.worldObj.getEntitiesWithinAABB(EntityChocobo.class, this.boundingBox.expand(8F, 8F, 8F));
            for (int i = 0; i < list.size(); i++)
            {
                EntityAnimalChocobo otherChoco = (EntityAnimalChocobo)list.get(i);
                
                if(otherChoco != this)
                {
                	boolean canMate = otherChoco.isInLove() && otherChoco.isMale() != this.isMale();
                	
                	// what's with the otherChoco.getGrowingAge stuff???
                	if(canMate || otherChoco.getGrowingAge() < 0)
                	{
                		return otherChoco;
                	}
                }
            }
        }
        return null;
    }

    
    public int getMaxSpawnedInChunk()
    {
        return 5;
    }
    
    public boolean getCanSpawnHere()
    {
        int x = MathHelper.floor_double(posX);
        int y = MathHelper.floor_double(boundingBox.minY);
        int z = MathHelper.floor_double(posZ);        
        Boolean isPosPathWeight = getBlockPathWeight(x, y, z) >= 0.0F;
        return  isPosPathWeight && super.getCanSpawnHere();
    }

    public int getTalkInterval()
    {
        return 150;
    }

	protected boolean canDespawn()
	{
		return false;
		//return !this.isTamed();
	}    

    protected boolean isLoverlyGysahl(ItemStack itemstack)
    {
        return itemstack.itemID == ModChocoCraft.gysahlLoverlyItem.shiftedIndex;
    }

    protected boolean isGoldenGysahl(ItemStack itemstack)
    {
        return itemstack.itemID == ModChocoCraft.gysahlGoldenItem.shiftedIndex;
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
        return getGrowingAge() < 0;
    }
    
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

    protected void fall(float fallHeight, int dummy)
    {
    	this.fall(fallHeight);
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
            float f2 = (float)((Math.atan2(d2, d1) * 180D) / 3.1415927410125732D) - 90F;
            float f3 = f2 - this.rotationYaw;
            this.moveForward = this.moveSpeed;
            for (; f3 < -180F; f3 += 360F) { }
            for (; f3 >= 180F; f3 -= 360F) { }
            if (f3 > 30F)
            {
                f3 = 30F;
            }
            if (f3 < -30F)
            {
                f3 = -30F;
            }
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
    	if (Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
    	{
    		for (int i = 0; i < amount; i++)
    		{
    			ChocoboParticleHelper.showParticleAroundEntityFx(showHeart ? "heart" : "smoke", this);
    		}
    	}
    }
    
    protected EntityPlayer getNearestPlayer()
    {
        @SuppressWarnings("rawtypes")
		List list1 = this.worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityPlayer.class, boundingBox.expand(8F, 8F, 8F));
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
		return Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide()
				&& ModChocoCraft.showChocoboNames && this.isTamed()
				&& this.riddenByEntity == null
				&& !this.isHidename() && !this.getName().isEmpty();
	}
	
    protected void sendTamedUpdate()
    {
		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			PacketChocoboTamed packet = new PacketChocoboTamed(this);
			int dimension = this.worldObj.getWorldInfo().getDimension();
			PacketDispatcher.sendPacketToAllAround(this.lastTickPosX, this.lastTickPosY, this.lastTickPosZ, 16*5, dimension, packet.getPacket());
		}
	}
	
    protected void sendAttributeUpdate()
    {
    	if (Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
		{
			PacketChocoboAttribute packet = new PacketChocoboAttribute(this);
			PacketDispatcher.sendPacketToServer(packet.getPacket());
		}
	}
    
	protected void sendHealthUpdate()
	{
		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			PacketChocoboHealth packet = new PacketChocoboHealth(this);
			int dimension = this.worldObj.getWorldInfo().getDimension();
			PacketDispatcher.sendPacketToAllAround(this.lastTickPosX, this.lastTickPosY, this.lastTickPosZ, 16*5, dimension, packet.getPacket());
		}
	}
	
	protected void sendRiderJumpUpdate()
	{
		if(Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
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
}
