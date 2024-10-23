package com.shnewbs.hashforge.items;

import com.shnewbs.hashforge.currency.CurrencyType;
import com.shnewbs.hashforge.HashForgeMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
public class CoinCreatorItem extends Item {


    CompoundTag tag = stack.getOrCreateTag();

    public CoinCreatorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(ItemStack stack, Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            CompoundTag tag = stack.getOrCreateTag();  // Correctly handle NBT

            String coinName = tag.getString("coinName");
            String symbol = tag.getString("symbol");
            long maxSupply = tag.getLong("maxSupply");

            CurrencyType.DynamicCoin newCoin = HashForgeMod.getInstance().getCoinRegistry().registerCoin(coinName, symbol, maxSupply);

            player.sendSystemMessage(Component.literal("Successfully created currency: " + newCoin.getDisplayName()));
        }
        return InteractionResult.SUCCESS;
    }
}
