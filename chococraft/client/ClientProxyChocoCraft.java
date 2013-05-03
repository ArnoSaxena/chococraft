// <copyright file="ClientProxyChocoCraft.java">
// Copyright (c) 2012 All Right Reserved, http://chococraft.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <email>al-s@gmx.de</email>
// <date>2012-10-25</date>
// <summary>ChocoCraft Client proxy</summary>

package chococraft.client;

import net.minecraftforge.common.MinecraftForge;
import chococraft.common.CommonProxyChocoCraft;
import chococraft.common.entities.EntityChicobo;
import chococraft.common.entities.colours.EntityChocoboBlack;
import chococraft.common.entities.colours.EntityChocoboBlue;
import chococraft.common.entities.colours.EntityChocoboGold;
import chococraft.common.entities.colours.EntityChocoboGreen;
import chococraft.common.entities.colours.EntityChocoboPink;
import chococraft.common.entities.colours.EntityChocoboPurple;
import chococraft.common.entities.colours.EntityChocoboRed;
import chococraft.common.entities.colours.EntityChocoboWhite;
import chococraft.common.entities.colours.EntityChocoboYellow;
import chococraft.common.entities.models.ModelChicobo;
import chococraft.common.entities.models.ModelChocobo;
import chococraft.common.entities.models.RenderChicobo;
import chococraft.common.entities.models.RenderChocobo;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxyChocoCraft extends CommonProxyChocoCraft
{
    @Override
    public void registerRenderThings()
    {
    	//MinecraftForgeClient.preloadTexture(Constants.CHOCOBO_ITEM_TEXTURES);
        //MinecraftForgeClient.preloadTexture(Constants.CHOCOBO_ARMOUR_TEXTURES_1);
        //MinecraftForgeClient.preloadTexture(Constants.CHOCOBO_ARMOUR_TEXTURES_2);
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
    
    @Override
    public int addArmor(String armor)
    {
        return RenderingRegistry.addNewArmourRendererPrefix(armor);
    }
    
    @Override
    public void registerEventListener()
    {
			MinecraftForge.EVENT_BUS.register(new ChocoCraftEventClient());
    }
}