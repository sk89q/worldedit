/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sk89q.worldedit.fabric.mixin;

import com.sk89q.worldedit.internal.wna.NativeBlockState;
import com.sk89q.worldedit.internal.wna.NativeChunkSection;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LevelChunkSection.class)
@Implements(@Interface(iface = NativeChunkSection.class, prefix = "ncs$"))
public abstract class MixinNativeChunkSection {
    @Shadow
    public abstract net.minecraft.world.level.block.state.BlockState setBlockState(
        int x, int y, int z, net.minecraft.world.level.block.state.BlockState state, boolean lock
    );

    @Shadow
    public abstract net.minecraft.world.level.block.state.BlockState getBlockState(
        int x, int y, int z
    );

    @Final
    @Mutable
    @Shadow
    private PalettedContainer<net.minecraft.world.level.block.state.BlockState> states;

    @Shadow
    private PalettedContainerRO<Holder<Biome>> biomes;

    @Shadow
    public abstract boolean hasOnlyAir();

    public boolean ncs$isOnlyAir() {
        return hasOnlyAir();
    }

    public NativeBlockState ncs$getThenSetBlock(int i, int j, int k, NativeBlockState blockState) {
        BlockState nativeState = (BlockState) blockState;
        if (ncs$isOnlyAir() && nativeState.isAir()) {
            return blockState;
        }
        return (NativeBlockState) setBlockState(i, j, k, nativeState, false);
    }

    public NativeBlockState ncs$getBlock(int i, int j, int k) {
        return (NativeBlockState) getBlockState(i, j, k);
    }

    public NativeChunkSection ncs$copy() {
        return (NativeChunkSection) new LevelChunkSection(
            copyPalettedContainer(states), copyPalettedContainer((PalettedContainer<Holder<Biome>>) biomes)
        );
    }

    /**
     * Mojang is bad at writing code and their copy method doesn't replace the resize handler so it resizes the wrong
     * thing. Reinitialize their data for them.
     *
     * @param container the container to copy
     * @return the copied container
     */
    @Unique
    private static <T> PalettedContainer<T> copyPalettedContainer(PalettedContainer<T> container) {
        // First init by directly moving the fields over
        PalettedContainer<T> copy = new PalettedContainer<>(container.registry, container.strategy, container.data);

        // Force re-create by using `null`, to make an actual copy of the data with the new resize handler.
        PalettedContainer.Data<T> data = copy.data;
        PalettedContainer.Data<T> data2 = copy.createOrReuseData(null, data.storage().getSize());
        data2.copyFrom(data.palette(), data.storage());
        copy.data = data2;

        return copy;
    }
}
