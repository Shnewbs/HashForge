package com.shnewbs.hashforge.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import java.util.concurrent.CompletableFuture;

public class CoinTagProvider extends TagsProvider<Item> {
    public CoinTagProvider(DataGenerator generator, CompletableFuture<Provider> future) {
        super(generator, Registries.ITEM, future, "hashforge");
    }

    @Override
    protected void addTags(Provider provider) {
        tag(CoinTagManager.COIN_TAG).add(Items.GOLD_INGOT, Items.IRON_INGOT);
    }
}
