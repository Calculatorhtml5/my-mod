package com.example.customweapons.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class ElementalSwordItem extends SwordItem {
    protected static final long COOLDOWN_TICKS = 60; // 3 seconds

    public ElementalSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    public void useAbility(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!player.getItemCooldownManager().is  OnCooldown(this)) {
            if (onAbilityUsed(world, player, stack)) {
                stack.damage(1, player, (p) -> p.sendToolBreakStatus(hand)); // Consume 1 durability
                player.getItemCooldownManager().set(this, (int) COOLDOWN_TICKS);
                player.sendMessage(Text.translatable("item.customweapons.ability_used").formatted(Formatting.GOLD), true);
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_TOTEM_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            } else {
                player.sendMessage(Text.translatable("item.customweapons.ability_failed").formatted(Formatting.RED), true);
            }
        } else {
            player.sendMessage(Text.translatable("item.customweapons.ability_on_cooldown", (int)(player.getItemCooldownManager().getCooldownProgress(this, 1.0F) * COOLDOWN_TICKS / 20) + 1).formatted(Formatting.YELLOW), true);
        }
    }

    protected abstract boolean onAbilityUsed(World world, PlayerEntity player, ItemStack stack);
}
