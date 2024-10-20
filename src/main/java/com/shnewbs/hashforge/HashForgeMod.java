package com.shnewbs.hashforge;

import com.shnewbs.hashforge.blocks.ModBlocks;
import com.shnewbs.hashforge.items.ModItems;
import com.shnewbs.hashforge.blockentity.ModBlockEntities;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(HashForgeMod.MODID)
public class HashForgeMod {
    public static final String MODID = "hashforge";

    public HashForgeMod(IEventBus modEventBus) {
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        // Add any other initialization code here
    }
}