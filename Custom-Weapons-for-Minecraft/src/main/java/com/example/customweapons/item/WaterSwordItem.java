package com.example.customweapons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import java.util.List;

public class WaterSwordItem extends ElementalSwordItem {
    private static final int RADIUS = 4;

    public WaterSwordItem() {
        super(ToolMaterials.DIAMOND, 3, -2.4F, new Settings().maxCount(1));
    }

    @Override
    protected boolean onAbilityUsed(World world, PlayerEntity player, ItemStack stack) {
        if (!world.isClient()) {
            Box area = player.getBoundingBox().expand(RADIUS);
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, area, e -> e != player);

            boolean affectedAny = false;
            for (LivingEntity entity : entities) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 1)); // Slow for 5 seconds
                affectedAny = true;
            }

            player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 80, 0)); // Regen for 4 seconds

            // Extinguish fires in a small radius
            for (BlockPos pos : BlockPos.iterateOutwards(player.getBlockPos(), RADIUS, 1, RADIUS)) {
                if (world.getBlockState(pos).isOf(net.minecraft.block.Blocks.FIRE)) {
                    world.setBlockState(pos, net.minecraft.block.Blocks.AIR.getDefaultState());
                }
            }

            if (affectedAny || player.hasStatusEffect(StatusEffects.REGENERATION)) {
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_DROWNED_SWIM, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
            return affectedAny || player.hasStatusEffect(StatusEffects.REGENERATION);
        } else {
            for (int i = 0; i < 20; i++) {
                double x = player.getX() + (world.random.nextDouble() - 0.5) * RADIUS * 2;
                double y = player.getY() + world.random.nextDouble() * RADIUS;
                double z = player.getZ() + (world.random.nextDouble() - 0.5) * RADIUS * 2;
                world.addParticle(ParticleTypes.BUBBLE, x, y, z, 0.0, 0.0, 0.0);
            }
        }
        return true;
    }
}
