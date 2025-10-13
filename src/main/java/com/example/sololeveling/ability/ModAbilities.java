package com.example.sololeveling.ability;

import java.util.HashMap;
import java.util.Map;

public class ModAbilities {
    public static final Map<String, Ability> ABILITIES = new HashMap<>();

    public static final Ability SHADOW_STEP = register(new ShadowStepAbility());
    public static final Ability MANA_BURST = register(new ManaBurstAbility());
    public static final Ability IRON_SKIN = register(new IronSkinAbility());
    public static final Ability SHADOWS_BLESSING = register(new ShadowsBlessingAbility());

    private static Ability register(Ability ability) {
        ABILITIES.put(ability.getId(), ability);
        return ability;
    }

    public static void registerAbilities() {
        // This method is called to ensure static initialization of abilities
        // No actual code here, just triggers the static block above.
    }
}