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

package com.sk89q.worldedit.forge;

import com.sk89q.worldedit.world.registry.BlockMaterial;
import com.sk89q.worldedit.world.registry.PassthroughBlockMaterial;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

import javax.annotation.Nullable;

// TODO Finish delegating all methods
/**
 * Forge block material that pulls as much info as possible from the Minecraft
 * Material, and passes the rest to another implementation, typically the
 * bundled block info.
 */
public class ForgeBlockMaterial extends PassthroughBlockMaterial {

    private final BlockState block;

    public ForgeBlockMaterial(BlockState block, @Nullable BlockMaterial secondary) {
        super(secondary);
        this.block = block;
    }

    @Override
    public boolean isAir() {
        return block.isAir() || super.isAir();
    }

    @Override
    public boolean isFullCube() {
        // return block.isCollisionShapeFullBlock();
        return super.isFullCube();
    }

    @Override
    public boolean isOpaque() {
        return block.canOcclude();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isPowerSource() {
        return block.isSignalSource();
    }

    @Override
    public boolean isLiquid() {
        return block.liquid();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isSolid() {
        return block.isSolid();
    }

    @Override
    public float getHardness() {
        return block.getBlock().defaultDestroyTime();
    }

    @Override
    public float getResistance() {
        // return block.getBlock().getExplosionResistance();
        return super.getResistance();
    }

    @Override
    public float getSlipperiness() {
        return block.getBlock().getFriction();
    }

    @Override
    public int getLightValue() {
        // return block.getLightEmission();
        return super.getLightValue();
    }

    @Override
    public int getMapColor() {
        return delegate.getColor().col;
    }

    @Override
    public boolean isFragileWhenPushed() {
        return block.getPistonPushReaction() == PushReaction.DESTROY;
    }

    @Override
    public boolean isUnpushable() {
        return block.getPistonPushReaction() == PushReaction.BLOCK;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isTicksRandomly() {
        return block.isRandomlyTicking();
    }

    @Override
    public boolean isMovementBlocker() {
        return block.blocksMotion();
    }

    @Override
    public boolean isBurnable() {
        return block.ignitedByLava();
    }

    @Override
    public boolean isToolRequired() {
        return !block.requiresCorrectToolForDrops();
    }

    @Override
    public boolean isReplacedDuringPlacement() {
        return block.canBeReplaced();
    }

    @Override
    public boolean isTranslucent() {
        return super.isTranslucent();
    }

    @Override
    public boolean hasContainer() {
        return block.hasBlockEntity();
    }

}
