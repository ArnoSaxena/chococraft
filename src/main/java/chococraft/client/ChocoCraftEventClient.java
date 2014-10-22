package chococraft.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import chococraft.common.ChocoCraftEventCommon;

public class ChocoCraftEventClient extends ChocoCraftEventCommon
{
    @SubscribeEvent
    public void onSound(SoundLoadEvent event)
    {
        try
        {
			//TODO update to 1.7
//            event.manager.addSound("chococraft:choco_kweh.ogg");
        }
        catch (Exception e)
        {
            System.err.println("Failed to register one or more sounds.");
        }
    }
}
