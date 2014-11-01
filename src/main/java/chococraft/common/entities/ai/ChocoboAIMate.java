package chococraft.common.entities.ai;

import java.util.List;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;
import chococraft.common.entities.EntityChocobo;

public class ChocoboAIMate extends EntityAIBase
{
    private EntityChocobo theChocobo;
    World theWorld;
    private EntityChocobo targetMate;
    int spawnBabyDelay;
    double moveSpeed;

    public ChocoboAIMate(EntityChocobo chocobo, double moveSpeed)
    {
        this.theChocobo = chocobo;
        this.theWorld = chocobo.worldObj;
        this.moveSpeed = moveSpeed;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
	public boolean shouldExecute()
    {
        if (!this.theChocobo.isInLove())
        {
            return false;
        }
        else
        {
            this.targetMate = this.getNearbyMate();
            return this.targetMate != null;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
	public boolean continueExecuting()
    {
        return this.targetMate.isEntityAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 3;
    }

    /**
     * Resets the task
     */
    @Override
	public void resetTask()
    {
        this.targetMate = null;
        this.spawnBabyDelay = 0;
    }

    /**
     * Updates the task
     */
    @Override
	public void updateTask()
    {
        this.theChocobo.getLookHelper().setLookPositionWithEntity(this.targetMate, 10.0F, (float)this.theChocobo.getVerticalFaceSpeed());
        this.theChocobo.getNavigator().tryMoveToEntityLiving(this.targetMate, this.moveSpeed);
        
        this.spawnBabyDelay++;

        if (this.spawnBabyDelay > 2 && this.theChocobo.getDistanceSqToEntity(this.targetMate) < 9.0D)
        {
            this.spawnBaby();
        }
    }

    /**
     * Loops through nearby animals and finds another animal of the same type that can be mated with. Returns the first
     * valid mate found.
     */
    private EntityChocobo getNearbyMate()
    {
        float f = 8.0F;
        @SuppressWarnings("rawtypes")
		List list = this.theWorld.getEntitiesWithinAABB(EntityChocobo.class, this.theChocobo.boundingBox.expand((double)f, (double)f, (double)f));
        double d0 = Double.MAX_VALUE;
        EntityChocobo chocobo = null;

		for (Object aList : list) {
			EntityChocobo chocoboIter = (EntityChocobo) aList;

			if (this.theChocobo.canMateWith(chocoboIter) && this.theChocobo.getDistanceSqToEntity(chocoboIter) < d0) {
				chocobo = chocoboIter;
				d0 = this.theChocobo.getDistanceSqToEntity(chocoboIter);
			}
		}
        return chocobo;
    }

    /**
     * Spawns a baby animal of the same type.
     */
    private void spawnBaby()
    {
    	this.theChocobo.procreate(this.targetMate);
    }
}
