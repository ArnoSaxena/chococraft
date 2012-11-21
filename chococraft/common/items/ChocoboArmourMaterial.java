package chococraft.common.items;

public enum ChocoboArmourMaterial
{
	CHOCOFEATHER(1, new int[] { 0, 0, 0, 0 }, 0);

	private int maxDamageFactor;
	private int dmgRedAmounts[];
	private int enchantability;

	private ChocoboArmourMaterial(int maxDamageFactor, int dmgRedAmounts[], int enchantability)
	{
		this.maxDamageFactor = maxDamageFactor;
		this.dmgRedAmounts = dmgRedAmounts;
		this.enchantability = enchantability;
	}

	/**
	 * Returns the durability for a armour slot of for this type.
	 */
	public int getDurability(int armourType)
	{
		return ChocoboItemDisguise.getMaxDamageArray()[armourType] * this.maxDamageFactor;
	}

	public int getDamageReductionAmount(int armourType)
	{
		return this.dmgRedAmounts[armourType];
	}

	public int getEnchantability()
	{
		return this.enchantability;
	}
}
