package chococraft.common.items;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialStraw extends Material
{

    public static final Material straw = new Material(MapColor.grassColor);	
	
	public MaterialStraw(MapColor mapColor)
	{
		super(mapColor);
	}
	
	@Override
    public boolean blocksMovement()
    {
        return false;
    }
}
