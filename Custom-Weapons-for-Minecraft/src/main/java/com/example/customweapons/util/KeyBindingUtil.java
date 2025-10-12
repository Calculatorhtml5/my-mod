package com.example.customweapons.util;

import com.example.customweapons.item.ElementalSwordItem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyBindingUtil {
    public static final String KEY_CATEGORY_CUSTOMWEAPONS = "key.category.customweapons.custom_weapons";
    public static final KeyBinding ACTIVATE_ABILITY_KEY = new KeyBinding(
            "key.customweapons.activate_ability", // The translation key of the keybinding
            InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard keys
            GLFW.GLFW_KEY_R, // The default keycode (R key)
            KEY_CATEGORY_CUSTOMWEAPONS // The category of the keybinding
    );

    public static void registerKeyBindings() {
        KeyBindingHelper.registerKeyBinding(ACTIVATE_ABILITY_KEY);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (ACTIVATE_ABILITY_KEY.wasPressed()) {
                if (client.player != null && client.player.getMainHandStack().getItem() instanceof ElementalSwordItem elementalSword) {
                    elementalSword.useAbility(client.player.getWorld(), client.player, client.player.getActiveHand());
                }
            }
        });
    }
}
