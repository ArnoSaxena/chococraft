// <copyright file="FactoryEntityChocobo.java">
// Copyright (c) 2012 All Right Reserved, http://chococraft.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <email>al-s@gmx.de</email>
// <date>2012-09-10</date>
// <summary>Creating instances of coloured chocobos</summary>

package chococraft.common.entities;

import java.util.Random;

import net.minecraft.src.World;
import chococraft.common.ChocoboNames;
import chococraft.common.ModChocoCraft;
import chococraft.common.entities.EntityAnimalChocobo.chocoboColor;
import chococraft.common.entities.colours.*;

public class FactoryEntityChocobo
{
	public static EntityChocobo createNewChocobo(World world, chocoboColor color)
	{
		boolean isMale = (new Random()).nextInt(100) < ModChocoCraft.genderMaleChance ? true : false;
		String name = ChocoboNames.getRandomName(isMale);
		EntityChocobo entityChocobo = null;
		
		switch(color)
		{
		case YELLOW:
			entityChocobo = new EntityChocoboYellow(world);
			break;
		case GREEN:
			entityChocobo = new EntityChocoboGreen(world);
			break;
		case BLUE:
			entityChocobo = new EntityChocoboBlue(world);
			break;
		case WHITE:
			entityChocobo = new EntityChocoboWhite(world);
			break;
		case BLACK:
			entityChocobo = new EntityChocoboBlack(world);
			break;
		case GOLD:
			entityChocobo = new EntityChocoboGold(world);
			break;
		case PINK:
			entityChocobo = new EntityChocoboPink(world);
			break;
		case RED:
			entityChocobo = new EntityChocoboRed(world);
			break;
		case PURPLE:
			entityChocobo = new EntityChocoboPurple(world);
			break;
		default:
			// todo error handling
			entityChocobo = new EntityChocoboYellow(world);
		}
		entityChocobo.setIsMale(isMale);
		entityChocobo.setName(name);
		return entityChocobo;
	}
	
	public static EntityChocobo createChocobo(World world, chocoboColor color, String name,
			String owner, boolean hidename, boolean tamed, Boolean following, boolean male)
	{
		EntityChocobo entityChocobo = FactoryEntityChocobo.createNewChocobo(world, color);
		entityChocobo.setName(name);
		entityChocobo.setOwner(owner);
		entityChocobo.setHidename(hidename);
		entityChocobo.setTamed(tamed);
		entityChocobo.setFollowing(following);
		entityChocobo.setIsMale(male);
		return entityChocobo;
	}
	
	public static EntityChicobo createNewChicobo(World world, chocoboColor color)
	{
		boolean isMale = (new Random()).nextInt(100) < ModChocoCraft.genderMaleChance ? true : false;
		EntityChicobo chicobo = new EntityChicobo(world);
		chicobo.setColor(color);
		chicobo.setIsMale(isMale);
		chicobo.setName(ChocoboNames.getRandomName(chicobo.isMale()));
		return chicobo;
	}
	
	public static EntityChicobo createChicobo(World world, chocoboColor color, String name,
			String owner, boolean hidename, boolean tamed, Boolean following, boolean male)
	{
		EntityChicobo entityChicobo = FactoryEntityChocobo.createNewChicobo(world, color);
		entityChicobo.setName(name);
		entityChicobo.setOwner(owner);
		entityChicobo.setHidename(hidename);
		entityChicobo.setTamed(tamed);
		entityChicobo.setFollowing(following);
		entityChicobo.setIsMale(male);
		return entityChicobo;
	}

}
