package com.shnewbs.hashforge.blockchain;

import net.minecraft.core.BlockPos;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChainBlock {
    private BlockPos pos;
    private String data;
    private String hash;
    private String previousHash;

    public ChainBlock(BlockPos pos, String previousHash) {
        this.pos = pos;
        this.data = "";
        this.previousHash = previousHash;
        this.hash = calculateHash();
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setData(String data) {
        this.data = data;
        this.hash = calculateHash(); // Recalculate hash when data changes
    }

    public String getData() {
        return data;
    }

    public String getHash() {
        return hash;
    }

    // Method to calculate SHA-256 hash
    public String calculateHash() {
        String input = previousHash + pos.toString() + data;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
