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

package com.sk89q.worldedit.fabric.internal;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.fabric.FabricAdapter;
import com.sk89q.worldedit.world.block.BlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class FabricServerLevelDelegateProxy implements InvocationHandler {

    private final EditSession editSession;
    private final ServerLevel serverLevel;

    private FabricServerLevelDelegateProxy(EditSession editSession, ServerLevel serverLevel) {
        this.editSession = editSession;
        this.serverLevel = serverLevel;
    }

    public static WorldGenLevel newInstance(EditSession editSession, ServerLevel serverLevel) {
        return (WorldGenLevel) Proxy.newProxyInstance(
            serverLevel.getClass().getClassLoader(),
            serverLevel.getClass().getInterfaces(),
            new FabricServerLevelDelegateProxy(editSession, serverLevel)
        );
    }

    @Nullable
    private BlockEntity getBlockEntity(BlockPos blockPos) {
        BlockEntity tileEntity = this.serverLevel.getChunkAt(blockPos).getBlockEntity(blockPos);
        if (tileEntity == null) {
            return null;
        }
        BlockEntity newEntity = tileEntity.getType().create(blockPos, getBlockState(blockPos));
        newEntity.load(NBTConverter.toNative(this.editSession.getFullBlock(FabricAdapter.adapt(blockPos)).getNbtReference().getValue()));

        return newEntity;
    }

    private BlockState getBlockState(BlockPos blockPos) {
        return FabricAdapter.adapt(this.editSession.getBlock(FabricAdapter.adapt(blockPos)));
    }

    private boolean setBlock(BlockPos blockPos, BlockState blockState) {
        try {
            return editSession.setBlock(FabricAdapter.adapt(blockPos), FabricAdapter.adapt(blockState));
        } catch (MaxChangedBlocksException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean removeBlock(BlockPos blockPos, boolean bl) {
        try {
            return editSession.setBlock(FabricAdapter.adapt(blockPos), BlockTypes.AIR.getDefaultState());
        } catch (MaxChangedBlocksException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean destroyBlock(BlockPos blockPos, boolean bl, @Nullable Entity entity, int i) {
        return removeBlock(blockPos, bl);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("getBlockState") && args.length == 1 && args[0] instanceof BlockPos blockPos) {
            return getBlockState(blockPos);
        } else if (method.getName().equals("getBlockEntity") && args.length == 1 && args[0] instanceof BlockPos blockPos) {
            return getBlockEntity(blockPos);
        } else if (method.getName().equals("setBlock") && args.length >= 2 && args[0] instanceof BlockPos blockPos && args[1] instanceof BlockState blockState) {
            return setBlock(blockPos, blockState);
        } else if (method.getName().equals("removeBlock") && args.length == 2 && args[0] instanceof BlockPos blockPos && args[1] instanceof Boolean bl) {
            return removeBlock(blockPos, bl);
        } else if (method.getName().equals("destroyBlock") && args.length == 4 && args[0] instanceof BlockPos blockPos && args[1] instanceof Boolean bl && args[2] instanceof Entity entity && args[3] instanceof Integer i) {
            return destroyBlock(blockPos, bl, entity, i);
        } else {
            return method.invoke(this.serverLevel, args);
        }
    }

}
