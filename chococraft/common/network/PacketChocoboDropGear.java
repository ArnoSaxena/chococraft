// <copyright file="PacketChocoboDropGear.java">
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
// <summary>Network Packet wrapper for sending drop gear request to a Chocobo entity</summary>

package chococraft.common.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.Player;
import chococraft.common.ChocoboHelper;
import chococraft.common.ModChocoCraft;
import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChocoboRideable;
import net.minecraft.src.ItemStack;

public class PacketChocoboDropGear extends PacketChocobo
{
	public PacketChocoboDropGear(EntityChocoboRideable chocobo)
	{
		super();		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(PID_DROPSADDLE);
			outputStream.writeInt(chocobo.entityId);
			outputStream.writeInt(chocobo.worldObj.getWorldInfo().getDimension());			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}		

		this.packet.data = bos.toByteArray();
		this.packet.length = bos.size();
	}

	public static void handleUpdate(DataInputStream inputStream, Player player)
	{
		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			try
			{
				int chocoboId = inputStream.readInt();
				int dimension = inputStream.readInt();
				EntityAnimalChocobo chocobo = ChocoboHelper.getChocoboByID(chocoboId, dimension);
				if(null != chocobo && chocobo instanceof EntityChocoboRideable)
				{
					EntityChocoboRideable chocoRideable = (EntityChocoboRideable)chocobo;
					
					if(chocoRideable.isSaddleBagged())
					{
						chocoRideable.entityDropItem(new ItemStack(ModChocoCraft.chocoboSaddleBagsItem, 1), 0.0F);
						chocoRideable.getChocoBagInventory().dropAllItems();
						chocoRideable.setSaddleBagged(false);
					}

					if(chocoRideable.isSaddled())
					{
						chocoRideable.entityDropItem(new ItemStack(ModChocoCraft.chocoboSaddleItem, 1), 0.0F);
						chocoRideable.setSaddled(false);
					}

					if(chocoRideable.isPackBagged())
					{
						chocoRideable.entityDropItem(new ItemStack(ModChocoCraft.chocoboPackBagsItem, 1), 0.0F);
						//chocoRideable.getChocoBagInventory().dropAllItems(); TODO
						chocoRideable.setPackBagged(false);
					}	
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return;
			}
		}
	}
}
