package chococraft.common.items;

import chococraft.common.Constants;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.CreativeTabs;

public class BlockGysahlGreen extends BlockFlower
{
	public BlockGysahlGreen(int blockId, int textureId)
    {
        super(blockId, textureId);
        blockIndexInTexture = textureId;
        setTickRandomly(true);
        float f = 0.5F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.setCreativeTab(CreativeTabs.tabDeco);
        this.disableStats();
        this.setRequiresSelfNotify();
        this.setTextureFile(Constants.CHOCOBO_ITEM_TEXTURES);
    }

	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		return 36;
	}
	
	protected boolean canThisPlantGrowOnThisBlockID(int blockId)
    {
		boolean canGrow = false;
		
		if(blockId == Block.tilledField.blockID 
				|| blockId == Block.grass.blockID
				|| blockId == Block.dirt.blockID)
		{
			canGrow = true;
		}
        return canGrow;
    }
}
