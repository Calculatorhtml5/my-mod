package com.example.elementalweapons.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ElementalWeaponItem extends SwordItem {
    private static final String LAST_ACTIVATION_KEY = "elementalweapons.last_ability_activation";

    public ElementalWeaponItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    protected abstract void activateAbility(PlayerEntity player, World world, ItemStack stack);
    protected abstract int getAbilityCooldown(); // in ticks
    public abstract Text getAbilityDescription();

    public boolean isAbilityReady(ItemStack stack, long worldTime) {
        NbtCompound nbt = stack.getOrCreateNbt();
        long lastActivationTime = nbt.getLong(LAST_ACTIVATION_KEY);
        return (worldTime - lastActivationTime) >= getAbilityCooldown();
    }

    public void setAbilityOnCooldown(ItemStack stack, long worldTime) {
        stack.getOrCreateNbt().putLong(LAST_ACTIVATION_KEY, worldTime);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.elementalweapons.ability_prompt").formatted(Formatting.GRAY));
        tooltip.add(getAbilityDescription().formatted(Formatting.AQUA));

        if (world != null) {
            NbtCompound nbt = stack.getOrCreateNbt();
            long lastActivationTime = nbt.getLong(LAST_ACTIVATION_KEY);
            long currentTime = world.getTime();
            long cooldownRemainingTicks = lastActivationTime + getAbilityCooldown() - currentTime;

            if (cooldownRemainingTicks > 0) {
                double cooldownSeconds = cooldownRemainingTicks / 20.0;
                tooltip.add(Text.translatable("item.elementalweapons.cooldown_remaining", String.format("%.1f", cooldownSeconds)).formatted(Formatting.RED));
            } else {
                tooltip.add(Text.translatable("item.elementalweapons.cooldown_ready").formatted(Formatting.GREEN));
            }
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
