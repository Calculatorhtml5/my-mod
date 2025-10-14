package com.example.superweapons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public abstract class SuperweaponItem extends SwordItem {
    private final int abilityCooldownTicks;

    public SuperweaponItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, int abilityCooldownTicks) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        this.abilityCooldownTicks = abilityCooldownTicks;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!world.isClient()) {
            if (!player.getItemCooldownManager().isCoolingDown(this)) {
                onRightClickAbility(world, player, hand);
                player.getItemCooldownManager().set(this, abilityCooldownTicks);
                player.swing(hand, true);
                return TypedActionResult.success(itemStack, world.isClient());
            } else {
                // Send cooldown message only if not already displayed (could spam in client side)
                if (player.getStackInHand(hand).getItem() == this) {
                    player.sendMessage(Text.translatable("item.superweapons.ability_on_cooldown", (int) (player.getItemCooldownManager().getCooldownProgress(this, 0) * abilityCooldownTicks / 20.0)), true);
                }
                return TypedActionResult.fail(itemStack);
            }
        }
        return TypedActionResult.pass(itemStack);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        onHitAbility(stack, target, attacker);
        return super.postHit(stack, target, attacker);
    }

    protected abstract void onRightClickAbility(World world, PlayerEntity player, Hand hand);
    protected void onHitAbility(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Default empty implementation, specific weapons can override this
    }
}
