package chococraft.common.network.clientSide;

import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.network.PacketHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 * Created by clienthax on 22/10/2014.
 */
public class ChocoboHunger implements IMessage {

	public int entityID;
	public int hungerTime;
	public int dimensionId;

	public ChocoboHunger() {}

	public ChocoboHunger(EntityAnimalChocobo chocobo) {
		this.entityID = chocobo.getEntityId();
		this.hungerTime = chocobo.getTimeUntilHunger();
		this.dimensionId = chocobo.worldObj.provider.dimensionId;
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(this.entityID);
		buffer.writeInt(this.hungerTime);
		buffer.writeInt(this.dimensionId);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.entityID = buffer.readInt();
		this.hungerTime = buffer.readInt();
		this.dimensionId = buffer.readInt();
	}

	public static class Handler implements IMessageHandler<ChocoboHunger, IMessage> {

		@Override
		public IMessage onMessage(ChocoboHunger message, MessageContext ctx) {
			EntityAnimalChocobo chocobo = PacketHelper.getChocoboByID(message.entityID, message.dimensionId);
			if(chocobo != null)
			{
				chocobo.setTimeUntilHunger(message.hungerTime);
			}
			return null;
		}
	}
}
