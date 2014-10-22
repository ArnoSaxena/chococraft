package chococraft.common.entities;

import java.util.ArrayList;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;


public class ChocoboRiderList
{
	private ArrayList<String> riders;
	
	public ChocoboRiderList()
	{
		this.riders = new ArrayList<String>();
	}
	
	public void readFromNBT(NBTTagList nbttaglist)
	{
        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            String rider = nbttagcompound.getString("name");
            this.riders.add(rider);

        }
	}

	public NBTBase writeToNBT(NBTTagList nbtTagList)
	{
		for (int i = 0; i < this.riders.size(); i++)
		{
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			nbttagcompound.setString("name", this.riders.get(i));
			nbtTagList.appendTag(nbttagcompound);
		}
		return nbtTagList;
	}
	
	public void addRider(String rider)
	{
		if(!this.riders.contains(rider))
		{
			this.riders.add(rider);
		}
	}
	
	public void removeRider(String rider)
	{
		if(this.riders.contains(rider))
		{
			this.riders.remove(rider);
		}
	}
	
	public boolean isRiderAllowed(String rider)
	{
		return this.riders.contains(rider);
	}
	
	public int getSize()
	{
		return this.riders.size();
	}
	
	public String getRider(int index)
	{
		if(index < this.getSize())
		{
			return this.riders.get(index);
		}
		else
		{
			return null;
		}
	}
}
