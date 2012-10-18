package chococraft.common.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.network.Player;
import chococraft.common.ChocoboHelper;
import chococraft.common.Constants;
import chococraft.common.ModChocoCraft;
import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChocoboRideable;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet250CustomPayload;

public class PacketChocoboDropSaddleAndBags extends Packet250CustomPayload
{
	public PacketChocoboDropSaddleAndBags(EntityChocoboRideable chocobo)
	{
		this.channel = Constants.PCHAN_CHOCOBO;

		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(chocobo.entityId);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}		

		this.data = bos.toByteArray();
		this.length = bos.size();
	}

	public static void handleUpdate(Packet250CustomPayload packet, Player player)
	{
		if (Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			try
			{
				int chocoboId = inputStream.readInt();
				EntityAnimalChocobo chocobo = ChocoboHelper.getChocoboByID(chocoboId, player);
				if(null != chocobo && chocobo instanceof EntityChocoboRideable)
				{
					EntityChocoboRideable chocoRideable = (EntityChocoboRideable)chocobo;
					
					if(chocoRideable.isSaddleBagged())
					{
						chocoRideable.entityDropItem(new ItemStack(ModChocoCraft.chocoboSaddleBagsItem, 1), 0.0F);
						//chocoRideable.bagsInventory.dropAllItems();
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
						//chocoRideable.bagsInventory.dropAllItems();
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
