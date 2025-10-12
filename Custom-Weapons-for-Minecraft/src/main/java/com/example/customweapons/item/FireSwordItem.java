package com.example.customweapons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import java.util.List;

public class FireSwordItem extends ElementalSwordItem {
    private static final int RADIUS = 5;

    public FireSwordItem() {
        super(ToolMaterials.DIAMOND, 3, -2.4F, new Settings().maxCount(1).fireproof());
    }

    @Override
    protected boolean onAbilityUsed(World world, PlayerEntity player, ItemStack stack) {
        if (!world.isClient()) {
            Box area = player.getBoundingBox().expand(RADIUS);
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, area, e -> e != player);

            boolean affectedAny = false;
            for (LivingEntity entity : entities) {
                if (!entity.isFireImmune()) {
                    entity.setOnFireFor(5); // Set on fire for 5 seconds
                    affectedAny = true;
                }
            }
            if (affectedAny) {
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
            return affectedAny;
        } else {
            for (int i = 0; i < 30; i++) {
                double x = player.getX() + (world.random.nextDouble() - 0.5) * RADIUS * 2;
                double y = player.getY() + world.random.nextDouble() * RADIUS;
                double z = player.getZ() + (world.random.nextDouble() - 0.5) * RADIUS * 2;
                world.addParticle(ParticleTypes.FLAME, x, y, z, 0.0, 0.0, 0.0);
            }
        }
        return true;
    }
}
