/* 
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javafx.geometry;

/**
Builder class for javafx.geometry.BoundingBox
@see javafx.geometry.BoundingBox
@deprecated This class is deprecated and will be removed in the next version
* @since JavaFX 2.0
*/
@javax.annotation.Generated("Generated by javafx.builder.processor.BuilderProcessor")
@Deprecated
public class BoundingBoxBuilder<B extends javafx.geometry.BoundingBoxBuilder<B>> implements javafx.util.Builder<javafx.geometry.BoundingBox> {
    protected BoundingBoxBuilder() {
    }
    
    /** Creates a new instance of BoundingBoxBuilder. */
    @SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
    public static javafx.geometry.BoundingBoxBuilder<?> create() {
        return new javafx.geometry.BoundingBoxBuilder();
    }
    
    private double depth;
    /**
    Set the value of the {@link javafx.geometry.BoundingBox#getDepth() depth} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B depth(double x) {
        this.depth = x;
        return (B) this;
    }
    
    private double height;
    /**
    Set the value of the {@link javafx.geometry.BoundingBox#getHeight() height} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B height(double x) {
        this.height = x;
        return (B) this;
    }
    
    private double minX;
    /**
    Set the value of the {@link javafx.geometry.BoundingBox#getMinX() minX} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B minX(double x) {
        this.minX = x;
        return (B) this;
    }
    
    private double minY;
    /**
    Set the value of the {@link javafx.geometry.BoundingBox#getMinY() minY} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B minY(double x) {
        this.minY = x;
        return (B) this;
    }
    
    private double minZ;
    /**
    Set the value of the {@link javafx.geometry.BoundingBox#getMinZ() minZ} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B minZ(double x) {
        this.minZ = x;
        return (B) this;
    }
    
    private double width;
    /**
    Set the value of the {@link javafx.geometry.BoundingBox#getWidth() width} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B width(double x) {
        this.width = x;
        return (B) this;
    }
    
    /**
    Make an instance of {@link javafx.geometry.BoundingBox} based on the properties set on this builder.
    */
    public javafx.geometry.BoundingBox build() {
        javafx.geometry.BoundingBox x = new javafx.geometry.BoundingBox(this.minX, this.minY, this.minZ, this.width, this.height, this.depth);
        return x;
    }
}
