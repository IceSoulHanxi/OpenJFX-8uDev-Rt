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
package com.sun.javafx.sg.prism;

import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.prism.CompositeMode;
import com.sun.prism.Graphics;
import com.sun.prism.RTTexture;
import com.sun.prism.ResourceFactory;
import com.sun.prism.Texture;
import com.sun.prism.camera.PrismCameraImpl;
import com.sun.prism.camera.PrismDefaultCamera;
import com.sun.prism.paint.Color;
import com.sun.prism.paint.Paint;

/**
 * @author Thor Johannesson
 */
public class NGSubScene extends NGNode {

    private int rtWidth, rtHeight;
    private RTTexture rtt;
    // ressolveRTT is a temporary render target to "resolve" a msaa render buffer
    // into a normal color render target.
    // REMIND: resolveRTT could be a single shared scratch rtt
    private RTTexture resolveRTT = null;
    private NGNode root = null;
    private boolean renderSG = true;
    // Depth and antiAliasing are immutable states
    private final boolean depthBuffer;
    private final boolean antiAliasing;

    public NGSubScene(boolean depthBuffer, boolean antiAliasing) {
        this.depthBuffer = depthBuffer;
        this.antiAliasing = antiAliasing;
    }

    private NGSubScene() {
        this(false, false);
    }

    public void setRoot(NGNode root) {
        this.root = root;
    }

    private Paint fillPaint;
    public void setFillPaint(Object paint) {
        fillPaint = (Paint)paint;
    }

    private PrismCameraImpl camera;
    public void setCamera(NGCamera camera) {
        if (camera != null) {
            this.camera = camera.getCameraImpl();
        } else {
            this.camera = PrismDefaultCamera.getInstance();
        }
    }

    public void setWidth(float width) {
        int iWidth = (int)Math.ceil(width);
        if (this.rtWidth != iWidth) {
            this.rtWidth = iWidth;
            geometryChanged();
            invalidateRTT();
        }
    }

    public void setHeight(float height) {
        int iHeight = (int)Math.ceil(height);
        if (this.rtHeight != iHeight) {
            this.rtHeight = iHeight;
            geometryChanged();
            invalidateRTT();
        }
    }

    private Object lights[];

    public Object[] getLights() { return lights; }

    public void setLights(Object[] lights) {
        this.lights = lights;
    }

    public void markContentDirty() {
        visualsChanged();
    }

    @Override
    protected void visualsChanged() {
        renderSG = true;
        super.visualsChanged();
    }

    @Override
    protected void geometryChanged() {
        renderSG = true;
        super.geometryChanged();
    }

    private void invalidateRTT() {
        if (rtt != null) {
            // TODO as possibile optimization by keeping old rtt if SubScene
            // becomes smaller
            rtt.dispose();
            rtt = null;
        }
    }

    @Override
    protected boolean hasOverlappingContents() {
        //TODO verify correctness
        return false;
    }

    private boolean isOpaque = false;
    private void applyBackgroundFillPaint(Graphics g) {
        isOpaque = true;
        if (fillPaint != null) {
            if (fillPaint instanceof Color) {
                Color fillColor = (Color)fillPaint;
                isOpaque = (fillColor.getAlpha() >= 1.0);
                g.clear(fillColor);
            } else {
                if (!fillPaint.isOpaque()) {
                    g.clear();
                    isOpaque = false;
                }
                g.setPaint(fillPaint);
                g.fillRect(0, 0, rtt.getContentWidth(), rtt.getContentHeight());
            }
        } else {
            isOpaque = false;
            // Default is transparent
            g.clear();
        }
    }

    @Override
    protected void renderContent(Graphics g) {
        if (rtt != null) {
            rtt.lock();
            if (rtt.isSurfaceLost()) {
                renderSG = true;
                rtt = null;
            }
        }

        if (renderSG || !root.isClean()) {
            if (rtt == null) {
                ResourceFactory factory = g.getResourceFactory();
                rtt = factory.createRTTexture(rtWidth, rtHeight,
                                              Texture.WrapMode.CLAMP_NOT_NEEDED,
                                              antiAliasing);
            }
            Graphics rttGraphics = rtt.createGraphics();
            rttGraphics.setLights(lights);

            rttGraphics.setDepthBuffer(depthBuffer);
            if (camera != null) {
                rttGraphics.setCamera(camera);
            }
            applyBackgroundFillPaint(rttGraphics);
            rttGraphics.setTransform(BaseTransform.IDENTITY_TRANSFORM);

            root.render(rttGraphics);
            root.clearDirtyTree();
            renderSG = false;
        }
        if (antiAliasing) {
            int x0 = rtt.getContentX();
            int y0 = rtt.getContentY();
            int x1 = x0 + rtt.getContentWidth();
            int y1 = y0 + rtt.getContentHeight();
            if ((isOpaque || g.getCompositeMode() == CompositeMode.SRC)
                    && g.getTransformNoClone().isTranslateOrIdentity()) {
                // Round translation to closest pixel
                int tx = (int)(g.getTransformNoClone().getMxt() + 0.5);
                int ty = (int)(g.getTransformNoClone().getMyt() + 0.5);
                // Blit SubScene directly to scene surface
                g.blit(rtt, null, x0, y0, x1, y1,
                            x0 + tx, y0 + ty, x1 + tx, y1 + ty);
            } else {
                if (resolveRTT != null &&
                        (resolveRTT.getContentWidth() < rtt.getContentWidth() ||
                        (resolveRTT.getContentHeight() < rtt.getContentHeight())))
                {
                    // If msaa rtt is larger than resolve buffer, then dispose
                    resolveRTT.dispose();
                    resolveRTT = null;
                }
                if (resolveRTT == null || resolveRTT.isSurfaceLost()) {
                    resolveRTT = g.getResourceFactory().createRTTexture(rtWidth, rtHeight,
                            Texture.WrapMode.CLAMP_NOT_NEEDED, false);
                } else {
                    resolveRTT.lock();
                }
                g.blit(rtt, resolveRTT, x0, y0, x1, y1,
                        x0, y0, x1, y1);
                g.drawTexture(resolveRTT, rtt.getContentX(), rtt.getContentY(),
                        rtt.getContentWidth(), rtt.getContentHeight());
                resolveRTT.unlock();
            }
        } else {
            g.drawTexture(rtt, rtt.getContentX(), rtt.getContentY(),
                          rtt.getContentWidth(), rtt.getContentHeight());
        }
        rtt.unlock();
    }

    public PrismCameraImpl getCamera() {
        return camera;
    }
}
