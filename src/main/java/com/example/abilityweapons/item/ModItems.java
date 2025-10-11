package com.example.abilityweapons.item;

import com.example.abilityweapons.AbilityWeapons;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    // Crafting Components
    public static final Item WEAPON_CORE = registerItem("weapon_core", new Item(new FabricItemSettings()));
    public static final Item INFUSED_FIRE_SHARD = registerItem("infused_fire_shard", new Item(new FabricItemSettings()));
    public static final Item INFUSED_ICE_SHARD = registerItem("infused_ice_shard", new Item(new FabricItemSettings()));
    public static final Item INFUSED_STORM_SHARD = registerItem("infused_storm_shard", new Item(new FabricItemSettings()));

    // Weapons
    public static final Item FIREBRAND = registerItem("firebrand",
            new FirebrandItem(ModToolMaterials.ABILITY_WEAPON, 3, -2.4f, new FabricItemSettings()));
    public static final Item FROSTBITE = registerItem("frostbite",
            new FrostbiteItem(ModToolMaterials.ABILITY_WEAPON, 3, -2.4f, new FabricItemSettings()));
    public static final Item THUNDERSTRIKE = registerItem("thunderstrike",
            new ThunderstrikeItem(ModToolMaterials.ABILITY_WEAPON, 3, -2.4f, new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(AbilityWeapons.MOD_ID, name), item);
    }

    public static void addItemsToCreativeTabs() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(FIREBRAND);
            entries.add(FROSTBITE);
            entries.add(THUNDERSTRIKE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(WEAPON_CORE);
            entries.add(INFUSED_FIRE_SHARD);
            entries.add(INFUSED_ICE_SHARD);
            entries.add(INFUSED_STORM_SHARD);
        });
    }

    public static void registerModItems() {
        AbilityWeapons.LOGGER.debug("Registering Mod Items for " + AbilityWeapons.MOD_ID);
        addItemsToCreativeTabs();
    }
}
