package com.sk89q.worldedit.fabric.mixin;

import com.sk89q.worldedit.internal.wna.NativePosition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockPos.class)
@Implements(@Interface(iface = NativePosition.class, prefix = "nc$"))
public abstract class MixinNativePosition extends Vec3i {
    public MixinNativePosition(int x, int y, int z) {
        super(x, y, z);
    }

    public int nc$x() {
        return getX();
    }

    public int nc$y() {
        return getY();
    }

    public int nc$z() {
        return getZ();
    }
}
