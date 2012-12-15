package chococraft.common.helper;

import java.util.Random;

import net.minecraft.src.Entity;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;

public class ChocoboParticleHelper
{
	// "bubble", "suspended", "depthsuspend", "townaura", "crit", "magicCrit",
	// "smoke", "mobSpell", "mobSpellAmbient", "spell", "instantSpell", "witchMagic", "note", "portal", 
	// "enchantmenttable", "explode", "flame", "lava", "footstep", "splash",
	// "largesmoke", "cloud", "reddust", "snowballpoof", "dripWater", "dripLava",
	// "snowshovel", "slime", "heart", "angryVillager", "happyVillager"
    public static void showParticleAroundEntityFx(String particleName, Entity entity)
    {
    	if(Side.CLIENT == FMLCommonHandler.instance().getEffectiveSide())
    	{
    		Random rand = new Random();

    		double partPosX = (entity.posX + (double)(rand.nextFloat() * entity.width * 2.0F)) - (double)entity.width;
    		double partPosY = entity.posY + 0.5D + (double)(rand.nextFloat() * entity.height);
    		double partPosZ = (entity.posZ + (double)(rand.nextFloat() * entity.width * 2.0F)) - (double)entity.width;
    		double partVelX = rand.nextGaussian() * 0.02D;
    		double partVelY = rand.nextGaussian() * 0.02D;
    		double partVelZ = rand.nextGaussian() * 0.02D;
    		entity.worldObj.spawnParticle(particleName, partPosX, partPosY, partPosZ, partVelX, partVelY, partVelZ);
    	}
    }
    
    public static void showParticleAroundEntityFx(String particleName, Entity entity, int amount)
    {
    	for(int i = 0; i < amount; i++)
    	{
    		showParticleAroundEntityFx(particleName, entity);
    	}
    }
    
    public static void showParticleAroundEntityFxDebugger(String particleName, Entity entity)
    {
    	showParticleAroundEntityFx(particleName, entity);
    } 
}
