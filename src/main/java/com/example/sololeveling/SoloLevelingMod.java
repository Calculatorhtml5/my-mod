package com.example.sololeveling;

import com.example.sololeveling.ability.ModAbilities;
import com.example.sololeveling.event.LivingEntityDeathHandler;
import com.example.sololeveling.event.PlayerTickHandler;
import com.example.sololeveling.networking.ModMessages;
import com.example.sololeveling.util.PlayerData;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoloLevelingMod implements ModInitializer {

    public static final String MOD_ID = "sololeveling";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final AttachmentType<PlayerData> PLAYER_DATA;

    static {
        PLAYER_DATA = AttachmentRegistry.create(new Identifier(MOD_ID, "player_data"), PlayerData::new);
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Solo Leveling Mod!");

        ModMessages.registerC2SPackets();

        // Register abilities
        ModAbilities.registerAbilities();

        // Register event handlers
        ServerLivingEntityEvents.AFTER_DEATH.register(new LivingEntityDeathHandler());
        ServerTickEvents.END_SERVER_TICK.register(new PlayerTickHandler());
        ServerPlayerEvents.AFTER_RESPAWN.register(new PlayerTickHandler());

        LOGGER.info("Solo Leveling Mod Initialized!");
    }
}