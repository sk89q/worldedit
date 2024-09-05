package com.sk89q.worldedit.internal.wna;

/**
 * The equivalent of {@link com.sk89q.worldedit.math.BlockVector3}, but in the platform's base.
 */
public interface NativePosition {
    int x();

    int y();

    int z();
}
