package com.shnewbs.hashforge.commands;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import java.util.function.Supplier;

public class WalletCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("wallet")
                .then(Commands.literal("balance").executes(WalletCommands::checkBalance))
                .then(Commands.literal("send").executes(WalletCommands::sendCoins))
        );
    }

    private static int checkBalance(CommandContext<CommandSourceStack> context) {
        String balance = "0.00000"; // Placeholder for actual balance retrieval
        context.getSource().sendSuccess(() -> Component.literal("Your balance is: " + balance), false);
        return 1;
    }

    private static int sendCoins(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> Component.literal("Transfer successful."), false);
        return 1;
    }
}
