package com.example.customweapons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.block.Blocks;

import java.util.List;

public class EarthSwordItem extends ElementalSwordItem {
    private static final int RADIUS = 3;

    public EarthSwordItem() {
        super(ToolMaterials.DIAMOND, 4, -2.8F, new Settings().maxCount(1));
    }

    @Override
    protected boolean onAbilityUsed(World world, PlayerEntity player, ItemStack stack) {
        if (!world.isClient()) {
            Box area = player.getBoundingBox().expand(RADIUS);
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, area, e -> e != player);

            boolean affectedAny = false;
            for (LivingEntity entity : entities) {
                Vec3d knockbackDir = entity.getPos().subtract(player.getPos()).normalize().add(0, 0.5, 0);
                entity.takeKnockback(1.0, knockbackDir.getX(), knockbackDir.getZ());
                entity.damage(world.getDamageSources().playerAttack(player), 2.0f); // Small damage
                affectedAny = true;
            }

            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 80, 0)); // Resistance for 4 seconds

            if (affectedAny || player.hasStatusEffect(StatusEffects.RESISTANCE)) {
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
            return affectedAny || player.hasStatusEffect(StatusEffects.RESISTANCE);
        } else {
            for (int i = 0; i < 30; i++) {
                double x = player.getX() + (world.random.nextDouble() - 0.5) * RADIUS * 2;
                double y = player.getY() + world.random.nextDouble() * RADIUS;
                double z = player.getZ() + (world.random.nextDouble() - 0.5) * RADIUS * 2;
                world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.DIRT.getDefaultState()), x, y, z, 0.0, 0.0, 0.0);
            }
        }
        return true;
    }
}
