package com.example.elementalweapons;

import com.example.elementalweapons.item.ModItems;
import com.example.elementalweapons.networking.ModMessages;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElementalWeaponsMod implements ModInitializer {
	public static final String MOD_ID = "elementalweapons";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Elemental Weapons Mod!");

		ModItems.registerModItems();
		ModMessages.registerC2SPackets();
	}
}
