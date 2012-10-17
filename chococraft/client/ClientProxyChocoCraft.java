package chococraft.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import chococraft.common.CommonProxyChocoCraft;
import chococraft.common.Constants;
import chococraft.common.entities.EntityChicobo;
import chococraft.common.entities.colours.*;
import chococraft.common.entities.models.ModelChicobo;
import chococraft.common.entities.models.ModelChocobo;
import chococraft.common.entities.models.RenderChicobo;
import chococraft.common.entities.models.RenderChocobo;

public class ClientProxyChocoCraft extends CommonProxyChocoCraft
{
    @Override
    public void registerRenderThings()
    {
    	MinecraftForgeClient.preloadTexture(Constants.CHOCOBO_ITEM_TEXTURES);
    }
    
    public static void registerRenderInformation()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityChocoboYellow.class, new RenderChocobo(new ModelChocobo(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityChocoboGreen.class, new RenderChocobo(new ModelChocobo(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityChocoboBlue.class, new RenderChocobo(new ModelChocobo(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityChocoboWhite.class, new RenderChocobo(new ModelChocobo(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityChocoboBlack.class, new RenderChocobo(new ModelChocobo(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityChocoboGold.class, new RenderChocobo(new ModelChocobo(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityChocoboPink.class, new RenderChocobo(new ModelChocobo(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityChocoboRed.class, new RenderChocobo(new ModelChocobo(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityChocoboPurple.class, new RenderChocobo(new ModelChocobo(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityChicobo.class, new RenderChicobo(new ModelChicobo(), 0.5F));
    }
}