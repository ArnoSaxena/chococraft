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

package net.minecraft.src;

public class EntityChicobo extends EntityAnimalChoco
{
    public int color;
    public boolean growUp;
    public boolean canGrowUp;
    //public boolean follow;
    public String name;
    public boolean hidename;
    public int timeUntilAdult;

    public EntityChicobo(World world)
    {
        super(world);
        name = "";
        this.setTamed(false);
        this.setFollowing(false);
        hidename = false;
        setSize(0.5F, 0.5F);
        growUp = false;
        canGrowUp = true;
        timeUntilAdult = rand.nextInt(2000) + 27000;
        setDelay(timeUntilAdult);
    }
    
    public void setColor(int color)
    {
    	if(COLOR_PURPLE <= color && COLOR_RED >= color)
    	{
    		this.color = color;
    	}
    	else
    	{
    		this.color = 0;
    	}
        if (color == COLOR_PURPLE)
        {
            isImmuneToFire = true;
        }
        this.texture = this.getEntityTexture();
        this.setEntityHealth(getMaxHealth());
    }

    public String getEntityTexture()
    {
        String s = new String("/choco/Chicobos/");
        if (this.isTamed())
        {
            s = (new StringBuilder()).append(s).append("Tamed/").toString();
        }
        if (color == COLOR_YELLOW)
        {
            s = (new StringBuilder()).append(s).append("chocobo.png").toString();
        }
        else if (color == COLOR_GREEN)
        {
            s = (new StringBuilder()).append(s).append("greenchocobo.png").toString();
        }
        else if (color == COLOR_BLUE)
        {
            s = (new StringBuilder()).append(s).append("bluechocobo.png").toString();
        }
        else if (color == COLOR_WHITE)
        {
            s = (new StringBuilder()).append(s).append("whitechocobo.png").toString();
        }
        else if (color == COLOR_BLACK)
        {
            s = (new StringBuilder()).append(s).append("blackchocobo.png").toString();
        }
        else if (color == COLOR_GOLD)
        {
            s = (new StringBuilder()).append(s).append("goldchocobo.png").toString();
        }
        else if (color == COLOR_PINK)
        {
            s = (new StringBuilder()).append(s).append("pinkchocobo.png").toString();
        }
        else if (color == COLOR_RED)
        {
            s = (new StringBuilder()).append(s).append("redchocobo.png").toString();
        }
        else if (color == COLOR_PURPLE)
        {
            s = (new StringBuilder()).append(s).append("purplechocobo.png").toString();
        }
        return s;
    }

    public int getMaxHealth()
    {
        if (color > COLOR_WHITE)
        {
            return 20;
        }
        return color <= COLOR_BLUE ? 10 : 15;
    }

