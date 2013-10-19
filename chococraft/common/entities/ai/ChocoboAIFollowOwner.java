package chococraft.common.entities.ai;

import net.minecraft.entity.ai.EntityAIBase;
import chococraft.common.entities.EntityAnimalChocobo;

public class ChocoboAIFollowOwner extends EntityAIBase
{
	private EntityAnimalChocobo chocobo;
	private float teleportDistance;
	private float moveSpeed;
    private int followDelay;
    private boolean chocoboAvoidsWater;
	private float originalStepHight;


	public ChocoboAIFollowOwner(EntityAnimalChocobo chocobo, float moveSpeed, float teleportDistance)
	{
        this.setMutexBits(1);
		this.chocobo = chocobo;
		this.teleportDistance = teleportDistance;
		this.moveSpeed = moveSpeed;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		if(this.chocobo.getOwner() == null || !this.chocobo.getOwner().isEntityAlive())
		{
			return false;
		}
		else if(!this.chocobo.isFollowing())
		{
			return false;
		} 
		else if(!this.chocobo.getNavigator().noPath())
		{
			return false;
		}
		else if (this.chocobo.getDistanceSqToEntity(this.chocobo.getOwner()) > (this.teleportDistance * this.teleportDistance))
		{
			return false;
		}		
		return true;  
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting()
	{
		return this.shouldExecute();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */	
	@Override
	public void startExecuting()
	{
		this.followDelay = 0;
		this.chocoboAvoidsWater = this.chocobo.getNavigator().getAvoidsWater();
		this.chocobo.getNavigator().setAvoidsWater(false);
		this.originalStepHight = this.chocobo.stepHeight;
		if(this.chocobo.stepHeight < 1.0F)
		{
			this.chocobo.stepHeight = 1.0F;
		}
		
		if(this.chocobo.isInWater())
		{
			this.chocobo.motionY = 0.1D;
			this.chocobo.setJumping(true);
		}

	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask()
	{
		this.chocobo.getNavigator().clearPathEntity();
		this.chocobo.getNavigator().setAvoidsWater(this.chocoboAvoidsWater);
		this.chocobo.stepHeight = this.originalStepHight;
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask()
	{
		if(--this.followDelay <= 0)
		{
			this.followDelay = 10;

			boolean followCheck = this.chocobo.getNavigator().tryMoveToEntityLiving(this.chocobo.getOwner(), this.moveSpeed);			
			if(!followCheck)
			{
				if(this.chocobo.getDistanceSqToEntity(this.chocobo.getOwner()) > (this.teleportDistance * this.teleportDistance))
				{
					this.chocobo.teleportToOwner();
					this.chocobo.getNavigator().clearPathEntity();
				}
			}
		}
	}
}
