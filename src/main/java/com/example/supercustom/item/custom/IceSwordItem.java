package com.example.supercustom.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class IceSwordItem extends SwordItem {
    private static final int COOLDOWN_TICKS = 80; // 4 seconds
    private static final double EFFECT_RADIUS = 5.0; // Radius for applying slow effect

    public IceSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient() && !user.getItemCooldownManager().isCoolingDown(this)) {
            // Play sound
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_PLAYER_HURT_FREEZE, SoundCategory.PLAYERS, 1.0F, 1.0F);

            // Apply slowness to nearby entities
            Box boundingBox = user.getBoundingBox().expand(EFFECT_RADIUS);
            List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, boundingBox, entity -> entity != user && !entity.isTeammate(user));

            for (LivingEntity entity : entities) {
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 2)); // 5 seconds, Slowness III
            }

            // Apply cooldown
            user.getItemCooldownManager().set(this, COOLDOWN_TICKS);

            // Damage item
            itemStack.damage(1, user, player -> player.sendToolBreakStatus(hand));

            return TypedActionResult.success(itemStack, world.isClient());
        }
        return TypedActionResult.pass(itemStack);
    }
}
