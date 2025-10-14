package com.example.superweapons;

import com.example.superweapons.item.EarthHammerItem;
import com.example.superweapons.item.FireSwordItem;
import com.example.superweapons.item.IceBladeItem;
import com.example.superweapons.item.SuperweaponCoreItem;
import com.example.superweapons.item.ThunderAxeItem;
import com.example.superweapons.item.material.SuperweaponMaterial;
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

public class Superweapons implements ModInitializer {
	public static final String MOD_ID = "superweapons";

	public static final Item SUPERWEAPON_CORE = registerItem("superweapon_core", new SuperweaponCoreItem(new Item.Settings().fireproof()));
	public static final Item FIRE_SWORD = registerItem("fire_sword", new FireSwordItem(SuperweaponMaterial.SUPERWEAPON_MATERIAL, 3, -2.4f, new Item.Settings().fireproof(), 60)); // 3 seconds cooldown
	public static final Item ICE_BLADE = registerItem("ice_blade", new IceBladeItem(SuperweaponMaterial.SUPERWEAPON_MATERIAL, 3, -2.4f, new Item.Settings().fireproof(), 100)); // 5 seconds cooldown
	public static final Item THUNDER_AXE = registerItem("thunder_axe", new ThunderAxeItem(SuperweaponMaterial.SUPERWEAPON_MATERIAL, 5, -3.0f, new Item.Settings().fireproof(), 400)); // 20 seconds cooldown
	public static final Item EARTH_HAMMER = registerItem("earth_hammer", new EarthHammerItem(SuperweaponMaterial.SUPERWEAPON_MATERIAL, 7, -3.2f, new Item.Settings().fireproof(), 80)); // 4 seconds cooldown

	private static final ItemGroup SUPERWEAPONS_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(FIRE_SWORD))
			.displayName(Text.translatable("itemgroup." + MOD_ID + ".superweapons_group"))
			.entries((context, entries) -> {
				entries.add(SUPERWEAPON_CORE);
				entries.add(FIRE_SWORD);
				entries.add(ICE_BLADE);
				entries.add(THUNDER_AXE);
				entries.add(EARTH_HAMMER);
			})
			.build();

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM_GROUP, new Identifier(MOD_ID, "superweapons_group"), SUPERWEAPONS_GROUP);
	}

	private static Item registerItem(String name, Item item) {
		return Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), item);
	}
}
