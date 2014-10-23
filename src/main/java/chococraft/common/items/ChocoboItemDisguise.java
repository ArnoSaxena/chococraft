package chococraft.common.items;

import chococraft.common.config.ChocoCraftItems;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import chococraft.common.config.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

//public class ChocoboItemDisguise extends ItemArmor implements IArmorTextureProvider
public class ChocoboItemDisguise extends ItemArmor
{
	
    private static final int maxDamageArray[] =
    {
        1, 1, 1, 1
    };
    public final int damageReduceAmount;
    public final int renderIndex;
    private final ChocoboArmourMaterial material;

	public ChocoboItemDisguise(ChocoboArmourMaterial armourMaterial, int renderIndex, int armorType)
	{
		super(ArmorMaterial.GOLD, renderIndex, armorType);
		this.material = armourMaterial;
		this.renderIndex = renderIndex;
		this.damageReduceAmount = this.material.getDamageReductionAmount(this.armorType);
        this.setMaxDamage(this.material.getDurability(this.armorType));
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		String name = this.getUnlocalizedName().substring(5);
		this.itemIcon = iconRegister.registerIcon(Constants.TCC_MODID + ":" + name);
	}
	
    @Override
	public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }

    static int[] getMaxDamageArray()
    {
        return maxDamageArray;
    }

	@Override
    public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String type)
	{
	    if (itemstack.getItem().equals(ChocoCraftItems.chocoDisguiseHelmetItem))
	    {
	      return Constants.CHOCOBO_ARMOUR_TEXTURES_1;
	    }
	    if (itemstack.getItem().equals(ChocoCraftItems.chocoDisguisePlateItem))
	    {
	      return Constants.CHOCOBO_ARMOUR_TEXTURES_1;
	    }
	    if (itemstack.getItem().equals(ChocoCraftItems.chocoDisguiseLegsItem))
	    {
	      return Constants.CHOCOBO_ARMOUR_TEXTURES_2;
	    }
	    if (itemstack.getItem().equals(ChocoCraftItems.chocoDisguiseBootsItem))
	    {
	      return Constants.CHOCOBO_ARMOUR_TEXTURES_1;
	    }
		return null;
	}	
}
