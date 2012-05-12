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

package net.minecraft.src;

import java.util.List;

public class EntityChocobo extends EntityAnimalChoco
{	
    public boolean field_753_a;
    public float field_752_b;
    public float destPos;
    public float field_757_d;
    public float field_756_e;
    public float field_755_h;
    public int timeUntilNextFeather;
    public int timeUntilNextPluck;
    public int color;
    public boolean fedGold;
    public boolean hidename;
    public String name;
    
    protected float flyingMovementFactor;
    
    protected boolean canClimb;
    protected boolean canCrossWater;
    protected boolean canJumpHigh;
    protected boolean canFly;
    protected boolean flying;
    protected boolean isHighJumping;
    protected boolean saddleBags;
    protected boolean packBags;
    protected ChocoBagInventory bagsInventory;
    // create inventory on creation of entityChocobo, but it is only
    // accessible if Chocobo is saddlebagged...

    public EntityChocobo(World world)
    {
        super(world);
        this.initialiseEntityChocobo(world, "", false, false, false);
    }

    public EntityChocobo(World world, String name, boolean hidename, boolean tamed, boolean following)
    {
        super(world);
        this.initialiseEntityChocobo(world, name, hidename, tamed, following);
    }
    
    protected void initialiseEntityChocobo(World world, String name, boolean hidename, boolean tamed, boolean following)
    {
    	this.bagsInventory = new ChocoSaddleBagInventory(this);
        this.field_753_a = false;
        this.field_752_b = 0.0F;
        this.destPos = 0.0F;
        this.field_755_h = 1.0F;
        this.hidename = hidename;
        this.name = name;
        this.setTamed(tamed);
        this.setFollowing(following);
        this.texture = this.getEntityTexture();
        this.setEntityHealth(this.getMaxHealth());
        this.setSize(1.0F, 2.0F);
        this.timeUntilNextFeather = this.rand.nextInt(6000) + 6000;
        this.timeUntilNextPluck = 0;
        this.setColorAbilities();
    }
    
    public void setFlying(Boolean flying)
    {
    	this.flying = flying;
    }
    
    public Boolean isFlying()
    {
    	return this.flying;
    }
    
    public void setColor(int color)
    {
    	if(COLOR_PURPLE <= color && COLOR_RED >= color)
    	{
    		this.color = color;
    	}
        this.setEntityHealth(this.getMaxHealth());
        this.setColorAbilities();
        this.texture = this.getEntityTexture();
    }

    public void setColorAbilities()
    {
    	this.flyingMovementFactor = 0.0F;
        this.canClimb = false;
        this.canCrossWater = false;
        this.canJumpHigh = false;
        this.canFly = false;
        switch (this.color)
        {
            case COLOR_GREEN:
            	this.canClimb = true;
            	break;

            case COLOR_BLUE:
            	this.canCrossWater = true;
            	break;

            case COLOR_WHITE:
            	this.canClimb = true;
            	this.canCrossWater = true;
                break;

            case COLOR_BLACK:
            	this.canClimb = true;
            	this.canCrossWater = true;
            	this.canJumpHigh = true;
                break;

            case COLOR_GOLD:
            case COLOR_PINK:
            case COLOR_RED:
            	this.flyingMovementFactor = 0.25F;
            	this.canClimb = true;
            	this.canCrossWater = true;
            	this.canFly = true;
                break;
            case COLOR_PURPLE:
            	this.flyingMovementFactor = 0.25F;
            	this.canClimb = true;
            	this.canCrossWater = false;
            	this.canFly = true;
                break;
        }
    }

