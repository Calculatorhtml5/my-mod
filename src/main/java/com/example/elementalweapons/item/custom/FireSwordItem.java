package com.example.elementalweapons.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class FireSwordItem extends ElementalWeaponItem {
    private static final int COOLDOWN_TICKS = 80; // 4 seconds
    private static final double ABILITY_RANGE = 4.0; // Blocks radius
    private static final int FIRE_DURATION_TICKS = 100; // 5 seconds

    public FireSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    protected void activateAbility(PlayerEntity player, World world, ItemStack stack) {
        if (!world.isClient()) {
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);

            Box searchBox = player.getBoundingBox().expand(ABILITY_RANGE);
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, searchBox, e -> e != player);

            for (LivingEntity target : entities) {
                target.setOnFireFor(FIRE_DURATION_TICKS / 20); // setOnFireFor takes seconds
            }
        }
    }

    @Override
    protected int getAbilityCooldown() {
        return COOLDOWN_TICKS;
    }

    @Override
    public Text getAbilityDescription() {
        return Text.translatable("item.elementalweapons.fire_sword.ability_description");
    }
}
