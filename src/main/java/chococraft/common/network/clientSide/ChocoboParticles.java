package chococraft.common.network.clientSide;

import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.helper.ChocoboParticleHelper;
import chococraft.common.network.PacketHelper;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 * Created by clienthax on 22/10/2014.
 */
public class ChocoboParticles implements IMessage {

	public int entityID;
	public String particleName;
	public int particleAmount;
	public int dimensionId;

	public ChocoboParticles() {}

	public ChocoboParticles(EntityAnimalChocobo chocobo, String particleName, int particleAmount) {
		this.entityID = chocobo.getEntityId();
		this.particleName = particleName;
		this.particleAmount = particleAmount;
		this.dimensionId = chocobo.worldObj.provider.dimensionId;
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(this.entityID);
		ByteBufUtils.writeUTF8String(buffer, particleName);
		buffer.writeInt(particleAmount);
		buffer.writeInt(this.dimensionId);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.entityID = buffer.readInt();
		this.particleName = ByteBufUtils.readUTF8String(buffer);
		this.particleAmount = buffer.readInt();
		this.dimensionId = buffer.readInt();
	}

	public static class Handler implements IMessageHandler<ChocoboParticles, IMessage> {

		@Override
		public IMessage onMessage(ChocoboParticles message, MessageContext ctx) {
			EntityAnimalChocobo chocobo = PacketHelper.getChocoboByID(message.entityID, message.dimensionId);
			if(chocobo != null)
			{
				ChocoboParticleHelper.showParticleAroundEntityFx(message.particleName, chocobo, message.particleAmount);
			}
			return null;
		}
	}
}
