package com.shnewbs.hashforge.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.shnewbs.hashforge.HashForgeMod;
import com.shnewbs.hashforge.currency.CurrencyType;
import com.shnewbs.hashforge.wallet.PendingReward;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public class WalletCommands {

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("wallet")
                .then(Commands.literal("create")
                        .executes(WalletCommands::createWallet))
                .then(Commands.literal("balance")
                        .executes(WalletCommands::getBalance))
                .then(Commands.literal("send")
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("currency", StringArgumentType.word())
                                        .then(Commands.argument("amount", DoubleArgumentType.doubleArg(0.0))
                                                .executes(context -> sendCoins(
                                                        context,
                                                        EntityArgument.getPlayer(context, "player"),
                                                        StringArgumentType.getString(context, "currency"),
                                                        DoubleArgumentType.getDouble(context, "amount")))))))
                .then(Commands.literal("pending")
                        .executes(WalletCommands::getPendingRewards)));
    }

    private static int createWallet(CommandContext<CommandSourceStack> context) {
        try {
            ServerPlayer player = context.getSource().getPlayerOrException();
            String playerUUID = player.getStringUUID();

            if (HashForgeMod.walletManager.hasWallet(playerUUID)) {
                context.getSource().sendSuccess(() -> Component.literal("You already have a wallet!"), false);
                return 0;
            }

            HashForgeMod.walletManager.createWallet(playerUUID);
            context.getSource().sendSuccess(() -> Component.literal("Created new wallet!"), false);
            return 1;
        } catch (Exception e) {
            context.getSource().sendFailure(Component.literal("Failed to create wallet: " + e.getMessage()));
            return 0;
        }
    }

    private static int getBalance(CommandContext<CommandSourceStack> context) {
        try {
            ServerPlayer player = context.getSource().getPlayerOrException();
            String playerUUID = player.getStringUUID();

            if (!HashForgeMod.walletManager.hasWallet(playerUUID)) {
                context.getSource().sendFailure(Component.literal("You don't have a wallet yet! Use /wallet create first."));
                return 0;
            }

            StringBuilder balanceMsg = new StringBuilder("Your wallet balances:\n");

            // Show balance for each currency type
            for (CurrencyType currency : CurrencyType.values()) {
                double balance = HashForgeMod.walletManager.getBalance(playerUUID, currency);
                balanceMsg.append(String.format("%s: %.8f\n", currency.getDisplayName(), balance));
            }

            context.getSource().sendSuccess(() -> Component.literal(balanceMsg.toString()), false);
            return 1;
        } catch (Exception e) {
            context.getSource().sendFailure(Component.literal("Failed to get balance: " + e.getMessage()));
            return 0;
        }
    }

    private static int sendCoins(CommandContext<CommandSourceStack> context, ServerPlayer targetPlayer, String currencyStr, double amount) {
        try {
            ServerPlayer sender = context.getSource().getPlayerOrException();
            String senderUUID = sender.getStringUUID();
            String targetUUID = targetPlayer.getStringUUID();

            if (!HashForgeMod.walletManager.hasWallet(senderUUID)) {
                context.getSource().sendFailure(Component.literal("You don't have a wallet yet! Use /wallet create first."));
                return 0;
            }

            // Parse currency type
            CurrencyType currency;
            try {
                currency = CurrencyType.valueOf(currencyStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                String validCurrencies = Arrays.stream(CurrencyType.values())
                        .map(CurrencyType::name)
                        .collect(Collectors.joining(", "));
                context.getSource().sendFailure(Component.literal("Invalid currency type! Valid types are: " + validCurrencies));
                return 0;
            }

            boolean success = HashForgeMod.walletManager.transfer(senderUUID, targetUUID, currency, amount);

            if (success) {
                context.getSource().sendSuccess(() -> Component.literal(String.format(
                        "Successfully sent %.8f %s to %s",
                        amount,
                        currency.getDisplayName(),
                        targetPlayer.getDisplayName().getString()
                )), false);
                return 1;
            } else {
                context.getSource().sendFailure(Component.literal("Transfer failed! Check your balance."));
                return 0;
            }
        } catch (Exception e) {
            context.getSource().sendFailure(Component.literal("Failed to send coins: " + e.getMessage()));
            return 0;
        }
    }

    private static int getPendingRewards(CommandContext<CommandSourceStack> context) {
        try {
            ServerPlayer player = context.getSource().getPlayerOrException();
            String playerUUID = player.getStringUUID();

            if (!HashForgeMod.walletManager.hasWallet(playerUUID)) {
                context.getSource().sendFailure(Component.literal("You don't have a wallet yet! Use /wallet create first."));
                return 0;
            }

            List<PendingReward> pendingRewards = HashForgeMod.walletManager.getPendingRewards(playerUUID);

            if (pendingRewards.isEmpty()) {
                context.getSource().sendSuccess(() -> Component.literal("You have no pending rewards."), false);
                return 1;
            }

            StringBuilder rewardsMsg = new StringBuilder("Your pending rewards:\n");
            for (PendingReward reward : pendingRewards) {
                rewardsMsg.append(String.format("%.8f %s (TX: %s)\n",
                        reward.getAmount(),
                        reward.getCurrencyType().getDisplayName(),
                        reward.getTransactionId()));
            }

            context.getSource().sendSuccess(() -> Component.literal(rewardsMsg.toString()), false);
            return 1;
        } catch (Exception e) {
            context.getSource().sendFailure(Component.literal("Failed to get pending rewards: " + e.getMessage()));
            return 0;
        }
    }
}