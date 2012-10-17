package chococraft.common;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public class CommonProxyChocoCraft
{
    public void registerRenderThings()
    {
        
    }
    
    public static void registerRenderInformation()
    {
        //No rendering for servers.
    }
   
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }

    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }
}