package com.shnewbs.hashforge;

import com.shnewbs.hashforge.blocks.ASICMinerBlock;
import com.shnewbs.hashforge.blocks.ASICMinerBlockEntity;
import com.shnewbs.hashforge.commands.WalletCommands;
import com.shnewbs.hashforge.wallet.WalletManager;
import com.shnewbs.hashforge.mining.MiningManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(HashForgeMod.MOD_ID)
public class HashForgeMod {
    public static final String MOD_ID = "hashforge";

    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MOD_ID);

    public static final DeferredBlock<ASICMinerBlock> ASIC_MINER_BLOCK = BLOCKS.register("asic_miner",
            () -> new ASICMinerBlock(BlockBehaviour.Properties.of().strength(3.5F)));

    public static final DeferredItem<BlockItem> ASIC_MINER_ITEM = ITEMS.register("asic_miner",
            () -> new BlockItem(ASIC_MINER_BLOCK.get(), new Item.Properties()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ASICMinerBlockEntity>> ASIC_MINER_BLOCK_ENTITY = BLOCK_ENTITIES.register(
            "asic_miner",
            () -> BlockEntityType.Builder.of(ASICMinerBlockEntity::new, ASIC_MINER_BLOCK.get()).build(null)
    );

    public static WalletManager walletManager;
    public static MiningManager miningManager;

    public HashForgeMod(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);

        walletManager = new WalletManager();
        miningManager = new MiningManager();

        // Register other event listeners and initialize other components here
    }

    // Add other necessary methods and event handlers
}