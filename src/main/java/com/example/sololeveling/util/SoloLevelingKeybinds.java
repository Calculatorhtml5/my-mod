package com.example.sololeveling.util;

import com.example.sololeveling.SoloLevelingMod;
import com.example.sololeveling.ability.ModAbilities;
import com.example.sololeveling.networking.packet.AbilityC2SPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class SoloLevelingKeybinds {
    public static final Map<String, KeyBinding> ABILITY_KEYBINDS = new HashMap<>();

    public static final KeyBinding ABILITY_Z = registerKeyBinding("ability_z", GLFW.GLFW_KEY_Z, ModAbilities.SHADOW_STEP.getId());
    public static final KeyBinding ABILITY_X = registerKeyBinding("ability_x", GLFW.GLFW_KEY_X, ModAbilities.MANA_BURST.getId());
    public static final KeyBinding ABILITY_C = registerKeyBinding("ability_c", GLFW.GLFW_KEY_C, ModAbilities.IRON_SKIN.getId());
    public static final KeyBinding ABILITY_V = registerKeyBinding("ability_v", GLFW.GLFW_KEY_V, ModAbilities.SHADOWS_BLESSING.getId());

    private static KeyBinding registerKeyBinding(String name, int key, String abilityId) {
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.sololeveling." + name,
                InputUtil.Type.KEYSYM,
                key,
                "category.sololeveling.abilities"
        ));
        ABILITY_KEYBINDS.put(abilityId, keyBinding);
        return keyBinding;
    }

    public static void registerKeybinds() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                // Iterate through registered ability keybinds and check if pressed
                ABILITY_KEYBINDS.forEach((abilityId, keyBinding) -> {
                    if (keyBinding.wasPressed()) {
                        AbilityC2SPacket.receive(client.player.getServer(), client.player, null, AbilityC2SPacket.create(abilityId), null);
                    }
                });
            }
        });
    }
}