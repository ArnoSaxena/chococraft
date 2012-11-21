package chococraft.common.gui;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.StringTranslate;

public class ChocoboCreativeTab extends CreativeTabs
{
	public ChocoboCreativeTab(String label)
	{
		super(label);
	}

	public String c()
	{
		return StringTranslate.getInstance().translateKey("" + this.getTabLabel());
	}

	public String getTabLabel()
	{
		return "Chocobo Items";
	}
}
