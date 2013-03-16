package chococraft.common.items;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import chococraft.common.Constants;



public class BlockStraw extends Block
{
    public BlockStraw(int par1, int par2)
    {
        super(par1, par2, Material.grass);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1F, 1.0F);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setTextureFile(Constants.CHOCOBO_ITEM_TEXTURES);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
//        int var5 = par1World.getBlockMetadata(par2, par3, par4) & 7;
//        return var5 >= 3 ? AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)par2 + this.minX, (double)par3 + this.minY, (double)par4 + this.minZ, (double)par2 + this.maxX, (double)((float)par3 + 0.5F), (double)par4 + this.maxZ) : null;
    	return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        //int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 7;
        //float var6 = (float)(2 * (1 + var5)) / 16.0F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.1F, 1.0F);
    }

    
    public void onNeighborBlockChange(World world, int posX, int posY, int posZ, int par5)
    {
        this.canStrawStay(world, posX, posY, posZ);
    }
    
    private boolean canStrawStay(World world, int posX, int posY, int posZ)
    {
        if (!this.canPlaceBlockAt(world, posX, posY, posZ))
        {
            world.setBlockWithNotify(posX, posY, posZ, 0);
            return false;
        }
        else
        {
            return true;
        }
    }
    
    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World world, int posX, int posY, int posZ)
    {
        int atBlockId = world.getBlockId(posX, posY - 1, posZ);
        Block block = Block.blocksList[atBlockId];
        return block != null && (Block.blocksList[atBlockId].isOpaqueCube()) ? world.getBlockMaterial(posX, posY - 1, posZ).blocksMovement() : false;
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World world, EntityPlayer player, int posX, int posY, int posZ, int blockDamage)
    {
        super.harvestBlock(world, player, posX, posY, posZ, blockDamage);
        world.setBlockWithNotify(posX, posY, posZ, 0);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return this.blockID;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11)
        {
            par1World.setBlockWithNotify(par2, par3, par4, 0);
        }
    }

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return par5 == 1 ? true : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }
}
