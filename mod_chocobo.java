// <copyright file="mod_chocobo.java">
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
// <summary>main entry point of the chococraft mod. Initialises basic variables and objects used by the mod.</summary>

package net.minecraft.src;

import java.util.*;
import net.minecraft.client.Minecraft;

public class mod_chocobo extends BaseMod
{
    public static int gysahlGreenid = 150;
    public static int gysahlCropid = 151;
    public static int chocoboRawid = 1502;
    public static int chocoboCookedid = 1503;
    public static int chocoboFeatherid = 1504;
    public static int chocoboSaddleid = 1505;
    public static int chocoboWhistleid = 1506;
    public static int chocoboLoverlyid = 1507;
    public static int chocoboGoldenid = 1508;
    public static int chocoboCakeid = 1509;
    public static int chocoboPinkid = 1510;
    public static int chocoboRedid = 1511;
    public static int gysahlSeedsid = 1513;
    public static int chibiGysahlid = 1514;
    public static int chocoboSaddleBagsid = 1497;
    public static int chocoboPackBagsid = 1496;
    public static int chocoboFertilizerid = 1501;
    public static int chocopediaid = 1500;
    public static int chocotweezerid = 1499;
    public static int netherChocoboEggId = 1498;
    public static int achievements_id = 4550;
    public static int maxChocobos = 20;
    public static int spawnProbability = 8;
    public static int sureSpawnThreshold = 5;
    public static int spawnEventTimeCycle = 300;
    public static int chocoboSpawnMinGroupCount = 3;
    public static int chocoboSpawnMaxGroupCount = 6;
    public static boolean show_chocobo_names = true;
    public static boolean chocobo_wing_flutter = true;
    public static final Block gysahlGreen;
    public static final Block gysahlStem;
    public static Item gysahlSeeds;
    public static Item chocoboLoverly;
    public static Item chocoboGolden;
    public static Item chocoboCake;
    public static Item chocoboPink;
    public static Item chocoboRed;
    public static Item chicoboGreen;
    public static Item chocoboRaw;
    public static Item chocoboCooked;
    public static Item chocoboFertilizer;
    public static Item chocoboFeather;
    public static Item chocoboSaddle;
    public static Item chocoboSaddleBags;
    public static Item chocoboPackBags;
    public static Item chocoboWhistle;
    public static Item chocopedia;
    public static Item chocotweezer;
    public static Item netherChocoboEgg;
    public static BiomeGenBase[] chocoboSpawnBiomes = null;
    public static int gysahlCrop0 = ModLoader.addOverride("/terrain.png", "/choco/Crops/gysahlstem.png");
    public static int gysahlCrop1 = ModLoader.addOverride("/terrain.png", "/choco/Crops/gysahlstem1.png");
    public static int gysahlCrop2 = ModLoader.addOverride("/terrain.png", "/choco/Crops/gysahlstem2.png");
    public static int gysahlCrop3 = ModLoader.addOverride("/terrain.png", "/choco/Crops/gysahlstem3.png");
    public static int gysahlCrop4 = ModLoader.addOverride("/terrain.png", "/choco/Crops/gysahlstem4.png");
    //private static final int achiev_y = -5;
    //private static final int achiev_x = -7;
    public static final Achievement pickedGreen;
    public static final Achievement farmGreen;
    public static final Achievement cookChoco;
    public static final Achievement tameChoco;
    public static final Achievement rideChoco;
    public static final Achievement breedChoco;
    public static final Achievement cakeChico;
    public static final Achievement chibiChico;
    public static final Achievement blueChoco;
    public static final Achievement greenChoco;
    public static final Achievement whiteChoco;
    public static final Achievement blackChoco;
    public static final Achievement goldChoco;
    public static final Achievement pinkChoco;
    public static final Achievement redChoco;
    private static GuiScreen lastGuiOpen;
    public static Minecraft mc = ModLoader.getMinecraftInstance();
    public static ChocoboSpawner chocoboSpawner;
    
    //model testing
    public static int size_x = 1;
    public static int size_y = 6;
    public static int size_z = 8;
    public static float pos_x = -15;
    public static float pos_y = 5;
    public static float pos_z = 1;
    
    public mod_chocobo()
    {
    }

    public String getVersion()
    {
        return "1.6.9";
    }

