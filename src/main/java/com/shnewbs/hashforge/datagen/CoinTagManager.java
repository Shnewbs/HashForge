package com.shnewbs.hashforge.datagen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CoinTagManager {
    public static final TagKey<Item> COIN_TAG = TagKey.create(Registries.ITEM, new ResourceLocation("hashforge", "coins"));
}
