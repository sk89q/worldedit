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

package com.sk89q.worldedit.extension.input;

import com.sk89q.worldedit.util.adventure.text.Component;

/**
 * Thrown when a match fails when input is parsed.
 */
public class NoMatchException extends InputParseException {

    /**
     * Create with a message.
     *
     * @param message the message
     */
    public NoMatchException(Component message) {
        super(message);
    }

    /**
     * Create with a message.
     *
     * @param message the message
     * @deprecated Use {@link NoMatchException#NoMatchException(Component)}
     */
    @Deprecated
    public NoMatchException(com.sk89q.worldedit.util.formatting.text.Component message) {
        super(message);
    }

    /**
     * Create with a message.
     *
     * @param message the message
     */
    @Deprecated
    public NoMatchException(String message) {
        super(message);
    }

    /**
     * Create with a message and a cause.
     *
     * @param message the message
     * @param cause the cause
     */
    public NoMatchException(Component message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create with a message and a cause.
     *
     * @param message the message
     * @param cause the cause
     * @deprecated Use {@link NoMatchException#NoMatchException(Component, Throwable)}
     */
    @Deprecated
    public NoMatchException(com.sk89q.worldedit.util.formatting.text.Component message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create with a message and a cause.
     *
     * @param message the message
     * @param cause the cause
     */
    @Deprecated
    public NoMatchException(String message, Throwable cause) {
        super(message, cause);
    }

}
