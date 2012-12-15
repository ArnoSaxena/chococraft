package chococraft.common.gui;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiTextField;

public class GuiClickTextField extends GuiTextField
{
	
	private GuiSelectNewOwner receiver;

    private final int xPos;
    private final int yPos;
    private final int width;
    private final int height;
	
	public GuiClickTextField(FontRenderer fontRenderer, int xPos, int yPos, int width, int height)
	{
		super(fontRenderer, xPos, yPos, width, height);

		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		
	}
	
	public void setReceiver(GuiSelectNewOwner gsno)
	{
		if(gsno != null)
		{
			this.receiver = gsno;
		}
	}
	
	@Override
    public void mouseClicked(int clickX, int clickY, int par3)
    {
        boolean clicked = clickX >= this.xPos && clickX < this.xPos + this.width && clickY >= this.yPos && clickY < this.yPos + this.height;
    	if(clicked && this.receiver != null)
    	{
    		if(!this.getText().isEmpty())
    		{
    			this.receiver.fillNameIntoNewOwnerTf(this.getText());
    		}
    	}
    }
}
