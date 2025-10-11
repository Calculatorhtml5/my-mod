package com.example.elementalweapons;

import com.example.elementalweapons.item.custom.ElementalWeaponItem;
import com.example.elementalweapons.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import org.lwjgl.glfw.GLFW;

public class ElementalWeaponsModClient implements ClientModInitializer {

    public static KeyBinding abilityKey;

    @Override
    public void onInitializeClient() {
        abilityKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.elementalweapons.ability",
                InputUtil.Type.KEYSYM, 
                GLFW.GLFW_KEY_R,
                "category.elementalweapons.keys"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (abilityKey.wasPressed()) {
                if (client.player != null) {
                    ItemStack mainHandStack = client.player.getMainHandStack();
                    if (mainHandStack.getItem() instanceof ElementalWeaponItem weapon) {
                        // Check cooldown client-side for immediate feedback
                        if (weapon.isAbilityReady(mainHandStack, client.world.getTime())) {
                            ClientPlayNetworking.send(ModMessages.ABILITY_PACKET_ID, ModMessages.createAbilityPacket());
                        } else {
                            // Could add a chat message or UI feedback here: "Ability on cooldown!"
                            // client.player.sendMessage(Text.translatable("message.elementalweapons.cooldown"), true);
                        }
                    }
                }
            }
        });
    }
}
