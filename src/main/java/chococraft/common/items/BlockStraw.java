package chococraft.common.items;

import chococraft.common.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;



public class BlockStraw extends Block
{
    public BlockStraw()
    {
        super(Material.circuits);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j)
	{
    	return this.blockIcon;
	}
    
    @Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
    	this.blockIcon = iconRegister.registerIcon(Constants.TCC_MODID + ":" + Constants.KEY_STRAW);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
    	return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
    }

    @Override
    public void onNeighborBlockChange(World world, int posX, int posY, int posZ, Block par5)
    {
        this.canStrawStay(world, posX, posY, posZ);
    }
    
    private boolean canStrawStay(World world, int posX, int posY, int posZ)
    {
        if (!this.canPlaceBlockAt(world, posX, posY, posZ))
        {
            world.setBlock(posX, posY, posZ, this, 0, 2);
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
    @Override
    public boolean canPlaceBlockAt(World world, int posX, int posY, int posZ)
    {
        Block block = world.getBlock(posX, posY - 1, posZ);
        return block != null && (block.isOpaqueCube()) ? block.getMaterial().blocksMovement() : false;
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    @Override
    public void harvestBlock(World world, EntityPlayer player, int posX, int posY, int posZ, int blockDamage)
    {
        super.harvestBlock(world, player, posX, posY, posZ, blockDamage);
        world.setBlockToAir(posX, posY, posZ);
        //world.setBlock(posX, posY, posZ, this.blockID, 0, 2);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
	//TODO update to 1.7
    /*@Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return this.itemid;
    }*/

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }
    
    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
        return (meta & 7) + 1;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World world, int posX, int posY, int posZ, Random random)
    {
        if (world.getSavedLightValue(EnumSkyBlock.Block, posX, posY, posZ) > 11)
        {
            world.setBlock(posX, posY, posZ, this, 0, 2);
        }
    }

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return par5 == 1 ? true : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }
}
