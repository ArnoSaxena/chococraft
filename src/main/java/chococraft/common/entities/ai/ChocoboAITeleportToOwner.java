package chococraft.common.entities.ai;

import net.minecraft.entity.ai.EntityAIBase;
import chococraft.common.entities.EntityAnimalChocobo;

public class ChocoboAITeleportToOwner extends EntityAIBase
{
	private EntityAnimalChocobo chocobo;
	private float teleportDistance;


	public ChocoboAITeleportToOwner(EntityAnimalChocobo chocobo, float teleportDistance)
	{
        this.setMutexBits(1);
		this.chocobo = chocobo;
		this.teleportDistance = teleportDistance;
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
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask()
	{
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask()
	{
		if(this.chocobo.getDistanceSqToEntity(this.chocobo.getOwner()) > (this.teleportDistance * this.teleportDistance))
		{
			this.chocobo.teleportToOwner();
			this.chocobo.getNavigator().clearPathEntity();
		}
	}
}