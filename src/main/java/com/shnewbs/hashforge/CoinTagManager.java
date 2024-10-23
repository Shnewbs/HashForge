package com.shnewbs.hashforge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CoinTagManager {
    public static final TagKey<Item> COIN_TAG = TagKey.create(Registries.ITEM, ResourceLocation.of("hashforge", "coins"));
}
