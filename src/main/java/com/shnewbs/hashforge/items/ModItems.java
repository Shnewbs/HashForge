package com.shnewbs.hashforge.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import com.shnewbs.hashforge.blocks.ModBlocks;
import com.shnewbs.hashforge.HashForgeMod;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HashForgeMod.MODID);

    public static final Supplier<Item> ASIC_MINER_ITEM = ITEMS.register("asic_miner",
            () -> new BlockItem(ModBlocks.ASIC_MINER.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}