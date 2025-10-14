package com.example.supercustom.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireSwordItem extends SwordItem {
    private static final int COOLDOWN_TICKS = 60; // 3 seconds

    public FireSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient() && !user.getItemCooldownManager().isCoolingDown(this)) {
            // Play sound
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);

            // Spawn fireball
            Vec3d lookVec = user.getRotationVec(1.0F);
            SmallFireballEntity fireball = new SmallFireballEntity(world, user, lookVec.x, lookVec.y, lookVec.z);
            fireball.setPos(user.getX() + lookVec.x * 0.5,
                            user.getEyeY() + lookVec.y * 0.5,
                            user.getZ() + lookVec.z * 0.5);
            world.spawnEntity(fireball);

            // Apply cooldown
            user.getItemCooldownManager().set(this, COOLDOWN_TICKS);

            // Damage item
            itemStack.damage(1, user, player -> player.sendToolBreakStatus(hand));

            return TypedActionResult.success(itemStack, world.isClient());
        }
        return TypedActionResult.pass(itemStack);
    }
}
