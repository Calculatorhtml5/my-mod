package com.example.customweapons;

import com.example.customweapons.item.*;
import com.example.customweapons.util.KeyBindingUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CustomWeaponsMod implements ModInitializer {
    public static final String MOD_ID = "customweapons";

    public static final Item FIRE_SWORD = registerItem("fire_sword", new FireSwordItem());
    public static final Item WATER_SWORD = registerItem("water_sword", new WaterSwordItem());
    public static final Item EARTH_SWORD = registerItem("earth_sword", new EarthSwordItem());
    public static final Item AIR_SWORD = registerItem("air_sword", new AirSwordItem());

    private static final ItemGroup CUSTOM_WEAPONS_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(FIRE_SWORD))
            .displayName(Text.translatable("itemGroup." + MOD_ID + ".custom_weapons_group"))
            .entries((context, entries) -> {
                entries.add(FIRE_SWORD);
                entries.add(WATER_SWORD);
                entries.add(EARTH_SWORD);
                entries.add(AIR_SWORD);
            })
            .build();

    @Override
    public void onInitialize() {
        System.out.println("Hello Fabric world from Custom Weapons!");

        Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "custom_weapons_group"), CUSTOM_WEAPONS_ITEM_GROUP);

        KeyBindingUtil.registerKeyBindings();
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), item);
    }
}
