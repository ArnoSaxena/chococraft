package chococraft.common.config;

import chococraft.common.entities.EntityChicobo;
import chococraft.common.entities.colours.*;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;

/**
 * Created by clienthax on 24/10/2014.
 */
public class ChocoCraftEntities {

	public static void registerChocobos()
	{
		registerChocoboEntityClass(EntityChicobo.class, "Chicobo", getRGBInt(170, 70, 250), getRGBInt(250, 250, 0));
		registerChocoboEntityClass(EntityChocoboYellow.class, "ChocoboYellow", getRGBInt(250, 250, 50), getRGBInt(180, 150, 1));
		registerChocoboEntityClass(EntityChocoboGreen.class, "ChocoboGreen", getRGBInt(50, 250, 50), getRGBInt(1, 100, 1));
		registerChocoboEntityClass(EntityChocoboBlue.class, "ChocoboBlue", getRGBInt(100, 100, 250), getRGBInt(100, 100, 200));
		registerChocoboEntityClass(EntityChocoboWhite.class, "ChocoboWhite", getRGBInt(240, 240, 240), getRGBInt(252, 252, 252));
		registerChocoboEntityClass(EntityChocoboBlack.class, "ChocoboBlack", getRGBInt(1, 1, 1), getRGBInt(150, 150, 150));
		registerChocoboEntityClass(EntityChocoboGold.class, "ChocoboGold", getRGBInt(250, 130, 70), getRGBInt(111, 90, 33));
		registerChocoboEntityClass(EntityChocoboPink.class, "ChocoboPink", getRGBInt(222, 20, 222), getRGBInt(250, 200, 250));
		registerChocoboEntityClass(EntityChocoboRed.class, "ChocoboRed", getRGBInt(250, 20, 20), getRGBInt(100, 20, 20));
		registerChocoboEntityClass(EntityChocoboPurple.class, "ChocoboPurple", getRGBInt(170, 70, 160), getRGBInt(60, 50, 111));
	}

	private static void registerChocoboEntityClass(Class <? extends Entity> entityClass, String entityName, int eggColor, int eggDotsColor)
	{
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, EntityRegistry.findGlobalUniqueEntityId(), eggColor, eggDotsColor);
	}

	private static int getRGBInt(int rInt, int gInt, int bInt)
	{
		return (rInt << 16) + (gInt << 8) + bInt;
	}
}
