package chococraft.common.network.serverSide;

import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.network.PacketHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 * Created by clienthax on 22/10/2014.
 */
public class ChocoboSetInLove implements IMessage {

	public int entityID;
	public boolean inLove;
	public int dimensionId;

	public ChocoboSetInLove() {}

	public ChocoboSetInLove(EntityAnimalChocobo chocobo) {
		this.entityID = chocobo.getEntityId();
		this.inLove = chocobo.isInLove();
		this.dimensionId = chocobo.worldObj.provider.dimensionId;
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(this.entityID);
		buffer.writeBoolean(this.inLove);
		buffer.writeInt(this.dimensionId);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.entityID = buffer.readInt();
		this.inLove = buffer.readBoolean();
		this.dimensionId = buffer.readInt();
	}

	public static class Handler implements IMessageHandler<ChocoboSetInLove, IMessage> {

		@Override
		public IMessage onMessage(ChocoboSetInLove message, MessageContext ctx) {
			EntityAnimalChocobo chocobo = PacketHelper.getChocoboByID(message.entityID, message.dimensionId);
			if(chocobo != null)
			{
				chocobo.setInLove(message.inLove);
			}
			return null;
		}
	}
}
