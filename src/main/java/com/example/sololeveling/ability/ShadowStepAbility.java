package com.example.sololeveling.ability;

import com.example.sololeveling.util.PlayerData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class ShadowStepAbility extends Ability {
    public ShadowStepAbility() {
        super("shadow_step", 40, 20 * 15, // 15 second cooldown
                Text.translatable("ability.sololeveling.shadow_step.name"),
                Text.translatable("ability.sololeveling.shadow_step.description"));
    }

    @Override
    protected void execute(PlayerEntity player, PlayerData playerData) {
        Vec3d lookVec = player.getRotationVector();
        double distance = 6.0; // Dash distance

        Vec3d targetPos = player.getPos().add(lookVec.multiply(distance));

        // Teleport the player, ensuring they don't go into blocks
        // More sophisticated collision checking can be added
        player.teleport(targetPos.getX(), targetPos.getY(), targetPos.getZ());

        if (player.world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.POOF, player.getX(), player.getY() + 0.5, player.getZ(), 15, 0.3, 0.3, 0.3, 0.0);
            serverWorld.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 0.5f, 1.5f + player.getRandom().nextFloat() * 0.5f);
        }
    }
}