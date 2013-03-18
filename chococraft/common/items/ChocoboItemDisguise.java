package chococraft.common.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IArmorTextureProvider;
import chococraft.common.Constants;
import chococraft.common.ModChocoCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ChocoboItemDisguise extends ItemArmor implements IArmorTextureProvider
{
	
    private static final int maxDamageArray[] =
    {
        1, 1, 1, 1
    };
    public final int damageReduceAmount;
    public final int renderIndex;
    private final ChocoboArmourMaterial material;

	public ChocoboItemDisguise(int itemIndex, ChocoboArmourMaterial armourMaterial, int renderIndex, int armorType)
	{
		super(itemIndex, EnumArmorMaterial.GOLD, renderIndex, armorType);
		this.material = armourMaterial;
		this.renderIndex = renderIndex;
		this.damageReduceAmount = this.material.getDamageReductionAmount(this.armorType);
        this.setMaxDamage(this.material.getDurability(this.armorType));
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabs.tabTools);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void func_94581_a(IconRegister iconRegister)
	{
		String name = this.getUnlocalizedName();
		if(name.equals("item." + Constants.KEY_DISGUISE_HEAD))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_DISGUISE_HEAD);
		}
		else if(name.equals("item." + Constants.KEY_DISGUISE_BODY))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_DISGUISE_BODY);
		}
		else if(name.equals("item." + Constants.KEY_DISGUISE_LEGS))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_DISGUISE_LEGS);
		}
		else if(name.equals("item." + Constants.KEY_DISGUISE_BOOTS))
		{
			this.iconIndex = iconRegister.func_94245_a(Constants.TCC_MODID + ":" + Constants.KEY_DISGUISE_BOOTS);
		}
	}
	
    public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }

    static int[] getMaxDamageArray()
    {
        return maxDamageArray;
    }

	@Override
	public String getArmorTextureFile(ItemStack itemstack)
	{
	    if (itemstack.itemID == ModChocoCraft.chocoDisguiseHelmetItem.itemID)
	    {
	      return Constants.CHOCOBO_ARMOUR_TEXTURES_1;
	    }
	    if (itemstack.itemID == ModChocoCraft.chocoDisguisePlateItem.itemID)
	    {
	      return Constants.CHOCOBO_ARMOUR_TEXTURES_1;
	    }
	    if (itemstack.itemID == ModChocoCraft.chocoDisguiseLegsItem.itemID)
	    {
	      return Constants.CHOCOBO_ARMOUR_TEXTURES_2;
	    }
	    if (itemstack.itemID == ModChocoCraft.chocoDisguiseBootsItem.itemID)
	    {
	      return Constants.CHOCOBO_ARMOUR_TEXTURES_1;
	    }
		return null;
	}	
}
