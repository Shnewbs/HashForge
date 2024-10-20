package com.shnewbs.hashforge.blockchain;

public class Transaction {
    private ChainBlock block;
    private String data;

    public Transaction(ChainBlock block, String data) {
        this.block = block;
        this.data = data;
    }

    public ChainBlock getBlock() {
        return block;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return data;
    }
}
