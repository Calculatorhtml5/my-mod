package com.example.elementalweapons.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class EarthHammerItem extends ElementalWeaponItem {
    private static final int COOLDOWN_TICKS = 100; // 5 seconds
    private static final double ABILITY_RANGE = 5.0; // Blocks radius
    private static final float ABILITY_DAMAGE = 8.0F;
    private static final float KNOCKBACK_STRENGTH = 1.5F;

    public EarthHammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    protected void activateAbility(PlayerEntity player, World world, ItemStack stack) {
        if (!world.isClient()) {
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);

            Box searchBox = player.getBoundingBox().expand(ABILITY_RANGE);
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, searchBox, e -> e != player);

            for (LivingEntity target : entities) {
                if (target.damage(world.getDamageSources().playerAttack(player), ABILITY_DAMAGE)) {
                    double deltaX = target.getX() - player.getX();
                    double deltaZ = target.getZ() - player.getZ();
                    double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
                    if (distance > 0) {
                        target.takeKnockback(KNOCKBACK_STRENGTH, deltaX / distance, deltaZ / distance);
                    }
                }
            }
            world.addParticle(ParticleTypes.EXPLOSION_EMITTER, player.getX(), player.getY(), player.getZ(), 1.0, 0.0, 0.0);
        }
    }

    @Override
    protected int getAbilityCooldown() {
        return COOLDOWN_TICKS;
    }

    @Override
    public Text getAbilityDescription() {
        return Text.translatable("item.elementalweapons.earth_hammer.ability_description");
    }
}
