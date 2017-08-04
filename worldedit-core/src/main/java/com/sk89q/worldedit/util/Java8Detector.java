/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.sk89q.worldedit.util;

import com.google.common.base.Joiner;
import com.sk89q.worldedit.WorldEdit;

public final class Java8Detector {

    public static void notifyIfNot8() {
        String verProperty = System.getProperty("java.version").replace("+", "_");
        String ver = verProperty.split("_")[0].replace("1.", "").replace(".0", "").replace("-ea", "");
        if (ver.contains("u")) ver = ver.substring(0, ver.lastIndexOf("u"));
        int major = Integer.parseInt(ver);
        if (major <= 7) {
            // Implicitly java 7 because we compile against 7, so this won't
            // even launch on 6.
            WorldEdit.logger.warning(
                    "WorldEdit has detected you are using Java 7"
                            + " (based on detected version "
                            + verProperty + ").");
            WorldEdit.logger.warning(
                    "WorldEdit will stop supporting Java less than version 8 in the future,"
                            + " due to Java 7 being EOL since April 2015."
                            + " Please update your server to Java 8.");
        }
    }

    private Java8Detector() {
    }

}
