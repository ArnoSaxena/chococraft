// <copyright file="ChocoboNames.java">
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
// <summary>Class for everything name related</summary>

package chococraft.common;

import java.util.Random;

public class ChocoboNames
{
	public static final int MALE = 0;
	public static final int FEMALE = 1;
	
    private static String maleNames[] =
    {
        "Boco", "Choco", "Patch", "Eddie", "Big Bird", "Chobi", "Horse Bird", "Mr. Yellowpuffs", "Oscar", "Wild",
        "Stitch", "Milo", "Lewis", "Simon", "Steed", "Bocobo", "Chobo", "Butter Fingers", "Caspar", "Chubby",
        "Coco", "Fuzzy", "Hulk", "Flopsy", "Lionel", "Tidus", "Cloud", "Sephiroth", "Butz", "Cecil", "Golbez",
        "Squall", "Zidane", "Garnet", "Kuja", "Locke", "Celes", "Crafty", "Sparky", "Skippy", 
        "Whiskers", "Mog's Mount", "Ruffles", "Quistis", "Noctis", "Firecracker", "Ballistic", "Blizzard",
        "Torobo", "Leon", "Firas", "Travis", "Indigo", "Montoya", "Cobalt", "Jinx", "Komet", "Beau", "Bone",
        "Claw", "Duke", "Easy", "Fire", "Fury", "Idol", "Iron", "Jack", "Mars", "Noir", "Snow", "Star", "Zero",
        "Ace", "Air", "Ice", "Max", "Neo", "Ray", "Alpha", "Arrow", "Avian", "Black", "Blade", "Blaze", "Blitz",
        "Chaos", "Dandy", "Jolly", "Omega", "Pluto", "Point", "Quake", "Titan", "Hope", "Ifrit", "Shiva"

    };
	
    private static String femaleNames[] =
    {
        "Choco", "Patch", "Chobi", "Wild", "Chubby", "Crystal", "Coco", "Fuzzy", "Flopsy", "Lulu", "Yuna", 
        "Cecil", "Kuja", "Terra", "Locke", "Celes", "Rikku", "Yuffie", "Selphie", "Rinoa", "Sparky", 
        "Skippy", "Whiskers", "Pupu", "Quistis", "Noctis", "Tranquille", "Twinkling", "Capucine", "Heidi",
        "Danseuse", "Mercedes", "Psyche", "Victory", "Liberty", "Emma", "Fortune", "Soleil", "Luna", "Violet",
        "Lilith", "Lilli", "Jinx", "Coco", "Fleur", "Feder", "Flora", "Kugel", "Bleu", "Blue", "Chic", "Ciel",
        "Face", "Fire", "Fury", "Iris", "Jade", "Joli", "Kiku", "Lady", "Miel", "Momo", "Moon", "Nana",
        "Noir", "Nova", "Rain", "Rose", "Ruby", "Star", "Vega", "Air", "Aki", "Ayu", "Fee", "Sky", "Sun",
        "Amber", "Angel", "Azure", "Belle", "Clair", "Ebony", "Ember", "Fairy", "Flare", "Glory", "Jaune", 
        "Jeune", "Jolly", "Lucky", "Olive", "Orange", "Venus", "Lightning"
    };
    
    public static String getRandomName(boolean isMale)
    {
    	if(isMale)
    	{
    		return ChocoboNames.getRandomMaleName();
    	}
    	else
    	{
    		return ChocoboNames.getRandomFemaleName();
    	}
    }
    
    public static String getRandomMaleName()
    {
        Random random = new Random();

        int i = maleNames.length;
        int j = random.nextInt(i);
        return maleNames[j];
    }
    
    public static String getRandomFemaleName()
    {
        Random random = new Random();
        int i = femaleNames.length;
        int j = random.nextInt(i);
        return femaleNames[j];
    }
}
