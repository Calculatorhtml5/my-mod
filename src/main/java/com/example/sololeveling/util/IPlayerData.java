package com.example.sololeveling.util;

import com.example.sololeveling.ability.Ability;
import net.minecraft.nbt.NbtCompound;

public interface IPlayerData {
    int getLevel();
    int getExperience();
    int getRequiredExperience();
    boolean addExperience(int amount);

    int getMana();
    int getMaxMana();
    void setMana(int mana);
    void addMana(int amount);
    void removeMana(int amount);
    void regenerateMana();

    int getStrength();
    int getStamina();
    int getAgility();
    int getPerception();
    int getIntelligence();

    boolean isOnCooldown(Ability ability);
    void addCooldown(Ability ability, int ticks);
    long getRemainingCooldownTicks(Ability ability);
    void tickCooldowns();

    // NBT Serialization
    NbtCompound writeToNbt();
    void readFromNbt(NbtCompound nbt);
}