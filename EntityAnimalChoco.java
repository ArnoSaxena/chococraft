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

package net.minecraft.src;

import java.util.List;

public abstract class EntityAnimalChoco extends EntityCreature
{
	public final static int COLOR_PURPLE = -1;
	public final static int COLOR_YELLOW = 0;
	public final static int COLOR_GREEN = 1;
	public final static int COLOR_BLUE = 2;
	public final static int COLOR_WHITE = 3;
	public final static int COLOR_BLACK = 4;
	public final static int COLOR_GOLD = 5;
	public final static int COLOR_PINK = 6;
	public final static int COLOR_RED = 7;
	
    public int inLove;
    protected int breeding;
	private boolean following;
	private boolean tamed;
	private EntityPlayer owner;
    private PathEntity pathToEntity;
    protected Entity entityToAttack;
    protected boolean hasAttacked;
    protected boolean wander;
    protected int fleeingTick;
	
	
	public EntityAnimalChoco(World world)
    {
        super(world);
        hasAttacked = false;
        wander = true;
        fleeingTick = 0;
        breeding = 0;

//        tasks.addTask(0, new EntityAISwimming(this));
//        tasks.addTask(1, new EntityAIPanic(this, 0.38F));
//        tasks.addTask(2, new EntityAIWander(this, 0.25F));
//        tasks.addTask(3, new EntityAIWatchClosest(this, net.minecraft.src.EntityPlayer.class, 6F));
//        tasks.addTask(4, new EntityAILookIdle(this));
    }

    public boolean isAIEnabled()
    {
        return false;
    }
	
    protected boolean isMovementCeased()
    {
        return false;
    }
	
	protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(12, new Integer(0));
    }
    
    public void setFollowing(Boolean following)
    {
    	this.following = following;
    }
    
    public Boolean isFollowing()
    {
    	return this.following;
    }

    public void setTamed(Boolean tamed)
    {
    	this.tamed = tamed;
    }
    
    public boolean isTamed()
    {
    	return this.tamed;
    }

    public void setOwner(EntityPlayer owner)
    {
    	this.owner = owner;
    }
    
    public EntityPlayer getOwner()
    {
    	return this.owner;
    }
    
    public int getDelay()
    {
        return dataWatcher.getWatchableObjectInt(12);
    }

    public void setDelay(int i)
    {
        dataWatcher.updateObject(12, Integer.valueOf(i));
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        int i = getDelay();
        if (i < 0)
        {
            i++;
            setDelay(i);
        }
        else if (i > 0)
        {
            i--;
            setDelay(i);
        }
        if (inLove > 0)
        {
            inLove--;
            String s = "heart";
            if (inLove % 10 == 0)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle(s, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
            }
        }
        else
        {
            breeding = 0;
        }
    }

    protected void attackEntity(Entity entity, float f)
    {
        if (entity instanceof EntityPlayer)
        {
            if (f < 3F)
            {
                double d = entity.posX - posX;
                double d1 = entity.posZ - posZ;
                rotationYaw = (float)((Math.atan2(d1, d) * 180D) / 3.1415927410125732D) - 90F;
                hasAttacked = true;
            }
            EntityPlayer entityplayer = (EntityPlayer)entity;
            if (entityplayer.getCurrentEquippedItem() == null || !isLoverlyGysahl(entityplayer.getCurrentEquippedItem()))
            {
                entityToAttack = null;
            }
        }
        else if (entity instanceof EntityAnimalChoco)
        {
            EntityAnimalChoco entityanimalchoco = (EntityAnimalChoco)entity;
            if (getDelay() > 0 && entityanimalchoco.getDelay() < 0)
            {
                if ((double)f < 2.5D)
                {
                    hasAttacked = true;
                }
            }
            else if (inLove > 0 && entityanimalchoco.inLove > 0)
            {
                if (entityanimalchoco.entityToAttack == null)
                {
                    entityanimalchoco.entityToAttack = this;
                }
                if (entityanimalchoco.entityToAttack == this && (double)f < 3.5D)
                {
                    entityanimalchoco.inLove++;
                    inLove++;
                    breeding++;
                    if (breeding % 4 == 0)
                    {
                        worldObj.spawnParticle("heart", (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, 0.0D, 0.0D, 0.0D);
                    }
                    if (breeding == 60)
                    {
                        proceate((EntityAnimalChoco)entity);
                    }
                }
                else
                {
                    breeding = 0;
                }
            }
            else
            {
                breeding = 0;
            }
        }
    }

    void proceate(EntityAnimalChoco entityanimalchoco)
    {
        EntityChicobo entitychicobo = spawnBabyAnimal(entityanimalchoco);
        checkColorAchievements(entitychicobo.color);
        entitychicobo.timeUntilAdult = rand.nextInt(2000) + 27000;
        entitychicobo.setDelay(entitychicobo.timeUntilAdult);
        entitychicobo.growUp = false;
        if (entitychicobo != null)
        {
            setDelay(9000);
            entityanimalchoco.setDelay(9000);
            inLove = 0;
            breeding = 0;
            entityToAttack = null;
            entityanimalchoco.entityToAttack = null;
            entityanimalchoco.breeding = 0;
            entityanimalchoco.inLove = 0;
            entitychicobo.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
            for (int i = 0; i < 7; i++)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle("heart", (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
            }

            worldObj.spawnEntityInWorld(entitychicobo);
        }
    }

    private void checkColorAchievements(int i)
    {
        EntityPlayer entityplayer = (EntityPlayer)worldObj.playerEntities.get(0);
        if (entityplayer != null)
        {
            entityplayer.addStat(mod_chocobo.breedChoco, 1);
            switch (i)
            {
                case 1:
                    entityplayer.addStat(mod_chocobo.greenChoco, 1);
                    break;

                case 2:
                    entityplayer.addStat(mod_chocobo.blueChoco, 1);
                    break;

                case 3:
                    entityplayer.addStat(mod_chocobo.whiteChoco, 1);
                    break;

                case 4:
                    entityplayer.addStat(mod_chocobo.blackChoco, 1);
                    break;

                case 5:
                    entityplayer.addStat(mod_chocobo.goldChoco, 1);
                    break;

                case 6:
                    entityplayer.addStat(mod_chocobo.pinkChoco, 1);
                    break;

                case 7:
                    entityplayer.addStat(mod_chocobo.redChoco, 1);
                    break;
            }
        }
    }

    protected abstract EntityChicobo spawnBabyAnimal(EntityAnimalChoco entityanimalchoco);

    protected void attackBlockedEntity(Entity entity, float f)
    {
    }

    public boolean attackEntityFrom(DamageSource damageSource, int tmpNaturalArmorRating)
    {
        fleeingTick = 60;
        entityToAttack = null;
        inLove = 0;
    	if(!worldObj.isRemote && this.isTamed() && damageSource.getEntity() instanceof EntityWolf)
    	{
    		EntityWolf attackingWolf = (EntityWolf)damageSource.getEntity();
    		if(attackingWolf.isTamed())
    		{
    			attackingWolf.getOwner().setLastAttackingEntity(attackingWolf);
    			attackingWolf.setAttackTarget(null);
    			return false;
    		}
    	}    	
    	return super.attackEntityFrom(damageSource, tmpNaturalArmorRating);
    }

    public float getBlockPathWeight(int i, int j, int k)
    {
        if (worldObj.getBlockId(i, j - 1, k) == Block.grass.blockID)
        {
            return 10F;
        }
        else
        {
            return worldObj.getLightBrightness(i, j, k) - 0.5F;
        }
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("Age", getDelay());
        nbttagcompound.setInteger("InLove", inLove);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setDelay(nbttagcompound.getInteger("Age"));
        inLove = nbttagcompound.getInteger("InLove");
    }
    
    @SuppressWarnings("rawtypes")
	protected Entity findPlayerToAttack()
    {
        if (fleeingTick > 0)
        {
            return null;
        }
        float f = 8F;
        if (inLove > 0)
        {
            List list = worldObj.getEntitiesWithinAABB(getClass(), boundingBox.expand(f, f, f));
            for (int i = 0; i < list.size(); i++)
            {
                EntityAnimalChoco entityanimalchoco = (EntityAnimalChoco)list.get(i);
                if (entityanimalchoco != this && entityanimalchoco.inLove > 0)
                {
                    return entityanimalchoco;
                }
            }
        }
        else if (getDelay() == 0)
        {
            List list1 = worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityPlayer.class, boundingBox.expand(f, f, f));
            for (int j = 0; j < list1.size(); j++)
            {
                EntityPlayer entityplayer = (EntityPlayer)list1.get(j);
                if (entityplayer.getCurrentEquippedItem() != null && isLoverlyGysahl(entityplayer.getCurrentEquippedItem()))
                {
                    return entityplayer;
                }
            }
        }
        else if (getDelay() > 0)
        {
            List list2 = worldObj.getEntitiesWithinAABB(getClass(), boundingBox.expand(f, f, f));
            for (int k = 0; k < list2.size(); k++)
            {
                EntityAnimalChoco entityanimalchoco1 = (EntityAnimalChoco)list2.get(k);
                if (entityanimalchoco1 != this && entityanimalchoco1.getDelay() < 0)
                {
                    return entityanimalchoco1;
                }
            }
        }
        return null;
    }

    public boolean getCanSpawnHere()
    {
        int x = MathHelper.floor_double(posX);
        int y = MathHelper.floor_double(boundingBox.minY);
        int z = MathHelper.floor_double(posZ);        

        Boolean onGrassBlock = worldObj.getBlockId(x, y - 1, z) == Block.grass.blockID;
        Boolean onLitBlock = worldObj.getFullBlockLightValue(x, y, z) > 8;
        Boolean isPosPathWeight = getBlockPathWeight(x, y, z) >= 0.0F;
        
        return  onGrassBlock && onLitBlock && isPosPathWeight && super.getCanSpawnHere();
    }

    public int getTalkInterval()
    {
        return 150;
    }

    protected boolean canDespawn()
    {
        return true;
    }

    protected int getExperiencePoints(EntityPlayer entityplayer)
    {
        return 1 + worldObj.rand.nextInt(3);
    }

    protected boolean isLoverlyGysahl(ItemStack itemstack)
    {
        return itemstack.itemID == mod_chocobo.chocoboLoverly.shiftedIndex;
    }

    protected boolean isGoldenGysahl(ItemStack itemstack)
    {
        return itemstack.itemID == mod_chocobo.chocoboGolden.shiftedIndex;
    }

    public boolean interact(EntityPlayer entityplayer)
    {
        return super.interact(entityplayer);
    }

    public boolean isChild()
    {
        return getDelay() < 0;
    }
    
    protected void fall(float fallHeight)
    {
        int i = (int)Math.ceil(fallHeight - 6F);
        if (i > 0)
        {
            if (i > 4)
            {
                worldObj.playSoundAtEntity(this, "damage.fallbig", 1.0F, 1.0F);
            }
            else
            {
                worldObj.playSoundAtEntity(this, "damage.fallsmall", 1.0F, 1.0F);
            }
            attackEntityFrom(DamageSource.fall, i);
            int j = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(posY - 0.20000000298023224D - (double)yOffset), MathHelper.floor_double(posZ));
            if (j > 0)
            {
                StepSound stepsound = Block.blocksList[j].stepSound;
                worldObj.playSoundAtEntity(this, stepsound.getStepSound(), stepsound.getVolume() * 0.5F, stepsound.getPitch() * 0.75F);
            }
        }
    }

    protected void fall(float fallHeight, int dummy)
    {
    	this.fall(fallHeight);
    }
    
    protected float getSpeedModifier()
    {
        float f = super.getSpeedModifier();
        return f;
    }
    
    protected void updateWanderPath()
    {
        Profiler.startSection("stroll");
        boolean flag = false;
        int i = -1;
        int j = -1;
        int k = -1;
        float f = -99999F;
        for (int l = 0; l < 10; l++)
        {
            int i1 = MathHelper.floor_double((posX + (double)rand.nextInt(13)) - 6D);
            int j1 = MathHelper.floor_double((posY + (double)rand.nextInt(7)) - 3D);
            int k1 = MathHelper.floor_double((posZ + (double)rand.nextInt(13)) - 6D);
            float f1 = getBlockPathWeight(i1, j1, k1);
            if (f1 > f)
            {
                f = f1;
                i = i1;
                j = j1;
                k = k1;
                flag = true;
            }
        }

        if (flag)
        {
            pathToEntity = worldObj.getEntityPathToXYZ(this, i, j, k, 10F, false, false, false, false);
        }
        Profiler.endSection();
    }

    public boolean hasPath()
    {
        return this.pathToEntity != null;
    }

    public void setPathToEntity(PathEntity pathentity)
    {
        this.pathToEntity = pathentity;
    }

    public Entity getEntityToAttack()
    {
        return this.entityToAttack;
    }

    public void setEntityToAttack(Entity entity)
    {
        this.entityToAttack = entity;
    }

    protected void updateEntityActionState()
    {
        Profiler.startSection("ai");
        if (this.fleeingTick > 0)
        {
        	this.fleeingTick--;
        }
        this.hasAttacked = isMovementCeased();
        float f = 16F;
        if (this.entityToAttack == null)
        {
        	this.entityToAttack = findPlayerToAttack();
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
            else
            {
                attackBlockedEntity(this.entityToAttack, distToAttackEntity);
            }
        }
        Profiler.endSection();
        if (!this.hasAttacked && this.entityToAttack != null && (this.pathToEntity == null || this.rand.nextInt(20) == 0))
        {
        	this.pathToEntity = this.worldObj.getPathEntityToEntity(this, this.entityToAttack, f, false, false, false, false);
        }
        else if (!this.hasAttacked && this.wander && (this.pathToEntity == null && this.rand.nextInt(180) == 0 || this.rand.nextInt(120) == 0 || this.fleeingTick > 0) && this.entityAge < 100)
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
        Profiler.startSection("followpath");
        Vec3D vec3d = this.pathToEntity.getCurrentNodeVec3d(this);
        for (double d = this.width * 2.0F; vec3d != null && vec3d.squareDistanceTo(posX, vec3d.yCoord, posZ) < d * d;)
        {
            this.pathToEntity.incrementPathIndex();
            if (this.pathToEntity.isFinished())
            {
                vec3d = null;
                this.pathToEntity = null;
            }
            else
            {
                vec3d = this.pathToEntity.getCurrentNodeVec3d(this);
            }
        }

        this.isJumping = false;
        if (vec3d != null)
        {
            double d1 = vec3d.xCoord - this.posX;
            double d2 = vec3d.zCoord - this.posZ;
            double d3 = vec3d.yCoord - (double)i;
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
        Profiler.endSection();
    }
}
