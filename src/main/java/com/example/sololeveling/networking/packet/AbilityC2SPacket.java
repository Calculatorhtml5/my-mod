package com.example.sololeveling.networking.packet;

import com.example.sololeveling.SoloLevelingMod;
import com.example.sololeveling.ability.Ability;
import com.example.sololeveling.util.PlayerData;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class AbilityC2SPacket {
    public static final Identifier ID = new Identifier(SoloLevelingMod.MOD_ID, "ability_c2s");

    public static PacketByteBuf create(String abilityId) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(abilityId);
        return buf;
    }

    public static void send(String abilityId) {
        ServerPlayNetworking.send(ID, create(abilityId));
    }

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, ServerPlayNetworking.PacketSender responseSender) {
        String abilityId = buf.readString();

        server.execute(() -> {
            Ability ability = Ability.byId(abilityId);
            if (ability != null) {
                PlayerData playerData = player.getAttachedData(SoloLevelingMod.PLAYER_DATA);
                if (playerData != null) {
                    ability.tryExecute(player, playerData);
                } else {
                    SoloLevelingMod.LOGGER.warn("Player data not found for player: {}", player.getName().getString());
                }
            } else {
                SoloLevelingMod.LOGGER.warn("Unknown ability ID received: {}", abilityId);
            }
        });
    }
}