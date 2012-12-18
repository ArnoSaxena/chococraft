package chococraft.common;

import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityPlayer;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import chococraft.common.entities.colours.EntityChocoboPurple;
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
    
    @ForgeSubscribe
    public void onBurnDamage(LivingAttackEvent event)
    {
    	if(event.entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)event.entity;
    		if(event.source.equals(DamageSource.lava) 
    				|| event.source.equals(DamageSource.inFire) 
    				|| event.source.equals(DamageSource.onFire))
    		{
    			if(player.ridingEntity instanceof EntityChocoboPurple)
    			{
    				event.setCanceled(true);
    			}
    		}
    	}
    }
}