    public String getEntityTexture()
    {
        String s = new String("/choco/Chocobos/");
        
        if(this.isPackBagged())
        {
        	s = (new StringBuilder()).append(s).append("PackBagged/").toString();
        }
        else if (this.isSaddleBagged())
        {
        	s = (new StringBuilder()).append(s).append("SaddleBagged/").toString();
        }
        else if (this.isSaddled())
        {
            s = (new StringBuilder()).append(s).append("Saddled/").toString();
        }
        else if (this.isTamed())
        {
            s = (new StringBuilder()).append(s).append("Tamed/").toString();
        }

        if (this.color == COLOR_YELLOW)
        {
            s = (new StringBuilder()).append(s).append("chocobo.png").toString();
        }
        else if (this.color == COLOR_GREEN)
        {
            s = (new StringBuilder()).append(s).append("greenchocobo.png").toString();
        }
        else if (this.color == COLOR_BLUE)
        {
            s = (new StringBuilder()).append(s).append("bluechocobo.png").toString();
        }
        else if (this.color == COLOR_WHITE)
        {
            s = (new StringBuilder()).append(s).append("whitechocobo.png").toString();
        }
        else if (this.color == COLOR_BLACK)
        {
            s = (new StringBuilder()).append(s).append("blackchocobo.png").toString();
        }
        else if (this.color == COLOR_GOLD)
        {
            s = (new StringBuilder()).append(s).append("goldchocobo.png").toString();
        }
        else if (this.color == COLOR_PINK)
        {
            s = (new StringBuilder()).append(s).append("pinkchocobo.png").toString();
        }
        else if (this.color == COLOR_RED)
        {
            s = (new StringBuilder()).append(s).append("redchocobo.png").toString();
        }
        else if (this.color == COLOR_PURPLE)
        {
            s = (new StringBuilder()).append(s).append("purplechocobo.png").toString();
        }
        return s;
    }

    public int getMaxHealth()
    {
        if (this.color > COLOR_BLACK)
        {
            return 50;
        } 
        else if(this.color == COLOR_PURPLE)
        {
        	return 40;
        }
        return this.color <= COLOR_BLUE ? 30 : 40;
    }

    public void onUpdate()
    {
        super.onUpdate();
        if (!this.worldObj.playerEntities.isEmpty())
        {
            EntityPlayer entityplayer = (EntityPlayer)this.worldObj.playerEntities.get(0);
            if (this.riddenByEntity != null)
            {
                if (entityplayer.isJumping)
                {
                    if (this.canFly)
                    {
                        this.motionY += 0.10000000000000001D;
                        this.setFlying(true);
                    }
                    else if (this.canJumpHigh && !this.isHighJumping)
                    {
                        this.motionY += 0.40000000000000002D;
                        this.isHighJumping = true;
                    }
                }
                if (entityplayer.isSneaking() && this.canFly)
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
                AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBoxFromPool(this.boundingBox.minX, d2, this.boundingBox.minZ, this.boundingBox.maxX, d3, this.boundingBox.maxZ);
                if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water))
                {
                    d += 1.0D / (double)i;
                }
            }

