package com.example.sololeveling.ability;

import com.example.sololeveling.util.PlayerData;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class IronSkinAbility extends Ability {
    public IronSkinAbility() {
        super("iron_skin", 60, 20 * 30, // 30 second cooldown
                Text.translatable("ability.sololeveling.iron_skin.name"),
                Text.translatable("ability.sololeveling.iron_skin.description"));
    }

    @Override
    protected void execute(PlayerEntity player, PlayerData playerData) {
        int duration = 20 * 10; // 10 seconds
        int resistanceLevel = 1; // Resistance I
        int slownessLevel = 0; // Slowness I, or 0 for no slowness

        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, duration, resistanceLevel, false, true, true));
        // Optionally add slowness as a trade-off
        // player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, duration, slownessLevel, false, true, true));
        player.world.sendEntityStatus(player, (byte) 24); // Particles for potion effect
    }
}