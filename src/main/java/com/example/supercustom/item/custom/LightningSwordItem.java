package com.example.supercustom.item.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class LightningSwordItem extends SwordItem {
    private static final int COOLDOWN_TICKS = 100; // 5 seconds
    private static final double RAYCAST_DISTANCE = 32.0; // How far the lightning can strike

    public LightningSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient() && !user.getItemCooldownManager().isCoolingDown(this)) {
            if (world instanceof ServerWorld serverWorld) {
                // Perform a raycast to find a target location
                Vec3d startVec = user.getEyePos();
                Vec3d lookVec = user.getRotationVec(1.0F);
                Vec3d endVec = startVec.add(lookVec.multiply(RAYCAST_DISTANCE));

                HitResult hitResult = world.raycast(new RaycastContext(startVec, endVec, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, user));

                BlockPos strikePos;
                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    strikePos = BlockPos.ofFloored(hitResult.getPos());
                } else if (hitResult.getType() == HitResult.Type.ENTITY) {
                    strikePos = BlockPos.ofFloored(hitResult.getPos());
                } else {
                    // If no target, strike a bit in front of the player
                    strikePos = BlockPos.ofFloored(endVec);
                }

                // Play sound
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.WEATHER, 1.0F, 1.0F);
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.WEATHER, 1.0F, 1.0F);

                // Spawn lightning bolt
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
                lightning.setPos(strikePos.getX() + 0.5, strikePos.getY(), strikePos.getZ() + 0.5);
                serverWorld.spawnEntity(lightning);

                // Apply cooldown
                user.getItemCooldownManager().set(this, COOLDOWN_TICKS);

                // Damage item
                itemStack.damage(1, user, player -> player.sendToolBreakStatus(hand));
            }
            return TypedActionResult.success(itemStack, world.isClient());
        }
        return TypedActionResult.pass(itemStack);
    }
}
