package com.shnewbs.hashforge.datagen;

import com.shnewbs.hashforge.HashForgeMod;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import java.util.concurrent.CompletableFuture;

public class CoinTagProvider extends TagsProvider<Item> {
    public CoinTagProvider(DataGenerator generator, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(generator, Registries.ITEM, lookupProvider, "hashforge", null);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(TagKey.create(Registries.ITEM, new ResourceLocation("hashforge", "coins"))).add(Items.GOLD_INGOT, Items.IRON_INGOT);
    }
}
