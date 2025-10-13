package com.example.sololeveling;

import com.example.sololeveling.networking.ModMessages;
import com.example.sololeveling.renderer.SoloLevelingHudRenderer;
import com.example.sololeveling.util.SoloLevelingKeybinds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class SoloLevelingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SoloLevelingKeybinds.registerKeybinds();
        HudRenderCallback.EVENT.register(new SoloLevelingHudRenderer());
        ModMessages.registerS2CPackets(); // In case we add S2C later, currently not strictly needed for basic HUD
    }
}