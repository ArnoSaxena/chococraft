package chococraft.common.network;

import chococraft.common.Constants;
import net.minecraft.src.Packet250CustomPayload;

public abstract class PacketChocobo
{
	public static int PID_ATTRIBUTE  = 0;
	public static int PID_DROPSADDLE = 1;
	public static int PID_HEALTH     = 2;
	public static int PID_MOUNT      = 3;
	public static int PID_RIDERJUMP  = 4;
	public static int PID_TAMED      = 5;
	
	protected Packet250CustomPayload packet = null;
	
	public PacketChocobo()
	{
		this.packet = new Packet250CustomPayload();
		this.packet.channel = Constants.PCHAN_CHOCOBO;
	}

	public Packet250CustomPayload getPacket()
	{
		return this.packet;
	}
}
