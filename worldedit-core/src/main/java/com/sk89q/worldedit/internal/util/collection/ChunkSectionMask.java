package com.sk89q.worldedit.internal.util.collection;

import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortCollection;
import it.unimi.dsi.fastutil.shorts.ShortIterator;

import java.util.BitSet;

/**
 * A mask for a chunk section.
 */
public class ChunkSectionMask {
    public static int index(int x, int y, int z) {
        // Each value is 0-15, so 4 bits
        // NOTE: This order specifically matches the order used by SectionPos in Minecraft, do not change unless they do
        return (x << 8) | (z << 4) | y;
    }

    @FunctionalInterface
    public interface PosConsumer {
        void apply(int x, int y, int z);
    }

    private final BitSet mask = new BitSet(4096);

    public boolean isSet(int x, int y, int z) {
        return mask.get(index(x, y, z));
    }

    public void set(int x, int y, int z) {
        mask.set(index(x, y, z));
    }

    public void clear(int x, int y, int z) {
        mask.clear(index(x, y, z));
    }

    public void clear() {
        mask.clear();
    }

    public void setAll() {
        mask.set(0, 4096);
    }

    public void forEach(PosConsumer consumer) {
        for (int i = mask.nextSetBit(0); i >= 0; i = mask.nextSetBit(i + 1)) {
            consumer.apply((i >> 8) & 0xF, i & 0xF, (i >> 4) & 0xF);
        }
    }

    public int cardinality() {
        return mask.cardinality();
    }

    /**
     * {@return a view of this mask as a short collection} Used for updating MC internals.
     */
    public ShortCollection asShortCollection() {
        return new AbstractShortCollection() {
            @Override
            public ShortIterator iterator() {
                return new ShortIterator() {
                    private int next = mask.nextSetBit(0);

                    @Override public short nextShort() {
                        if (!hasNext()) {
                            throw new IllegalStateException();
                        }
                        // Uses the fact that we share the order with SectionPos to efficiently map
                        short value = (short) next;
                        next = mask.nextSetBit(next + 1);
                        return value;
                    }

                    @Override public boolean hasNext() {
                        return next >= 0;
                    }
                };
            }

            @Override
            public int size() {
                return mask.cardinality();
            }
        };
    }
}
