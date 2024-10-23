package com.shnewbs.hashforge;

import com.shnewbs.hashforge.blocks.ASICMinerBlock;
import com.shnewbs.hashforge.datagen.CoinTagProvider;
import com.shnewbs.hashforge.datagen.DataGenerators;
import com.shnewbs.hashforge.items.ASICMinerItem;
import com.shnewbs.hashforge.items.CoinCreatorItem;
import com.shnewbs.hashforge.registries.CoinRegistry;
import com.shnewbs.hashforge.screens.CoinCreatorScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DefferedRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HashForgeMod
{
    public static final String MODID = "hashforge";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    // This is used to create a new creative tab for the mod
    public static final CreativeModeTab HASH_FORGE_TAB = new CreativeModeTab(MODID) {
        @Override
        public net.minecraft.world.item.ItemStack makeIcon() {
            return new net.minecraft.world.item.ItemStack(ASIC_MINER_ITEM.get());
        }
    };

    // Registries for the mod
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // This is used to register the ASIC Miner block and item
    public static final RegistryObject<Block> ASIC_MINER_BLOCK = BLOCKS.register("asic_miner", () -> new ASICMinerBlock(Block.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Item> ASIC_MINER_ITEM = ITEMS.register("asic_miner_item", () -> new ASICMinerItem(ASIC_MINER_BLOCK.get(), new Item.Properties().tab(HASH_FORGE_TAB)));

    // This is used to register the Coin Creator item
    public static final RegistryObject<Item> COIN_CREATOR = ITEMS.register("coin_creator", () -> new CoinCreatorItem(new Item.Properties().tab(HASH_FORGE_TAB).stacksTo(1)));

    // This is the main function that is called when the mod is loaded
    public static void init()
    {
        // Register the coin registry data map type
        NeoForge.EVENT_BUS.register(CoinRegistry.class);

        // Register the blocks and items
        BLOCKS.register(NeoForge.EVENT_BUS);
        ITEMS.register(NeoForge.EVENT_BUS);

        // Register the coin creator screen
        MenuScreens.register(CoinRegistry.COIN_CREATOR_MENU.get(), CoinCreatorScreen::new);
    }

    // This function is called to register the data generators
    public static void onGatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        var lookupProvider = event.getLookupProvider();

        if (event.includeServer())
        {
            generator.addProvider(new DataGenerators(generator, lookupProvider));
        }
        if (event.includeClient())
        {
            lookupProvider.thenAccept(data -> generator.addProvider(new CoinTagProvider(generator, Registries.ITEM, data, "hashforge", null)));
        }
    }
}