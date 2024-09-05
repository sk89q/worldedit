package com.sk89q.worldedit.fabric.mixin;

import com.mojang.serialization.MapCodec;
import com.sk89q.worldedit.internal.wna.NativeBlockState;
import com.sk89q.worldedit.internal.wna.NativePosition;
import com.sk89q.worldedit.internal.wna.NativeWorld;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockState.class)
@Implements(@Interface(iface = NativeBlockState.class, prefix = "nbs$"))
public abstract class MixinNativeBlockState extends BlockBehaviour.BlockStateBase {
    protected MixinNativeBlockState(Block owner, Reference2ObjectArrayMap<Property<?>, Comparable<?>> values, MapCodec<BlockState> propertiesCodec) {
        super(owner, values, propertiesCodec);
    }

    public boolean nbs$isSame(NativeBlockState other) {
        return this == other;
    }

    public boolean nbs$isSameBlockType(NativeBlockState other) {
        return this.getBlock() == ((BlockState) other).getBlock();
    }

    public boolean nbs$hasBlockEntity() {
        return super.hasBlockEntity();
    }

    public NativeBlockState nbs$updateFromNeighbourShapes(
        NativeWorld world, NativePosition position
    ) {
        return (NativeBlockState) Block.updateFromNeighbourShapes(
            (BlockState) (Object) this, (LevelAccessor) world, (BlockPos) position
        );
    }
}
