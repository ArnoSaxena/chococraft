package chococraft.client;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import chococraft.common.ChocoCraftEventCommon;
import chococraft.common.Constants;
import chococraft.common.ModChocoCraft;

public class ChocoCraftEventClient extends ChocoCraftEventCommon
{
    @ForgeSubscribe
    public void onSound(SoundLoadEvent event)
    {
        try
        {
            event.manager.soundPoolSounds.addSound("choco_kweh.ogg", ModChocoCraft.class.getResource(Constants.CHOCOBO_SOUND_FOLDER + "/choco_kweh.ogg"));            
        }
        catch (Exception e)
        {
            System.err.println("Failed to register one or more sounds.");
        }
    }
}
