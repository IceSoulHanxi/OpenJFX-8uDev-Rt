/*
 * Copyright (c) 2000, 2010, Oracle and/or its affiliates. All rights reserved.
 */
package javafx.scene.input;

import com.sun.javafx.pgstub.StubScene;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

public class TouchEventTest {
    private static final int SANE_BENCHMARK_CYCLES = 1000000;
    private static final int CRAZY_BENCHMARK_CYCLES = 500000;

    private int touched;

    @Test
    public void shouldPassModifiers() {
        Scene scene = createScene();
        Rectangle rect =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        touched = 0;
        rect.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(touched) {
                    case 1:
                        assertEquals(true, event.isShiftDown());
                        assertEquals(false, event.isControlDown());
                        assertEquals(true, event.isAltDown());
                        assertEquals(false, event.isMetaDown());
                        break;
                    case 2:
                        assertEquals(false, event.isShiftDown());
                        assertEquals(true, event.isControlDown());
                        assertEquals(false, event.isAltDown());
                        assertEquals(true, event.isMetaDown());
                        break;
                    case 3:
                        assertEquals(false, event.isShiftDown());
                        assertEquals(true, event.isControlDown());
                        assertEquals(true, event.isAltDown());
                        assertEquals(false, event.isMetaDown());
                        break;
                    default:
                        fail("Wrong touch point id " + event.getTouchPoint().getId());
                }
            }
        });

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, true, false, true, false, true);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.STATIONARY, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, true, false, true, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        assertEquals(3, touched);
    }

    @Test
    public void shouldCountTouchesCorrectly() {
        Scene scene = createScene();
        Rectangle rect =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        touched = 0;
        rect.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(touched) {
                    case 1:
                        assertEquals(1, event.getTouchCount());
                        assertEquals(1, event.getTouchPoints().size());
                        break;
                    case 2:
                    case 3:
                        assertEquals(2, event.getTouchCount());
                        assertEquals(2, event.getTouchPoints().size());
                        break;
                    case 4:
                    case 5:
                        assertEquals(2, event.getTouchCount());
                        assertEquals(2, event.getTouchPoints().size());
                        break;
                    case 6:
                        assertEquals(1, event.getTouchCount());
                        assertEquals(1, event.getTouchPoints().size());
                        break;
                    default:
                        fail("Wrong touch point id " + event.getTouchPoint().getId());
                }
            }
        });

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, true, false, true);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.STATIONARY, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 2, 120, 120, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, true, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.STATIONARY, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 2, 120, 120, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, true, false, true, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        assertEquals(6, touched);
    }

    @Test
    public void shouldGenerateCorrectEventSetIDs() {
        Scene scene = createScene();
        Rectangle rect =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        touched = 0;
        rect.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(touched) {
                    case 1:
                        assertEquals(1, event.getEventSetId());
                        break;
                    case 2:
                    case 3:
                        assertEquals(2, event.getEventSetId());
                        break;
                    case 4:
                    case 5:
                        assertEquals(3, event.getEventSetId());
                        break;
                    case 6:
                        assertEquals(4, event.getEventSetId());
                        break;
                    default:
                        fail("Wrong touch point id " + event.getTouchPoint().getId());
                }
            }
        });

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, true, false, true);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.STATIONARY, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 2, 120, 120, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, true, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.STATIONARY, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 2, 120, 120, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, true, false, true, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        assertEquals(6, touched);
    }

    @Test
    public void shouldReIDTouchPoints() {
        Scene scene = createScene();
        Rectangle rect =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        touched = 0;
        rect.setOnTouchPressed(new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(event.getTouchPoint().getId()) {
                    case 1:
                        assertEquals(110.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(110.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(1, touched);
                        break;
                    case 2:
                        assertEquals(120.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(120.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(2, touched);
                        break;
                    case 3:
                        assertEquals(130.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(130.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(3, touched);
                        break;
                    default:
                        fail("Wrong touch point id " + event.getTouchPoint().getId());
                }
            }
        });

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 3, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1368, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 120, 120, 120, 120);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 152, 130, 130, 130, 130);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        assertEquals(3, touched);
    }

    @Test
    public void shouldNotReuseTouchPointID() {
        Scene scene = createScene();
        Rectangle rect =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        touched = 0;
        rect.setOnTouchPressed(new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(event.getTouchPoint().getId()) {
                    case 1:
                        assertEquals(110.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(110.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(1, touched);
                        break;
                    case 2:
                        assertEquals(120.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(120.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(2, touched);
                        break;
                    case 3:
                        assertEquals(130.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(130.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(3, touched);
                        break;
                    default:
                        fail("Wrong touch point id " + event.getTouchPoint().getId());
                }
            }
        });

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1368, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 120, 120, 120, 120);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.STATIONARY, 1368, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 1, 120, 120, 120, 120);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.STATIONARY, 1368, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 130, 130, 130, 130);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        assertEquals(3, touched);
    }

    @Test
    public void shouldMaintainPressOrder() {
        Scene scene = createScene();
        Rectangle rect =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        touched = 0;
        rect.setOnTouchPressed(new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(event.getTouchPoint().getId()) {
                    case 1:
                        assertEquals(110.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(110.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(1, event.getTouchPoints().get(0).getId());
                        assertEquals(2, event.getTouchPoints().get(1).getId());
                        assertEquals(1, touched);
                        break;
                    case 2:
                        assertEquals(120.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(120.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(1, event.getTouchPoints().get(0).getId());
                        assertEquals(2, event.getTouchPoints().get(1).getId());
                        assertEquals(2, touched);
                        break;
                    case 3:
                        assertEquals(130.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(130.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(1, event.getTouchPoints().get(0).getId());
                        assertEquals(3, event.getTouchPoints().get(1).getId());
                        assertEquals(3, touched);
                        break;
                    default:
                        fail("Wrong touch point id " + event.getTouchPoint().getId());
                }
            }
        });

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1368, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 120, 120, 120, 120);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 1, 120, 120, 120, 120);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.STATIONARY, 1368, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 130, 130, 130, 130);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.STATIONARY, 1368, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        assertEquals(3, touched);
    }

    @Test
    public void shouldMaintainIDMapping() {
        Scene scene = createScene();
        Rectangle rect =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        touched = 0;
        rect.setOnTouchPressed(new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(event.getTouchPoint().getId()) {
                    case 1:
                        assertEquals(110.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(110.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(1, event.getTouchPoints().get(0).getId());
                        assertEquals(2, event.getTouchPoints().get(1).getId());
                        assertEquals(1, touched);
                        break;
                    case 2:
                        assertEquals(120.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(120.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(1, event.getTouchPoints().get(0).getId());
                        assertEquals(2, event.getTouchPoints().get(1).getId());
                        assertEquals(2, touched);
                        break;
                    default:
                        fail("Wrong touch point id " + event.getTouchPoint().getId());
                }
            }
        });

        rect.setOnTouchMoved(new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(event.getTouchPoint().getId()) {
                    case 1:
                        assertEquals(120.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(120.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(1, event.getTouchPoints().get(0).getId());
                        assertEquals(2, event.getTouchPoints().get(1).getId());
                        assertEquals(3, touched);
                        break;
                    case 2:
                        assertEquals(110.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(110.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(1, event.getTouchPoints().get(0).getId());
                        assertEquals(2, event.getTouchPoints().get(1).getId());
                        assertEquals(4, touched);
                        break;
                    default:
                        fail("Wrong touch point id " + event.getTouchPoint().getId());
                }
            }
        });

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1368, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 127, 120, 120, 120, 120);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.MOVED, 127, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.MOVED, 1368, 120, 120, 120, 120);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        assertEquals(4, touched);
    }

    @Test
    public void shouldMaintainIDMappingInDynamicConditions() {
        Scene scene = createScene();
        Rectangle rect =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        touched = 0;
        rect.setOnTouchPressed(new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(event.getTouchPoint().getId()) {
                    case 1:
                        assertEquals(110.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(110.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(1, event.getTouchPoints().get(0).getId());
                        assertEquals(2, event.getTouchPoints().get(1).getId());
                        assertEquals(1, touched);
                        break;
                    case 2:
                        assertEquals(120.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(120.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(1, event.getTouchPoints().get(0).getId());
                        assertEquals(2, event.getTouchPoints().get(1).getId());
                        assertEquals(2, touched);
                        break;
                    case 3:
                        assertEquals(160.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(160.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(2, event.getTouchPoints().get(0).getId());
                        assertEquals(3, event.getTouchPoints().get(1).getId());
                        assertEquals(3, touched);
                        break;
                    default:
                        fail("Wrong touch point id " + event.getTouchPoint().getId());
                }
            }
        });

        rect.setOnTouchMoved(new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(event.getTouchPoint().getId()) {
                    case 2:
                        assertEquals(120.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(120.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(2, event.getTouchPoints().get(0).getId());
                        assertEquals(3, event.getTouchPoints().get(1).getId());
                        assertEquals(4, touched);
                        break;
                    case 3:
                        assertEquals(160.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(160.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(2, event.getTouchPoints().get(0).getId());
                        assertEquals(3, event.getTouchPoints().get(1).getId());
                        assertEquals(5, touched);
                        break;
                    default:
                        fail("Wrong touch point id " + event.getTouchPoint().getId());
                }
            }
        });

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1368, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 127, 120, 120, 120, 120);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.STATIONARY, 127, 120, 120, 120, 120);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 1368, 120, 120, 120, 120);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 11, 160, 160, 160, 160);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.STATIONARY, 127, 120, 120, 120, 120);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.MOVED, 11, 160, 160, 160, 160);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.MOVED, 127, 120, 120, 120, 120);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        assertEquals(5, touched);
    }

    @Test
    public void shouldResetIDsAfterGesture() {
        Scene scene = createScene();
        Rectangle rect =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        touched = 0;
        rect.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(touched) {
                    case 1:
                    case 2:
                        assertEquals(1, event.getEventSetId());
                        assertEquals(touched, event.getTouchPoint().getId());
                        break;
                    case 3:
                    case 4:
                        assertEquals(2, event.getEventSetId());
                        assertEquals(touched - 2, event.getTouchPoint().getId());
                        break;
                    case 5:
                    case 6:
                        assertEquals(1, event.getEventSetId());
                        assertEquals(touched - 4, event.getTouchPoint().getId());
                        break;
                    case 7:
                    case 8:
                        assertEquals(2, event.getEventSetId());
                        assertEquals(touched - 6, event.getTouchPoint().getId());
                        break;
                    default:
                        fail("Wrong touch point id " + event.getTouchPoint().getId());
                }
            }
        });

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 2, 120, 120, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, true, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 2, 120, 120, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 2, 120, 120, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, true, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 2, 120, 120, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();


        assertEquals(8, touched);
    }

    @Test
    public void touchPointsShouldContainTouchPoint() {
        Scene scene = createScene();
        Rectangle rect =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        touched = 0;
        rect.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(touched) {
                    case 1:
                    case 3:
                        assertSame(event.getTouchPoint(), event.getTouchPoints().get(0));
                        break;
                    case 2:
                    case 4:
                        assertSame(event.getTouchPoint(), event.getTouchPoints().get(1));
                        break;
                    default:
                        fail("Wrong touch point id " + event.getTouchPoint().getId());
                }
            }
        });

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 2, 120, 120, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 2, 120, 120, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        assertEquals(4, touched);
    }

    @Test
    public void touchPointsShouldHaveCorrectTarget() {
        Scene scene = createScene();

        final Rectangle rect1 =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        final Rectangle rect2 =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(1);

        touched = 0;
        rect1.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                assertSame(rect1, event.getTouchPoint().getTarget());
                assertSame(rect2, event.getTouchPoints().get(1).getTarget());
            }
        });
        rect2.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                assertSame(rect2, event.getTouchPoint().getTarget());
                assertSame(rect1, event.getTouchPoints().get(0).getTarget());
            }
        });

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 2, 220, 220, 220, 220);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 2, 220, 220, 220, 220);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        assertEquals(4, touched);
    }

    @Test
    public void testTouchPointsBelongsTo() {
        final Scene scene = createScene();

        final Rectangle rect1 =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        final Rectangle rect2 =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(1);

        touched = 0;
        rect1.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                assertTrue(event.getTouchPoint().belongsTo(rect1));
                assertTrue(event.getTouchPoint().belongsTo(scene.getRoot()));
                assertTrue(event.getTouchPoint().belongsTo(scene));
                assertFalse(event.getTouchPoint().belongsTo(rect2));

                assertFalse(event.getTouchPoints().get(1).belongsTo(rect1));
                assertTrue(event.getTouchPoints().get(1).belongsTo(scene.getRoot()));
                assertTrue(event.getTouchPoints().get(1).belongsTo(scene));
                assertTrue(event.getTouchPoints().get(1).belongsTo(rect2));
            }
        });
        rect2.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                assertTrue(event.getTouchPoint().belongsTo(rect2));
                assertTrue(event.getTouchPoint().belongsTo(scene.getRoot()));
                assertTrue(event.getTouchPoint().belongsTo(scene));
                assertFalse(event.getTouchPoint().belongsTo(rect1));

                assertFalse(event.getTouchPoints().get(0).belongsTo(rect2));
                assertTrue(event.getTouchPoints().get(0).belongsTo(scene.getRoot()));
                assertTrue(event.getTouchPoints().get(0).belongsTo(scene));
                assertTrue(event.getTouchPoints().get(0).belongsTo(rect1));
            }
        });

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 2, 220, 220, 220, 220);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 2, 220, 220, 220, 220);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        assertEquals(4, touched);
    }

    @Test
    public void shouldPickAndGrabTouchPoints() {
        Scene scene = createScene();
        final Rectangle rect1 =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        final Rectangle rect2 =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(1);

        touched = 0;
        rect1.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(touched) {
                    case 1:
                        assertEquals(1, event.getTouchPoint().getId());
                        assertEquals(150.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(155.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(150.0, event.getTouchPoint().getSceneX(), 0.0001);
                        assertEquals(155.0, event.getTouchPoint().getSceneY(), 0.0001);
                        assertEquals(1150.0, event.getTouchPoint().getScreenX(), 0.0001);
                        assertEquals(1155.0, event.getTouchPoint().getScreenY(), 0.0001);
                        break;
                    case 3:
                        assertEquals(1, event.getTouchPoint().getId());
                        assertEquals(250.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(255.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(250.0, event.getTouchPoint().getSceneX(), 0.0001);
                        assertEquals(255.0, event.getTouchPoint().getSceneY(), 0.0001);
                        assertEquals(1250.0, event.getTouchPoint().getScreenX(), 0.0001);
                        assertEquals(1255.0, event.getTouchPoint().getScreenY(), 0.0001);
                        break;
                    default:
                        fail("Wrong touch point delivery");
                }
            }
        });

        rect2.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(touched) {
                    case 2:
                        assertEquals(2, event.getTouchPoint().getId());
                        assertEquals(260.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(265.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(260.0, event.getTouchPoint().getSceneX(), 0.0001);
                        assertEquals(265.0, event.getTouchPoint().getSceneY(), 0.0001);
                        assertEquals(1260.0, event.getTouchPoint().getScreenX(), 0.0001);
                        assertEquals(1265.0, event.getTouchPoint().getScreenY(), 0.0001);
                        break;
                    case 4:
                        assertEquals(2, event.getTouchPoint().getId());
                        assertEquals(160.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(165.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(160.0, event.getTouchPoint().getSceneX(), 0.0001);
                        assertEquals(165.0, event.getTouchPoint().getSceneY(), 0.0001);
                        assertEquals(1160.0, event.getTouchPoint().getScreenX(), 0.0001);
                        assertEquals(1165.0, event.getTouchPoint().getScreenY(), 0.0001);
                        break;
                    default:
                        fail("Wrong touch point delivery");
                }
            }
        });

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 3, 150, 155, 1150, 1155);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 4, 260, 265, 1260, 1265);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.MOVED, 3, 250, 255, 1250, 1255);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.MOVED, 4, 160, 165, 1160, 1165);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        assertEquals(4, touched);
    }

    @Test
    public void ungrabShouldEnablePickingForTouchPoints() {
        Scene scene = createScene();
        Rectangle rect1 =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        Rectangle rect2 =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(1);

        touched = 0;
        rect1.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(touched) {
                    case 1:
                        assertEquals(1, event.getTouchPoint().getId());
                        assertEquals(150.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(155.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(150.0, event.getTouchPoint().getSceneX(), 0.0001);
                        assertEquals(155.0, event.getTouchPoint().getSceneY(), 0.0001);
                        assertEquals(1150.0, event.getTouchPoint().getScreenX(), 0.0001);
                        assertEquals(1155.0, event.getTouchPoint().getScreenY(), 0.0001);
                        event.getTouchPoint().ungrab();
                        break;
                    case 4:
                        assertEquals(2, event.getTouchPoint().getId());
                        assertEquals(160.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(165.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(160.0, event.getTouchPoint().getSceneX(), 0.0001);
                        assertEquals(165.0, event.getTouchPoint().getSceneY(), 0.0001);
                        assertEquals(1160.0, event.getTouchPoint().getScreenX(), 0.0001);
                        assertEquals(1165.0, event.getTouchPoint().getScreenY(), 0.0001);
                        break;
                    default:
                        fail("Wrong touch point delivery");
                }
            }
        });

        rect2.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(touched) {
                    case 2:
                        assertEquals(2, event.getTouchPoint().getId());
                        assertEquals(260.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(265.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(260.0, event.getTouchPoint().getSceneX(), 0.0001);
                        assertEquals(265.0, event.getTouchPoint().getSceneY(), 0.0001);
                        assertEquals(1260.0, event.getTouchPoint().getScreenX(), 0.0001);
                        assertEquals(1265.0, event.getTouchPoint().getScreenY(), 0.0001);
                        event.getTouchPoint().ungrab();
                        break;
                    case 3:
                        assertEquals(1, event.getTouchPoint().getId());
                        assertEquals(250.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(255.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(250.0, event.getTouchPoint().getSceneX(), 0.0001);
                        assertEquals(255.0, event.getTouchPoint().getSceneY(), 0.0001);
                        assertEquals(1250.0, event.getTouchPoint().getScreenX(), 0.0001);
                        assertEquals(1255.0, event.getTouchPoint().getScreenY(), 0.0001);
                        break;
                    default:
                        fail("Wrong touch point delivery");
                }
            }
        });

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 3, 150, 155, 1150, 1155);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 4, 260, 265, 1260, 1265);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.MOVED, 3, 250, 255, 1250, 1255);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.MOVED, 4, 160, 165, 1160, 1165);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        assertEquals(4, touched);
    }

    @Test
    public void grabWithArgShouldAffectDelivery() {
        Scene scene = createScene();
        final Rectangle rect1 =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        final Rectangle rect2 =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(1);

        touched = 0;
        rect1.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(touched) {
                    case 1:
                        assertEquals(1, event.getTouchPoint().getId());
                        assertEquals(150.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(155.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(150.0, event.getTouchPoint().getSceneX(), 0.0001);
                        assertEquals(155.0, event.getTouchPoint().getSceneY(), 0.0001);
                        assertEquals(1150.0, event.getTouchPoint().getScreenX(), 0.0001);
                        assertEquals(1155.0, event.getTouchPoint().getScreenY(), 0.0001);
                        event.getTouchPoints().get(1).grab(rect1);
                        break;
                    case 3:
                        assertEquals(1, event.getTouchPoint().getId());
                        assertEquals(250.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(255.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(250.0, event.getTouchPoint().getSceneX(), 0.0001);
                        assertEquals(255.0, event.getTouchPoint().getSceneY(), 0.0001);
                        assertEquals(1250.0, event.getTouchPoint().getScreenX(), 0.0001);
                        assertEquals(1255.0, event.getTouchPoint().getScreenY(), 0.0001);
                        break;
                    case 4:
                        assertEquals(2, event.getTouchPoint().getId());
                        assertEquals(160.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(165.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(160.0, event.getTouchPoint().getSceneX(), 0.0001);
                        assertEquals(165.0, event.getTouchPoint().getSceneY(), 0.0001);
                        assertEquals(1160.0, event.getTouchPoint().getScreenX(), 0.0001);
                        assertEquals(1165.0, event.getTouchPoint().getScreenY(), 0.0001);
                        break;
                    default:
                        fail("Wrong touch point delivery");
                }
            }
        });

        rect2.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(touched) {
                    case 2:
                        assertEquals(2, event.getTouchPoint().getId());
                        assertEquals(260.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(265.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(260.0, event.getTouchPoint().getSceneX(), 0.0001);
                        assertEquals(265.0, event.getTouchPoint().getSceneY(), 0.0001);
                        assertEquals(1260.0, event.getTouchPoint().getScreenX(), 0.0001);
                        assertEquals(1265.0, event.getTouchPoint().getScreenY(), 0.0001);
                        break;
                    default:
                        fail("Wrong touch point delivery");
                }
            }
        });

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 3, 150, 155, 1150, 1155);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 4, 260, 265, 1260, 1265);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.MOVED, 3, 250, 255, 1250, 1255);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.MOVED, 4, 160, 165, 1160, 1165);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        assertEquals(4, touched);
    }

    @Test
    public void grabWithoutArgShouldAffectDelivery() {
        Scene scene = createScene();
        final Rectangle rect1 =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        final Rectangle rect2 =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(1);

        touched = 0;
        rect1.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(touched) {
                    case 1:
                        assertEquals(1, event.getTouchPoint().getId());
                        assertEquals(150.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(155.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(150.0, event.getTouchPoint().getSceneX(), 0.0001);
                        assertEquals(155.0, event.getTouchPoint().getSceneY(), 0.0001);
                        assertEquals(1150.0, event.getTouchPoint().getScreenX(), 0.0001);
                        assertEquals(1155.0, event.getTouchPoint().getScreenY(), 0.0001);
                        event.getTouchPoints().get(1).grab();
                        break;
                    case 3:
                        assertEquals(1, event.getTouchPoint().getId());
                        assertEquals(250.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(255.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(250.0, event.getTouchPoint().getSceneX(), 0.0001);
                        assertEquals(255.0, event.getTouchPoint().getSceneY(), 0.0001);
                        assertEquals(1250.0, event.getTouchPoint().getScreenX(), 0.0001);
                        assertEquals(1255.0, event.getTouchPoint().getScreenY(), 0.0001);
                        break;
                    case 4:
                        assertEquals(2, event.getTouchPoint().getId());
                        assertEquals(160.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(165.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(160.0, event.getTouchPoint().getSceneX(), 0.0001);
                        assertEquals(165.0, event.getTouchPoint().getSceneY(), 0.0001);
                        assertEquals(1160.0, event.getTouchPoint().getScreenX(), 0.0001);
                        assertEquals(1165.0, event.getTouchPoint().getScreenY(), 0.0001);
                        break;
                    default:
                        fail("Wrong touch point delivery");
                }
            }
        });

        rect2.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                touched++;
                switch(touched) {
                    case 2:
                        assertEquals(2, event.getTouchPoint().getId());
                        assertEquals(260.0, event.getTouchPoint().getX(), 0.0001);
                        assertEquals(265.0, event.getTouchPoint().getY(), 0.0001);
                        assertEquals(260.0, event.getTouchPoint().getSceneX(), 0.0001);
                        assertEquals(265.0, event.getTouchPoint().getSceneY(), 0.0001);
                        assertEquals(1260.0, event.getTouchPoint().getScreenX(), 0.0001);
                        assertEquals(1265.0, event.getTouchPoint().getScreenY(), 0.0001);
                        break;
                    default:
                        fail("Wrong touch point delivery");
                }
            }
        });

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 3, 150, 155, 1150, 1155);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 4, 260, 265, 1260, 1265);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.MOVED, 3, 250, 255, 1250, 1255);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.MOVED, 4, 160, 165, 1160, 1165);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        assertEquals(4, touched);
    }

    @Test
    public void shouldIgnoreIndirectTouchEvents() {
        Scene scene = createScene();
        Rectangle rect =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        rect.addEventHandler(TouchEvent.ANY, new EventHandler<TouchEvent>() {
            @Override public void handle(TouchEvent event) {
                fail("Delivered indirect touch event");
            }
        });

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, false, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, false, true, false, true, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.RELEASED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();
    }

    @Test(expected=RuntimeException.class)
    public void shouldThrowREOnWrongSmallId() {
        Scene scene = createScene();
        Rectangle rect =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.MOVED, 2, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();
    }

    @Test(expected=RuntimeException.class)
    public void shouldThrowREOnWrongLargeId() {
        Scene scene = createScene();
        Rectangle rect =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1368, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.MOVED, 127, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();
    }

    @Test(expected=RuntimeException.class)
    public void shouldThrowREOnBigTPNumber() {
        Scene scene = createScene();
        Rectangle rect =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1368, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();
    }

    @Test(expected=RuntimeException.class)
    public void shouldThrowREOnSmallTPNumber() {
        Scene scene = createScene();
        Rectangle rect =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 2, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1368, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();
    }

    @Test(expected=RuntimeException.class)
    public void shouldThrowREOnLostRelease() {
        Scene scene = createScene();
        Rectangle rect =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1368, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, true, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();
    }

    @Ignore("In Scene is workaround for RT-20139 which makes this test fail")
    @Test(expected=RuntimeException.class)
    public void shouldThrowREOnLostIndirectRelease() {
        Scene scene = createScene();
        Rectangle rect =
                (Rectangle) scene.getRoot().getChildrenUnmodifiable().get(0);

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, false, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1368, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();

        ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                System.currentTimeMillis(), 1, false, false, false, false, false);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                TouchPoint.State.PRESSED, 1, 110, 110, 110, 110);
        ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();
    }

    private Scene createScene() {
        final Group root = new Group();

        final Scene scene = new Scene(root, 400, 400);

        Rectangle rect = new Rectangle(100, 100, 100, 100);
        Rectangle rect2 = new Rectangle(200, 200, 100, 100);

        root.getChildren().addAll(rect, rect2);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        return scene;
    }

    @Test
    @Ignore("This is a benchmark, not any functional test. Run it individually if you wish.")
    public void saneOrderingBenchmark() {
        long[] ids = new long[] { 2, 3, 4, 5, 6 };
        boolean[] active = new boolean[] { false, false, false, false, false };
        int count = 0;
        int tick = 5;
        int available = 5;
        Random rand = new Random();

        Scene scene = createScene();


        int ticker = 0;
        int added = -1;
        int removed = -1;
        long timer = System.currentTimeMillis();
        for (int i = 0; i < SANE_BENCHMARK_CYCLES; i++) {
            ticker++;
            if (ticker == tick) {
                ticker = 0;

                boolean up;
                if (count == available) {
                    up = false;
                } else if (count == 0) {
                    up = true;
                } else {
                    up = Math.random() > 0.4;
                }

                if (up) {
                    for (int j = 0; j < available; j++) {
                        if (!active[j]) {
                            active[j] = true;
                            added = j;
                            count++;
                            break;
                        }
                    }
                } else {
                    int which = rand.nextInt(count);
                    int k = 0;
                    for (int j = 0; j < available; j++) {
                        if (active[j]) {
                            k++;
                            if (k == which) {
                                active[j] = false;
                                removed = j;
                                count--;
                                break;
                            }
                        }
                    }
                }
            }

            int reporting = count + (removed >= 0 ? 1 : 0);

            ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                    System.currentTimeMillis(), reporting, true, false, false, false, false);

            for (int j = 0; j < available; j++) {
                if (active[j] || removed == j) {
                    TouchPoint.State state = TouchPoint.State.MOVED;
                    if (added == j) {
                        state = TouchPoint.State.PRESSED;
                    } else if (removed == j) {
                        state = TouchPoint.State.RELEASED;
                    } else {
                    }

                    ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                            state, ids[j], 150, 150, 150, 150);
                }
            }

            ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();
            removed = -1;
            added = -1;
        }
        long timer2 = System.currentTimeMillis();
        System.out.println("*************************************************");
        System.out.println("Benchmark1 time: " + (timer2 - timer));
        System.out.println("*************************************************");
        System.out.println("");
    }

    @Test
    @Ignore("This is a benchmark, not any functional test. Run it individually if you wish.")
    public void crazyOrderingBenchmark() {
        long[] ids = new long[] { 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000 };
        boolean[] active = new boolean[] { false, false, false, false, false,
                false, false, false, false, false };
        int count = 0;
        int tick = 5;
        int available = 10;
        Random rand = new Random();

        Scene scene = createScene();


        int ticker = 0;
        int added = -1;
        int removed = -1;
        long timer = System.currentTimeMillis();
        for (int i = 0; i < CRAZY_BENCHMARK_CYCLES; i++) {
            ticker++;
            if (ticker == tick) {
                ticker = 0;

                boolean up;
                if (count == available) {
                    up = false;
                } else if (count == 0) {
                    up = true;
                } else {
                    up = Math.random() > 0.4;
                }

                if (up) {
                    int which = rand.nextInt(available - count);
                    int k = 0;
                    for (int j = 0; j < available; j++) {
                        if (!active[j]) {
                            k++;
                            if (k == which) {
                                active[j] = true;
                                added = j;
                                count++;
                                ids[j] = Math.abs(rand.nextLong());
                                if (ids[j] == 0) {
                                    ids[j] = 1;
                                }
                                break;
                            }
                        }
                    }
                } else {
                    int which = rand.nextInt(count);
                    int k = 0;
                    for (int j = 0; j < available; j++) {
                        if (active[j]) {
                            k++;
                            if (k == which) {
                                active[j] = false;
                                removed = j;
                                count--;
                                break;
                            }
                        }
                    }
                }
            }

            int reporting = count + (removed >= 0 ? 1 : 0);

            ((StubScene) scene.impl_getPeer()).getListener().touchEventBegin(
                    System.currentTimeMillis(), reporting, true, false, false, false, false);

            for (int j = 0; j < available; j++) {
                if (active[j] || removed == j) {
                    TouchPoint.State state = TouchPoint.State.MOVED;
                    if (added == j) {
                        state = TouchPoint.State.PRESSED;
                    } else if (removed == j) {
                        state = TouchPoint.State.RELEASED;
                    }

                    ((StubScene) scene.impl_getPeer()).getListener().touchEventNext(
                            state, ids[j], 150, 150, 150, 150);
                }
            }

            ((StubScene) scene.impl_getPeer()).getListener().touchEventEnd();
            removed = -1;
            added = -1;
        }
        long timer2 = System.currentTimeMillis();
        System.out.println("*************************************************");
        System.out.println("Benchmark2 time: " + (timer2 - timer));
        System.out.println("*************************************************");
        System.out.println("");
    }

}
