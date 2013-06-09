package chococraft.common.network.serverSide;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class PacketChocoboSpawnItem extends PacketChocoboServer
{
	public PacketChocoboSpawnItem(World world, ItemStack stack, double posX, double posY, double posZ)
	{
		super();		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try
		{
			outputStream.writeInt(PID_SPAWN_ITEM);
			outputStream.writeInt(world.provider.dimensionId);			
			outputStream.writeInt(stack.itemID);
			outputStream.writeInt(stack.stackSize);
			outputStream.writeInt(stack.getItemDamage());
			outputStream.writeDouble(posX);
			outputStream.writeDouble(posY);
			outputStream.writeDouble(posZ);
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
				int dimensionId = inputStream.readInt();
				int itemId = inputStream.readInt();
				int stackSize = inputStream.readInt();
				int damage = inputStream.readInt();
				double posX = inputStream.readDouble();
				double posY = inputStream.readDouble();
				double posZ = inputStream.readDouble();
				
				WorldServer world = MinecraftServer.getServer().worldServerForDimension(dimensionId);				
				ItemStack itemStack = new ItemStack(itemId, stackSize, damage);
				EntityItem entityItem = new EntityItem(world, posX, posY, posZ, itemStack);				
				world.spawnEntityInWorld(entityItem);
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return;
			}
		}
	}
}
