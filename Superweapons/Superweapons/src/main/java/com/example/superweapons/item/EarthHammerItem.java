package com.example.superweapons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class EarthHammerItem extends SuperweaponItem {
    public EarthHammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, int abilityCooldownTicks) {
        super(toolMaterial, attackDamage, attackSpeed, settings, abilityCooldownTicks);
    }

    @Override
    protected void onRightClickAbility(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient()) {
            HitResult hitResult = player.raycast(16.0, 0.0F, false); // Raycast for 16 blocks
            Vec3d explosionPos;

            if (hitResult.getType() != HitResult.Type.MISS) {
                explosionPos = hitResult.getPos();
            } else {
                // If no block is hit, create explosion in front of player
                explosionPos = player.getPos().add(player.getRotationVec(1.0F).multiply(3.0));
            }

            world.createExplosion(player, explosionPos.getX(), explosionPos.getY(), explosionPos.getZ(), 2.5F, Explosion.DestructionType.NONE);
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        }
    }

    // Earth Hammer does not have a special on-hit ability by default, but could be added here.
}
