package com.example.elementalweapons.item;

import com.example.elementalweapons.ElementalWeaponsMod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup ELEMENTAL_WEAPONS_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(ElementalWeaponsMod.MOD_ID, "elemental_weapons"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.elementalweapons.elemental_weapons"))
                    .icon(() -> new ItemStack(ModItems.FIRE_SWORD)).entries((displayContext, entries) -> {
                        entries.add(ModItems.FIRE_SWORD);
                        entries.add(ModItems.ICE_AXE);
                        entries.add(ModItems.LIGHTNING_SCYTHE);
                        entries.add(ModItems.EARTH_HAMMER);
                    }).build());

    public static void registerItemGroups() {
        ElementalWeaponsMod.LOGGER.info("Registering Item Groups for " + ElementalWeaponsMod.MOD_ID);
    }
}
