package chococraft.common.entities;

public class RiderActionState
{
    public float moveStrafe;
    public float moveForward;
    public boolean jump;
    public boolean sneak;
    
    public RiderActionState()
    {
    	this.moveStrafe = 0.0F;
    	this.moveForward = 0.0F;
    	this.jump = false;
    	this.sneak = false;
    }
}