// <copyright file="EntityChocobo.java">
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
// <summary>Entity class for the Chocobo mobs</summary>

package chococraft.common.entities;

import java.util.List;
import java.util.Random;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import chococraft.common.Constants;
import chococraft.common.ModChocoCraft;
import chococraft.common.entities.ai.ChocoboAIMate;
import chococraft.common.entities.colours.EntityChocoboPurple;
import chococraft.common.helper.ChocoboMathHelper;
import chococraft.common.helper.ChocoboParticleHelper;

public abstract class EntityChocobo extends EntityChocoboRideable
{	
	public float wingRotation;
	public float destPos;
	public float wingRotDelta;
	public int timeUntilNextFeather;
	public boolean fedGold;

	public EntityChocobo(World world)
	{
		super(world);
		
		this.wingRotation = 0.0F;
		this.destPos = 0.0F;
		this.wingRotDelta = 1.0F;
		this.setSize(ModChocoCraft.chocoboWidth, ModChocoCraft.chocoboHeight);
		this.timeUntilNextFeather = this.rand.nextInt(ModChocoCraft.featherDelayRandom) + ModChocoCraft.featherDelayStatic;
        
		this.tasks.addTask(this.taskNumber++, new ChocoboAIMate(this, 1.0D));
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
        this.dataWatcher.addObject(Constants.DW_ID_CHOC_FLAGS, (byte) 0);
	}
	
