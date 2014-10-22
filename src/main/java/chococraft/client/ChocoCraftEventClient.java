package chococraft.client;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import chococraft.common.ChocoCraftEventCommon;

public class ChocoCraftEventClient extends ChocoCraftEventCommon
{
    @ForgeSubscribe
    public void onSound(SoundLoadEvent event)
    {
        try
        {
            event.manager.addSound("chococraft:choco_kweh.ogg");
        }
        catch (Exception e)
        {
            System.err.println("Failed to register one or more sounds.");
        }
    }
}
