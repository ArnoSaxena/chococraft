// <copyright file="PacketChocobo.java">
// Copyright (c) 2012 All Right Reserved, http://chococraft.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <email>al-s@gmx.de</email>
// <date>2012-10-25</date>
// <summary>abstract class for all ChocoCraft wrapper network packets</summary>

package chococraft.common.network;

import net.minecraft.network.packet.Packet250CustomPayload;
import chococraft.common.Constants;

public abstract class PacketChocobo
{
	public static int PID_ATTRIBUTE    =  0;
	public static int PID_DROPSADDLE   =  1;
	public static int PID_HEALTH       =  2;
	public static int PID_MOUNT        =  3;
	public static int PID_RIDERJUMP    =  4;
	public static int PID_TAMED        =  5;
	public static int PID_CHIGROWUP    =  6;
	public static int PID_PARTICLES    =  7;
	public static int PID_SETINLOVE    =  8;
	public static int PID_CHOWN        =  9;
	public static int PID_HUNGER       = 10;
	public static int PID_SETUP        = 11;
	public static int PID_SPAWN_ITEM   = 12;
	public static int PID_RIDER_ACT_ST = 13;
	
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
