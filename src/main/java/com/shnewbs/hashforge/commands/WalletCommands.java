package com.shnewbs.hashforge.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WalletCommands {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        LOGGER.info("Registering hf commands");
        dispatcher.register(
                Commands.literal("hf")
                        .then(Commands.literal("wallet")
                                .executes(context -> {
                                    context.getSource().sendSuccess(() -> Component.literal("Wallet commands: /hf wallet balance <currencyType>"), false);
                                    return 1;
                                })
                        )
                        .then(Commands.literal("asic")
                                .executes(context -> {
                                    context.getSource().sendSuccess(() -> Component.literal("ASIC commands: /hf asic list"), false);
                                    return 1;
                                })
                        )
        );
    }
}
