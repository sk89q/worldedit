package com.sk89q.worldedit.internal.wna;

import com.sk89q.worldedit.internal.util.collection.ChunkSectionMask;

import javax.annotation.Nullable;

/**
 * Represents a chunk in the world. Made of {@link NativeChunkSection PlatformChunkSections}.
 */
public interface NativeChunk {
    NativeWorld getWorld();

    boolean isTicking();

    NativePosition getWorldPos(int offsetX, int offsetY, int offsetZ);

    NativeBlockState getBlockState(NativePosition blockPos);

    @Nullable
    NativeBlockState setBlockState(NativePosition blockPos, NativeBlockState newState);

    void notifyBlockUpdate(NativePosition pos, NativeBlockState oldState, NativeBlockState newState);

    void markBlockChanged(NativePosition pos);

    void markSectionChanged(int index, ChunkSectionMask changed);

    void updateHeightmaps();

    void updateLightingForSectionAirChange(int index, boolean onlyAir);

    void removeSectionBlockEntity(int chunkX, int chunkY, int chunkZ);

    void initializeBlockEntity(int chunkX, int chunkY, int chunkZ, NativeBlockState newState);

    /**
     * Get the chunk section at the given index.
     *
     * @param index the index, from 0 to the max height divided by 16
     * @return the chunk section
     */
    NativeChunkSection getChunkSection(int index);

    /**
     * Replaces a chunk section in the given chunk. This method is also responsible for updating heightmaps
     * and creating block entities, to keep consistency with {@link #setBlockState(NativePosition, NativeBlockState)}
     * (the method we used to use). This is usually easily done by calling
     * {@link WNASharedImpl#postChunkSectionReplacement(NativeChunk, int, NativeChunkSection, NativeChunkSection)}.
     *
     * @param index the index, from 0 to the max height divided by 16
     * @param section the new chunk section
     * @return the old chunk section
     */
    NativeChunkSection setChunkSection(int index, NativeChunkSection section);
}
