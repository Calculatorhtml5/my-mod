package com.example.sololeveling.event;

import com.example.sololeveling.SoloLevelingMod;
import com.example.sololeveling.util.PlayerData;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class LivingEntityDeathHandler implements ServerLivingEntityEvents.AfterDeath {

    @Override
    public void afterDeath(LivingEntity entity, DamageSource damageSource) {
        if (damageSource.getAttacker() instanceof PlayerEntity player) {
            if (entity.world.isClient) return; // Only process on server side

            PlayerData playerData = player.getAttachedData(SoloLevelingMod.PLAYER_DATA);
            if (playerData == null) {
                SoloLevelingMod.LOGGER.error("Player data not found for player: {}", player.getName().getString());
                return;
            }

            int expGain = calculateExperience(entity);
            if (expGain > 0) {
                boolean leveledUp = playerData.addExperience(expGain);
                if (leveledUp) {
                    player.sendMessage(Text.translatable("message.sololeveling.level_up", playerData.getLevel()), false);
                } else {
                    player.sendMessage(Text.translatable("message.sololeveling.exp_gain", expGain, playerData.getExperience(), playerData.getRequiredExperience()), true);
                }
                // Optionally drop less vanilla XP orbs
                // entity.world.spawnEntity(new ExperienceOrbEntity(entity.world, entity.getX(), entity.getY(), entity.getZ(), expGain / 5));
            }
        }
    }

    private int calculateExperience(LivingEntity entity) {
        // Basic experience calculation. Can be expanded for more complex logic.
        // e.g., stronger mobs give more XP, specific mob types, etc.
        int baseExp = 0;
        if (entity instanceof PlayerEntity) {
            baseExp = 0; // Don't get exp from killing other players
        } else if (entity.isUndead() || entity.isBaby()) { // undead mobs
            baseExp = 5;
        } else if (entity.getType().isHostile()) {
            baseExp = 10; // General hostile mobs
        } else {
            baseExp = 2; // Passive mobs
        }

        return (int) (baseExp + (entity.getMaxHealth() / 2.0)); // Scale with health
    }
}