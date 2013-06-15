// <copyright file="GuiChocopedia.java">
// Copyright (c) 2012 All Right Reserved, http://chococraft.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <author>EddieV (initial source)</author>
// <email>al-s@gmx.de</email>
// <date>2012-05-09</date>
// <summary>Gui for the Chocopedia item</summary>

package chococraft.common.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringTranslate;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import chococraft.common.ModChocoCraft;
import chococraft.common.entities.EntityAnimalChocobo;
import chococraft.common.entities.EntityChocoboRideable;
import chococraft.common.helper.ChocoboEntityHelper;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiChocopedia extends GuiScreen
{
	private static int BUTTON_ID_RENAME       = 0;
	private static int BUTTON_ID_CONFIRM      = 1;
	private static int BUTTON_ID_HIDENAME     = 2;
	private static int BUTTON_ID_FOLLOWING    = 3;
	private static int BUTTON_ID_REMOVESADDLE = 4;
	private static int BUTTON_ID_CHANGEOWNER  = 5;
	
	private static int BUTTON_ID_BOOK_DONE    = 6;
	private static int BUTTON_ID_BOOK_PREV    = 7;
	private static int BUTTON_ID_BOOK_NEXT    = 8;
	
	
	private EntityAnimalChocobo chocobo;
	private EntityPlayer player;
	private GuiScreen parentGuiScreen;
	
	// Chocobo Options
	private GuiButton hideNameButton;
	private GuiButton followingButton;
	private GuiButton removeSaddleButton;
	private GuiButton renameButton;
	private GuiButton confirmButton;
	private GuiButton changeOwnerButton;

	// Chocopedia
    private String bookTitle = "The Chocopedia";
    private String bookAuthor = "by Torojima";
    private int bookImageWidth = 192;
    private int bookImageHeight = 192;	
    private GuiChocopediaButtonNextPage buttonNextPage;
    private GuiChocopediaButtonNextPage buttonPreviousPage;
    private GuiButton buttonDone;
    private int currPage = 0;
	
	public GuiChocopedia(GuiScreen guiscreen, EntityAnimalChocobo entitychocobo, EntityPlayer thePlayer)
	{
		this.chocobo = entitychocobo;
		this.player = thePlayer;
		this.parentGuiScreen = guiscreen;
	}

	@Override
	public void updateScreen()
	{
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initGui()
	{		
		if(null != this.chocobo)
		{
			// Chocobo options view			
			StringTranslate stringtranslate = StringTranslate.getInstance();
			Keyboard.enableRepeatEvents(true);
			this.buttonList.clear();

			//int yPos = 48;
			int yPos = 24;
			int xPos = this.width / 2 - 100;

			// rename button
			this.renameButton = this.createGuiButton(BUTTON_ID_RENAME, xPos, (yPos += 24), stringtranslate.translateKey("Rename"));
			this.checkButtonOwner(this.player, this.chocobo.getOwner(), this.renameButton);
			this.buttonList.add(this.renameButton);

			// hide name button
			String lblNameShown = (this.chocobo.isHidename()) ? "Name Hidden" : "Name Shown";
			this.hideNameButton = this.createGuiButton(BUTTON_ID_HIDENAME, xPos, (yPos += 24), stringtranslate.translateKey(lblNameShown));
			this.checkButtonOwner(this.player, this.chocobo.getOwner(), this.hideNameButton);
			this.buttonList.add(this.hideNameButton);

			// following button
			//String lblFollowing = (this.chocobo.isFollowing()) ? "Following" : "Not Following";
			String lblFollowing = this.getFollowStatus();
			this.followingButton = this.createGuiButton(BUTTON_ID_FOLLOWING, xPos, (yPos += 24), stringtranslate.translateKey(lblFollowing));
			this.checkButtonOwner(this.player, this.chocobo.getOwner(), this.followingButton);
			this.buttonList.add(this.followingButton);

			// confirm button
			this.confirmButton = this.createGuiButton(BUTTON_ID_CONFIRM, xPos, (yPos += 24), stringtranslate.translateKey("Confirm"));
			this.checkButtonOwner(this.player, this.chocobo.getOwner(), this.confirmButton);
			this.buttonList.add(this.confirmButton);

			// change owner button
			if(ModChocoCraft.isRemoteClient)
			{
				this.changeOwnerButton = this.createGuiButton(BUTTON_ID_CHANGEOWNER, xPos, yPos += 30, stringtranslate.translateKey("change owner"));
				this.checkButtonOwner(this.player, this.chocobo.getOwner(), this.changeOwnerButton);
				this.buttonList.add(this.changeOwnerButton);
			}

			// remove saddle button
			if(this.chocobo instanceof EntityChocoboRideable  && null == this.chocobo.riddenByEntity)
			{
				EntityChocoboRideable chocoboRideable = (EntityChocoboRideable)this.chocobo;
				if(chocoboRideable.isSaddled() || chocoboRideable.isPackBagged())
				{
					String lblRemoveSaddle = "Drop Gear";
					this.removeSaddleButton = this.createGuiButton(BUTTON_ID_REMOVESADDLE, xPos, (yPos += 24), stringtranslate.translateKey(lblRemoveSaddle));
					this.checkButtonOwner(this.player, this.chocobo.getOwner(), this.removeSaddleButton);
					this.buttonList.add(this.removeSaddleButton);
				}
			}
		}
		else
		{
			// Chocopedia view
            this.buttonList.add(this.buttonDone = new GuiButton(BUTTON_ID_BOOK_DONE, this.width / 2 - 100, 4 + this.bookImageHeight, 200, 20, StatCollector.translateToLocal("gui.done")));
            int i = (this.width - this.bookImageWidth) / 2;
            this.buttonList.add(this.buttonNextPage = new GuiChocopediaButtonNextPage(BUTTON_ID_BOOK_NEXT, i + 120, 156, true));
            this.buttonList.add(this.buttonPreviousPage = new GuiChocopediaButtonNextPage(BUTTON_ID_BOOK_PREV, i + 38, 156, false));
            this.updateButtons();
		}
	}
	
    private void updateButtons()
    {
        this.buttonNextPage.drawButton = this.currPage < ChocopediaPages.Instance().getNPages() - 1;
        this.buttonPreviousPage.drawButton = this.currPage > 0;
        this.buttonDone.drawButton = true;
    }

	protected GuiButton createGuiButton(int id, int xPos, int yPos, String label)
	{
		return new GuiButton(id, xPos, yPos, 90, 20, label);
	}

	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (!guibutton.enabled)
		{
			return;
		}
		if (guibutton.id == BUTTON_ID_CONFIRM)
		{
			this.mc.displayGuiScreen(parentGuiScreen);
		}
		else if (guibutton.id == BUTTON_ID_HIDENAME)
		{
			this.chocobo.setHidename(!this.chocobo.isHidename());
			this.hideNameButton.displayString = (this.chocobo.isHidename()) ? "Name Hidden" : "Name Shown";
		}
		else if (guibutton.id == BUTTON_ID_FOLLOWING)
		{
			this.chocobo.toggleFollowWanderStay();
			this.followingButton.displayString = this.getFollowStatus();
		}
		else if (guibutton.id == BUTTON_ID_RENAME)
		{
			FMLClientHandler.instance().getClient().displayGuiScreen(new GuiNameChocobo(this, chocobo));
		}
		else if (guibutton.id == BUTTON_ID_REMOVESADDLE)
		{
			if(this.chocobo instanceof EntityChocoboRideable && null == this.chocobo.riddenByEntity)
			{
				((EntityChocoboRideable)this.chocobo).sendDropSaddleAndBags();
			}
			this.mc.displayGuiScreen(this.parentGuiScreen);
		}
		else if (guibutton.id == BUTTON_ID_CHANGEOWNER)
		{
			FMLClientHandler.instance().getClient().displayGuiScreen(new GuiSelectNewOwner(this, this.chocobo));
		}
		else if(guibutton.id == BUTTON_ID_BOOK_DONE)
		{
			this.mc.displayGuiScreen(this.parentGuiScreen);
		}
        else if (guibutton.id == BUTTON_ID_BOOK_NEXT)
        {
            if (this.currPage < ChocopediaPages.Instance().getNPages() - 1)
            {
                ++this.currPage;
                this.updateButtons();
            }
        }
        else if (guibutton.id == BUTTON_ID_BOOK_PREV)
        {
            if (this.currPage > 0)
            {
                --this.currPage;
                this.updateButtons();
            }
        }
	}

	@Override
	protected void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);
	}

	@Override
	public void drawScreen(int i, int j, float f)
	{
		if(null != this.chocobo)
		{
			drawDefaultBackground();
			String breedStatus = "";
			String gender = this.chocobo.getGender();
			String ownerName = (new StringBuilder()).append("owner: ").append(this.chocobo.getOwnerName()).toString();
			String hungry = chocobo.isHungry() ? " (hungry)" : "";
			String health = (new StringBuilder()).append("health: ").append(chocobo.getHealth()).append("/").append(chocobo.getMaxHealth()).append(hungry).toString();

			if (this.chocobo.getGrowingAge() > 0 || this.chocobo.isChild())
			{
				breedStatus = "cannot breed";
			}
			else
			{
				breedStatus = "can breed";
			}

			this.drawCenteredString(this.fontRenderer, this.chocobo.getName(), this.width / 2, (height / 4 - 60) + 20, 0xffffff);

			int posY = 24;
			int posX = this.width / 2 + 10;
			int fontColour = 0xa0a0a0;

			this.drawString(this.fontRenderer, ownerName,   posX, (posY += 24), fontColour);
			this.drawString(this.fontRenderer, health,      posX, (posY += 24), fontColour);
			this.drawString(this.fontRenderer, gender + " (" + breedStatus + ")",      posX, (posY += 24), fontColour);

			if(this.chocobo.getOwnerName().equals("Torojima"))
			{
				// it's me, display debug information
				int debugLineHeight = 13;

				int amountWildChocobos = ChocoboEntityHelper.countWildChocobos(this.chocobo.worldObj);
				int amountChocobos = ChocoboEntityHelper.countEntities(EntityAnimalChocobo.class, this.chocobo.worldObj);
				int debugFontColour = 0x11a0a0;
				String biomeName = this.chocobo.worldObj.getBiomeGenForCoords((int)this.chocobo.posX, (int)this.chocobo.posZ).biomeName;
				String biomeDisplayString = "Biome: " + biomeName;

				String chocoAmountString = (new StringBuilder()).append("Chocos: ").append(amountChocobos).append(" wild: ").append(amountWildChocobos).toString();
				this.drawString(this.fontRenderer, chocoAmountString,   posX, (posY += debugLineHeight), debugFontColour);
				this.drawString(this.fontRenderer, biomeDisplayString,   posX, (posY += debugLineHeight), debugFontColour);
				this.drawString(this.fontRenderer, "Spawn: " + ModChocoCraft.spawnDbStatus,   posX, (posY += debugLineHeight), debugFontColour);

				long spawnTimeDelay = this.chocobo.worldObj.getTotalWorldTime() - ModChocoCraft.spawnDbTimeDelay;
				String spawnTimeDelayString = (new StringBuilder()).append("Spawn Time: ").append(spawnTimeDelay).toString();
				this.drawString(this.fontRenderer, spawnTimeDelayString,   posX, (posY += debugLineHeight), debugFontColour);

				String statusRemoteClient = (new StringBuilder()).append("Remote Cient: ").append(Boolean.toString(ModChocoCraft.isRemoteClient)).toString();
				this.drawString(this.fontRenderer, statusRemoteClient,   posX, (posY += debugLineHeight), debugFontColour);
			}
		}
		else
		{
			// Chocopedia view
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.renderEngine.bindTexture("/gui/book.png");
			int xCenter = (this.width - this.bookImageWidth) / 2;
			this.drawTexturedModalRect(xCenter, 2, 0, 0, this.bookImageWidth, this.bookImageHeight);

			if (this.currPage == 0)
			{
				int posY = 50;
				int titelWidth = this.fontRenderer.getStringWidth(this.bookTitle);
				this.fontRenderer.drawString(EnumChatFormatting.GOLD + this.bookTitle, xCenter + 36 + (116 - titelWidth) / 2, posY, 0);
				
				posY += 10;
				int authorWidth = this.fontRenderer.getStringWidth(this.bookAuthor);
				this.fontRenderer.drawString(EnumChatFormatting.DARK_GRAY + this.bookAuthor, xCenter + 36 + (116 - authorWidth) / 2, posY, 0);
				
				if(this.player.username.equals("Torojima"))
				{
					String biomeDisplayString = "Biome: " + this.player.worldObj.getBiomeGenForCoords((int)this.player.posX, (int)this.player.posZ).biomeName;
					String spawnStatus = "Spawn: " + ModChocoCraft.spawnDbStatus;

					int amountWildChocobos = ChocoboEntityHelper.countWildChocobos(this.player.worldObj);
					int amountChocobos = ChocoboEntityHelper.countEntities(EntityAnimalChocobo.class, this.player.worldObj);
					String chocoAmountString = (new StringBuilder()).append("Chocos: ").append(amountChocobos).append(" wild: ").append(amountWildChocobos).toString();			

					String statusRemoteClient = (new StringBuilder()).append("Remote: ").append(Boolean.toString(ModChocoCraft.isRemoteClient)).toString();
					
					posY += 30;
					this.fontRenderer.drawString(biomeDisplayString, xCenter + 36, posY, 0);
					posY += 10;
					this.fontRenderer.drawString(spawnStatus, xCenter + 36, posY, 0);
					posY += 10;
					this.fontRenderer.drawString(chocoAmountString, xCenter + 36, posY, 0);
					posY += 10;
					this.fontRenderer.drawString(statusRemoteClient, xCenter + 36, posY, 0);
				}
			}
			else
			{
				ChocopediaPages pages = ChocopediaPages.Instance();
				String pageContent = "";

				if(this.currPage > 1)
				{
					int indicatorCurrentPage = this.currPage -1;
					int indicatorAmountPages = ChocopediaPages.Instance().getNPages() - 2;
					String pageIndicator = String.format(StatCollector.translateToLocal("book.pageIndicator"), new Object[] {indicatorCurrentPage, indicatorAmountPages});
					int pageIndicatorWidth = this.fontRenderer.getStringWidth(pageIndicator);
					this.fontRenderer.drawString(pageIndicator, xCenter - pageIndicatorWidth + this.bookImageWidth - 44, 2 + 16, 0);
				}

				if (this.currPage >= 0 && this.currPage < pages.getNPages())
				{
					pageContent = pages.getPage(this.currPage);
				}
				this.fontRenderer.drawSplitString(pageContent, xCenter + 36, 2 + 16 + 16, 116, 0);
			}
		}
		super.drawScreen(i, j, f);
	}

	private void checkButtonOwner(EntityPlayer player, EntityPlayer owner, GuiButton button)
	{
		if(!player.equals(owner))
		{
			button.enabled = false;
		}
	}

	private String getFollowStatus()
	{
		if(this.chocobo.isFollowing() && !this.chocobo.isWander())
		{
			return "follow";
		}
		else if(!this.chocobo.isFollowing() && this.chocobo.isWander())
		{
			return "wander";
		}
		else if(!this.chocobo.isFollowing() && !this.chocobo.isWander())
		{
			return "stay";
		}
		else
		{
			return "";
		}
	}
}
