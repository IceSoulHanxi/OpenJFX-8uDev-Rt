/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.sun.javafx.logging;

import com.oracle.jrockit.jfr.ContentType;
import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.EventToken;
import com.oracle.jrockit.jfr.TimedEvent;
import com.oracle.jrockit.jfr.ValueDefinition;

@EventDefinition(path="javafx/pulse", name = "JavaFX Pulse Phase", description="Describes a phase in JavaFX pulse processing", stacktrace=false, thread=true)
public class JFRPulseEvent extends TimedEvent {

    @ValueDefinition(name="pulseID", description="Pulse number", contentType=ContentType.None, relationKey="http://www.oracle.com/javafx/pulse/id")
    private int pulseNumber;

    @ValueDefinition(name="phaseName", description="Pulse phase name", contentType=ContentType.None)
    private String phase;

    public JFRPulseEvent(EventToken eventToken) {
        super(eventToken);
    }

    public int getPulseNumber() {
        return pulseNumber;
    }

    public void setPulseNumber(int n) {
        pulseNumber = n;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String s) {
        phase = s;
    }
}
