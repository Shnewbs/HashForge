// File: Block.java
// Directory: com/shnewbs/hashforge/blockchain/

package com.shnewbs.hashforge.blockchain;

import net.minecraft.core.BlockPos;

public class Block {
    private BlockPos pos;
    private String data;

    public Block(BlockPos pos) {
        this.pos = pos;
        this.data = "";
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setData(String data) {
        this.data = data;
    }
}