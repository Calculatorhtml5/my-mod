package com.example.abilityweapons.recipe;

import com.example.abilityweapons.AbilityWeapons;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void registerRecipes() {
        AbilityWeapons.LOGGER.debug("Registering recipes for " + AbilityWeapons.MOD_ID);
        // Recipes are registered automatically via JSONs in data/abilityweapons/recipes
    }
}
