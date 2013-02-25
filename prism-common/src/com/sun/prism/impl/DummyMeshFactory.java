/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.prism.impl;

import com.sun.prism.Material;
import com.sun.prism.Mesh;
import com.sun.prism.MeshFactory;
import com.sun.prism.MeshView;
import com.sun.prism.PhongMaterial;
import com.sun.prism.Texture;

// TODO: 3D - Not clear that we want or need this
public class DummyMeshFactory implements MeshFactory {

    @Override
    public Mesh createMesh() {
        return new Mesh() {
            public int getNumGPUVertices() {
                return 0;
            }

            public boolean buildGeometry(Mesh.Geometry geom) {
                return true;
            }
        };
    }

    @Override
    public MeshView createMeshView(Mesh mesh) {
        return new MeshView() {
            @Override
            public boolean setMaterial(Material material) {
                return true;
            }

            public void setPos(float[] tm3x4) {
            }

            public void setWireframe(boolean isWired) {
            }

            public void setAmbient(float r, float g, float b) {
            }

            public boolean setLight(int index, float x, float y, float z, float r, float g, float b, float w) {
                return true;
            }

            public void setCullingMode(int mode) {
            }
        };
    }

    @Override
    public PhongMaterial createPhongMaterial() {
        return new PhongMaterial() {
            public void setSolidColor(float r, float g, float b, float a) {
            }

            public void setMap(int mapID, Texture hTexture) {
            }
        };
    }
}
