package com.shnewbs.hashforge.datagen;

import com.shnewbs.hashforge.HashForgeMod;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class CoinTagProvider extends TagsProvider<Item> {

    public CoinTagProvider(PackOutput output, CompletableFuture<TagLookup<Item>> lookupProvider) {
        super(output, Registries.ITEM, lookupProvider, HashForgeMod.MOD_ID, null);
    }

    @Override
    protected void addTags() {
        TagKey<Item> coinTag = TagKey.create(Registries.ITEM, ResourceLocation.of("hashforge", "coins"));
        tag(coinTag).add(HashForgeMod.getItems().get("coin_creator"));
    }
}
