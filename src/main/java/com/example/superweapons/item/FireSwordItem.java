package com.example.superweapons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireSwordItem extends SuperweaponItem {
    public FireSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, int abilityCooldownTicks) {
        super(toolMaterial, attackDamage, attackSpeed, settings, abilityCooldownTicks);
    }

    @Override
    protected void onRightClickAbility(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient()) {
            Vec3d lookVec = player.getRotationVec(1.0F);
            SmallFireballEntity fireball = new SmallFireballEntity(world, player.getX() + lookVec.x, player.getEyeY() + lookVec.y, player.getZ() + lookVec.z, lookVec.x, lookVec.y, lookVec.z);
            fireball.setOwner(player);
            fireball.setPos(player.getX() + lookVec.x, player.getEyeY() + lookVec.y, player.getZ() + lookVec.z);
            world.spawnEntity(fireball);
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
        }
    }

    @Override
    protected void onHitAbility(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setOnFireFor(8); // Set target on fire for 8 seconds
    }
}
