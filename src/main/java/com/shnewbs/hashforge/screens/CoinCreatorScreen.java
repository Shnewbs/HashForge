package com.shnewbs.hashforge.screens;

import com.shnewbs.hashforge.HashForgeMod;
import com.shnewbs.hashforge.currency.CurrencyType;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.gui.GuiGraphics;

public class CoinCreatorScreen extends Screen {
    private EditBox nameField;
    private EditBox symbolField;
    private EditBox supplyField;
    private final Player player;
    private static final int FIELD_WIDTH = 200;
    private static final int FIELD_HEIGHT = 20;

    public CoinCreatorScreen(Player player) {
        super(Component.literal("Create Your Cryptocurrency"));
        this.player = player;
    }

    @Override
    protected void init() {
        int centerX = width / 2;
        int centerY = height / 2;

        nameField = new EditBox(font, centerX - FIELD_WIDTH / 2, centerY - 40, FIELD_WIDTH, FIELD_HEIGHT, Component.literal("Coin Name"));
        nameField.setMaxLength(32);

        symbolField = new EditBox(font, centerX - FIELD_WIDTH / 2, centerY, FIELD_WIDTH, FIELD_HEIGHT, Component.literal("Symbol"));
        symbolField.setMaxLength(5);

        supplyField = new EditBox(font, centerX - FIELD_WIDTH / 2, centerY + 40, FIELD_WIDTH, FIELD_HEIGHT, Component.literal("Max Supply"));
        supplyField.setFilter(s -> s.matches("\\d*"));  // Ensure only numbers are input

        addRenderableWidget(Button.builder(Component.literal("Create Coin"), this::createCoin)
                .pos(centerX - 100, centerY + 80)
                .size(200, 20)
                .build());

        addRenderableWidget(nameField);
        addRenderableWidget(symbolField);
        addRenderableWidget(supplyField);
    }

    private void createCoin(Button button) {
        try {
            String name = nameField.getValue().trim();
            String symbol = symbolField.getValue().trim().toUpperCase();
            long maxSupply = Long.parseLong(supplyField.getValue().trim());

            if (name.isEmpty() || symbol.isEmpty()) {
                player.sendSystemMessage(Component.literal("Please fill in all fields"));
                return;
            }

            if (maxSupply <= 0) {
                player.sendSystemMessage(Component.literal("Max supply must be greater than 0"));
                return;
            }

            // Register the coin and check if the coin was created successfully
            try {
                CurrencyType.DynamicCoin newCoin = HashForgeMod.getInstance().getCoinRegistry().registerCoin(name, symbol, maxSupply);
                onClose();
                player.getMainHandItem().shrink(1);  // Deduct the item used for coin creation
                player.sendSystemMessage(Component.literal("Successfully created " + name + " (" + symbol + ")"));
            } catch (IllegalArgumentException e) {
                player.sendSystemMessage(Component.literal("Failed to create coin. Name might already be taken."));
            }
        } catch (NumberFormatException e) {
            player.sendSystemMessage(Component.literal("Invalid max supply value"));
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);

        graphics.drawCenteredString(font, "Create Your Cryptocurrency", width / 2, height / 2 - 60, 0xFFFFFF);
        graphics.drawString(font, "Coin Name:", width / 2 - FIELD_WIDTH / 2, height / 2 - 52, 0xFFFFFF);
        graphics.drawString(font, "Symbol:", width / 2 - FIELD_WIDTH / 2, height / 2 - 12, 0xFFFFFF);
        graphics.drawString(font, "Max Supply:", width / 2 - FIELD_WIDTH / 2, height / 2 + 28, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
