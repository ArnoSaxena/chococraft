package chococraft.common.entities.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class ChocoboAIWatchClosest extends EntityAIBase
{
    private EntityLiving theWatcher;

    /** The closest entity which is being watched by this one. */
    protected Entity closestEntity;
    private float watchDistance;
    private int lookTime;
    private Class<?> watchedClass;

    public ChocoboAIWatchClosest(EntityLiving chocobo, Class<?> entityClass, float distance)
    {
        this.theWatcher = chocobo;
        this.watchedClass = entityClass;
        this.watchDistance = distance;
        this.setMutexBits(2);
    }


    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
	public boolean shouldExecute()
    {
    	if (this.theWatcher.getRNG().nextFloat() >= 0.5F)
    	{
    		return false;
    	}
    	else
    	{    	
    		if (this.theWatcher.getAttackTarget() != null)
    		{
    			this.closestEntity = this.theWatcher.getAttackTarget();
    		}

    		if (this.watchedClass == EntityPlayer.class)
    		{
    			this.closestEntity = this.theWatcher.worldObj.getClosestPlayerToEntity(this.theWatcher, (double)this.watchDistance);
    		}
    		else
    		{
    			this.closestEntity = this.theWatcher.worldObj.findNearestEntityWithinAABB(this.watchedClass, this.theWatcher.boundingBox.expand((double)this.watchDistance, 3.0D, (double)this.watchDistance), this.theWatcher);
    		}

    		return this.closestEntity != null;
    	}
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
	public boolean continueExecuting()
    {
        return this.closestEntity.isEntityAlive() && (this.theWatcher.getDistanceSqToEntity(this.closestEntity) <= (double) (this.watchDistance * this.watchDistance) && this.lookTime > 0);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
	public void startExecuting()
    {
        this.lookTime = 40 + this.theWatcher.getRNG().nextInt(40);
    }

    /**
     * Resets the task
     */
    @Override
	public void resetTask()
    {
        this.closestEntity = null;
    }

    /**
     * Updates the task
     */
    @Override
	public void updateTask()
    {
        this.theWatcher.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + (double)this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 10.0F, (float)this.theWatcher.getVerticalFaceSpeed());
        --this.lookTime;
    }
}
