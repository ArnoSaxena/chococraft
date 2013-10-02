package chococraft.common.entities;

public class RiderActionState
{
    private float moveStrafe;
    private float moveForward;
    private boolean jump;
    private boolean sneak;
    private boolean changed;
    
    public RiderActionState()
    {
    	this.moveStrafe = 0.0F;
    	this.moveForward = 0.0F;
    	this.jump = false;
    	this.sneak = false;
    	this.changed = true;
    }
    
    public void setMoveStrafe(float moveStrafe)
    {
    	if(moveStrafe != this.moveStrafe)
    	{
    		this.changed = true;
    		this.moveStrafe = moveStrafe;
    	}
    }
    
    public float getMoveStrafe()
    {
    	return this.moveStrafe;
    }
    
    public void setMoveForward(float moveForward)
    {
    	if(moveForward != this.moveForward)
    	{
    		this.changed = true;
    		this.moveForward = moveForward;
    	}
    }
    
    public float getMoveForward()
    {
    	return this.moveForward;
    }
    
    public void setJump(boolean jump)
    {
    	if(jump != this.jump)
    	{
    		this.changed = true;
    		this.jump = jump;
    	}
    }
    
    public boolean isJump()
    {
    	return this.jump;
    }
    
    public void setSneak(boolean sneak)
    {
    	if(sneak != this.sneak)
    	{
    		this.changed = true;
    		this.sneak = sneak;
    	}
    }
    
    public boolean isSneak()
    {
    	return this.sneak;
    }
    
    public void resetChanged()
    {
    	this.changed = false;
    }
    
    public boolean isChanged()
    {
    	return this.changed;
    }
    
}