package com.shnewbs.hashforge.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.shnewbs.hashforge.HashForgeMod;
import com.shnewbs.hashforge.wallet.Wallet;
import com.shnewbs.hashforge.currency.CurrencyType;
import com.shnewbs.hashforge.mining.MiningManager;
import com.shnewbs.hashforge.blocks.ASICMinerBlockEntity;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.Map;

public class WalletCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("wallet")
                .then(Commands.literal("balance")
                        .then(Commands.argument("currencyType", StringArgumentType.word())
                                .executes(context -> showBalance(
                                        context.getSource().getPlayerOrException(),
                                        CurrencyType.valueOf(StringArgumentType.getString(context, "currencyType").toUpperCase())
                                ))
                        )
                )
                .then(Commands.literal("send")
                        .then(Commands.argument("currencyType", StringArgumentType.word())
                                .then(Commands.argument("coin", StringArgumentType.word())
                                        .then(Commands.argument("recipient", EntityArgument.player())
                                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg(0.000001))
                                                        .executes(context -> sendCurrency(
                                                                context.getSource().getPlayerOrException(),
                                                                EntityArgument.getPlayer(context, "recipient"),
                                                                DoubleArgumentType.getDouble(context, "amount"),
                                                                CurrencyType.valueOf(StringArgumentType.getString(context, "currencyType").toUpperCase()),
                                                                StringArgumentType.getString(context, "coin")
                                                        ))
                                                )
                                        )
                                )
                        )
                )
                .then(Commands.literal("asic")
                        .then(Commands.literal("list")
                                .executes(context -> listASICMiners(context.getSource().getPlayerOrException()))
                        )
                        .then(Commands.literal("info")
                                .then(Commands.argument("minerIndex", StringArgumentType.word())
                                        .executes(context -> getASICInfo(
                                                context.getSource().getPlayerOrException(),
                                                StringArgumentType.getString(context, "minerIndex")
                                        ))
                                )
                        )
                )
        );
    }

    private static int showBalance(ServerPlayer player, CurrencyType currencyType) {
        try {
            Wallet wallet = HashForgeMod.walletManager.getWallet(player.getUUID());
            if (wallet != null) {
                Map<String, Double> balances = wallet.getAllBalances(currencyType);
                player.sendSystemMessage(Component.literal("Your " + currencyType.name() + " balances:"));
                for (Map.Entry<String, Double> entry : balances.entrySet()) {
                    player.sendSystemMessage(Component.literal(entry.getKey() + ": " + entry.getValue()));
                }
            } else {
                player.sendSystemMessage(Component.literal("You don't have a wallet yet."));
            }
        } catch (Exception e) {
            player.sendSystemMessage(Component.literal("Error retrieving balance: " + e.getMessage()));
        }
        return 1;
    }

    private static int sendCurrency(ServerPlayer sender, ServerPlayer recipient, double amount, CurrencyType currencyType, String coin) {
        try {
            boolean success = HashForgeMod.walletManager.transferFunds(sender.getUUID(), recipient.getUUID(), currencyType, coin, amount);
            if (success) {
                sender.sendSystemMessage(Component.literal("Successfully sent " + amount + " " + coin + " (" + currencyType.name() + ") to " + recipient.getName().getString()));
                recipient.sendSystemMessage(Component.literal("Received " + amount + " " + coin + " (" + currencyType.name() + ") from " + sender.getName().getString()));
            } else {
                sender.sendSystemMessage(Component.literal("Failed to send " + amount + " " + coin + " (" + currencyType.name() + "). Insufficient balance or invalid transaction."));
            }
        } catch (Exception e) {
            sender.sendSystemMessage(Component.literal("Error processing transaction: " + e.getMessage()));
        }
        return 1;
    }

    private static int listASICMiners(ServerPlayer player) {
        List<ASICMinerBlockEntity> miners = MiningManager.getMinersForPlayer(player.getUUID());

        if (miners.isEmpty()) {
            player.sendSystemMessage(Component.literal("You don't have any active ASIC miners."));
            return 0;
        }

        player.sendSystemMessage(Component.literal("Your active ASIC miners:"));
        for (int i = 0; i < miners.size(); i++) {
            ASICMinerBlockEntity miner = miners.get(i);
            player.sendSystemMessage(Component.literal(
                    String.format("%d. Mining %s - Hash Rate: %.2f H/s",
                            i + 1,
                            miner.getCurrentCoin(),
                            miner.getHashRate()
                    )
            ));
        }
        player.sendSystemMessage(Component.literal("Use '/wallet asic info <number>' to get detailed information about a specific miner."));

        return 1;
    }

    private static int getASICInfo(ServerPlayer player, String minerIndex) {
        List<ASICMinerBlockEntity> miners = MiningManager.getMinersForPlayer(player.getUUID());

        int index;
        try {
            index = Integer.parseInt(minerIndex) - 1;
        } catch (NumberFormatException e) {
            player.sendSystemMessage(Component.literal("Invalid miner number. Please use a number."));
            return 0;
        }

        if (index < 0 || index >= miners.size()) {
            player.sendSystemMessage(Component.literal("Invalid miner number. You have " + miners.size() + " active miners."));
            return 0;
        }

        ASICMinerBlockEntity miner = miners.get(index);
        player.sendSystemMessage(Component.literal("ASIC Miner #" + (index + 1) + " Information:"));
        player.sendSystemMessage(Component.literal("Status: " + (miner.isPowered() ? "Powered" : "Unpowered")));
        player.sendSystemMessage(Component.literal("Mining: " + miner.getCurrentCoin()));
        player.sendSystemMessage(Component.literal("Hash Rate: " + String.format("%.2f H/s", miner.getHashRate())));
        player.sendSystemMessage(Component.literal("Energy Consumption: " + miner.getEnergyConsumption() + " FE/t"));
        player.sendSystemMessage(Component.literal("Efficiency: " + String.format("%.2f%%", miner.getEfficiency())));
        player.sendSystemMessage(Component.literal("Heat Output: " + String.format("%.2f units", miner.getHeatOutput())));

        return 1;
    }
}