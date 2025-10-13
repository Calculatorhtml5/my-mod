package com.example.sololeveling.renderer;

import com.example.sololeveling.SoloLevelingMod;
import com.example.sololeveling.util.PlayerData;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.text.Text;

public class SoloLevelingHudRenderer implements HudRenderCallback {
    private static final Identifier MANA_BAR_TEXTURE = new Identifier(SoloLevelingMod.MOD_ID, "textures/gui/mana_bar.png");

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;

        if (player == null || client.options.hudHidden) {
            return;
        }

        PlayerData playerData = player.getAttachedData(SoloLevelingMod.PLAYER_DATA);
        if (playerData == null) {
            return;
        }

        int scaledWidth = drawContext.getScaledWindowWidth();
        int scaledHeight = drawContext.getScaledWindowHeight();
        TextRenderer textRenderer = client.textRenderer;

        // Render Level and XP
        String levelText = "LVL: " + playerData.getLevel();
        String expText = "EXP: " + playerData.getExperience() + " / " + playerData.getRequiredExperience();

        int levelTextWidth = textRenderer.getWidth(levelText);
        int expTextWidth = textRenderer.getWidth(expText);

        int xLevel = scaledWidth - levelTextWidth - 10;
        int yLevel = 10;
        int xExp = scaledWidth - expTextWidth - 10;
        int yExp = yLevel + textRenderer.fontHeight + 2;

        drawContext.drawTextWithShadow(textRenderer, Text.literal(levelText), xLevel, yLevel, 0xFFFFFF);
        drawContext.drawTextWithShadow(textRenderer, Text.literal(expText), xExp, yExp, 0xADD8E6); // Light Blue for XP

        // Render Mana Bar
        int manaBarWidth = 100;
        int manaBarHeight = 10;
        int manaBarX = scaledWidth - manaBarWidth - 10;
        int manaBarY = yExp + textRenderer.fontHeight + 5;

        float manaProgress = (float) playerData.getMana() / playerData.getMaxMana();
        int filledManaWidth = (int) (manaBarWidth * manaProgress);

        // Render mana bar background (grey)
        drawContext.fill(manaBarX, manaBarY, manaBarX + manaBarWidth, manaBarY + manaBarHeight, 0xFF555555); // Dark Grey background
        // Render filled mana (blue)
        drawContext.fill(manaBarX, manaBarY, manaBarX + filledManaWidth, manaBarY + manaBarHeight, 0xFF0000FF); // Blue mana

        // Render mana text
        String manaText = "MANA: " + playerData.getMana() + " / " + playerData.getMaxMana();
        int manaTextWidth = textRenderer.getWidth(manaText);
        drawContext.drawTextWithShadow(textRenderer, Text.literal(manaText), manaBarX + manaBarWidth / 2 - manaTextWidth / 2, manaBarY + 1, 0xFFFFFF);
    }
}