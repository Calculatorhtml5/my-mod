package com.example.superweapons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ThunderAxeItem extends SuperweaponItem {
    public ThunderAxeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, int abilityCooldownTicks) {
        super(toolMaterial, attackDamage, attackSpeed, settings, abilityCooldownTicks);
    }

    @Override
    protected void onRightClickAbility(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient()) {
            HitResult hitResult = player.raycast(64.0, 0.0F, false); // Raycast for 64 blocks, ignoring fluids
            if (hitResult.getType() != HitResult.Type.MISS) {
                Vec3d pos = hitResult.getPos();
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
                lightning.setPos(pos.getX(), pos.getY(), pos.getZ());
                world.spawnEntity(lightning);
            }
        }
    }

    // Thunder Axe does not have a special on-hit ability by default, but could be added here.
}
