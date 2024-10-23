package com.shnewbs.hashforge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class CoinTagProvider extends ItemTagsProvider
{
    public CoinTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper)
    {
        super(output, completableFuture, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider)
    {
        this.tag(TagKey.create(Registries.ITEM, new ResourceLocation("hashforge", "coins"))).add(Items.GOLD_INGOT, Items.IRON_INGOT);
    }
}