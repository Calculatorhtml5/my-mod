package com.example.abilityweapons;

import com.example.abilityweapons.item.ModItems;
import com.example.abilityweapons.recipe.ModRecipes;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbilityWeapons implements ModInitializer {
	public static final String MOD_ID = "abilityweapons";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing AbilityWeapons mod!");

		ModItems.registerModItems();
		ModRecipes.registerRecipes();
	}
}
