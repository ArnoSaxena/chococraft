// <copyright file="EntityNetherChocoboEgg.java">
// Copyright (c) 2012 All Right Reserved, http://chococraft.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <author>EddieV (initial source)</author>
// <email>al-s@gmx.de</email>
// <date>2012-05-09</date>
// <summary>Entity class for nether Chocobo eggs</summary>

package net.minecraft.src;

public class EntityNetherChocoEgg extends EntityThrowable
{
    public EntityNetherChocoEgg(World world)
    {
        super(world);
    }

    public EntityNetherChocoEgg(World world, EntityLiving entityliving)
    {
        super(world, entityliving);
    }

    public EntityNetherChocoEgg(World world, double d, double d1, double d2)
    {
        super(world, d, d1, d2);
    }

    protected void onThrowableCollision(MovingObjectPosition movingobjectposition)
    {
        if (movingobjectposition.entityHit != null)
        {
            if (movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), 0));
        }
        if (!worldObj.isRemote)
        {
            byte byte0 = 1;
            if (rand.nextInt(32) == 0)
            {
                byte0 = 4;
            }
            for (int j = 0; j < byte0; j++)
            {
                EntityChicobo entitychicobo = new EntityChicobo(worldObj);
                entitychicobo.func_48122_d(-24000);
                entitychicobo.timeUntilAdult = rand.nextInt(2000) + 27000;
                entitychicobo.setDelay(entitychicobo.timeUntilAdult);
                entitychicobo.growUp = false;
                entitychicobo.setColor(-1);
                entitychicobo.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
                worldObj.spawnEntityInWorld(entitychicobo);
            }
        }
        for (int i = 0; i < 8; i++)
        {
            worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!worldObj.isRemote)
        {
            setDead();
        }
    }

	@Override
	protected void onImpact(MovingObjectPosition movingobjectposition) {
		onThrowableCollision(movingobjectposition);
	}
}
