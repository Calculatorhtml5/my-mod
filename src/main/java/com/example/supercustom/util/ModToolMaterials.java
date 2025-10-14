package com.example.supercustom.util;

import com.example.supercustom.item.ModItems;
import net.fabricmc.fabric.api.item.v1.FabricToolMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;

public class ModToolMaterials {
    public static final FabricToolMaterial ELEMENTAL = new FabricToolMaterial() {
        @Override
        public int getDurability() {
            return 1200; // Slightly less than Netherite, more than Diamond
        }

        @Override
        public float getMiningSpeedMultiplier() {
            return 8.0f; // Diamond speed
        }

        @Override
        public float getAttackDamage() {
            return 3.0f; // Base damage bonus, similar to diamond sword (3 + 3 = 6 attack damage)
        }

        @Override
        public int getMiningLevel() {
            return 3; // Can mine Obsidian
        }

        @Override
        public int getEnchantability() {
            return 15; // Similar to Iron
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(ModItems.ELEMENTAL_GEM);
        }
    };
}
