package com.example.sololeveling.ability;

import com.example.sololeveling.util.PlayerData;
import net.minecraft.text.Text;
import net.minecraft.entity.player.PlayerEntity;

import java.util.function.Consumer;

public abstract class Ability {
    private final String id;
    private final int manaCost;
    private final int cooldownTicks;
    private final Text name;
    private final Text description;

    public Ability(String id, int manaCost, int cooldownTicks, Text name, Text description) {
        this.id = id;
        this.manaCost = manaCost;
        this.cooldownTicks = cooldownTicks;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getCooldown() {
        return cooldownTicks;
    }

    public Text getName() {
        return name;
    }

    public Text getDescription() {
        return description;
    }

    public boolean canUse(PlayerEntity player, PlayerData playerData) {
        return playerData.getMana() >= manaCost && !playerData.isOnCooldown(this);
    }

    // Method to be overridden by specific abilities
    protected abstract void execute(PlayerEntity player, PlayerData playerData);

    // Wrapper to apply common logic (mana cost, cooldown, etc.)
    public void tryExecute(PlayerEntity player, PlayerData playerData) {
        if (canUse(player, playerData)) {
            playerData.removeMana(manaCost);
            playerData.addCooldown(this, cooldownTicks);
            execute(player, playerData);
            player.sendMessage(Text.translatable("message." + getId() + ".used").append(Text.literal(" (")
                    .append(Text.literal(String.valueOf(manaCost))).append(Text.literal(" Mana)"))), true);
        } else if (playerData.getMana() < manaCost) {
            player.sendMessage(Text.translatable("message.sololeveling.not_enough_mana"), true);
        } else if (playerData.isOnCooldown(this)) {
            long remainingTicks = playerData.getRemainingCooldownTicks(this);
            player.sendMessage(Text.translatable("message.sololeveling.on_cooldown", (remainingTicks / 20)), true);
        }
    }

    public static Ability byId(String id) {
        return ModAbilities.ABILITIES.get(id);
    }
}