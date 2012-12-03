package chococraft.common.entities.ai;

import chococraft.common.entities.EntityAnimalChocobo;

import net.minecraft.src.EntityAIBase;

public class EntityAIChocoboFollowOwner extends EntityAIBase
{
    EntityAnimalChocobo chocobo;
    float teleportDistance;

    public EntityAIChocoboFollowOwner(EntityAnimalChocobo chocobo, float teleportDistance)
    {
        this.chocobo = chocobo;
        this.teleportDistance = teleportDistance;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	if(this.chocobo.getOwner() == null)
    	{
    		return false;
    	}
    	
        if (!this.chocobo.isFollowing())
        {
            return false;
        }
        return true;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
    	if(!this.chocobo.isFollowing())
    	{
    		return false;
    	}
    	
    	if(!this.chocobo.getNavigator().noPath())
    	{
    		return false;
    	}
    	   
    	if(this.chocobo.getOwner() == null)
    	{
    		return false;
    	}
    	else
    	{
            if (!this.chocobo.getOwner().isEntityAlive())
            {
                return false;
            }
            return true;    		
    	}
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting(){}

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.chocobo.getNavigator().clearPathEntity();
        this.chocobo.getNavigator().setAvoidsWater(!this.chocobo.canCrossWater);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
    	if(!this.chocobo.getNavigator().tryMoveToEntityLiving(this.chocobo.getOwner(), this.teleportDistance))
    	{
    		this.chocobo.teleportToOwner();
    		this.chocobo.getNavigator().clearPathEntity();
    	}
    }
}
