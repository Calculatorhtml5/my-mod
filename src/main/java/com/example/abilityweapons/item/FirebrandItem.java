package com.example.abilityweapons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class FirebrandItem extends AbilityWeaponItem {
    public FirebrandItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setFireTicks(20 * 5); // 5 seconds of fire
        return super.postHit(stack, target, attacker);
    }
}
