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

import com.sk89q.worldedit.util.PropertiesConfiguration;

import java.io.File;
import java.nio.file.Path;

public class ForgeConfiguration extends PropertiesConfiguration {

    public boolean creativeEnable = false;
    public boolean cheatMode = false;
    public boolean commandBlockSupport = false;

    public ForgeConfiguration(ForgeWorldEdit mod) {
        super(mod.getWorkingDir().resolve("worldedit.properties"));
    }

    @Override
    protected void loadExtra() {
        creativeEnable = getBool("use-in-creative", false);
        cheatMode = getBool("cheat-mode", false);
        commandBlockSupport = getBool("command-block-support", false);
    }

    @Override
    public Path getWorkingDirectoryPath() {
        return ForgeWorldEdit.inst.getWorkingDir();
    }
}
