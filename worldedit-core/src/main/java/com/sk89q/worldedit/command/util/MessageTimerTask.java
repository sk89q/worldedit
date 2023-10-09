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

package com.sk89q.worldedit.command.util;

import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.util.formatting.text.Component;

import java.util.TimerTask;
import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public class MessageTimerTask extends TimerTask {

    private final Actor sender;
    private final Component message;
    @Nullable
    private final Component workingMessage;

    private boolean hasRunBefore = false;

    @Deprecated
    MessageTimerTask(Actor sender, String message) {
        this(sender, Component.text(message), null);
    }

    MessageTimerTask(Actor sender, Component message, @Nullable Component workingMessage) {
        checkNotNull(sender);
        checkNotNull(message);

        this.sender = sender;
        this.message = message;
        this.workingMessage = workingMessage;
    }

    @Override
    public void run() {
        if (!hasRunBefore) {
            sender.printDebug(message);
            hasRunBefore = true;
        } else if (workingMessage != null) {
            sender.printDebug(workingMessage);
        } else {
            cancel();
        }
    }

}