    public void load()
    {
        System.out.println((new StringBuilder()).append("GYSAHL ID: ").append(gysahlGreenid).append(" / ").append(gysahlGreen.blockID).toString());
        gysahlGreen.blockIndexInTexture = ModLoader.addOverride("/terrain.png", "/choco/gysahlgreens.png");
        
        ModLoader.registerBlock(gysahlGreen);
        ModLoader.addName(gysahlGreen, "Gysahl Greens");
        ModLoader.registerBlock(gysahlStem);
        ModLoader.addName(gysahlStem, "Gysahl Stem");
        
        chocoboRaw.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/chocoRaw.png");
        chocoboCooked.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/chocoCooked.png");
        chocoboFeather.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/chocoFeather.png");
        chocoboSaddle.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/chocoSaddle.png");
        chocoboSaddleBags.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/chocoSaddleBags.png");
        chocoboPackBags.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/chocoPackBags.png");
        chocoboLoverly.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/loverlygysahl.png");
        chocoboGolden.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/goldengysahl.png");
        chocoboWhistle.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/chocoWhistle.png");
        chocoboCake.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/gysahlCake.png");
        chocoboPink.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/gysahlpink.png");
        chocoboRed.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/gysahlred.png");
        chocoboFertilizer.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/Crops/fertilizer.png");
        chocopedia.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/chocopedia.png");
        chocotweezer.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/tweezers.png");
        gysahlSeeds.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/gysahlseeds.png");
        netherChocoboEgg.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/netheregg.png");
        chicoboGreen.iconIndex = ModLoader.addOverride("/gui/items.png", "/choco/chibigysahl.png");
        
        ModLoader.addName(chocoboRaw, "Raw Chocobo Leg");
        ModLoader.addName(chocoboCooked, "Cooked Chocobo Leg");
        ModLoader.addName(chocoboFeather, "Chocobo Feather");
        ModLoader.addName(chocoboSaddle, "Chocobo Saddle");
        ModLoader.addName(chocoboSaddleBags, "Chocobo Saddle Bags");
        ModLoader.addName(chocoboPackBags, "Chocobo Pack Bags");
        ModLoader.addName(chocoboLoverly, "Loverly Gysahl");
        ModLoader.addName(chocoboGolden, "Golden Gysahl");
        ModLoader.addName(chocoboWhistle, "Wild Chocobo Whistle");
        ModLoader.addName(chocoboCake, "Gysahl Cake");
        ModLoader.addName(chocoboPink, "Pink Gysahl");
        ModLoader.addName(chocoboRed, "Red Gysahl");
        ModLoader.addName(chicoboGreen, "Chibi Gysahl");
        ModLoader.addName(chocoboFertilizer, "Chocobo Fertilizer");
        ModLoader.addName(chocotweezer, "Chocobo Tweezers");
        ModLoader.addName(chocopedia, "Chocopedia");
        ModLoader.addName(gysahlSeeds, "Gysahl Seeds");
        ModLoader.addName(netherChocoboEgg, "Nether Chocobo Egg");
        
        // chocobo saddle
        ModLoader.addRecipe(new ItemStack(chocoboSaddle, 1), new Object[]
                {
                    "-X-", 
                    " Y ", 
                    Character.valueOf('X'), Item.leather, 
                    Character.valueOf('Y'), chocoboFeather, 
                    Character.valueOf('-'), Item.silk
                });

        // chocobo saddle bag
        ModLoader.addRecipe(new ItemStack(chocoboSaddleBags, 1), new Object[]
                {
        			" Y ",
                    "X X", 
                    " X ", 
                    Character.valueOf('X'), Item.leather, 
                    Character.valueOf('Y'), chocoboFeather
                });

        // chocobo pack bag
        ModLoader.addRecipe(new ItemStack(chocoboPackBags, 1), new Object[]
                {
        			"-Y-",
                    "C C", 
                    "-X-", 
                    Character.valueOf('C'), Block.cloth, 
                    Character.valueOf('X'), Item.leather, 
                    Character.valueOf('Y'), chocoboFeather,
                    Character.valueOf('-'), Item.silk
                });

        
        // loverly ghysal
        ModLoader.addRecipe(new ItemStack(chocoboLoverly, 1), new Object[]
                {
                    "RY", 
                    "X ", 
                    Character.valueOf('X'), gysahlGreen, 
                    Character.valueOf('Y'), chocoboFeather, 
                    Character.valueOf('R'), Block.plantRed
                });

        // golden ghysal
        ModLoader.addRecipe(new ItemStack(chocoboGolden, 1), new Object[]
                {
                    "FGF", 
                    "GXG", 
                    "FGF", 
                    Character.valueOf('X'), gysahlGreen, 
                    Character.valueOf('F'), chocoboFeather, 
                    Character.valueOf('G'), Item.goldNugget
                });

        // chibi ghysal
        ModLoader.addRecipe(new ItemStack(chicoboGreen, 1), new Object[]
                {
                    " R ", 
                    "RGR", 
                    " R ", 
                    Character.valueOf('R'), Item.redstone, 
                    Character.valueOf('G'), gysahlGreen
                });
        
        // chocopedia
        ModLoader.addRecipe(new ItemStack(chocopedia, 1), new Object[]
                {
                    "FGF", 
                    "IBI", 
                    "FLF", 
                    Character.valueOf('B'), Item.book,
                    Character.valueOf('F'), chocoboFeather,
                    Character.valueOf('I'), new ItemStack(Item.dyePowder, 1, 0),
                    Character.valueOf('L'), new ItemStack(Item.dyePowder, 1, 4),
                    Character.valueOf('G'), Item.goldNugget
                });
        
        // chocotweezer
        ModLoader.addRecipe(new ItemStack(chocotweezer, 1), new Object[]
                {
                    "  F", 
                    " I ", 
                    "I  ", 
                    Character.valueOf('I'), Item.ingotIron, 
                    Character.valueOf('F'), chocoboFeather
                });
        
        // chocobo whistle
        ModLoader.addShapelessRecipe(new ItemStack(chocoboWhistle, 1), new Object[]
                {
                    new ItemStack(chocoboFeather, 1), 
                    new ItemStack(Item.ingotGold, 1)
                });
        
        // ghysal seeds
        ModLoader.addShapelessRecipe(new ItemStack(gysahlSeeds, 3), new Object[]
                {
                    new ItemStack(gysahlGreen, 1)
                });
        
        // chocobo fertilizer
        ModLoader.addShapelessRecipe(new ItemStack(chocoboFertilizer, 1), new Object[]
                {
        			new ItemStack(chocoboFeather, 1),
        			new ItemStack(Item.dyePowder, 1, 15)
                });
        
        // chocobo cake
        ModLoader.addRecipe(new ItemStack(chocoboCake, 1), new Object[]
                {
                    "MGM", 
                    "SES", 
                    "WGW", 
                    Character.valueOf('G'), gysahlGreen, 
                    Character.valueOf('M'), Item.bucketMilk, 
                    Character.valueOf('S'), Item.sugar, 
                    Character.valueOf('W'), Item.wheat,
                    Character.valueOf('E'), Item.egg
                });
        
        // pink ghysal
        ModLoader.addShapelessRecipe(new ItemStack(chocoboPink, 1), new Object[]
                {
                    new ItemStack(gysahlGreen, 1), 
                    new ItemStack(Item.dyePowder, 1, 9)
                });
        
        // red ghysal
        ModLoader.addShapelessRecipe(new ItemStack(chocoboRed, 1), new Object[]
                {
                    new ItemStack(gysahlGreen, 1), 
                    new ItemStack(Item.dyePowder, 1, 1)
                });

        ModLoader.addSmelting(chocoboRaw.shiftedIndex, new ItemStack(chocoboCooked));

        ModLoader.registerEntityID(EntityChocobo.class, "Chocobo", ModLoader.getUniqueEntityId());
        ModLoader.registerEntityID(EntityChicobo.class, "Chicobo", ModLoader.getUniqueEntityId());
        ModLoader.registerEntityID(EntityNetherChocobo.class, "Nether Chocobo", ModLoader.getUniqueEntityId());
        ModLoader.registerEntityID(EntityNetherChocoEgg.class, "Nether Chocobo Egg", ModLoader.getUniqueEntityId());
        
        ModLoader.addAchievementDesc(pickedGreen, "Flower Girl", "Pick a Gysahl Green up.");
        ModLoader.addAchievementDesc(farmGreen, "Farmer Boy", "Harvest a Gysahl Green you planted.");
        ModLoader.addAchievementDesc(tameChoco, "Beastmaster", "Tame a Chocobo.");
        ModLoader.addAchievementDesc(rideChoco, "Chocobo Knight", "Ride a saddled Chocobo.");
        ModLoader.addAchievementDesc(breedChoco, "Chocobo Breeder", "Breed a new Chicobo.");
        ModLoader.addAchievementDesc(cookChoco, "Chocobo Eater", "Cook a Raw Chocobo Leg.");
        ModLoader.addAchievementDesc(cakeChico, "You Shall Grow", "Feed a Gysahl Cake to a Chicobo.");
        ModLoader.addAchievementDesc(chibiChico, "You Shall Not Grow", "Feed a Chibi Gysahl to a Chicobo.");
        ModLoader.addAchievementDesc(blueChoco, "Blue Kweh", "Breed a Blue Chocobo.");
        ModLoader.addAchievementDesc(greenChoco, "Green Kweh", "Breed a Green Chocobo.");
        ModLoader.addAchievementDesc(whiteChoco, "White Kweh", "Breed a White Chocobo.");
        ModLoader.addAchievementDesc(blackChoco, "Black Kweh", "Breed a Black Chocobo.");
        ModLoader.addAchievementDesc(goldChoco, "Gold Kweh", "Breed a Golden Chocobo.");
        ModLoader.addAchievementDesc(pinkChoco, "Pink Kweh", "Breed a Pink Chocobo.");
        ModLoader.addAchievementDesc(redChoco, "Red Kweh", "Breed a Red Chocobo.");
        
        ModLoader.setInGUIHook(this, true, true);
        ModLoader.setInGameHook(this, true, true);
        
        chocoboSpawner = new ChocoboSpawner();
        //nether chocobos only spawn in nether
        ModLoader.addSpawn(EntityNetherChocobo.class, 12, chocoboSpawnMinGroupCount, chocoboSpawnMaxGroupCount, EnumCreatureType.creature, new BiomeGenBase[]{ BiomeGenBase.hell });
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void addRenderer(Map map)
    {
        map.put(net.minecraft.src.EntityChocobo.class, new RenderChocobo(new ModelChocobo(), 0.5F));
        map.put(net.minecraft.src.EntityNetherChocobo.class, new RenderChocobo(new ModelChocobo(), 0.5F));
        map.put(net.minecraft.src.EntityChicobo.class, new RenderChicobo(new ModelChicobo(), 0.3F));
    }

    public void generateSurface(World world, Random random, int i, int j)
    {
        int k = i + random.nextInt(8);
        int l = random.nextInt(128);
        int i1 = j + random.nextInt(8);
        (new WorldGenGysahls(gysahlGreen.blockID)).generate(world, random, k, l, i1);
    }

    public void onItemPickup(EntityPlayer entityplayer, ItemStack itemstack)
    {
        if (itemstack.itemID == gysahlGreen.blockID)
        {
            entityplayer.addStat(pickedGreen, 1);
        }
    }

    public void takenFromFurnace(EntityPlayer entityplayer, ItemStack itemstack)
    {
        if (itemstack.itemID == chocoboCooked.shiftedIndex)
        {
            entityplayer.addStat(cookChoco, 1);
        }
    }

    public boolean onTickInGame(float f, Minecraft minecraft)
    {
    	//chocoboSpawner.doChocoboSpawningTest(mc.theWorld, maxChocobos, false);

        if (mc.theWorld.worldInfo.getWorldTime() % spawnEventTimeCycle == 0L)
        {	
        	if(!mc.theWorld.isRemote)
        	{
        		chocoboSpawner.doChocoboSpawning(mc.theWorld, maxChocobos);
        		//chocoboSpawner.doChocoboSpawningTest(mc.theWorld);
        	}
        }
        if (minecraft.currentScreen == null)
        {
            lastGuiOpen = null;
        }
        return true;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean onTickInGUI(float f, Minecraft minecraft, GuiScreen guiscreen)
    {
        if ((guiscreen instanceof GuiContainerCreative) && !(lastGuiOpen instanceof GuiContainerCreative) && !minecraft.theWorld.isRemote)
        {
            Container container = ((GuiContainer)guiscreen).inventorySlots;
			List list = ((ContainerCreative)container).itemList;
            list.add(new ItemStack(gysahlGreen, 1, 0));
            lastGuiOpen = guiscreen;
        }
        return true;
    }

    public static String getRandomName()
    {
        Random random = new Random();
        String as[] =
        {
            "Boco", "Choco", "Patch", "Eddie", "Big Bird", "Chobi", "Horse Bird", "Mr. Yellowpuffs", "Oscar", "Wild",
            "Stitch", "Milo", "Lewis", "Simon", "Steed", "Bocobo", "Chobo", "Butter Fingers", "Caspar", "Chubby",
            "Crystal", "Coco", "Fuzzy", "Hulk", "Flopsy", "Lionel", "Lulu", "Yuna", "Tidus", "Cloud",
            "Sephiroth", "Butz", "Cecil", "Golbez", "Squall", "Lightning", "Zidane", "Garnet", "Kuja", "Terra",
            "Locke", "Celes", "Rikku", "Yuffie", "Selphie", "Rinoa", "Crafty", "Sparky", "Skippy", "Whiskers",
            "Pupu", "Mog's Mount", "Ruffles", "Quistis", "Noctis"
        };
        int i = as.length;
        int j = random.nextInt(i);
        return as[j];
    }

    static
    {
    	ChocoboConfig.readConfigFile();
    	
        gysahlGreen = (BlockFlower)(new BlockFlower(gysahlGreenid, 0)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("gysahlGreen");
        gysahlStem = (new ChocoboBlockCrops(gysahlCropid, 0)).setHardness(0.0F).setStepSound(Block.soundWoodFootstep).setBlockName("gysahlStem").setRequiresSelfNotify();
        gysahlSeeds = (new ItemSeeds(gysahlSeedsid, gysahlStem.blockID, Block.tilledField.blockID)).setItemName("gysahlSeeds");
        chocoboLoverly = (new Item(chocoboLoverlyid)).setItemName("chocoboLoverly");
        chocoboGolden = (new Item(chocoboGoldenid)).setItemName("chocoboGolden");
        chocoboCake = (new Item(chocoboCakeid)).setItemName("chocoboCake");
        chocoboPink = (new Item(chocoboPinkid)).setItemName("chocoboPink");
        chocoboRed = (new Item(chocoboRedid)).setItemName("chocoboRed");
        chicoboGreen = (new Item(chibiGysahlid)).setItemName("chicoboGreen");
        chocoboRaw = (new Item(chocoboRawid)).setItemName("chocoboRaw");
        chocoboCooked = (new ItemFood(chocoboCookedid, 8, 0.6F, true)).setItemName("chocoboCooked");
        chocoboFertilizer = (new ChocoboFertilizer(chocoboFertilizerid)).setItemName("chocoboFertilizer");
        chocoboFeather = (new Item(chocoboFeatherid)).setItemName("chocoboFeather");
        chocoboSaddle = (new Item(chocoboSaddleid)).setItemName("chocoboSaddle").setMaxStackSize(1);
        chocoboSaddleBags = (new Item(chocoboSaddleBagsid)).setItemName("chocoboSaddleBags").setMaxStackSize(16);
        chocoboPackBags = (new Item(chocoboPackBagsid)).setItemName("chocoboPackBags").setMaxStackSize(16);
        chocoboWhistle = (new Item(chocoboWhistleid)).setItemName("chocoboWhistle");
        chocopedia = (new Item(chocopediaid)).setItemName("chocopedia");
        chocotweezer = (new Item(chocotweezerid)).setItemName("chocotweezer");
        netherChocoboEgg = (new ItemNetherChocoEgg(netherChocoboEggId)).setItemName("netherChocoboEgg");
        pickedGreen = (new Achievement(achievements_id, "pickedGreen", -7, -5, gysahlGreen, null)).registerAchievement();
        farmGreen = (new Achievement(achievements_id + 1, "farmGreen", -7, -6, gysahlSeeds, pickedGreen)).registerAchievement();
        cookChoco = (new Achievement(achievements_id + 2, "cookChoco", -7, -4, chocoboCooked, pickedGreen)).registerAchievement();
        tameChoco = (new Achievement(achievements_id + 3, "tameChoco", -6, -5, chocoboFeather, pickedGreen)).registerAchievement();
        rideChoco = (new Achievement(achievements_id + 4, "rideChoco", -6, -7, chocoboSaddle, tameChoco)).registerAchievement();
        breedChoco = (new Achievement(achievements_id + 5, "breedChoco", -5, -6, chocoboLoverly, tameChoco)).registerAchievement();
        cakeChico = (new Achievement(achievements_id + 6, "cakeChico", -5, -4, chocoboCake, breedChoco)).registerAchievement();
        chibiChico = (new Achievement(achievements_id + 7, "chibiChico", -5, -3, chicoboGreen, breedChoco)).registerAchievement();
        blueChoco = (new Achievement(achievements_id + 8, "blueChoco", -4, -6, chocoboLoverly, breedChoco)).registerAchievement();
        greenChoco = (new Achievement(achievements_id + 9, "greenChoco", -4, -7, chocoboLoverly, breedChoco)).registerAchievement();
        whiteChoco = (new Achievement(achievements_id + 10, "whiteChoco", -3, -7, chocoboLoverly, greenChoco)).registerAchievement();
        blackChoco = (new Achievement(achievements_id + 11, "blackChoco", -2, -7, chocoboLoverly, whiteChoco)).registerAchievement();
        goldChoco = (new Achievement(achievements_id + 12, "goldChoco", -1, -7, chocoboGolden, blackChoco)).registerAchievement();
        pinkChoco = (new Achievement(achievements_id + 13, "pinkChoco", -1, -6, chocoboPink, goldChoco)).registerAchievement();
        redChoco = (new Achievement(achievements_id + 14, "redChoco", 0, -7, chocoboRed, goldChoco)).registerAchievement();
    }
}
