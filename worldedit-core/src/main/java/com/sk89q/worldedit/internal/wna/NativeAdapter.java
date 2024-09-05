package com.sk89q.worldedit.internal.wna;

import com.sk89q.worldedit.world.block.BlockState;

/**
 * Methods for adapting data to the native types.
 */
public interface NativeAdapter {
    NativeBlockState toNative(BlockState state);

    BlockState fromNative(NativeBlockState state);

    NativePosition newBlockPos(int x, int y, int z);
}
