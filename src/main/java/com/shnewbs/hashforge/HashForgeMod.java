package com.shnewbs.hashforge;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegisterObject;
import com.shnewbs.hashforge.blocks.ASICMinerBlock;
import com.shnewbs.hashforge.items.CoinCreatorItem;

public class HashForgeMod {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, "hashforge");
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, "hashforge");

    public static final DeferredRegisterObject<Block> ASIC_MINER_BLOCK = BLOCKS.register("asic_miner", ASICMinerBlock::new);
    public static final DeferredRegisterObject<Item> ASIC_MINER_ITEM = ITEMS.register("asic_miner_item", () -> new BlockItem(ASIC_MINER_BLOCK.get(), new Item.Properties()));

    public static final DeferredRegisterObject<Item> COIN_CREATOR = ITEMS.register("coin_creator", () -> new CoinCreatorItem(new Item.Properties().stacksTo(1)));
}
