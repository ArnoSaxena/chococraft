package chococraft.common.network.serverSide;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/**
 * Created by clienthax on 22/10/2014.
 */
public class ChocoboSpawnItem implements IMessage {//TODO- heh this doesn't seem exploitable right -.-?

	public int dimensionId;
	public ItemStack itemStack;
	public int posX;
	public int posY;
	public int posZ;

	public ChocoboSpawnItem() {}

	public ChocoboSpawnItem(World world, ItemStack stack, double posX, double posY, double posZ) {
		this.dimensionId = world.provider.dimensionId;
		this.itemStack = stack;
		this.posX = (int)posX;
		this.posY = (int)posY;
		this.posZ = (int)posZ;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.dimensionId);
		ByteBufUtils.writeItemStack(buf, itemStack);
		buf.writeInt(posX);
		buf.writeInt(posY);
		buf.writeInt(posZ);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.dimensionId = buf.readInt();
		this.itemStack = ByteBufUtils.readItemStack(buf);
		this.posX = buf.readInt();
		this.posY = buf.readInt();
		this.posZ = buf.readInt();
	}

	public static class Handler implements IMessageHandler<ChocoboSpawnItem, IMessage> {

		@Override
		public IMessage onMessage(ChocoboSpawnItem message, MessageContext ctx) {

			WorldServer world = MinecraftServer.getServer().worldServerForDimension(message.dimensionId);
			EntityItem entityItem = new EntityItem(world, message.posX, message.posY, message.posZ, message.itemStack);
			world.spawnEntityInWorld(entityItem);

			return null;
		}
	}
}
