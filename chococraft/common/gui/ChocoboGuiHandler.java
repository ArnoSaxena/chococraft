package chococraft.common.gui;

import chococraft.common.ChocoboHelper;
import chococraft.common.bags.ChocoBagContainer;
import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChocobo;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class ChocoboGuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int chocoboId, int saddleOrPack, int dummy)
	{
		EntityChocobo chocobo = this.getChocoboById(chocoboId, world.getWorldInfo().getDimension());
		if(null != chocobo)
		{
			return new ChocoBagContainer(player.inventory, chocobo.getChocoBagInventory());
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int chocoboId, int saddleOrPack, int dummy)
	{
		EntityChocobo chocobo = this.getChocoboById(chocoboId, world.getWorldInfo().getDimension());
		if(null != chocobo)
		{
			return new GuiChocoboBag(player.inventory, chocobo.getChocoBagInventory());
		}
		return null;
	}

	private EntityChocobo getChocoboById(int chocoboId, int dimension)
	{
		EntityAnimalChocobo entityAnimalChocobo = ChocoboHelper.getChocoboByID(chocoboId, dimension);
		if(entityAnimalChocobo instanceof EntityChocobo)
		{
			return (EntityChocobo)entityAnimalChocobo;
		}
		return null;
	}
}
