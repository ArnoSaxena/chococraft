package chococraft.common.items;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialStraw extends Material
{

    public static final Material straw = new Material(MapColor.grassColor);	
	
	public MaterialStraw(MapColor mapColor)
	{
		super(mapColor);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public boolean blocksMovement()
    {
        return false;
    }
}
