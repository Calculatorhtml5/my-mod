package com.example.supercustom;

import com.example.supercustom.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuperCustom implements ModInitializer {
	public static final String MOD_ID = "supercustom";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Super Custom Mod!");

		ModItems.registerModItems();
	}
}
