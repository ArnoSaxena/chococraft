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

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import chococraft.common.ModChocoCraft;
import chococraft.common.entities.EntityAnimalChocobo.chocoboColor;
import chococraft.common.network.clientSide.PacketChocoboParticles;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

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
		if(Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			if (mObjPos.entityHit != null)
			{
				mObjPos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0);
			}
			
			EntityChicobo babyChicobo = FactoryEntityChocobo.createNewChicobo(worldObj, chocoboColor.PURPLE);
			if (babyChicobo != null)
			{
				babyChicobo.setTimeUntilAdult(rand.nextInt(ModChocoCraft.growupDelayRandom) + ModChocoCraft.growupDelayStatic);
				babyChicobo.setGrowingAge(babyChicobo.getTimeUntilAdult());
				babyChicobo.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
				this.worldObj.spawnEntityInWorld(babyChicobo);
				for (int i = 0; i < 8; i++)
				{
					this.sendParticleUpdate("snowballpoof", babyChicobo, 7);
				}
			}
			this.setDead();
		}
	}

	protected void sendParticleUpdate(String particleName, EntityAnimalChocobo chocobo, int amount)
	{
		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			PacketChocoboParticles packet = new PacketChocoboParticles(chocobo, particleName, amount);
			int dimension = chocobo.worldObj.provider.dimensionId;
			PacketDispatcher.sendPacketToAllAround(chocobo.lastTickPosX, chocobo.lastTickPosY, chocobo.lastTickPosZ, 16*5, dimension, packet.getPacket());
		}		
	}
}
