package com.shnewbs.hashforge;

import com.shnewbs.hashforge.blocks.ASICMinerBlock;
import com.shnewbs.hashforge.blocks.ASICMinerBlockEntity;
import com.shnewbs.hashforge.items.CoinCreatorItem;
import com.shnewbs.hashforge.currency.CoinRegistry;
import com.shnewbs.hashforge.wallet.WalletManager;
import com.shnewbs.hashforge.mining.MiningManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(HashForgeMod.MOD_ID)
public class HashForgeMod {
    public static final String MOD_ID = "hashforge";
    private static final Logger LOGGER = LogManager.getLogger();
    private static HashForgeMod instance;
    private final CoinRegistry coinRegistry;

    // Deferred Registers
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MOD_ID);

    // Block and Item registration
    public static final DeferredBlock<ASICMinerBlock> ASIC_MINER_BLOCK = BLOCKS.register("asic_miner",
            () -> new ASICMinerBlock(BlockBehaviour.Properties.of().strength(3.5F)));

    public static final DeferredItem<BlockItem> ASIC_MINER_ITEM = ITEMS.register("asic_miner_item",
            () -> new BlockItem(ASIC_MINER_BLOCK.get(), new Item.Properties()));

    public static final DeferredHolder<Item, CoinCreatorItem> COIN_CREATOR = ITEMS.register("coin_creator",
            () -> new CoinCreatorItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ASICMinerBlockEntity>> ASIC_MINER_BLOCK_ENTITY = BLOCK_ENTITIES.register(
            "asic_miner",
            () -> BlockEntityType.Builder.of(ASICMinerBlockEntity::new, ASIC_MINER_BLOCK.get()).build(null)
    );

    // Other mod systems
    public static WalletManager walletManager;
    public static MiningManager miningManager;

    // Constructor
    public HashForgeMod(IEventBus modEventBus) {
        instance = this;
        this.coinRegistry = new CoinRegistry();

        // Register blocks, items, and entities
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);

        // Initialize managers
        walletManager = new WalletManager();
        miningManager = new MiningManager();

        // Register to the NeoForge event bus
        modEventBus.addListener(this::onServerStarting);
    }

    public static HashForgeMod getInstance() {
        return instance;
    }

    public CoinRegistry getCoinRegistry() {
        return coinRegistry;
    }

    public static DeferredRegister<Item> getItems() {
        return ITEMS;
    }

    private void onServerStarting(ServerStartingEvent event) {
        // Load any saved coin data
        coinRegistry.loadCoins();
        walletManager.loadWallets();

        LOGGER.info("HashForgeMod initialized");
    }
}
