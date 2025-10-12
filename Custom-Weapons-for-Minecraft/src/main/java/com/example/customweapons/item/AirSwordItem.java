package com.example.customweapons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class AirSwordItem extends ElementalSwordItem {
    private static final double KNOCKBACK_STRENGTH = 1.5;
    private static final int RADIUS = 6;

    public AirSwordItem() {
        super(ToolMaterials.DIAMOND, 2, -2.0F, new Settings().maxCount(1));
    }

    @Override
    protected boolean onAbilityUsed(World world, PlayerEntity player, ItemStack stack) {
        if (!world.isClient()) {
            Vec3d lookVec = player.getRotationVector();
            Box area = player.getBoundingBox().expand(lookVec.x * RADIUS, lookVec.y * RADIUS, lookVec.z * RADIUS).expand(RADIUS / 2.0); // Cone-like area
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, area, e -> e != player && e.isAttackable());

            boolean affectedAny = false;
            for (LivingEntity entity : entities) {
                Vec3d knockbackDirection = entity.getPos().subtract(player.getPos()).normalize();
                entity.takeKnockback(KNOCKBACK_STRENGTH, knockbackDirection.x, knockbackDirection.z);
                affectedAny = true;
            }

            // Propel player forward slightly
            player.addVelocity(lookVec.x * 0.5, 0.2, lookVec.z * 0.5);
            player.fallDistance = 0;

            if (affectedAny) {
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PHANTOM_FLAP, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
            return true;
        } else {
            Vec3d lookVec = player.getRotationVector();
            for (int i = 0; i < 50; i++) {
                double x = player.getX() + lookVec.x * i * 0.2 + (world.random.nextDouble() - 0.5) * 0.5;
                double y = player.getY() + 1.0 + lookVec.y * i * 0.2 + (world.random.nextDouble() - 0.5) * 0.5;
                double z = player.getZ() + lookVec.z * i * 0.2 + (world.random.nextDouble() - 0.5) * 0.5;
                world.addParticle(ParticleTypes.CLOUD, x, y, z, 0.0, 0.0, 0.0);
            }
        }
        return true;
    }
}
