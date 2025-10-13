package com.example.sololeveling.event;

import com.example.sololeveling.SoloLevelingMod;
import com.example.sololeveling.util.PlayerData;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class PlayerTickHandler implements ServerTickEvents.EndTick, ServerPlayerEvents.AfterRespawn {

    private static final UUID STRENGTH_MODIFIER_UUID = UUID.fromString("8e6d017a-c7a3-4b6a-8b8b-9e4a3c2e1f2d");
    private static final UUID STAMINA_MODIFIER_UUID = UUID.fromString("a1b2c3d4-e5f6-7890-1234-567890abcdef");
    private static final UUID AGILITY_MODIFIER_UUID = UUID.fromString("cba98765-4321-fedc-ba98-76543210fedc");


    @Override
    public void onEndTick(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            PlayerData playerData = player.getAttachedData(SoloLevelingMod.PLAYER_DATA);
            if (playerData == null) continue;

            playerData.tickCooldowns();
            playerData.regenerateMana();

            // Apply attributes from player data to vanilla attributes
            applyAttributeModifiers(player, playerData);
        }
    }

    // This is called when a player respawns, ensuring attributes are reapplied
    @Override
    public void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive) {
        // Transfer data to the new player instance
        PlayerData oldData = oldPlayer.getAttachedData(SoloLevelingMod.PLAYER_DATA);
        if (oldData != null) {
            PlayerData newData = newPlayer.getAttachedData(SoloLevelingMod.PLAYER_DATA);
            if (newData != null) {
                newData.readFromNbt(oldData.writeToNbt()); // Deep copy of data
                SoloLevelingMod.LOGGER.info("Transferred Solo Leveling data to respawned player {}. Level: {}", newPlayer.getName().getString(), newData.getLevel());
            } else {
                SoloLevelingMod.LOGGER.error("New player data not found after respawn for {}. This shouldn't happen!", newPlayer.getName().getString());
            }
        }

        // Reapply attribute modifiers after respawn
        PlayerData playerData = newPlayer.getAttachedData(SoloLevelingMod.PLAYER_DATA);
        if (playerData != null) {
            applyAttributeModifiers(newPlayer, playerData);
        }
    }

    private void applyAttributeModifiers(ServerPlayerEntity player, PlayerData playerData) {
        // Strength -> Attack Damage
        EntityAttributeInstance attackDamage = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (attackDamage != null) {
            attackDamage.removeModifier(STRENGTH_MODIFIER_UUID);
            double strengthBonus = playerData.getStrength() * 0.5; // 0.5 attack damage per Strength point
            if (strengthBonus > 0) {
                attackDamage.addTemporaryModifier(new EntityAttributeModifier(STRENGTH_MODIFIER_UUID, "SoloLevelingStrength", strengthBonus, EntityAttributeModifier.Operation.ADDITION));
            }
        }

        // Stamina -> Max Health
        EntityAttributeInstance maxHealth = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.removeModifier(STAMINA_MODIFIER_UUID);
            double staminaBonus = playerData.getStamina() * 1.0; // 1.0 max health per Stamina point
            if (staminaBonus > 0) {
                maxHealth.addTemporaryModifier(new EntityAttributeModifier(STAMINA_MODIFIER_UUID, "SoloLevelingStamina", staminaBonus, EntityAttributeModifier.Operation.ADDITION));
            }
            // Ensure current health doesn't exceed new max health, or fill to new max if increased
            if (player.getHealth() > player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            }
        }

        // Agility -> Movement Speed
        EntityAttributeInstance movementSpeed = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (movementSpeed != null) {
            movementSpeed.removeModifier(AGILITY_MODIFIER_UUID);
            double agilityBonus = playerData.getAgility() * 0.005; // 0.5% speed per Agility point
            if (agilityBonus > 0) {
                movementSpeed.addTemporaryModifier(new EntityAttributeModifier(AGILITY_MODIFIER_UUID, "SoloLevelingAgility", agilityBonus, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
    }
}