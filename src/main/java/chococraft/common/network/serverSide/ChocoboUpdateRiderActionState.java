package chococraft.common.network.serverSide;

import chococraft.common.ModChocoCraft;
import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChocoboRideable;
import chococraft.common.entities.RiderActionState;
import chococraft.common.network.PacketHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;

/**
 * Created by clienthax on 22/10/2014.
 */
public class ChocoboUpdateRiderActionState implements IMessage {

	public int entityID;
	public int dimensionID;
	public float moveStrafe = 0.0f;
	public float moveForward = 0.0f;
	public boolean jump = false;
	public boolean sneak = false;

	public ChocoboUpdateRiderActionState() {}

	public ChocoboUpdateRiderActionState(EntityAnimalChocobo chocobo, Entity rider) {
		this.entityID = chocobo.getEntityId();
		this.dimensionID = chocobo.worldObj.provider.dimensionId;

		RiderActionState ras = ModChocoCraft.proxy.getRiderActionState(rider);
		if(null != ras)
		{
			this.moveStrafe = ras.getMoveStrafe();
			this.moveForward = ras.getMoveForward();
			this.jump = ras.isJump();
			this.sneak = ras.isSneak();
		}
	}


	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.entityID);
		buf.writeInt(this.dimensionID);
		buf.writeFloat(this.moveStrafe);
		buf.writeFloat(this.moveForward);
		buf.writeBoolean(this.jump);
		buf.writeBoolean(this.sneak);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityID = buf.readInt();
		this.dimensionID = buf.readInt();
		this.moveStrafe = buf.readFloat();
		this.moveForward = buf.readFloat();
		this.jump = buf.readBoolean();
		this.sneak = buf.readBoolean();
	}

	public static class Handler implements IMessageHandler<ChocoboUpdateRiderActionState, IMessage> {
		@Override
		public IMessage onMessage(ChocoboUpdateRiderActionState message, MessageContext ctx) {
			EntityAnimalChocobo chocobo = PacketHelper.getChocoboByID(message.entityID, message.dimensionID);
			if(null != chocobo && chocobo instanceof EntityChocoboRideable)
			{
				RiderActionState ras = new RiderActionState();
				ras.setMoveStrafe(message.moveStrafe);
				ras.setMoveForward(message.moveForward);
				ras.setJump(message.jump);
				ras.setSneak(message.sneak);
				((EntityChocoboRideable)chocobo).setRiderActionState(ras);
			}

			return null;
		}
	}
}
