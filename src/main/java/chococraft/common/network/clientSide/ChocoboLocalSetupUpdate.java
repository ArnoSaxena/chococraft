package chococraft.common.network.clientSide;

import chococraft.common.ChocoboConfig;
import chococraft.common.ModChocoCraft;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 * Created by clienthax on 22/10/2014.
 */
public class ChocoboLocalSetupUpdate implements IMessage {//TODO - is this even needed..

	public boolean hungerEnabled;

	public ChocoboLocalSetupUpdate() {
		this.hungerEnabled = ModChocoCraft.hungerEnabled;
	}


	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeBoolean(this.hungerEnabled);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.hungerEnabled = buffer.readBoolean();
	}

	public static class Handler implements IMessageHandler<ChocoboLocalSetupUpdate, IMessage> {

		@Override
		public IMessage onMessage(ChocoboLocalSetupUpdate message, MessageContext ctx) {
			ModChocoCraft.isRemoteClient = false;
			ChocoboConfig.readConfigFilePreInit();
			return null;
		}
	}
}
