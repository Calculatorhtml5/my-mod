package com.example.superweapons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class IceBladeItem extends SuperweaponItem {
    public IceBladeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, int abilityCooldownTicks) {
        super(toolMaterial, attackDamage, attackSpeed, settings, abilityCooldownTicks);
    }

    @Override
    protected void onRightClickAbility(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient()) {
            double radius = 5.0;
            Box box = player.getBoundingBox().expand(radius);
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, box, entity -> entity != player);

            for (LivingEntity entity : entities) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 3)); // Slowness IV for 5 seconds
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 100, 2)); // Mining Fatigue III for 5 seconds
            }
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);

            // Client-side particle effect for frost aura
            world.spawnParticles(ParticleTypes.SNOWFLAKE, player.getX(), player.getY() + 1.0, player.getZ(), 50, radius, 1.0, radius, 0.05);
        }
    }

    @Override
    protected void onHitAbility(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 1)); // Slowness II for 3 seconds
    }
}
