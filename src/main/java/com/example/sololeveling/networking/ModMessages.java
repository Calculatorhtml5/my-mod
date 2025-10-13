package com.example.sololeveling.networking;

import com.example.sololeveling.SoloLevelingMod;
import com.example.sololeveling.networking.packet.AbilityC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {

    public static final Identifier ABILITY_PACKET_ID = new Identifier(SoloLevelingMod.MOD_ID, "ability_packet");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(ABILITY_PACKET_ID, AbilityC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        // Currently no S2C packets are needed for this mod.
        // If you want to send data from server to client, register them here.
    }
}