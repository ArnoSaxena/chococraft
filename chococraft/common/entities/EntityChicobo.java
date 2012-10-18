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

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import chococraft.common.*;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.PathEntity;
import net.minecraft.src.World;

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
        this.setGrowingAge(this.getTimeUntilAdult());
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
    
    @SideOnly(Side.CLIENT)
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
    		}
    	}
    	if (this.growUp && this.isCanGrowUp())
    	{
    		if(Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
    		{
    			this.lastTickPosX = this.posX;
    			this.lastTickPosY = this.posY;
    			this.lastTickPosZ = this.posZ;
    			EntityChocobo entitychocobo = FactoryEntityChocobo.createChocobo(this.worldObj, this.color, this.getName(), this.getOwnerName(), this.isHidename(), this.isTamed(), this.isFollowing(), this.isMale());
    			entitychocobo.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
    			entitychocobo.setGrowingAge(6000);
    			this.worldObj.spawnEntityInWorld(entitychocobo);
    			for (int i = 0; i < 20; i++)
    			{
    				ChocoboHelper.showParticleAroundEntityFx("explode", this);
    			}
    			this.setEntityHealth(0);
    			setDead();
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
    			if (itemstack.itemID == ModChocoCraft.chocoboCakeItem.shiftedIndex)
    			{
    				this.onGysahlCakeUse(entityplayer);
    				interacted = true;
    			} 
    			else if (itemstack.itemID == ModChocoCraft.gysahlChibiItem.shiftedIndex)
    			{
    				this.onGysahlChibiUse(entityplayer);
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
    		}
    		else
    		{
    			this.showAmountHeartsOrSmokeFx(false, 7);
    		}
    	}
    }

    protected void onGysahlCakeUse(EntityPlayer entityplayer)
    {
    	if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
    	{
    		if (this.isTamed())
    		{
    			this.useItem(entityplayer);
    			this.setCanGrowUp(true);
    			this.growUp = true;
    		}
    		else
    		{
    			this.showAmountHeartsOrSmokeFx(false, 7);
    		}
    	}
    }

    public void toggleFollow()
    {
    	this.setFollowing(!this.isFollowing());
    	this.setWander(!this.isFollowing());
    	this.showAmountHeartsOrSmokeFx(this.isFollowing(), 7);
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
                    	this.setWander(false);
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

    protected boolean canDespawn()
    {
        return false;
    }

    public boolean canRenderName()
    {
        return super.canRenderName() && this.getName() != "";
    }

	@Override
	public EntityAnimal spawnBabyAnimal(EntityAnimal dummy) {
		return null;
	}

	@Override
	public void setStepHeight(boolean mounted)
	{
		this.stepHeight = 0.5F;
	}
	
	public void setLandMovementFactor(boolean mounted)
	{
		this.landMovementFactor = 0.1F;			
	}
}
