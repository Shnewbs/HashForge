package com.shnewbs.hashforge.datagen;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class CoinTagManager {
    public static final TagKey<Item> COIN_TAG = TagKey.create(Registries.ITEM, ResourceLocation.parse("hashforge:coins"));
}
