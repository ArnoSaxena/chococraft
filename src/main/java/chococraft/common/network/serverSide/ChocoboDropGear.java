package chococraft.common.network.serverSide;

import chococraft.common.config.ChocoCraftItems;
import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChocoboRideable;
import chococraft.common.network.PacketHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;

/**
 * Created by clienthax on 22/10/2014.
 */
public class ChocoboDropGear implements IMessage {

	public int entityID;
	public int dimensionId;

	public ChocoboDropGear() {}

	public ChocoboDropGear(EntityChocoboRideable chicobo) {
		this.entityID = chicobo.getEntityId();
		this.dimensionId = chicobo.worldObj.provider.dimensionId;
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(this.entityID);
		buffer.writeInt(this.dimensionId);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.entityID = buffer.readInt();
		this.dimensionId = buffer.readInt();
	}

	public static class Handler implements IMessageHandler<ChocoboDropGear, IMessage> {

		@Override
		public IMessage onMessage(ChocoboDropGear message, MessageContext ctx) {
			EntityAnimalChocobo chocobo = PacketHelper.getChocoboByID(message.entityID, message.dimensionId);
			if(chocobo != null && chocobo instanceof EntityChocoboRideable)
			{
				EntityChocoboRideable chocoRideable = (EntityChocoboRideable)chocobo;

				if(chocoRideable.isSaddleBagged())
				{
					chocoRideable.entityDropItem(new ItemStack(ChocoCraftItems.chocoboSaddleBagsItem, 1), 0.0F);
					chocoRideable.getChocoBagInventory().dropAllItems();
					chocoRideable.setSaddleBagged(false);
				}

				if(chocoRideable.isSaddled())
				{
					chocoRideable.entityDropItem(new ItemStack(ChocoCraftItems.chocoboSaddleItem, 1), 0.0F);
					chocoRideable.setSaddled(false);
				}

				if(chocoRideable.isPackBagged())
				{
					chocoRideable.entityDropItem(new ItemStack(ChocoCraftItems.chocoboPackBagsItem, 1), 0.0F);
					//chocoRideable.getChocoBagInventory().dropAllItems(); TODO
					chocoRideable.setPackBagged(false);
				}
			}
			return null;
		}
	}
}
