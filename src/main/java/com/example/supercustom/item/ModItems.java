package com.example.supercustom.item;

import com.example.supercustom.SuperCustom;
import com.example.supercustom.item.custom.FireSwordItem;
import com.example.supercustom.item.custom.IceSwordItem;
import com.example.supercustom.item.custom.LightningSwordItem;
import com.example.supercustom.util.ModToolMaterials;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item ELEMENTAL_GEM = registerItem("elemental_gem",
            new Item(new Item.Settings()));

    public static final SwordItem FIRE_SWORD = registerItem("fire_sword",
            new FireSwordItem(ModToolMaterials.ELEMENTAL, 3, -2.4f, new Item.Settings().fireproof()));

    public static final SwordItem ICE_SWORD = registerItem("ice_sword",
            new IceSwordItem(ModToolMaterials.ELEMENTAL, 3, -2.4f, new Item.Settings().fireproof()));

    public static final SwordItem LIGHTNING_SWORD = registerItem("lightning_sword",
            new LightningSwordItem(ModToolMaterials.ELEMENTAL, 3, -2.4f, new Item.Settings().fireproof()));


    private static <T extends Item> T registerItem(String name, T item) {
        return Registry.register(Registries.ITEM, new Identifier(SuperCustom.MOD_ID, name), item);
    }

    public static void addItemsToItemGroup() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(FIRE_SWORD);
            entries.add(ICE_SWORD);
            entries.add(LIGHTNING_SWORD);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(ELEMENTAL_GEM);
        });
    }

    public static void registerModItems() {
        SuperCustom.LOGGER.debug("Registering Mod Items for " + SuperCustom.MOD_ID);
        addItemsToItemGroup();
    }
}
