package com.example.sololeveling.ability;

import com.example.sololeveling.util.PlayerData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class ShadowsBlessingAbility extends Ability {
    public ShadowsBlessingAbility() {
        super("shadows_blessing", 100, 20 * 60, // 60 second cooldown
                Text.translatable("ability.sololeveling.shadows_blessing.name"),
                Text.translatable("ability.sololeveling.shadows_blessing.description"));
    }

    @Override
    protected void execute(PlayerEntity player, PlayerData playerData) {
        if (player.world instanceof ServerWorld serverWorld) {
            double radius = 7.0;
            int durationTicks = 20 * 10; // 10 seconds duration

            // Apply Strength to player
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, durationTicks, 0, false, true, true));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, durationTicks, 0, false, true, true));

            // Deal damage/wither to nearby hostile entities
            Vec3d pos = player.getPos();
            Box searchBox = new Box(pos.x - radius, pos.y - radius, pos.z - radius, pos.x + radius, pos.y + radius, pos.z + radius);
            List<LivingEntity> hostileEntities = serverWorld.getEntitiesByClass(LivingEntity.class, searchBox, (entity ->
                    entity != player && entity.isUndead() && !entity.isTeammate(player))); // Target undead or general hostile

            for (LivingEntity entity : hostileEntities) {
                if (entity.squaredDistanceTo(player) < radius * radius) {
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, durationTicks, 0, false, true, true));
                }
            }

            // Visual and sound effects
            serverWorld.spawnParticles(ParticleTypes.SOUL, pos.x, pos.y + 1, pos.z, 30, radius * 0.5, radius * 0.5, radius * 0.5, 0.0);
            serverWorld.playSound(null, pos.x, pos.y, pos.z, SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.PLAYERS, 1.0f, 0.7f + player.getRandom().nextFloat() * 0.6f);
        }
    }
}