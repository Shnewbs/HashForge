package com.shnewbs.hashforge.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.shnewbs.hashforge.HashForgeMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

public class WalletCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("wallet")
                .then(Commands.literal("balance").executes(WalletCommands::checkBalance))
                .then(Commands.literal("send").executes(WalletCommands::sendCoins))
        );
    }

    private static int checkBalance(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayerOrException();
        String playerUUID = player.getStringUUID();
        if (HashForgeMod.walletManager.hasWallet(playerUUID)) {
            double balance = HashForgeMod.walletManager.getBalance(playerUUID, "defaultCurrency");
            context.getSource().sendSuccess(Component.literal("Your balance is: " + balance), false);
        } else {
            context.getSource().sendFailure(Component.literal("You don't have a wallet."));
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int sendCoins(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player sender = context.getSource().getPlayerOrException();
        String senderUUID = sender.getStringUUID();
        String targetUUID = "targetUUID";  // Replace with actual logic to fetch target player UUID.
        String currency = "defaultCurrency";
        double amount = 10;  // Replace with actual amount from arguments.

        if (HashForgeMod.walletManager.transfer(senderUUID, targetUUID, currency, amount)) {
            context.getSource().sendSuccess(Component.literal("Transfer successful."), false);
        } else {
            context.getSource().sendFailure(Component.literal("Transfer failed."));
        }
        return Command.SINGLE_SUCCESS;
    }
}
