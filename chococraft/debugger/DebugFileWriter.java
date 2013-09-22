package chococraft.debugger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.FMLCommonHandler;

public class DebugFileWriter
{
	
	private static final DebugFileWriter INSTANCE = new DebugFileWriter();
	private static File file;
	public static Logger ccLog = Logger.getLogger("ChocoCraft");
	
	public DebugFileWriter()
	{
		if (MinecraftServer.getServer().isDedicatedServer())
		{
			file = new File(MinecraftServer.getServer().getFolderName(), "../chocoboDebug.txt");
		}
		else
		{
			file = new File(Minecraft.getMinecraft().mcDataDir, "mods/chocoboDebug.txt");
		}
			
		
		if(!file.exists())
		{
			try
			{
				file.createNewFile();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static DebugFileWriter instance()
	{
		return INSTANCE;
	}
	
	private void writeLine(String lineToWrite)
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			
			writer.write("\n" + lineToWrite);
			
			writer.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		ccLog.info(lineToWrite);
	}

	public void writeLine(String who, String lineToWrite)
	{
		this.writeLine("[" + who + ": " + FMLCommonHandler.instance().getEffectiveSide().toString().toLowerCase() + "] " + lineToWrite);
	}
	
	public void writeSideStatement(String who)
	{
		this.writeLine(who, "we are on the " + FMLCommonHandler.instance().getEffectiveSide().toString().toLowerCase() + " side.");
	}

	public void writeEmptyLine()
	{
		this.writeLine("");
	}
	
	public void writeDashLine()
	{
		this.writeLine("\n------------------------------------");
	}
}