            if (d < 1.0D)
            {
                double d1 = d * 2D - 1.0D;
                this.motionY += 0.039999999105930328D * d1;
            }
            else
            {
                if (this.motionY < 0.0D)
                {
                	this.motionY /= 2D;
                }
                this.motionY += 0.0070000002160668373D;
            }
        }
    }

    public void moveEntityWithHeading(float f, float f1)
    {
        if (isInWater())
        {
            float landMovement = 0.02F;
            if (canCrossWater)
            {
                landMovement = landMovementFactor * 1.5F;
            }
            moveFlying(f, f1, landMovement);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.80000001192092896D;
            motionY *= 0.80000001192092896D;
            motionZ *= 0.80000001192092896D;
            if (!canCrossWater)
            {
                motionY -= 0.02D;
            }
            if (isCollidedHorizontally && isOffsetPositionInLiquid(motionX, ((motionY + 0.60000002384185791D) - this.posY) + this.posY, motionZ))
            {
                motionY = 0.29999999999999999D;
            }
        }
        else if (handleLavaMovement())
        {
        	if(this.isNetherChocobo())
        	{
                moveFlying(f, f1, this.landMovementFactor * 2.0F);
                moveEntity(motionX, motionY, motionZ);
                motionX *= 0.80000001192092896D;
                motionY *= 0.80000001192092896D;
                motionZ *= 0.80000001192092896D;
        	}
        	else
        	{
        		moveFlying(f, f1, 0.02F);
        		moveEntity(motionX, motionY, motionZ);
        		motionX *= 0.5D;
        		motionY *= 0.5D;
        		motionZ *= 0.5D;
        		motionY -= 0.02D;
        	}
            if (isCollidedHorizontally && isOffsetPositionInLiquid(motionX, ((motionY + 0.60000002384185791D) - this.posY) + this.posY, motionZ))
            {
                motionY = 0.29999999999999999D;
            }
        }
        else if (this.isFlying())
        {
            moveFlying(f, f1, this.flyingMovementFactor);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.80000001192092896D;
            motionY *= 0.80000001192092896D;
            motionZ *= 0.80000001192092896D;
            motionY -= 0.050000000000000003D;
        }
        else
        {
            float f3 = 0.91F;
            if (onGround)
            {
                f3 = 0.5460001F;
                int i = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                if (i > 0)
                {
                    f3 = Block.blocksList[i].slipperiness * 0.91F;
                }
            }
            float f4 = 0.1627714F / (f3 * f3 * f3);
            float f6 = onGround ? landMovementFactor * f4 : jumpMovementFactor;
            moveFlying(f, f1, f6);
            f3 = 0.91F;
            if (onGround)
            {
                f3 = 0.5460001F;
                int j = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                if (j > 0)
                {
                    f3 = Block.blocksList[j].slipperiness * 0.91F;
                }
            }
            if (isOnLadder())
            {
                float f7 = 0.15F;
                if (motionX < (double)(-f7))
                {
                    motionX = -f7;
                }
                if (motionX > (double)f7)
                {
                    motionX = f7;
                }
                if (motionZ < (double)(-f7))
                {
                    motionZ = -f7;
                }
                if (motionZ > (double)f7)
                {
                    motionZ = f7;
                }
                fallDistance = 0.0F;
                if (motionY < -0.14999999999999999D)
                {
                    motionY = -0.14999999999999999D;
                }
                if (isSneaking() && motionY < 0.0D)
                {
                    motionY = 0.0D;
                }
            }
            moveEntity(motionX, motionY, motionZ);
            if (isCollidedHorizontally && isOnLadder())
            {
                motionY = 0.20000000000000001D;
            }
            motionY -= 0.080000000000000002D;
            motionY *= 0.98000001907348633D;
            motionX *= f3;
            motionZ *= f3;
        }
        field_705_Q = field_704_R;
        double d2 = posX - prevPosX;
        double d3 = posZ - prevPosZ;
        float f8 = MathHelper.sqrt_double(d2 * d2 + d3 * d3) * 4F;
        if (f8 > 1.0F)
        {
            f8 = 1.0F;
        }
        field_704_R += (f8 - field_704_R) * 0.4F;
        field_703_S += field_704_R;
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        field_756_e = field_752_b;
        field_757_d = destPos;
        destPos += (double)(onGround ? -1 : 4) * 0.29999999999999999D;
        if (destPos < 0.0F)
        {
            destPos = 0.0F;
        }
        if (destPos > 1.0F)
        {
            destPos = 1.0F;
        }
        if (!onGround && field_755_h < 1.0F)
        {
            field_755_h = 1.0F;
        }
        field_755_h *= 0.90000000000000002D;
        if (!onGround && motionY < 0.0D)
        {
            motionY *= 0.79999999999999993D;
        }
        field_752_b += field_755_h * 2.0F;
        if (!worldObj.isRemote && --timeUntilNextFeather <= 0)
        {
            int i = rand.nextInt(3);
            if (i == 0 && !isSaddled())
            {
                dropItem(mod_chocobo.chocoboFeather.shiftedIndex, 1);
            }
            timeUntilNextFeather = rand.nextInt(6000) + 6000;
        }
        if (timeUntilNextPluck > 0)
        {
            timeUntilNextPluck--;
        }
    }

    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Saddle", this.isSaddled());
        nbttagcompound.setBoolean("SaddleBag", this.isSaddleBagged());
        nbttagcompound.setBoolean("PackBag", this.isPackBagged());
        nbttagcompound.setInteger("Color", this.color);
        nbttagcompound.setInteger("Pluck", this.timeUntilNextPluck);
        nbttagcompound.setBoolean("Follow", this.isFollowing());
        nbttagcompound.setBoolean("Tamed", this.isTamed());
        nbttagcompound.setString("Name", this.name);
        nbttagcompound.setBoolean("HideName", this.hidename);
        nbttagcompound.setTag("SaddleBagInventory", this.bagsInventory.writeToNBT(new NBTTagList()));

    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        this.setSaddled(nbttagcompound.getBoolean("Saddle"));
        this.setSaddleBagged(nbttagcompound.getBoolean("SaddleBag"));
        this.setPackBagged(nbttagcompound.getBoolean("PackBag"));
        this.setTamed(nbttagcompound.getBoolean("Tamed"));
        this.color = nbttagcompound.getInteger("Color");
        this.texture = getEntityTexture();
        this.setColorAbilities();
        this.name = nbttagcompound.getString("Name");
        this.hidename = nbttagcompound.getBoolean("HideName");
        this.timeUntilNextPluck = nbttagcompound.getInteger("Pluck");
        this.setFollowing(nbttagcompound.getBoolean("Follow"));
        if (this.isFollowing() || this.isSaddled() || this.isPackBagged())
        {
        	this.wander = false;
        }
        else
        {
        	this.wander = true;
        }
        NBTTagList nbttaglist = nbttagcompound.getTagList("SaddleBagInventory");
        this.bagsInventory.readFromNBT(nbttaglist);
    }

    protected String getLivingSound()
    {
        if (rand.nextInt(4) == 0)
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
    	// if Chocobo has saddlebag shift-click will allways open it 
    	if(entityplayer.isSneaking() && (this.isSaddleBagged() || this.isPackBagged())){
    		return this.onInteraction(entityplayer);
    	}
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (itemstack != null && !worldObj.isRemote)
        {
            if (itemstack.itemID == mod_chocobo.gysahlGreen.blockID)
            {
                this.onGysahlGreenUse(entityplayer);
            }
            else if (itemstack.itemID == mod_chocobo.chocoboWhistle.shiftedIndex)
            {
                this.onWhistleUse();
            }
            else if (itemstack.itemID == mod_chocobo.chocoboPink.shiftedIndex || itemstack.itemID == mod_chocobo.chocoboRed.shiftedIndex)
            {
            	if(!(this instanceof EntityNetherChocobo))
            	{
            		this.onPaint(entityplayer, Boolean.valueOf(itemstack.itemID == mod_chocobo.chocoboPink.shiftedIndex));
            	}
            	else
            	{
            		this.showHeartsOrSmokeFX(false);
            	}
            }
            else if (isLoverlyGysahl(itemstack) || isGoldenGysahl(itemstack))
            {
                this.onBreedGysahlUse(entityplayer, isGoldenGysahl(itemstack));
            }
            else if (itemstack.itemID == mod_chocobo.chocoboSaddle.shiftedIndex)
            {
                this.onSaddleUse(entityplayer);
            }
            else if (itemstack.itemID == mod_chocobo.chocoboSaddleBags.shiftedIndex)
            {
                this.onSaddleBagsUse(entityplayer);
            }
            else if (itemstack.itemID == mod_chocobo.chocoboPackBags.shiftedIndex)
            {
                this.onPackBagsUse(entityplayer);
            }
            else if (itemstack.itemID == mod_chocobo.chocoboFeather.shiftedIndex)
            {
                this.onFeatherUse(entityplayer);
            }
            else if (itemstack.itemID == mod_chocobo.chocopedia.shiftedIndex)
            {
                this.onChocopediaUse();
            }
            else if (itemstack.itemID == mod_chocobo.chocotweezer.shiftedIndex)
            {
                this.onTweezersUse();
            }
            else
            {
                return this.onInteraction(entityplayer);
            }
        }
        else
        {
            return this.onInteraction(entityplayer);
        }
        return true;
    }

	public void onTweezersUse()
    {
        if ((this.isTamed() || isSaddled()) && this.riddenByEntity == null)
        {
            if (this.timeUntilNextPluck == 0)
            {
                int i = rand.nextInt(100);
                if (i < 75)
                {
                    dropItem(mod_chocobo.chocoboFeather.shiftedIndex, 1);
                }
                else
                {
                    onWhistleUse();
                }
                timeUntilNextPluck = rand.nextInt(6000) + 6000;
            }
            else
            {
                showHeartsOrSmokeFX(false);
            }
        }
    }

    public void onChocopediaUse()
    {
        if (this.isTamed() || isSaddled())
        {
            if (riddenByEntity == null)
            {
                mod_chocobo.mc.displayGuiScreen(new GuiChocopedia(mod_chocobo.mc.currentScreen, this));
            }
            else
            {
                showHeartsOrSmokeFX(false);
            }
        }
    }

    protected boolean onInteraction(EntityPlayer entityplayer)
    {
        if (this.isSaddled())
        {
        	if(entityplayer.isSneaking() && this.isSaddleBagged())
        	{
        		mod_chocobo.mc.displayGuiScreen(new GuiChocoboBag(entityplayer.inventory, this.getIInventory()));
        	}
        	else if (this.riddenByEntity == null || this.riddenByEntity == entityplayer)
            {
                entityplayer.mountEntity(this);
                if (this.riddenByEntity == null)
                {
                    this.dismountChocobo(entityplayer);
                }
                else
                {
                    this.mountChocobo(entityplayer);
                }
            }
        }
        else if (this.isPackBagged())
        {
        	mod_chocobo.mc.displayGuiScreen(new GuiChocoboBag(entityplayer.inventory, this.getIInventory()));
        }
        else if (this.isTamed())
        {
            this.toggleFollow();
        }
        else
        {
            return super.interact(entityplayer);
        }
        return true;
    }

    private void mountChocobo(EntityPlayer entityplayer)
    {
        entityplayer.addStat(mod_chocobo.rideChoco, 1);
        this.setMoveSpeed(true, entityplayer);
    }

    private void dismountChocobo(EntityPlayer entityplayer)
    {
    	this.isJumping = false;
        this.setMoveSpeed(false, entityplayer);
    }

    public void onFeatherUse(EntityPlayer entityplayer)
    {
        if (this.isSaddled() && this.riddenByEntity == null)
        {
            this.toggleFollow();
        }
        else if (this.isTamed())
        {
            this.toggleFollow();
        }
        else
        {
            this.showHeartsOrSmokeFX(false);
        }
    }

    public void toggleFollow()
    {
    	this.setFollowing(!this.isFollowing());
        if (!isSaddled())
        {
            this.wander = !this.isFollowing();
        }
        showHeartsOrSmokeFX(this.isFollowing());
    }

    public void onSaddleUse(EntityPlayer entityplayer)
    {
        if (this.isTamed() && !this.isSaddled() && !this.isPackBagged())
        {
            this.setSaddled(true);
            this.wander = false;
            this.setFollowing(false);
            this.useItem(entityplayer);
        }
    }
    
    public void onPackBagsUse(EntityPlayer entityplayer)
    {
    	if(this.isTamed() && !this.isSaddled() && !this.isPackBagged())
    	{
    		this.setPackBagged(true);
    		this.wander = false;
    		this.setFollowing(false);
    		this.useItem(entityplayer);
    	}
	}

    
    public void onSaddleBagsUse(EntityPlayer entityplayer)
    {
    	if (this.isTamed() && this.isSaddled() && !this.isSaddleBagged())
    	{
    		this.setSaddleBagged(true);
    		this.useItem(entityplayer);
    	}
    }

    public void onBreedGysahlUse(EntityPlayer entityplayer, boolean fedGold)
    {
        if (this.isTamed() && this.getDelay() == 0)
        {
            this.fedGold = fedGold;
            this.useItem(entityplayer);
            this.inLove = 600;
            this.entityToAttack = null;
            for (int i = 0; i < 7; i++)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                this.worldObj.spawnParticle("heart", (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
            }
        }
        else
        {
            this.showHeartsOrSmokeFX(false);
        }
    }

    private void onPaint(EntityPlayer entityplayer, Boolean boolean1)
    {
        if (color == COLOR_GOLD)
        {
            for (int i = 0; i < 20; i++)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle("explode", (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
            }

            if (boolean1.booleanValue())
            {
                color = COLOR_PINK;
            }
            else
            {
                color = COLOR_RED;
            }
            useItem(entityplayer);
            texture = getEntityTexture();
        }
        else
        {
            showHeartsOrSmokeFX(false);
        }
    }

    public void onWhistleUse()
    {
        if (this.isTamed())
        {
            if(this.isSaddleBagged())
        	{
        		this.dropItem(mod_chocobo.chocoboSaddleBags.shiftedIndex, 1);
        		this.bagsInventory.dropAllItems();
        	}

            if(this.isSaddled())
            {
        		this.dropItem(mod_chocobo.chocoboSaddle.shiftedIndex, 1);            	
            }
            
            if(this.isPackBagged())
        	{
        		this.dropItem(mod_chocobo.chocoboPackBags.shiftedIndex, 1);
        		this.bagsInventory.dropAllItems();
        	}
                        
            EntityChocobo newChocobo;
            if(this instanceof EntityNetherChocobo)
            {
            	newChocobo = new EntityNetherChocobo(this.worldObj, this.name, true, false, false);
            }
            else
            {
            	newChocobo = new EntityChocobo(this.worldObj, this.name, true, false, false);
            }
            newChocobo.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            newChocobo.setColor(this.color);
            newChocobo.setDelay(6000);
            newChocobo.health = this.health;
            newChocobo.wander = true;
            worldObj.spawnEntityInWorld(newChocobo);
            this.health = 0;
            this.setDead();
        }
    }

    public void onGysahlGreenUse(EntityPlayer entityplayer)
    {
        if (!this.isTamed())
        {
            useItem(entityplayer);
            if (rand.nextInt(3) == 0)
            {
                this.setTamed(true);
                this.wander = false;
                this.setFollowing(true);
                this.hidename = true;
                this.showHeartsOrSmokeFX(true);
                this.worldObj.setEntityState(this, (byte)7);
                entityplayer.addStat(mod_chocobo.tameChoco, 1);
                if (this.name.isEmpty())
                {
                	this.setRandomName();
                }
                this.texture = this.getEntityTexture();
            }
            else
            {
            	this.showHeartsOrSmokeFX(false);
            	this.worldObj.setEntityState(this, (byte)6);
            }
        }
        else if (health < getMaxHealth())
        {
            useItem(entityplayer);
            heal(10);
            showHeartsOrSmokeFX(true);
        }
    }

    private void useItem(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        itemstack.stackSize--;
        if (itemstack.stackSize <= 0)
        {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
        }
    }
    
    protected void dropFewItems(boolean par1, int par2)
    {
    	super.dropFewItems(par1, par2);
    }

    protected int getDropItemId()
    {
        if (isBurning() && !this.isNetherChocobo())
        {
            return mod_chocobo.chocoboCooked.shiftedIndex;
        }
        else
        {
            return mod_chocobo.chocoboRaw.shiftedIndex;
        }
    }

    public boolean isSaddled()
    {
        return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void setSaddled(boolean flag)
    {
        if (flag)
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)1));
        }
        else
        {
            dataWatcher.updateObject(16, Byte.valueOf((byte)0));
        }
        this.texture = this.getEntityTexture();
    }

    public Boolean isSaddleBagged()
    {
        return this.saddleBags;
    }
    
    public Boolean isPackBagged()
    {
    	return this.packBags;
    }

    public void setSaddleBagged(boolean saddleBags)
    {
    	if(saddleBags != this.saddleBags)
    	{
    		ChocoBagInventory newBagInventory;
    		if(saddleBags)
    		{
        		newBagInventory = new ChocoSaddleBagInventory(this);
    		}
    		else
    		{
        		newBagInventory = new ChocoPackBagInventory(this);
    		}
    		newBagInventory.insertInventory(this.getIInventory());
    		this.bagsInventory = newBagInventory;
    		this.saddleBags = saddleBags;
            this.texture = this.getEntityTexture();
    	}
    	
//    	this.saddleBags = saddleBags;
//    	if(!saddleBags)
//    	{
//    		this.bagsInventory.dropAllItems();
//    	}
//        this.texture = this.getEntityTexture();
    }
    
    public void setPackBagged(boolean packBagged)
    {
    	if(packBagged != this.packBags)
    	{
    		ChocoBagInventory newBagInventory;
    		if(packBagged)
    		{
        		newBagInventory = new ChocoPackBagInventory(this);
    		}
    		else
    		{
        		newBagInventory = new ChocoSaddleBagInventory(this);
    		}
    		newBagInventory.insertInventory(this.getIInventory());
    		this.bagsInventory = newBagInventory;
    		this.packBags = packBagged;
            this.texture = this.getEntityTexture();
    	}
    }
    
    public void updateEntityActionState()
    {
        if (!worldObj.playerEntities.isEmpty())
        {
            EntityPlayer entityplayer = (EntityPlayer)worldObj.playerEntities.get(0);
            if (riddenByEntity != null && (riddenByEntity instanceof EntityLiving))
            {
            	if(this.isNetherChocobo())
            	{
            		//entityplayer.setFire(0);
            		entityplayer.extinguish();
                	// perhaps extinguish() is the effect looked for...
            	}
                prevRotationYaw = rotationYaw = riddenByEntity.rotationYaw;
                prevRotationPitch = rotationPitch = 0.0F;
                EntityLiving entityliving = (EntityLiving)riddenByEntity;
                moveForward = entityliving.moveForward;
                moveStrafing = entityliving.moveStrafing;
                isJumping = entityliving.isJumping;
                double d = Math.abs(Math.sqrt(motionX * motionX + motionZ * motionZ));
                if (d > 0.375D)
                {
                    double d1 = 0.375D / d;
                    motionX = motionX * d1;
                    motionZ = motionZ * d1;
                }
                @SuppressWarnings("rawtypes")
                List list = worldObj.getEntitiesWithinAABBExcludingEntity(entityplayer, boundingBox.expand(1.0D, 1.0D, 1.0D));
                if (list != null)
                {
                    for (int i = 0; i < list.size(); i++)
                    {
                        Entity entity = (Entity)list.get(i);
                        if (!entity.isDead)
                        {
                            entity.onCollideWithPlayer(entityplayer);
                        }
                    }
                }
                return;
            }
            if (!this.hasAttacked && !this.hasPath() && this.isFollowing() && (this.isSaddled() || this.isTamed()))
            {
                if (entityplayer != null)
                {
                    if (entityplayer.isDead)
                    {
                    	this.setFollowing(false);
                        wander = false;
                        return;
                    }
                    float f = entityplayer.getDistanceToEntity(this);
                    if (f > 4F)
                    {
                        getPathOrWalkableBlock(entityplayer, f);
                    }
                    else
                    {
                        super.updateEntityActionState();
                        return;
                    }
                }
            }
            else if (this.isSaddled() && !this.isFollowing() && this.inLove == 0)
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

    protected void setMoveSpeed(boolean mounting, EntityPlayer entityplayer)
    {
        if (mounting)
        {
        	this.stepHeight = 1.0F;
            if (this.color > COLOR_BLACK)
            {
            	this.landMovementFactor = 0.35F;
            }
            else if (this.color > COLOR_BLUE)
            {
            	this.landMovementFactor = 0.25F;
            }
            else
            {
            	this.landMovementFactor = 0.2F;
            }
            
            if (this.canClimb)
            {
            	this.stepHeight = 2.0F;
            }
        }
        else
        {
        	this.landMovementFactor = 0.1F;
        	this.stepHeight = 0.5F;
        }
    }

    void getPathOrWalkableBlock(Entity entity, float f)
    {
        PathEntity pathentity = worldObj.getPathEntityToEntity(this, entity, 16F, canCrossWater, canClimb, canFly, true);
        if (pathentity == null && f > 12F)
        {
            int i = MathHelper.floor_double(entity.posX) - 2;
            int j = MathHelper.floor_double(entity.posZ) - 2;
            int k = MathHelper.floor_double(entity.boundingBox.minY);
            for (int l = 0; l <= 4; l++)
            {
                for (int i1 = 0; i1 <= 4; i1++)
                {
                    if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && worldObj.isBlockOpaqueCube(i + l, k - 1, j + i1) && !worldObj.isBlockOpaqueCube(i + l, k, j + i1) && !worldObj.isBlockOpaqueCube(i + l, k + 1, j + i1))
                    {
                        setLocationAndAngles((float)(i + l) + 0.5F, k, (float)(j + i1) + 0.5F, rotationYaw, rotationPitch);
                        return;
                    }
                }
            }
        }
        else
        {
            setPathToEntity(pathentity);
        }
    }

    protected void fall(float f)
    {
    	if(this.isNetherChocobo())
    	{
    		return;
    	}
        if (color < COLOR_GOLD)
        {
            super.fall(f);
        }
    }

    void showHeartsOrSmokeFX(boolean flag)
    {
        String s = "heart";
        if (!flag)
        {
            s = "smoke";
        }
        for (int i = 0; i < 7; i++)
        {
            double d = rand.nextGaussian() * 0.02D;
            double d1 = rand.nextGaussian() * 0.02D;
            double d2 = rand.nextGaussian() * 0.02D;
            worldObj.spawnParticle(s, (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + 0.5D + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
        }
    }

    protected EntityChicobo spawnBabyAnimal(EntityAnimalChoco otherParent)
    {
        EntityChocobo chocoboOtherParent = (EntityChocobo)otherParent;
        EntityChicobo childChicobo = new EntityChicobo(worldObj);
        int colorParentA = chocoboOtherParent.color;
        int colorParentB = this.color;
        int k = 4;
        if (fedGold && chocoboOtherParent.fedGold)
        {
            k -= 2;
        }
        int chicoboColor = COLOR_YELLOW;
        int l = rand.nextInt(k);
        if (l == 0)
        {
        	chicoboColor = getMutation(colorParentA, colorParentB, chocoboOtherParent);
        }
        else if (colorParentA > COLOR_BLACK)
        {
            if (colorParentB > COLOR_BLACK)
            {
            	chicoboColor = COLOR_YELLOW;
            }
            else if (rand.nextBoolean())
            {
            	chicoboColor = colorParentB;
            }
            else
            {
            	chicoboColor = COLOR_YELLOW;
            }
        }
        else if (colorParentB > COLOR_BLACK)
        {
            if (rand.nextBoolean())
            {
            	chicoboColor = COLOR_YELLOW;
            }
            else
            {
            	chicoboColor = colorParentA;
            }
        }
        else if (rand.nextBoolean())
        {
        	chicoboColor = colorParentB;
        }
        else
        {
        	chicoboColor = colorParentA;
        }
        childChicobo.setColor(chicoboColor);
        return childChicobo;
    }

    private int getMutation(int colorParentA, int colorParentB, EntityChocobo entitychocobo)
    {
        if (colorParentA == COLOR_YELLOW && colorParentB == COLOR_YELLOW)
        {
            return !rand.nextBoolean() ? COLOR_BLUE : COLOR_GREEN;
        }
        if (colorParentA == COLOR_GREEN && colorParentB == COLOR_BLUE 
        		|| colorParentA == COLOR_BLUE && colorParentB == COLOR_GREEN)
        {
            return COLOR_WHITE;
        }
        if (colorParentA == COLOR_YELLOW && colorParentB == COLOR_WHITE 
        		|| colorParentA == COLOR_WHITE && colorParentB == COLOR_YELLOW)
        {
            return COLOR_BLACK;
        }
        if (colorParentA == COLOR_WHITE && colorParentB == COLOR_BLACK 
        		|| colorParentA == COLOR_BLACK && colorParentB == COLOR_WHITE)
        {
            if (fedGold && entitychocobo.fedGold)
            {
                return COLOR_GOLD;
            }
            if (rand.nextBoolean())
            {
                return colorParentB;
            }
            else
            {
                return colorParentA;
            }
        }
        if (colorParentA > COLOR_BLACK)
        {
            if (colorParentB > COLOR_BLACK)
            {
                return COLOR_YELLOW;
            }
            else
            {
                return colorParentB;
            }
        }
        if (colorParentB > COLOR_BLACK)
        {
            return colorParentA;
        }
        if (rand.nextBoolean())
        {
            return colorParentB;
        }
        else
        {
            return colorParentA;
        }
    }

    protected boolean canDespawn()
    {
        return !this.isTamed() && !isSaddled();
    }

    public void setRandomName()
    {
        name = mod_chocobo.getRandomName();
    }

    public void setName(String s)
    {
    	this.name = s;
    }

    public boolean canRenderName()
    {
        return mod_chocobo.show_chocobo_names && !hidename && riddenByEntity == null && name != "" && (this.isTamed() || isSaddled());
    }
    
    public boolean isNetherChocobo()
    {
    	return this instanceof EntityNetherChocobo;
    }
    
    public IInventory getIInventory()
    {
    	return (IInventory)this.bagsInventory;
    }
    
    @Override
    public void onDeath(DamageSource damageSource)
    {
    	if(this.isSaddled())
    	{
    		this.dropItem(mod_chocobo.chocoboSaddle.shiftedIndex, 1);
    	}
    	if(this.isSaddleBagged())
    	{
    		this.dropItem(mod_chocobo.chocoboSaddleBags.shiftedIndex, 1);
    		this.bagsInventory.dropAllItems();
    	}
    	if(this.isPackBagged())
    	{
    		this.dropItem(mod_chocobo.chocoboPackBags.shiftedIndex, 1);
    		this.bagsInventory.dropAllItems();
    	}
    	super.onDeath(damageSource);
    }
}
