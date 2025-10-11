package com.example.elementalweapons.item.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.List;

public class LightningScytheItem extends ElementalWeaponItem {
    private static final int COOLDOWN_TICKS = 160; // 8 seconds
    private static final double TARGET_RANGE = 20.0;
    private static final double MIN_TARGET_DISTANCE = 3.0;

    public LightningScytheItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    protected void activateAbility(PlayerEntity player, World world, ItemStack stack) {
        if (!world.isClient()) {
            ServerWorld serverWorld = (ServerWorld) world;
            Vec3d strikePos;

            HitResult hitResult = player.raycast(TARGET_RANGE, 0, false); // false for liquid handling, but we want block/entity

            LivingEntity targetEntity = null;
            // Try to find a living entity in range
            List<LivingEntity> potentialTargets = world.getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(TARGET_RANGE), e -> e != player && !e.isDead());
            if (!potentialTargets.isEmpty()) {
                targetEntity = potentialTargets.stream()
                        .min(Comparator.comparingDouble(e -> e.squaredDistanceTo(player)))
                        .orElse(null);
            }

            if (targetEntity != null) {
                strikePos = targetEntity.getPos();
            } else if (hitResult.getType() == HitResult.Type.BLOCK || hitResult.getType() == HitResult.Type.ENTITY) {
                strikePos = hitResult.getPos();
            } else {
                // If no target or block is hit, strike at a point in front of the player
                strikePos = player.getEyePos().add(player.getRotationVector().multiply(TARGET_RANGE / 2));
            }

            LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
            lightning.setPos(strikePos.getX(), strikePos.getY(), strikePos.getZ());
            // Set the lightning bolt to be caused by the player to attribute damage
            lightning.setChanneler(player);
            serverWorld.spawnEntity(lightning);

            world.playSound(null, strikePos.getX(), strikePos.getY(), strikePos.getZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.WEATHER, 1.0F, 0.8F + world.random.nextFloat() * 0.2F);
            world.playSound(null, strikePos.getX(), strikePos.getY(), strikePos.getZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.WEATHER, 1.0F, 0.8F + world.random.nextFloat() * 0.2F);
        }
    }

    @Override
    protected int getAbilityCooldown() {
        return COOLDOWN_TICKS;
    }

    @Override
    public Text getAbilityDescription() {
        return Text.translatable("item.elementalweapons.lightning_scythe.ability_description");
    }
}
