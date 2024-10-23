package com.shnewbs.hashforge.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class CoinTagProvider extends TagsProvider<Item> {
    public CoinTagProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, net.minecraft.core.registries.Registries.ITEM, "hashforge", existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(CoinTagManager.COIN_TAG).add(Items.GOLD_INGOT, Items.IRON_INGOT);
    }
}
