package com.example.elementalweapons.item.custom;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class IceAxeItem extends ElementalWeaponItem {
    private static final int COOLDOWN_TICKS = 120; // 6 seconds
    private static final double ABILITY_RANGE = 6.0; // Blocks radius
    private static final int SLOW_DURATION_TICKS = 100; // 5 seconds
    private static final int SLOW_AMPLIFIER = 1;

    public IceAxeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    protected void activateAbility(PlayerEntity player, World world, ItemStack stack) {
        if (!world.isClient()) {
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1.0F, 0.9F + world.random.nextFloat() * 0.2F);

            Box searchBox = player.getBoundingBox().expand(ABILITY_RANGE);
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, searchBox, e -> e != player);

            for (LivingEntity target : entities) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, SLOW_DURATION_TICKS, SLOW_AMPLIFIER));
            }

            // Create a small patch of ice blocks around the player
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos pos = player.getBlockPos().add(x, -1, z);
                    if (world.getBlockState(pos).isReplaceable() && world.isAir(pos.up())) {
                        world.setBlockState(pos, Blocks.ICE.getDefaultState());
                    }
                }
            }
        }
    }

    @Override
    protected int getAbilityCooldown() {
        return COOLDOWN_TICKS;
    }

    @Override
    public Text getAbilityDescription() {
        return Text.translatable("item.elementalweapons.ice_axe.ability_description");
    }
}
