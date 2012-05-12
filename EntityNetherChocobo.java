// <copyright file="EntityNetherChocobo.java">
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
// <summary>Entity class for nether chocobos</summary>

package net.minecraft.src;

public class EntityNetherChocobo extends EntityChocobo
{
    private boolean riderFireStatus;
    //private float flyingMovementFactor;

    public EntityNetherChocobo(World world)
    {
        super(world);
        this.initialiseNetherChocobo(world, "", false, false, false);
    }

    public EntityNetherChocobo(World world, String name, boolean hidename, boolean tamed, boolean following)
    {
        super(world);
        this.initialiseNetherChocobo(world, name, hidename, tamed, following);
    }
    
    protected void initialiseNetherChocobo(World world, String name, boolean hidename, boolean tamed, boolean following)
    {
    	super.initialiseEntityChocobo(world, name, hidename, tamed, following);
        this.setColor(COLOR_PURPLE);
        this.isImmuneToFire = true;
    }

    public boolean getCanSpawnHere()
    {
    	// same as EntityLiving
        return worldObj.difficultySetting > 0 && worldObj.checkIfAABBIsClear(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && !worldObj.isAnyLiquid(boundingBox);
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        this.setColor(COLOR_PURPLE);
    }

    public boolean isBurning()
    {
        return false;
    }

    private boolean isInLava()
    {
    	return super.handleLavaMovement();
    }

    public boolean handleLavaMovement()
    {
        return false;
    }

    public void onUpdate()
    {
        super.onUpdate();
        if (!worldObj.playerEntities.isEmpty())
        {
            EntityPlayer entityplayer = (EntityPlayer)this.worldObj.playerEntities.get(0);
            if (this.riddenByEntity != null)
            {
                if (entityplayer.isJumping)
                {
                    this.motionY += 0.10000000000000001D;
                    this.setFlying(true);
                }
                if (entityplayer.isSneaking() && !this.isInLava())
                {
                	this.motionY -= 0.14999999999999999D;
                }
            }
            else if (this.isFlying() && !this.onGround)
            {
            	this.motionY -= 0.25D;
            }
        }        
        
        if (this.isFlying() && this.onGround)
        {
        	this.setFlying(false);
        }
        
        // if(this.isNetherChocobo() && isInLava())
        if (isInLava())
        {
            int i = 5;
            double d = 0.0D;
            for (int j = 0; j < i; j++)
            {
                double d2 = (boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(j + 0)) / (double)i) - 0.125D;
                double d3 = (boundingBox.minY + ((boundingBox.maxY - boundingBox.minY) * (double)(j + 1)) / (double)i) - 0.125D;
                AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBoxFromPool(boundingBox.minX, d2, boundingBox.minZ, boundingBox.maxX, d3, boundingBox.maxZ);
                if (worldObj.isAABBInMaterial(axisalignedbb, Material.lava))
                {
                    d += 1.0D / (double)i;
                }
            }

            if (d < 1.0D)
            {
                motionY += 0.039999999105930328D * (d * 2D - 1.0D);
            }
            else
            {
                if (motionY < 0.0D)
                {
                    motionY /= 2D;
                }
                motionY += 0.0070000002160668373D;
            }
        }
    }
    
	// x = wide
	// y = hight
	// z = lenght

    protected void setMoveSpeed(boolean mounting, EntityPlayer entityplayer)
    {
        if (mounting)
        {
            this.landMovementFactor = 0.25F;
            this.riderFireStatus = entityplayer.isImmuneToFire;
            entityplayer.isImmuneToFire = true;
            this.stepHeight = 1.0F;
        }
        else
        {
            this.landMovementFactor = 0.1F;
            entityplayer.isImmuneToFire = this.riderFireStatus;
            this.stepHeight = 0.5F;
        }
    }

    void proceate(EntityAnimalChoco entityanimalchoco)
    {
        setDelay(9000);
        entityanimalchoco.setDelay(9000);
        this.inLove = 0;
        this.breeding = 0;
        this.entityToAttack = null;
        entityanimalchoco.entityToAttack = null;
        entityanimalchoco.breeding = 0;
        entityanimalchoco.inLove = 0;
        dropItem(mod_chocobo.netherChocoboEgg.shiftedIndex, 1);
    }

    protected EntityChicobo spawnBabyAnimal(EntityAnimalChoco entityanimalchoco)
    {
        return null;
    }
}
