package com.example.sololeveling.util;

import com.example.sololeveling.SoloLevelingMod;
import com.example.sololeveling.ability.Ability;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtLong;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class PlayerData implements IPlayerData {
    private int level;
    private int experience;
    private int mana;
    private int strength;
    private int stamina;
    private int agility;
    private int perception;
    private int intelligence;
    private final Map<String, Long> cooldowns;

    private static final int BASE_REQUIRED_EXP = 100;
    private static final int MANA_REGEN_INTERVAL = 20; // Every second
    private static final int MANA_REGEN_AMOUNT = 5; // Base mana regen
    private static final int BASE_MAX_MANA = 100;

    public PlayerData() {
        this.level = 1;
        this.experience = 0;
        this.mana = BASE_MAX_MANA;
        this.strength = 1;
        this.stamina = 1;
        this.agility = 1;
        this.perception = 1;
        this.intelligence = 1;
        this.cooldowns = new HashMap<>();
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getExperience() {
        return experience;
    }

    @Override
    public int getRequiredExperience() {
        return BASE_REQUIRED_EXP * level;
    }

    @Override
    public boolean addExperience(int amount) {
        this.experience += amount;
        boolean leveledUp = false;
        while (this.experience >= getRequiredExperience()) {
            this.experience -= getRequiredExperience();
            levelUp();
            leveledUp = true;
        }
        return leveledUp;
    }

    private void levelUp() {
        this.level++;
        this.mana = getMaxMana(); // Restore mana on level up
        // Auto-allocate attribute points for simplicity
        // In a more complex mod, players would choose where to put points
        if (level % 5 == 0) {
            this.strength++;
            this.stamina++;
            this.agility++;
            this.perception++;
            this.intelligence++;
        } else if (level % 2 == 0) {
            this.strength++;
            this.stamina++;
        } else {
            this.agility++;
            this.intelligence++;
        }

        SoloLevelingMod.LOGGER.info("Player leveled up to {}! Attributes: Str {}, Sta {}, Agi {}, Perc {}, Int {}",
                level, strength, stamina, agility, perception, intelligence);
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public int getMaxMana() {
        // Max mana scales with Intelligence
        return BASE_MAX_MANA + (intelligence * 10) + (level * 2);
    }

    @Override
    public void setMana(int mana) {
        this.mana = Math.max(0, Math.min(mana, getMaxMana()));
    }

    @Override
    public void addMana(int amount) {
        setMana(this.mana + amount);
    }

    @Override
    public void removeMana(int amount) {
        setMana(this.mana - amount);
    }

    @Override
    public void regenerateMana() {
        if (MinecraftServer.getServer().getTicks() % MANA_REGEN_INTERVAL == 0) {
            addMana(MANA_REGEN_AMOUNT + (intelligence / 2)); // Mana regen scales with Intelligence
        }
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public int getStamina() {
        return stamina;
    }

    @Override
    public int getAgility() {
        return agility;
    }

    @Override
    public int getPerception() {
        return perception;
    }

    @Override
    public int getIntelligence() {
        return intelligence;
    }

    @Override
    public boolean isOnCooldown(Ability ability) {
        return cooldowns.containsKey(ability.getId()) && cooldowns.get(ability.getId()) > MinecraftServer.getServer().getTicks();
    }

    @Override
    public void addCooldown(Ability ability, int ticks) {
        cooldowns.put(ability.getId(), MinecraftServer.getServer().getTicks() + ticks);
    }

    @Override
    public long getRemainingCooldownTicks(Ability ability) {
        if (!isOnCooldown(ability)) return 0;
        return cooldowns.get(ability.getId()) - MinecraftServer.getServer().getTicks();
    }

    @Override
    public void tickCooldowns() {
        // Remove expired cooldowns to prevent map from growing indefinitely
        long currentTick = MinecraftServer.getServer().getTicks();
        cooldowns.entrySet().removeIf(entry -> entry.getValue() <= currentTick);
    }

    @Override
    public NbtCompound writeToNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("Level", level);
        nbt.putInt("Experience", experience);
        nbt.putInt("Mana", mana);
        nbt.putInt("Strength", strength);
        nbt.putInt("Stamina", stamina);
        nbt.putInt("Agility", agility);
        nbt.putInt("Perception", perception);
        nbt.putInt("Intelligence", intelligence);

        NbtCompound cooldownsNbt = new NbtCompound();
        cooldowns.forEach((abilityId, endTime) -> cooldownsNbt.putLong(abilityId, endTime));
        nbt.put("Cooldowns", cooldownsNbt);

        return nbt;
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        this.level = nbt.getInt("Level");
        this.experience = nbt.getInt("Experience");
        this.mana = nbt.getInt("Mana");
        this.strength = nbt.getInt("Strength");
        this.stamina = nbt.getInt("Stamina");
        this.agility = nbt.getInt("Agility");
        this.perception = nbt.getInt("Perception");
        this.intelligence = nbt.getInt("Intelligence");

        if (nbt.contains("Cooldowns", NbtElement.COMPOUND_TYPE)) {
            NbtCompound cooldownsNbt = nbt.getCompound("Cooldowns");
            this.cooldowns.clear();
            cooldownsNbt.getKeys().forEach(abilityId -> {
                long endTime = cooldownsNbt.getLong(abilityId);
                this.cooldowns.put(abilityId, endTime);
            });
        }

        // Ensure mana doesn't exceed max after loading (e.g. if intelligence changed, or max_mana was updated)
        this.mana = Math.min(this.mana, getMaxMana());
    }
}