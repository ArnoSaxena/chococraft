// <copyright file="EntityPurpleChocoboEgg.java">
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
// <date>2012-12-04</date>
// <summary>Entity class for nether Chocobo eggs</summary>

package chococraft.common.entities;

import chococraft.common.entities.EntityAnimalChocobo.chocoboColor;
import chococraft.common.network.clientSide.PacketChocoboParticles;
import chococraft.debugger.DebugFileWriter;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityThrowable;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;

public class EntityPurpleChocoboEgg extends EntityThrowable
{
	public EntityPurpleChocoboEgg(World world)
	{
		super(world);
	}

	public EntityPurpleChocoboEgg(World world, EntityLiving entityliving)
	{
		super(world, entityliving);
	}

	public EntityPurpleChocoboEgg(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}

	@Override
	protected void onImpact(MovingObjectPosition mObjPos)
	{
		DebugFileWriter.instance().writeLine("EPChEg", "onImpact");
		
		if(Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			if (mObjPos.entityHit != null)
			{
				mObjPos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_85052_h()), 0);
			}
			
			EntityChicobo babyChicobo = FactoryEntityChocobo.createNewChicobo(worldObj, chocoboColor.PURPLE);
			if (babyChicobo != null)
			{
				babyChicobo.setTimeUntilAdult(rand.nextInt(2000) + 27000);
				babyChicobo.setGrowingAge(babyChicobo.getTimeUntilAdult());
				babyChicobo.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
				this.worldObj.spawnEntityInWorld(babyChicobo);
				for (int i = 0; i < 8; i++)
				{
					this.sendParticleUpdate("snowballpoof", babyChicobo);
				}
			}
			this.setDead();
		}
	}

	protected void sendParticleUpdate(String particleName, EntityAnimalChocobo chocobo)
	{
		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			PacketChocoboParticles packet = new PacketChocoboParticles(chocobo, particleName);
			int dimension = chocobo.worldObj.provider.dimensionId;
			PacketDispatcher.sendPacketToAllAround(chocobo.lastTickPosX, chocobo.lastTickPosY, chocobo.lastTickPosZ, 16*5, dimension, packet.getPacket());
		}		
	}
}