    public boolean isChild()
    {
        return true;
    }

    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!growUp)
        {
            timeUntilAdult = getDelay();
            timeUntilAdult--;
            setDelay(timeUntilAdult);
            if (timeUntilAdult <= 0)
            {
                growUp = true;
            }
        }
        if (!worldObj.isRemote && growUp && canGrowUp)
        {
            lastTickPosX = posX;
            lastTickPosY = posY;
            lastTickPosZ = posZ;
            if (color != COLOR_PURPLE)
            {
                EntityChocobo entitychocobo = new EntityChocobo(worldObj, name, hidename, this.isTamed(), this.isFollowing());
                entitychocobo.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
                entitychocobo.setColor(this.color);
                entitychocobo.setDelay(6000);
                worldObj.spawnEntityInWorld(entitychocobo);
            }
            else
            {
                EntityNetherChocobo entitynetherchocobo = new EntityNetherChocobo(worldObj, name, hidename, this.isTamed(), this.isFollowing());
                entitynetherchocobo.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
                entitynetherchocobo.health = entitynetherchocobo.getMaxHealth();
                entitynetherchocobo.texture = entitynetherchocobo.getEntityTexture();
                entitynetherchocobo.setDelay(6000);
                worldObj.spawnEntityInWorld(entitynetherchocobo);
            }
            for (int i = 0; i < 20; i++)
            {
                double d = rand.nextGaussian() * 0.02D;
                double d1 = rand.nextGaussian() * 0.02D;
                double d2 = rand.nextGaussian() * 0.02D;
                worldObj.spawnParticle("explode", (posX + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, posY + (double)(rand.nextFloat() * height), (posZ + (double)(rand.nextFloat() * width * 2.0F)) - (double)width, d, d1, d2);
            }

            health = 0;
            setDead();
            growUp = false;
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
        nbttagcompound.setInteger("TimeToAdult", timeUntilAdult);
        nbttagcompound.setInteger("Color", color);
        nbttagcompound.setBoolean("Tamed", this.isTamed());
        nbttagcompound.setBoolean("Follow", this.isFollowing());
        nbttagcompound.setBoolean("CanGrow", canGrowUp);
        nbttagcompound.setString("Name", name);
        nbttagcompound.setBoolean("HideName", hidename);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        timeUntilAdult = nbttagcompound.getInteger("TimeToAdult");
        this.setTamed(nbttagcompound.getBoolean("Tamed"));
        color = nbttagcompound.getInteger("Color");
        texture = getEntityTexture();
        this.setFollowing(nbttagcompound.getBoolean("Follow"));
        canGrowUp = nbttagcompound.getBoolean("CanGrow");
        name = nbttagcompound.getString("Name");
        hidename = nbttagcompound.getBoolean("HideName");
        wander = !this.isFollowing();
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
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (itemstack != null && itemstack.itemID == mod_chocobo.gysahlGreen.blockID)
        {
            if (!this.isTamed())
            {
                itemstack.stackSize--;
                if (itemstack.stackSize <= 0)
                {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                }
                if (!worldObj.isRemote)
                {
                    if (rand.nextInt(3) == 0)
                    {
                    	this.setTamed(true);
                    	this.setFollowing(true);
                        showHeartsOrSmokeFX(true);
                        worldObj.setEntityState(this, (byte)7);
                        this.setRandomName();
                        hidename = true;
                        this.texture = this.getEntityTexture();
                    }
                    else
                    {
                        showHeartsOrSmokeFX(false);
                        worldObj.setEntityState(this, (byte)6);
                    }
                }
                return true;
            }
            if (health < getMaxHealth())
            {
                itemstack.stackSize--;
                if (itemstack.stackSize <= 0)
                {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                }
                heal(10);
                return true;
            }
        }
        else
        {
            if (itemstack != null && itemstack.itemID == mod_chocobo.chocoboWhistle.shiftedIndex)
            {
                if (this.isTamed())
                {
                	this.setTamed(false);
                	this.setFollowing(false);
                    riddenByEntity = null;
                    showHeartsOrSmokeFX(false);
                    hidename = true;
                    this.texture = this.getEntityTexture();
                }
                return true;
            }
            if (itemstack != null && itemstack.itemID == mod_chocobo.chocoboCake.shiftedIndex)
            {
                if (this.isTamed())
                {
                    itemstack.stackSize--;
                    if (itemstack.stackSize <= 0)
                    {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                    }
                    canGrowUp = true;
                    growUp = true;
                    entityplayer.addStat(mod_chocobo.cakeChico, 1);
                }
                else
                {
                    showHeartsOrSmokeFX(false);
                }
                return true;
            }
            if (itemstack != null && itemstack.itemID == mod_chocobo.chicoboGreen.shiftedIndex)
            {
                if (this.isTamed())
                {
                    itemstack.stackSize--;
                    if (itemstack.stackSize <= 0)
                    {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                    }
                    canGrowUp = false;
                    entityplayer.addStat(mod_chocobo.chibiChico, 1);
                }
                else
                {
                    showHeartsOrSmokeFX(false);
                }
                return true;
            }
            if (itemstack != null && itemstack.itemID == mod_chocobo.chocopedia.shiftedIndex)
            {
                onChocopediaUse();
            }
            else if (this.isTamed())
            {
                toggleFollow();
                return true;
            }
            else
            {
                return super.interact(entityplayer);
            }
        }
        return true;
    }

    public void toggleFollow()
    {
    	this.setFollowing(!this.isFollowing());
        wander = !this.isFollowing();
        showHeartsOrSmokeFX(this.isFollowing());
    }

    private void onChocopediaUse()
    {
        if (this.isTamed())
        {
            mod_chocobo.mc.displayGuiScreen(new GuiChocopedia(mod_chocobo.mc.currentScreen, this));
        }
        else
        {
            showHeartsOrSmokeFX(false);
        }
    }

    protected int getDropItemId()
    {
        return -1;
    }

    public void updateEntityActionState()
    {
        if (!worldObj.playerEntities.isEmpty())
        {
            EntityPlayer entityplayer = (EntityPlayer)worldObj.playerEntities.get(0);
            if (!hasPath() && this.isTamed() && this.isFollowing())
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
            else
            {
                super.updateEntityActionState();
                return;
            }
        }
    }

    private void getPathOrWalkableBlock(Entity entity, float f)
    {
        PathEntity pathentity = worldObj.getPathEntityToEntity(this, entity, 16F, false, false, false, false);
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
        super.fall(f);
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

    protected EntityChicobo spawnBabyAnimal(EntityAnimalChoco entityanimalchoco)
    {
        return new EntityChicobo(worldObj);
    }

    protected boolean canDespawn()
    {
        return false;
    }

    public void setRandomName()
    {
        name = mod_chocobo.getRandomName();
    }

    public void setName(String s)
    {
        name = s;
    }

    public boolean canRenderName()
    {
        return mod_chocobo.show_chocobo_names && !hidename && name != "" && this.isTamed();
    }

    public boolean isBurning()
    {
        if (color == -1)
        {
            return false;
        }
        else
        {
            return super.isBurning();
        }
    }
    
    public void func_48122_d(int par1)
    {
        dataWatcher.updateObject(12, Integer.valueOf(par1));
    }
}
