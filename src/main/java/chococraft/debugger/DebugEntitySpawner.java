package chococraft.debugger;

import java.util.Random;



import net.minecraft.world.World;
import chococraft.common.entities.EntityAnimalChocobo.chocoboColor;
import chococraft.common.entities.EntityChicobo;
import chococraft.common.entities.FactoryEntityChocobo;

public class DebugEntitySpawner
{
	private static final DebugEntitySpawner INSTANCE = new DebugEntitySpawner();
	
	public static DebugEntitySpawner instance()
	{
		return INSTANCE;
	}
	
	public void spawnRandomChocoboAtCoords(World world, double xPos, double yPos, double zPos)
	{
		DebugFileWriter.instance().writeLine("DeEnSp", "--- start of spawnRandomChocoboAtCoords at x:" + xPos + ", y:" + yPos + ", z:" + zPos  + "-------");
		if (!world.isRemote)
		{
			// We are on the server side.
			DebugFileWriter.instance().writeLine("DeEnSp", "called from the server side, so do the spawning");

			Random rand = new Random();
			int colourIdx = rand.nextInt((chocoboColor.values()).length);
	        //EntityChocobo ec = (EntityChocobo) FactoryEntityChocobo.createChocobo(world, chocoboColor.values()[colourIdx], "testingChocobo", "", false, false, false);

	        ////////////
			
			EntityChicobo ec = FactoryEntityChocobo.createNewChicobo(world, chocoboColor.values()[colourIdx]);		
			DebugFileWriter.instance().writeLine("DeEnSp", "new chicobo created in DebugEntitySpawner with FactoryEntityChocobo");		
			//////////////
			DebugFileWriter.instance().writeLine("DeEnSp", "set location and angles");		
	        ec.setLocationAndAngles(xPos, yPos, zPos, 0.0F, 0.0F);
	        DebugFileWriter.instance().writeLine("DeEnSp", "next line will spawn the just created Chicobo");
	        world.spawnEntityInWorld(ec);
			DebugFileWriter.instance().writeLine("DeEnSp", "--- end of spawnRandomChocoboAtCoords after spawning a " + ec.color.toString().toLowerCase() + " chicobo -------");
		}
		else
		{
			// We are on the client side.
			DebugFileWriter.instance().writeLine("DeEnSp", "called from the client side, so do *not* do the spawning");
		}
	}
}
