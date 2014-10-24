// <copyright file="CommonProxyChocoCraft.java">
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
// <summary>ChocoCraft Common proxy</summary>

package chococraft.common.proxy;

import chococraft.common.entities.EntityChocoboRideable;
import chococraft.common.entities.RiderActionState;
import chococraft.common.events.ChocoCraftEventCommon;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxyChocoCraft
{
    public void registerRenderThings()
    {
        
    }
    
    public void registerRenderInformation()
    {
        //No rendering for servers.
    }
    
    public int addArmor(String armor)
    {
        return 0;
    }
    
    public void registerEventListener()
    {
		MinecraftForge.EVENT_BUS.register(new ChocoCraftEventCommon());
    }
    
    public void updateRiderActionState(EntityChocoboRideable chocobo, Entity entity)
    {
    }

	public RiderActionState getRiderActionState(Entity rider)
	{
		return null;
	}
}