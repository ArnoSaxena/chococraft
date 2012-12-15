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

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAgeable;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import chococraft.common.Constants;
import chococraft.common.ModChocoCraft;
import chococraft.common.entities.colours.EntityChocoboPurple;
import chococraft.common.helper.ChocoboParticleHelper;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;

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
		this.texture = this.getEntityTexture();
		this.setSize(1.0F, 2.0F);
		this.timeUntilNextFeather = this.rand.nextInt(ModChocoCraft.featherDelayRandom) + ModChocoCraft.featherDelayStatic;
	}

	protected void entityInit()
	{
		super.entityInit();
        this.dataWatcher.addObject(Constants.DW_ID_CHOC_FLAGS, Byte.valueOf((byte)0));
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
	
	protected void fall(float fallHeight)
	{
		super.fall(fallHeight);
	}

	abstract public String getEntityColourTexture();

	public String getEntityTexture()
	{
		String s = new String(Constants.CHOCOBO_ENTITY_TEXTURES);

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

		return (new StringBuilder()).append(s).append(this.getEntityColourTexture()).toString();
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
			int i = 5;
			double d = 0.0D;
			for (int j = 0; j < i; j++)
			{
				double d2 = (this.boundingBox.minY + ((this.boundingBox.maxY - this.boundingBox.minY) * (double)(j + 0)) / (double)i) - 0.125D;
				double d3 = (this.boundingBox.minY + ((this.boundingBox.maxY - this.boundingBox.minY) * (double)(j + 1)) / (double)i) - 0.125D;
				AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(this.boundingBox.minX, d2, this.boundingBox.minZ, this.boundingBox.maxX, d3, this.boundingBox.maxZ);
				if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water))
				{
					d += 1.0D / (double)i;
				}
			}

			if (d < 1.0D)
			{
				double d1 = d * 2D - 1.0D;
				this.motionY += 0.04D * d1;
			}
			else
			{
				if (this.motionY < 0.0D)
				{
					this.motionY /= 2D;
				}
				this.motionY += 0.007D;
			}
		}
	}

	public void moveEntityWithHeading(float strafe, float forward)
	{
		if (this.isInWater())
		{
			float landMovement = 0.02F;
			if (this.canCrossWater)
			{
				landMovement = this.landMovementFactor * 0.3F;
			}
			this.moveFlying(strafe, forward, landMovement);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.8D;
			this.motionY *= 0.8D;
			this.motionZ *= 0.8D;
			
			if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, ((this.motionY + 0.6D) - this.posY) + this.posY, this.motionZ))
			{
				this.motionY = 0.3D;
			}
		}
		else if (this.handleLavaMovement())
		{
			if(this instanceof EntityChocoboPurple)
			{
				this.moveFlying(strafe, forward, this.landMovementFactor * 2.0F);
				this.moveEntity(motionX, motionY, motionZ);
				this.motionX *= 0.8D;
				this.motionY *= 0.8D;
				this.motionZ *= 0.8D;
				
				if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, ((this.motionY + 0.6D) - this.posY) + this.posY, this.motionZ))
				{
					this.motionY = 0.3D;
				}
			}
			else
			{
				this.moveFlying(strafe, forward, 0.02F);
				this.moveEntity(this.motionX, this.motionY, this.motionZ);
				this.motionX *= 0.5D;
				this.motionY *= 0.5D;
				this.motionZ *= 0.5D;
				this.motionY -= 0.02D;
			}
			
			if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, ((this.motionY + 0.6D) - this.posY) + this.posY, this.motionZ))
			{
				this.motionY = 0.3D;
			}
		}
		else if (this.isFlying())
		{
			this.moveFlying(strafe, forward, this.flyingMovementFactor);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.8D;
			this.motionY *= 0.8D;
			this.motionZ *= 0.8D;
			this.motionY -= 0.02D;
		}
		else
		{
			float f3 = 0.91F;
			if (this.onGround)
			{
				f3 = 0.5460001F;
				int blockId = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
				if (blockId > 0)
				{
					f3 = Block.blocksList[blockId].slipperiness * 0.91F;
				}
			}
			float f4 = 0.1627714F / (f3 * f3 * f3);
			float f6 = this.onGround ? this.landMovementFactor * f4 : this.jumpMovementFactor;
			this.moveFlying(strafe, forward, f6);
			f3 = 0.91F;
			if (this.onGround)
			{
				f3 = 0.5460001F;
				int blockId = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
				if (blockId > 0)
				{
					f3 = Block.blocksList[blockId].slipperiness * 0.91F;
				}
			}
			if (isOnLadder())
			{
				float f7 = 0.15F;
				if (this.motionX < (double)(-f7))
				{
					this.motionX = -f7;
				}
				if (this.motionX > (double)f7)
				{
					this.motionX = f7;
				}
				if (this.motionZ < (double)(-f7))
				{
					this.motionZ = -f7;
				}
				if (this.motionZ > (double)f7)
				{
					this.motionZ = f7;
				}
				this.fallDistance = 0.0F;
				if (this.motionY < -0.15D)
				{
					this.motionY = -0.15D;
				}
				if (isSneaking() && this.motionY < 0.0D)
				{
					this.motionY = 0.0D;
				}
			}
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			if (this.isCollidedHorizontally && isOnLadder())
			{
				this.motionY = 0.2D;
			}
			this.motionY -= 0.08D;
			this.motionY *= 0.98D;
			this.motionX *= f3;
			this.motionZ *= f3;
		}
		this.prevLegYaw = this.legYaw;
		double diffX = posX - prevPosX;
		double diffZ = posZ - prevPosZ;
		float moveDistance = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ) * 4F;
		if (moveDistance > 1.0F)
		{
			moveDistance = 1.0F;
		}
		this.legYaw += (moveDistance - this.legYaw) * 0.4F;
		this.legSwing += this.legYaw;
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		if (this.isInLove())
        {
            // do we have an attack entity?
            if (this.entityToAttack != null)
            {
        		if (Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
        		{
        			ChocoboParticleHelper.showParticleAroundEntityFx("heart", this);
        		}
            	// are they not in love anymore?
            	if (!((EntityAnimalChocobo)this.entityToAttack).isInLove())
            	{
            		this.entityToAttack = null;
            	}
            }
        }
        
		// handle wing rotation
        if (Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
        {
        	this.destPos += (double)(this.onGround ? -1 : 4) * 0.3D;
        	if (this.destPos < 0.0F)
        	{
        		this.destPos = 0.0F;
        	}
        	if (this.destPos > 1.0F)
        	{
        		this.destPos = 1.0F;
        	}
        	if (!this.onGround && this.wingRotDelta < 1.0F)
        	{
        		this.wingRotDelta = 1.0F;
        	}
        	this.wingRotDelta *= 0.9D;
        	if (!this.onGround && this.motionY < 0.0D)
        	{
        		this.motionY *= 0.8D;
        	}
        	this.wingRotation += this.wingRotDelta * 2.0F;
        }

		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
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
		interacted  = super.interact(entityplayer);
		
		if(!interacted)
		{
			ItemStack itemstack = entityplayer.inventory.getCurrentItem();
			if(itemstack == null || itemstack.stackSize == 0)
			{
				interacted = this.onOpenSaddlePackBagInteraction(entityplayer);
			}
			else
			{
				if (itemstack.itemID == ModChocoCraft.gysahlPinkItem.shiftedIndex)
				{
					this.onPaint(entityplayer, true);
					interacted = true;
				}
				else if (itemstack.itemID == ModChocoCraft.gysahlRedItem.shiftedIndex)
				{
					this.onPaint(entityplayer, false);
					interacted = true;
				}
				else if (isLoverlyGysahl(itemstack) || isGoldenGysahl(itemstack))
				{
					this.onBreedGysahlUse(entityplayer, isGoldenGysahl(itemstack));
					interacted = true;
				}
				else if (itemstack.itemID == ModChocoCraft.chocoboFeatherItem.shiftedIndex)
				{
					this.onFeatherUse(entityplayer);
					interacted = true;
				}
				else if (itemstack.itemID == Item.silk.shiftedIndex)
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
		boolean interacted = false;
		interacted = super.onEmptyHandInteraction(entityplayer);
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
    	if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
    	{
    		this.entityDropItem(new ItemStack(ModChocoCraft.chocoboFeatherItem, 1), 0.0F);
    	}
	}

	protected void dropFewItems(boolean par1, int par2)
	{
		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			super.dropFewItems(par1, par2);
		}
	}

	protected int getDropItemId()
	{
		if (isBurning())
		{
			return ModChocoCraft.chocoboLegCookedItem.shiftedIndex;
		}
		else
		{
			return ModChocoCraft.chocoboLegRawItem.shiftedIndex;
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
			if (this.riddenByEntity != null && (this.riddenByEntity instanceof EntityLiving))
			{
				if(this instanceof EntityChocoboPurple)
				{					
					this.riddenByEntity.extinguish();
					this.riddenByEntity.fireResistance = 100;
				}
				prevRotationYaw = rotationYaw = riddenByEntity.rotationYaw;
				prevRotationPitch = rotationPitch = 0.0F;
				EntityLiving entityliving = (EntityLiving)riddenByEntity;
				isJumping = entityliving.isJumping;
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
						return;
					}
				}
			}
			else if (this.isSaddled() && !this.isFollowing() && !this.isInLove())
			{
				return;
			}
			else
			{
				super.updateEntityActionState();
				return;
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
		Boolean canmatewith = false;
		
    	if(otherEntity != this)
    	{
    		if(otherEntity instanceof EntityChocobo)
    		{
    			EntityChocobo otherChocobo = (EntityChocobo)otherEntity;
    			canmatewith = this.isInLove() && otherChocobo.isInLove();
    		}
    	}
    	return canmatewith;
    }
	
	public abstract chocoboColor getBabyAnimalColor(EntityAgeable otherAnimalParent);
	//public EntityAnimal spawnBabyAnimal(EntityAnimal otherAnimalParent)
	@Override
	public EntityAgeable func_90011_a(EntityAgeable otherAnimalParent)
	{
		chocoboColor chicoboColor = this.getBabyAnimalColor(otherAnimalParent);
		EntityChicobo childChicobo = new EntityChicobo(worldObj);
		childChicobo.setColor(chicoboColor);
		return childChicobo;
	}
}
