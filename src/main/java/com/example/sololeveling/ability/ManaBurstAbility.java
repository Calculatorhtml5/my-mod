package com.example.sololeveling.ability;

import com.example.sololeveling.util.PlayerData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class ManaBurstAbility extends Ability {
    public ManaBurstAbility() {
        super("mana_burst", 75, 20 * 25, // 25 second cooldown
                Text.translatable("ability.sololeveling.mana_burst.name"),
                Text.translatable("ability.sololeveling.mana_burst.description"));
    }

    @Override
    protected void execute(PlayerEntity player, PlayerData playerData) {
        if (player.world instanceof ServerWorld serverWorld) {
            double radius = 5.0;
            float damage = 8.0f + playerData.getIntelligence() * 0.5f; // Scale with intelligence

            Vec3d pos = player.getPos();
            Box searchBox = new Box(pos.x - radius, pos.y - radius, pos.z - radius, pos.x + radius, pos.y + radius, pos.z + radius);

            List<LivingEntity> entities = serverWorld.getEntitiesByClass(LivingEntity.class, searchBox, (entity ->
                    entity != player && !entity.isTeammate(player)));

            DamageSources damageSources = serverWorld.getDamageSources();
            DamageSource source = damageSources.magic(player, player);

            for (LivingEntity entity : entities) {
                if (entity.squaredDistanceTo(player) < radius * radius) {
                    entity.damage(source, damage);
                }
            }

            // Visual and sound effects
            serverWorld.spawnParticles(ParticleTypes.ENCHANT, pos.x, pos.y + 0.5, pos.z, 50, radius * 0.5, radius * 0.5, radius * 0.5, 0.0);
            serverWorld.playSound(null, pos.x, pos.y, pos.z, SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL, SoundCategory.PLAYERS, 1.0f, 0.8f + player.getRandom().nextFloat() * 0.4f);
        }
    }
}