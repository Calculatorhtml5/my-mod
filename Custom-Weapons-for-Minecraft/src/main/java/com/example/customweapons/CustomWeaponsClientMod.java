package com.example.customweapons;

import net.fabricmc.api.ClientModInitializer;

// This class is specifically for client-side only initializations if any are needed.
// Currently, keybindings are handled in KeyBindingUtil, which is called from the main mod initializer.
public class CustomWeaponsClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Client-side initialization code goes here.
        // Keybindings are registered via the main mod initializer calling KeyBindingUtil.registerKeyBindings().
    }
}
