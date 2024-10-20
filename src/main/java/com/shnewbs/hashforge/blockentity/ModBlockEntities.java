package com.shnewbs.hashforge.blockentity;

import com.shnewbs.hashforge.HashForgeMod;
import com.shnewbs.hashforge.blocks.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, HashForgeMod.MODID);

    public static final Supplier<BlockEntityType<ASICMinerBlockEntity>> ASIC_MINER_ENTITY = BLOCK_ENTITIES.register("asic_miner",
            () -> BlockEntityType.Builder.of(ASICMinerBlockEntity::new, ModBlocks.ASIC_MINER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}