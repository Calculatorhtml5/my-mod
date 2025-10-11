package com.example.elementalweapons.item;

import com.example.elementalweapons.ElementalWeaponsMod;
import com.example.elementalweapons.item.custom.*;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    // Using Netherite-tier tool material for elemental weapons
    public static final ToolMaterial ELEMENTAL_TOOL_MATERIAL = ToolMaterials.NETHERITE;

    public static final Item FIRE_SWORD = registerItem("fire_sword",
            new FireSwordItem(ELEMENTAL_TOOL_MATERIAL, 3, -2.4F, new Item.Settings()));
    public static final Item ICE_AXE = registerItem("ice_axe",
            new IceAxeItem(ELEMENTAL_TOOL_MATERIAL, 5, -3.0F, new Item.Settings()));
    public static final Item LIGHTNING_SCYTHE = registerItem("lightning_scythe",
            new LightningScytheItem(ELEMENTAL_TOOL_MATERIAL, 4, -2.8F, new Item.Settings()));
    public static final Item EARTH_HAMMER = registerItem("earth_hammer",
            new EarthHammerItem(ELEMENTAL_TOOL_MATERIAL, 7, -3.2F, new Item.Settings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ElementalWeaponsMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ElementalWeaponsMod.LOGGER.info("Registering Mod Items for " + ElementalWeaponsMod.MOD_ID);
        ModItemGroups.registerItemGroups();
    }
}
