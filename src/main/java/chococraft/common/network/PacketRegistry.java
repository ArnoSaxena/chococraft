// <copyright file="ChocoboPacketHandler.java">
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
// <summary>receiving and distributing network communication packets intended for the ChocoCraft mod.</summary>

package chococraft.common.network;

import chococraft.common.config.Constants;
import chococraft.common.network.clientSide.*;
import chococraft.common.network.serverSide.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketRegistry {

public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.TCC_MODID);

	static int id = 0;

	public static void registerPackets() {
		//Client side
		INSTANCE.registerMessage(ChicoboCanGrowUp.Handler.class, ChicoboCanGrowUp.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(ChocoboHealth.Handler.class, ChocoboHealth.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(ChocoboHunger.Handler.class, ChocoboHunger.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(ChocoboLocalSetupUpdate.Handler.class, ChocoboLocalSetupUpdate.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(ChocoboParticles.Handler.class, ChocoboParticles.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(ChocoboSetupUpdate.Handler.class, ChocoboSetupUpdate.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(ChocoboTamed.Handler.class, ChocoboTamed.class, id++, Side.CLIENT);

		//Server side
		INSTANCE.registerMessage(ChocoboAttribute.Handler.class, ChocoboAttribute.class, id++, Side.SERVER);
		INSTANCE.registerMessage(ChocoboChangeOwner.Handler.class, ChocoboChangeOwner.class, id++, Side.SERVER);
		INSTANCE.registerMessage(ChocoboDropGear.Handler.class, ChocoboDropGear.class, id++, Side.SERVER);
		INSTANCE.registerMessage(ChocoboMount.Handler.class, ChocoboMount.class, id++, Side.SERVER);
		INSTANCE.registerMessage(ChocoboSetInLove.Handler.class, ChocoboSetInLove.class, id++, Side.SERVER);
		INSTANCE.registerMessage(ChocoboUpdateRiderActionState.Handler.class, ChocoboUpdateRiderActionState.class, id++, Side.SERVER);

	}

}