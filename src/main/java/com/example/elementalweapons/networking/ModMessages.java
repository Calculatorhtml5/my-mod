package com.example.elementalweapons.networking;

import com.example.elementalweapons.ElementalWeaponsMod;
import com.example.elementalweapons.item.custom.ElementalWeaponItem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier ABILITY_PACKET_ID = new Identifier(ElementalWeaponsMod.MOD_ID, "ability_packet");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(ABILITY_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            // This code runs on the server when it receives the packet from the client.
            server.execute(() -> {
                ItemStack mainHandStack = player.getMainHandStack();
                if (mainHandStack.getItem() instanceof ElementalWeaponItem weapon) {
                    if (weapon.isAbilityReady(mainHandStack, player.getWorld().getTime())) {
                        weapon.activateAbility(player, player.getWorld(), mainHandStack);
                        weapon.setAbilityOnCooldown(mainHandStack, player.getWorld().getTime());
                    }
                }
            });
        });
    }

    public static PacketByteBuf createAbilityPacket() {
        return PacketByteBufs.create(); // Empty packet, just for signaling
    }
}
