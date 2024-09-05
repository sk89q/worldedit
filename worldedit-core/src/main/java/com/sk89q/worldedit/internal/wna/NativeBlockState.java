package com.sk89q.worldedit.internal.wna;

/**
 * The equivalent of {@link com.sk89q.worldedit.world.block.BlockState}, but in the platform's base.
 */
public interface NativeBlockState {
    boolean isSame(NativeBlockState other);

    boolean isSameBlockType(NativeBlockState other);

    boolean hasBlockEntity();

    NativeBlockState updateFromNeighbourShapes(NativeWorld world, NativePosition position);
}
