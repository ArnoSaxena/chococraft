package chococraft.common;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;
import chococraft.common.helper.ChocoboPlayerHelper;
import chococraft.common.items.BlockGysahlStem;

public class ChocoCraftEvent
{
	@ForgeSubscribe
	public void onUseBonemeal(BonemealEvent event)
	{
		if (event.ID == ModChocoCraft.gysahlStemBlock.blockID)
		{
			if (((BlockGysahlStem)ModChocoCraft.gysahlStemBlock).onBonemealUse(event.world, event.X, event.Y, event.Z))
			{
				ChocoboPlayerHelper.useCurrentItem(event.entityPlayer);
			}
		}
	}

//    @ForgeSubscribe
//    public void onUserConnect(PlayerEvent event)
//    {
//    	if(event.entity instanceof EntityPlayer)
//    	{
//    	}
//    }

//    @ForgeSubscribe
//    public void onSound(SoundLoadEvent event)
//    {
//        try
//        {
//            event.manager.soundPoolSounds.addSound("chocobokweh", ModChocoCraft.class.getResource(Constants.CHOCOBO_SOUND_FOLDER + "/choco_kweh.ogg"));            
//        }
//        catch (Exception e)
//        {
//            System.err.println("Failed to register one or more sounds.");
//        }
//    }
}
