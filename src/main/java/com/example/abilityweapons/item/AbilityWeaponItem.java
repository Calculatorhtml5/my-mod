package com.example.abilityweapons.item;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class AbilityWeaponItem extends SwordItem {
    public AbilityWeaponItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }
}
