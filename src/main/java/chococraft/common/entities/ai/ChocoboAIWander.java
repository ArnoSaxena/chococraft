package chococraft.common.entities.ai;

import chococraft.common.entities.EntityAnimalChocobo;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class ChocoboAIWander extends EntityAIBase
{
    private EntityAnimalChocobo chocobo;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private float speed;

    public ChocoboAIWander(EntityAnimalChocobo chocobo, float speed)
    {
        this.chocobo = chocobo;
        this.speed = speed;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
	public boolean shouldExecute()
    {
        if (this.chocobo.getAge() >= 100)
        {
            return false;
        }
        else if(!this.chocobo.isWander())
        {
        	return false;
        }
        else if(this.chocobo.getRNG().nextInt(120) != 0)
        {
            return false;
        }
        else
        {
            Vec3 vector = RandomPositionGenerator.findRandomTarget(this.chocobo, 10, 7);

            if (vector == null)
            {
                return false;
            }
            else
            {
                this.xPosition = vector.xCoord;
                this.yPosition = vector.yCoord;
                this.zPosition = vector.zCoord;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
	public boolean continueExecuting()
    {
        return !this.chocobo.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
	public void startExecuting()
    {
        this.chocobo.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);        
    }
}