	protected abstract float getChocoboMaxHealth();
	
	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(this.getChocoboMaxHealth());
    }
	
	@Override
	public void writeSpawnData(ByteBuf data)
	{
		super.writeSpawnData(data);
	}

	@Override
	public void readSpawnData(ByteBuf data)
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
	
	@Override
	protected void fall(float fallHeight)
	{
		super.fall(fallHeight);
	}

	abstract public String getEntityColourTexture();

	public String getEntityTexture()
	{
		String s = Constants.CHOCOBO_ENTITY_TEXTURES;

		if(this.isPackBagged())
		{
			s = (new StringBuilder()).append(s).append(Constants.CHOCOBO_ETXT_PACKBAGGED).toString();
		}
		else if (this.isSaddleBagged())
		{
			s = (new StringBuilder()).append(s).append(Constants.CHOCOBO_ETXT_SADDLEBAGGED).toString();
		}
		else if (this.isSaddled())
		{
			s = (new StringBuilder()).append(s).append(Constants.CHOCOBO_ETXT_SADDLED).toString();
		}
		else if (this.isTamed())
		{
			s = (new StringBuilder()).append(s).append(Constants.CHOCOBO_ETXT_TAMED).toString();
		}
		else
		{
			s = (new StringBuilder()).append(s).append(Constants.CHOCOBO_ETXT_UNTAMED).toString();
		}

		if(this.isMale())
		{
			s = (new StringBuilder()).append(s).append(Constants.CHOCOBO_ETXT_MALE).toString();
		}
		else
		{
			s = (new StringBuilder()).append(s).append(Constants.CHOCOBO_ETXT_FEMALE).toString();
		}

		s = (new StringBuilder()).append(s).append(this.getEntityColourTexture()).toString();
		
		return s;
	}

	public void onUpdate()
	{
        
		super.onUpdate();
		
		if (this.riddenByEntity == null && this.isFlying() && !this.onGround)
		{
			this.motionY -= 0.25D;
		}
		
		if (this.isFlying() && this.onGround)
		{
			this.setFlying(false);
		}

		if (this.isHighJumping && this.onGround)
		{
			this.isHighJumping = false;
		}

        if (this.isInWater() && this.canCrossWater)
        {
            this.motionY += 0.04D;
            this.inWater = false;
        }
	}
	
	@Override
    public float getAIMoveSpeed()
    {
	    if (this.riddenByEntity != null)
	    {
	        if (this.onGround)
            {
                return (float) this.landSpeedFactor / 100.0F;
            }
	    }
	    
	    return super.getAIMoveSpeed();
    }

	public void moveEntityWithHeading(float strafe, float forward)
	{
	    if (this.riddenByEntity != null)
	    {
    	    strafe = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5F;
    	    forward = ((EntityLivingBase)this.riddenByEntity).moveForward;
    	    
            if (!this.worldObj.isRemote)
            {
                if (this.isAirBorne)
                {
                    this.jumpMovementFactor = (float) this.airbornSpeedFactor / 100.0F;
                }
                else if (this.isInWater())
                {
                    this.jumpMovementFactor = (float) this.waterSpeedFactor / 100.0F;
                }
                
                this.setAIMoveSpeed(forward);
                super.moveEntityWithHeading(strafe, forward);
            }
	    }
	    else
	    {
	        super.moveEntityWithHeading(strafe, forward);
	    }

        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d0 = this.posX - this.prevPosX;
        double d1 = this.posZ - this.prevPosZ;
        float f4 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;

        if (f4 > 1.0F)
        {
            f4 = 1.0F;
        }

        this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		// handle mating behaviour
		if (this.isInLove())
        {
            // do we have an attack entity?
            if (this.entityToAttack != null)
            {
        		if (this.isClient())
        		{
        			ChocoboParticleHelper.showParticleAroundEntityFx(Constants.PARTICLE_HEART, this);
        		}
        		
            	// are they still in love?
        		if(this.entityToAttack instanceof EntityAnimalChocobo)
        		{
        			EntityAnimalChocobo otherChoco = (EntityAnimalChocobo)this.entityToAttack;
        			if(!otherChoco.isInLove())
        			{
                		this.entityToAttack = null;
                		this.hasMate = false;        				
        			}
        		}        		
            }
        }
        
		// handle wing rotation
        if (this.isClient())
        {
        	this.destPos += (double)(this.onGround ? -1 : 4) * 0.3D;
        	
        	this.destPos = ChocoboMathHelper.clamp(this.destPos, 0.0F, 1.0F);
        	
        	if (!this.onGround)
        	{
        		this.wingRotDelta = ChocoboMathHelper.minLimit(this.wingRotDelta, 1.0F);
        	}
        	
        	this.wingRotDelta *= 0.9D;
        	
        	if (!this.onGround && this.motionY < 0.0D)
        	{
        		this.motionY *= 0.8D;
        	}
        	this.wingRotation += this.wingRotDelta * 2.0F;
        }

        // handle drop feather
		if (this.isServer())
		{
			if(--this.timeUntilNextFeather <= 0)
			{
				int d100 = this.rand.nextInt(100);
				if (d100 < ModChocoCraft.featherDropChance)
				{
					this.dropFeather();
				}
				this.timeUntilNextFeather = this.rand.nextInt(ModChocoCraft.featherDelayRandom) + ModChocoCraft.featherDelayStatic;
			}
		}
	}

	protected String getLivingSound()
	{
		if (ModChocoCraft.livingSoundProb != 0 
				&& rand.nextInt(4) == 0 
				&& rand.nextInt(100) < ModChocoCraft.livingSoundProb)
		{
			return "chococraft:choco_kweh";
		}
		else
		{
			return "";
		}
	}

	protected String getHurtSound()
	{
		return "chococraft:choco_kweh";
	}

	protected String getDeathSound()
	{
		return "chococraft:choco_kweh";
	}

	public boolean interact(EntityPlayer entityplayer)
	{
		boolean interacted  = super.interact(entityplayer);
		
		if(!interacted)
		{
			ItemStack itemstack = entityplayer.inventory.getCurrentItem();
			if(itemstack == null || itemstack.stackSize == 0)
			{
				interacted = this.onOpenSaddlePackBagInteraction(entityplayer);
			}
			else
			{
				if (itemstack.getItem().equals(ModChocoCraft.gysahlPinkItem))
				{
					this.onPaint(entityplayer, true);
					interacted = true;
				}
				else if (itemstack.getItem().equals(ModChocoCraft.gysahlRedItem))
				{
					this.onPaint(entityplayer, false);
					interacted = true;
				}
				else if (isLoverlyGysahl(itemstack) || isGoldenGysahl(itemstack))
				{
					this.onBreedGysahlUse(entityplayer, isGoldenGysahl(itemstack));
					interacted = true;
				}
				else if (itemstack.getItem().equals(ModChocoCraft.chocoboFeatherItem))
				{
					this.onFeatherUse(entityplayer);
					interacted = true;
				}
				else if (itemstack.getItem().equals(Items.string))
				{
					this.onSilkUse(entityplayer);
					interacted = true;
				}
			}
		}
		
		if(!interacted)
		{
			interacted = this.onOpenSaddlePackBagInteraction(entityplayer);
		}
		if(!interacted)
		{
			this.onEmptyHandInteraction(entityplayer);
		}
		
		// don't hit Chocobo by accident if shift key is pressed
    	if(!interacted && entityplayer.isSneaking())
    	{
    		interacted = true;
    	}		
		return interacted;
	}
	
	protected boolean onEmptyHandInteraction(EntityPlayer entityplayer)
	{
		boolean interacted = super.onEmptyHandInteraction(entityplayer);
		return interacted;
	}
	
	public void onBreedGysahlUse(EntityPlayer player, boolean fedGold)
	{
		if (this.isTamed() && this.getGrowingAge() == 0 && this.isOwner(player))
		{
			this.fedGold = fedGold;
			this.useItem(player);
			this.setInLove(true);
			this.entityToAttack = null;

			this.showAmountHeartsOrSmokeFx(true, 7);
		}
		else
		{
			this.showAmountHeartsOrSmokeFx(false, 7);
		}
	}

	protected void onPaint(EntityPlayer entityplayer, boolean isPink)
	{
		this.showAmountHeartsOrSmokeFx(false, 7);
	}

    // TODO: goes to EntityAnimalChocobo
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
	
	public void onSilkUse(EntityPlayer player)
	{
		if(this.isTamed() && this.isOwner(player))
		{
			this.toggleFollowStay();
		}
		else
		{
			this.showAmountHeartsOrSmokeFx(false, 7);
		}
	}

	public void dropFeather()
	{
    	if (this.isServer())
    	{
    		this.entityDropItem(new ItemStack(ModChocoCraft.chocoboFeatherItem, 1), 0.0F);
    	}
	}

	protected void dropFewItems(boolean par1, int par2)
	{
		if (this.isServer())
		{
			super.dropFewItems(par1, par2);
		}
	}

	@Override
	protected Item getDropItem()
	{
		if (isBurning())
		{
			return ModChocoCraft.chocoboLegCookedItem;
		}
		else
		{
			return ModChocoCraft.chocoboLegRawItem;
		}
	}

	public void updateEntityActionState()
	{
		if(this instanceof EntityChocoboPurple)
		{
			this.extinguish();
			this.fireResistance = 100;
		}
		
		if (!worldObj.playerEntities.isEmpty())
		{
			if (this.riddenByEntity != null && (this.riddenByEntity instanceof EntityPlayer))
			{
				if(this instanceof EntityChocoboPurple)
				{					
					this.riddenByEntity.extinguish();
					this.riddenByEntity.fireResistance = 100;
				}
				prevRotationYaw = rotationYaw = riddenByEntity.rotationYaw;
				prevRotationPitch = rotationPitch = 0.0F;
				this.setJumping(this.riderActionState.isJump());
				
				double d = Math.abs(Math.sqrt(motionX * motionX + motionZ * motionZ));
				if (d > 0.375D)
				{
					double d1 = 0.375D / d;
					motionX = motionX * d1;
					motionZ = motionZ * d1;
				}
				@SuppressWarnings("rawtypes")
				List list = worldObj.getEntitiesWithinAABBExcludingEntity(this.riddenByEntity, boundingBox.expand(1.0D, 1.0D, 1.0D));
				if (list != null)
				{
					for (int i = 0; i < list.size(); i++)
					{
						Entity entity = (Entity)list.get(i);
						if (!entity.isDead)
						{
							if(this.riddenByEntity instanceof EntityPlayer)
							{
								entity.onCollideWithPlayer((EntityPlayer)this.riddenByEntity);
							}
						}
					}
				}
				return;
			}
			
			if (!this.hasAttacked && !this.hasPath() && this.isFollowing() && this.isTamed())
			{
				EntityPlayer owner = this.getOwner();

				if (owner != null)
				{
					if (owner.isDead)
					{
						this.setFollowing(false);
						this.setWander(false);
						this.setStepHeight(false);
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
					}
				}
			}
			else if (this.isSaddled() && !this.isFollowing() && !this.isInLove())
			{
			}
			else
			{
				super.updateEntityActionState();
			}
		}
	}

	@Override
	public void onDeath(DamageSource damageSource)
	{
		// drop some feathers on death
		int randFeatherAmount = new Random().nextInt(3);
		for(int i = 0; i <= randFeatherAmount; i++)
		{
			this.dropFeather();
		}

		super.onDeath(damageSource);
	}
	
	@Override
    public boolean canMateWith(EntityAnimal otherEntity)
    {
    	if(otherEntity != this)
    	{
    		if(otherEntity instanceof EntityChocobo)
    		{
    			EntityChocobo otherChocobo = (EntityChocobo)otherEntity;
    			
    			if(this.isMale() == otherChocobo.isMale())
    			{
    				return false;
    			}

				return !(!this.isInLove() || !otherChocobo.isInLove());

			}
    	}
    	return false;
    }
	
	public abstract chocoboColor getBabyAnimalColor(EntityAgeable otherAnimalParent);
	
	@Override
	public EntityAgeable createChild(EntityAgeable otherAnimalParent)
	{
		chocoboColor chicoboColor = this.getBabyAnimalColor(otherAnimalParent);
		EntityChicobo childChicobo = new EntityChicobo(worldObj);
		childChicobo.setColor(chicoboColor);
		return childChicobo;
	}
}
