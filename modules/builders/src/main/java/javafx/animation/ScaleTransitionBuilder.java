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

package javafx.animation;

/**
Builder class for javafx.animation.ScaleTransition
@see javafx.animation.ScaleTransition
@deprecated This class is deprecated and will be removed in the next version
* @since JavaFX 2.0
*/
@javax.annotation.Generated("Generated by javafx.builder.processor.BuilderProcessor")
@Deprecated
public final class ScaleTransitionBuilder extends javafx.animation.TransitionBuilder<javafx.animation.ScaleTransitionBuilder> implements javafx.util.Builder<javafx.animation.ScaleTransition> {
    protected ScaleTransitionBuilder() {
    }
    
    /** Creates a new instance of ScaleTransitionBuilder. */
    @SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
    public static javafx.animation.ScaleTransitionBuilder create() {
        return new javafx.animation.ScaleTransitionBuilder();
    }
    
    private int __set;
    private void __set(int i) {
        __set |= 1 << i;
    }
    public void applyTo(javafx.animation.ScaleTransition x) {
        super.applyTo(x);
        int set = __set;
        while (set != 0) {
            int i = Integer.numberOfTrailingZeros(set);
            set &= ~(1 << i);
            switch (i) {
                case 0: x.setByX(this.byX); break;
                case 1: x.setByY(this.byY); break;
                case 2: x.setByZ(this.byZ); break;
                case 3: x.setDuration(this.duration); break;
                case 4: x.setFromX(this.fromX); break;
                case 5: x.setFromY(this.fromY); break;
                case 6: x.setFromZ(this.fromZ); break;
                case 7: x.setNode(this.node); break;
                case 8: x.setToX(this.toX); break;
                case 9: x.setToY(this.toY); break;
                case 10: x.setToZ(this.toZ); break;
            }
        }
    }
    
    private double byX;
    /**
    Set the value of the {@link javafx.animation.ScaleTransition#getByX() byX} property for the instance constructed by this builder.
    */
    public javafx.animation.ScaleTransitionBuilder byX(double x) {
        this.byX = x;
        __set(0);
        return this;
    }
    
    private double byY;
    /**
    Set the value of the {@link javafx.animation.ScaleTransition#getByY() byY} property for the instance constructed by this builder.
    */
    public javafx.animation.ScaleTransitionBuilder byY(double x) {
        this.byY = x;
        __set(1);
        return this;
    }
    
    private double byZ;
    /**
    Set the value of the {@link javafx.animation.ScaleTransition#getByZ() byZ} property for the instance constructed by this builder.
    */
    public javafx.animation.ScaleTransitionBuilder byZ(double x) {
        this.byZ = x;
        __set(2);
        return this;
    }
    
    private javafx.util.Duration duration;
    /**
    Set the value of the {@link javafx.animation.ScaleTransition#getDuration() duration} property for the instance constructed by this builder.
    */
    public javafx.animation.ScaleTransitionBuilder duration(javafx.util.Duration x) {
        this.duration = x;
        __set(3);
        return this;
    }
    
    private double fromX;
    /**
    Set the value of the {@link javafx.animation.ScaleTransition#getFromX() fromX} property for the instance constructed by this builder.
    */
    public javafx.animation.ScaleTransitionBuilder fromX(double x) {
        this.fromX = x;
        __set(4);
        return this;
    }
    
    private double fromY;
    /**
    Set the value of the {@link javafx.animation.ScaleTransition#getFromY() fromY} property for the instance constructed by this builder.
    */
    public javafx.animation.ScaleTransitionBuilder fromY(double x) {
        this.fromY = x;
        __set(5);
        return this;
    }
    
    private double fromZ;
    /**
    Set the value of the {@link javafx.animation.ScaleTransition#getFromZ() fromZ} property for the instance constructed by this builder.
    */
    public javafx.animation.ScaleTransitionBuilder fromZ(double x) {
        this.fromZ = x;
        __set(6);
        return this;
    }
    
    private javafx.scene.Node node;
    /**
    Set the value of the {@link javafx.animation.ScaleTransition#getNode() node} property for the instance constructed by this builder.
    */
    public javafx.animation.ScaleTransitionBuilder node(javafx.scene.Node x) {
        this.node = x;
        __set(7);
        return this;
    }
    
    private double toX;
    /**
    Set the value of the {@link javafx.animation.ScaleTransition#getToX() toX} property for the instance constructed by this builder.
    */
    public javafx.animation.ScaleTransitionBuilder toX(double x) {
        this.toX = x;
        __set(8);
        return this;
    }
    
    private double toY;
    /**
    Set the value of the {@link javafx.animation.ScaleTransition#getToY() toY} property for the instance constructed by this builder.
    */
    public javafx.animation.ScaleTransitionBuilder toY(double x) {
        this.toY = x;
        __set(9);
        return this;
    }
    
    private double toZ;
    /**
    Set the value of the {@link javafx.animation.ScaleTransition#getToZ() toZ} property for the instance constructed by this builder.
    */
    public javafx.animation.ScaleTransitionBuilder toZ(double x) {
        this.toZ = x;
        __set(10);
        return this;
    }
    
    /**
    Make an instance of {@link javafx.animation.ScaleTransition} based on the properties set on this builder.
    */
    public javafx.animation.ScaleTransition build() {
        javafx.animation.ScaleTransition x = new javafx.animation.ScaleTransition();
        applyTo(x);
        return x;
    }
}
