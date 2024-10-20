package com.shnewbs.hashforge.wallet;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class Wallet {
    private UUID walletID;
    private double balance;
    private KeyPair keyPair; // Private and public keys for digital signature

    public Wallet() {
        this.walletID = UUID.randomUUID(); // Generate a unique ID for the wallet
        this.balance = 0.0; // Start with zero balance
        this.keyPair = generateKeyPair(); // Generate key pair for the wallet
    }

    // Generate public/private key pair for digital signature
    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048); // 2048-bit key length for security
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public UUID getWalletID() {
        return walletID;
    }

    public double getBalance() {
        return balance;
    }

    public void addCoins(double amount) {
        this.balance += amount;
    }

    public boolean removeCoins(double amount) {
        if (amount <= balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }
}
